import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class UnitTest {

    @Test
    public void testValueComparatorWithMatchingValues() {
        String expectedMessage = "+-------------------------+-----------------------------+\n" +
                "\n" +
                "|Key                      |           Values            |           \n" +
                "\n" +
                "+-------------------------+-----------------------------+\n" +
                "\n" +
                "|account                  |            BATS             |\n" +
                "\n" +
                "|clordid                  |            None             |\n" +
                "\n" +
                "+-------------------------+-----------------------------+";
        String actualMessage = "+-------------------------+-----------------------------+\n" +
                "\n" +
                "|Key                      |           Values            |           \n" +
                "\n" +
                "+-------------------------+-----------------------------+\n" +
                "\n" +
                "|account                  |            BATS             |\n" +
                "\n" +
                "|clordid                  |            None             |\n" +
                "\n" +
                "+-------------------------+-----------------------------+";
        Map<String, List<String>> differences = MessageTest.valueComparator(expectedMessage, actualMessage);
        assertTrue(differences.get("Notes").isEmpty());
        assertTrue(differences.get("NonMatchingKeys").isEmpty());
        assertEquals(0, differences.size() - 2); // Subtracting notes and nonMatchingKeys entries
    }

    @Test
    public void testValueComparatorWithNonMatchingValues() {
        String expectedMessage = "+-------------------------+-----------------------------+\n" +
                "\n" +
                "|Key                      |           Values            |           \n" +
                "\n" +
                "+-------------------------+-----------------------------+\n" +
                "\n" +
                "|account                  |            BATS             |\n" +
                "\n" +
                "|clordid                  |            None             |\n" +
                "\n" +
                "+-------------------------+-----------------------------+";
        String actualMessage = "+-------------------------+-----------------------------+\n" +
                "\n" +
                "|Key                      |           Values            |           \n" +
                "\n" +
                "+-------------------------+-----------------------------+\n" +
                "\n" +
                "|account                  |            NYSE             |\n" +
                "\n" +
                "|clordid                  |            123             |\n" +
                "\n" +
                "+-------------------------+-----------------------------+";
        Map<String, List<String>> differences = MessageTest.valueComparator(expectedMessage, actualMessage);
        assertEquals(2, differences.size() - 2); // Subtracting notes and nonMatchingKeys entries
        assertEquals(List.of("BATS", "NYSE"), differences.get("account"));
        assertEquals(List.of("None", "123"), differences.get("clordid"));
    }

    @Test
    public void testValueComparatorWithMissingAndExtraKeys() {
        String expectedMessage = "+-------------------------+-----------------------------+\n" +
                "\n" +
                "|Key                      |           Values            |           \n" +
                "\n" +
                "+-------------------------+-----------------------------+\n" +
                "\n" +
                "|account                  |            BATS             |\n" +
                "\n" +
                "|clordid                  |            None             |\n" +
                "\n" +
                "+-------------------------+-----------------------------+";
        String actualMessage = "+-------------------------+-----------------------------+\n" +
                "\n" +
                "|Key                      |           Values            |           \n" +
                "\n" +
                "+-------------------------+-----------------------------+\n" +
                "\n" +
                "|account                  |            BATS             |\n" +
                "\n" +
                "|operatorid                  |            CFE             |\n" +
                "\n" +
                "+-------------------------+-----------------------------+";
        Map<String, List<String>> differences = MessageTest.valueComparator(expectedMessage, actualMessage);
        assertTrue(differences.containsKey("Notes"));
        assertTrue(differences.get("Notes").contains("Missing key in Actual message: clordid"));
        assertTrue(differences.get("Notes").contains("Extra key in Actual message: operatorid"));
    }

    @Test
    public void testValueComparatorWithDuplicateKeys() {
        String expectedMessage = "+-------------------------+-----------------------------+\n" +
                "\n" +
                "|Key                      |           Values            |           \n" +
                "\n" +
                "+-------------------------+-----------------------------+\n" +
                "\n" +
                "|account                  |            BATS             |\n" +
                "\n" +
                "|account                  |            None             |\n" +
                "\n" +
                "+-------------------------+-----------------------------+";
        String actualMessage = "+-------------------------+-----------------------------+\n" +
                "\n" +
                "|Key                      |           Values            |           \n" +
                "\n" +
                "+-------------------------+-----------------------------+\n" +
                "\n" +
                "|account                  |            BATS             |\n" +
                "\n" +
                "|account                  |            NYSE             |\n" +
                "\n" +
                "+-------------------------+-----------------------------+";
        Map<String, List<String>> differences = MessageTest.valueComparator(expectedMessage, actualMessage);
        assertTrue(differences.containsKey("account*duplicate-1*"));
        assertEquals(List.of("None", "NYSE"), differences.get("account*duplicate-1*"));
    }
//    @Test
//    public void testWithEmptyKeyValues() {
//        String expectedMessage = "+-------------------------+-----------------------------+\n" +
//                "\n" +
//                "|Key                      |           Values            |           \n" +
//                "\n" +
//                "+-------------------------+-----------------------------+\n" +
//                "\n" +
//                "|account                  |            BATS             |\n" +
//                "\n" +
//                "|                  |            None             |\n" +
//                "\n" +
//                "+-------------------------+-----------------------------+";
//        String actualMessage = "+-------------------------+-----------------------------+\n" +
//                "\n" +
//                "|Key                      |           Values            |           \n" +
//                "\n" +
//                "+-------------------------+-----------------------------+\n" +
//                "\n" +
//                "|account                  |            BATS             |\n" +
//                "\n" +
//                "|                  |            Unexpected             |\n" +
//                "\n" +
//                "+-------------------------+-----------------------------+";
//        Map<String, List<String>> differences = MessageTest.valueComparator(expectedMessage, actualMessage);
////        assertTrue(differences.containsKey("*NULL-1*"));
////        assertEquals(List.of("None", "Unexpected"), differences.get("*NULL-1*"));
//
//    }

    @Test
    public void testMessageParser(){
        String message = "+-------------------------+-----------------------------+\n" +
                "\n" +
                "|Key                      |           Values            |           \n" +
                "\n" +
                "+-------------------------+-----------------------------+\n" +
                "\n" +
                "|account                  |            BATS             |\n" +
                "\n" +
                "|clordid                  |            None             |\n" +
                "\n" +
                "+-------------------------+-----------------------------+";

       Map<String,String> map =  MessageTest.extractKeyValue(message);
        assertFalse(map.containsKey("Key"));
        assertEquals(map.size(),2);

    }
    @Test
    public void testMessageParserWithEmptyKey(){
        String message = "+-------------------------+-----------------------------+\n" +
                "\n" +
                "|Key                      |           Values            |           \n" +
                "\n" +
                "+-------------------------+-----------------------------+\n" +
                "\n" +
                "|account                  |            BATS             |\n" +
                "\n" +
                "|                  |            None             |\n" +
                "\n" +
                "+-------------------------+-----------------------------+";

        Map<String,String> map =  MessageTest.extractKeyValue(message);
        assertTrue(map.containsKey("*NULL*"));
        assertEquals(map.size(),2);
    }
}
