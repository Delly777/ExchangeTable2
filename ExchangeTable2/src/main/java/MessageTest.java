import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class MessageTest {

    public static void main(String[] args) throws IOException {

        String actualMessage = MessageUtils.getActualMessage();

        String expectedMessage = MessageUtils.getExpectedMessage();

        Map<String,List<String>> differences = valueComparator(expectedMessage, actualMessage);

        System.out.println(differences);

        pushToExcel(differences);

    }

    public static Map<String, List<String>> valueComparator(String expectedMessage, String actualMessage) {

        Map<String, String> expectedMap = extractKeyValue(expectedMessage); //expected key and value
        Map<String, String> actualMap = extractKeyValue(actualMessage); //actual key and value

        Map<String, List<String>> differences = new HashMap<>();
        List<String> notes = new ArrayList<>(); // missing keys
        List<String> nonMatchingKeys = new ArrayList<>(); // different keys - same value

        // get all keys to have all possible scenarios
        Set<String> allKeys = getAllKeys(expectedMap, actualMap);

        // Check for differences and missing keys in the expected map

        for (String key : allKeys) {
            String expectedValue = expectedMap.get(key);
            String actualValue = actualMap.get(key);

            // Key missing in actual map
            if (!actualMap.containsKey(key)) {
                String nonMatchingKey = findKeyByValue(actualMap, expectedValue);
                if (nonMatchingKey != null) {
                    nonMatchingKeys.add(String.format("Expected key '%s' not found, but similar value found under key '%s'.", key, nonMatchingKey));
                } else {
                    notes.add("Missing key in Actual message: " + key);
                }
            }
            // Key missing in expected map
            else if (!expectedMap.containsKey(key)) {
                String nonMatchingKey = findKeyByValue(expectedMap, actualValue);
                if (nonMatchingKey != null) {
                    nonMatchingKeys.add(String.format("Actual key '%s' not found, but similar value found under key '%s'.", key, nonMatchingKey));
                } else {
                    notes.add("Extra key in Actual message: " + key);
                }
            }
            // Key exists in both, but values differ
            else if (!Objects.equals(expectedValue, actualValue)) {
                differences.put(key, Arrays.asList(expectedValue, actualValue));
            }
        }

        differences.put("Notes", notes);
        differences.put("NonMatchingKeys", nonMatchingKeys);

        return differences;
    }

    public static Map<String, String> extractKeyValue(String message) {
        String[] lines = message.strip().split("\n");
        Map<String, String> map = new LinkedHashMap<>(); // Keep order
        Map<String, Integer> keyOccurrences = new LinkedHashMap<>(); // Track occurrences of each key

        for (String line : lines) {
            String[] values = line.split("\\|");

            if (values.length >= 3) {
                if (values[1].strip().equals("Key") && values[2].strip().equals("Values")) {
                    continue; // Skip header line
                }

                String key = values[1].strip().equals("") ? "*NULL*" : values[1].strip();
                String value = values[2].strip();

                // Check for duplicate keys and append a counter if necessary
                if (map.containsKey(key)) {
                    // Increase the occurrence count for this key
                    int count = keyOccurrences.getOrDefault(key, 1);
                    keyOccurrences.put(key, count + 1);

                    // Append the occurrence count to make the key unique
                    key = key + "*duplicate-" + count + "*";
                } else {
                    // If it's the first occurrence, just add it to the occurrences map
                    keyOccurrences.put(key, 1);
                }

                map.put(key, value);
            }
        }

        return map;
    }

    public static Set<String> getAllKeys(Map<String, String> expectedValue, Map<String, String> actualValue) {

        Set<String> allKeys = new LinkedHashSet<>(actualValue.keySet());
        allKeys.addAll(expectedValue.keySet());

        return allKeys;
    }

    private static String findKeyByValue(Map<String, String> map, String value) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static void pushToExcel(Map<String,List<String>> diff) throws IOException {

        FileInputStream inputStream = new FileInputStream("/Users/delly/IdeaProjects/ExchangeTable2/src/test/java/testData/diffExchangeTable.xlsx");

        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet worksheet = workbook.getSheet("Sheet1");
        XSSFRow currentRow = worksheet.createRow(0);
        currentRow.createCell(0).setCellValue("Key");
        currentRow.createCell(1).setCellValue("Expected Message");
        currentRow.createCell(2).setCellValue("Actual Message");
        currentRow.createCell(3).setCellValue("NonMatchingKeys");
        currentRow.createCell(4).setCellValue("Notes");

        int rowNum = 1;

        for (String key : diff.keySet()) {

            currentRow = worksheet.createRow(rowNum);

            if (!key.equals("NonMatchingKeys") && !key.equals("Notes")) {

                for (int i = 0; i < diff.get(key).size(); i++) {
                    currentRow.createCell(0).setCellValue(key);
                    currentRow.createCell(i + 1).setCellValue(diff.get(key).get(i));
                }
                rowNum++;
            }
        }

        for (int i = 0; i < diff.get("NonMatchingKeys").size(); i++) {
                currentRow= worksheet.getRow(i+1);
                currentRow.createCell(3).setCellValue(diff.get("NonMatchingKeys").get(i));
            }

        for (int i = 0; i < diff.get("Notes").size(); i++) {

                currentRow= worksheet.getRow(i+1);
                currentRow.createCell(4).setCellValue(diff.get("Notes").get(i));
        }

        inputStream.close();

        // save changes in Excel file
        try (FileOutputStream outputStream = new FileOutputStream("/Users/delly/IdeaProjects/ExchangeTable2/src/test/java/testData/diffExchangeTable.xlsx")) {
            workbook.write(outputStream);
        }

        workbook.close();
    }

}
