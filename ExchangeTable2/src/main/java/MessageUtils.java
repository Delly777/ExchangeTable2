public class MessageUtils {

    public static String getExpectedMessage(){

        String expectedMessage ="+-------------------------+-----------------------------+\n" +
                "\n" +
                "|Key                      |           Values            |           \n" +
                "\n" +
                "+-------------------------+-----------------------------+\n" +
                "\n" +
                "|account                  |            BATS             |\n" +
                "\n" +
                "|clordid                  |            None             |\n" +
                "\n" +
                "|custorderhandlinginst    |              Y              |\n" +
                "\n" +
                "|executionid              |         S100000FDR          |\n" +
                "\n" +
                "|executiontype            |              4              |\n" +
                "\n" +
                "|lastshares               |              0              |\n" +
                "\n" +
                "|operatorid               |             148             |\n" +
                "\n" +
                "|orderid                  |        172H1JDVCPVW         |\n" +
                "\n" +
                "|orderquantity            |             10              |\n" +
                "\n" +
                "|price                    |            11.00            |\n" +
                "\n" +
                "|securitytype             |             FUT             |\n" +
                "\n" +
                "|sequencenumber           |              0              |\n" +
                "\n" +
                "|side                     |              2              |\n" +
                "\n" +
                "|symbol                   |           000001            |\n" +
                "\n" +
                "|targetcomputerid         |            FOOA             |\n" +
                "\n" +
                "|targetsubid              |             ''              |\n" +
                "\n" +
                "|transacttime             |            None             |\n" +
                "\n" +
                "+-------------------------+-----------------------------+";

        return expectedMessage;
    }

    public static String getActualMessage(){

        String actualMessage = "+-------------------------+-----------------------------+\n" +
                "\n" +
                "|Key                      |           Values            |\n" +
                "\n" +
                "+-------------------------+-----------------------------+\n" +
                "\n" +
                "|account                  |            BATS             |\n" +
                "\n" +
                "|clordid                  |    restingSellOrder_GVB     |\n" +
                "\n" +
                "|custorderhandlinginst    |              Y              |\n" +
                "\n" +
                "|executionid              |         S100000FE0          |\n" +
                "\n" +
                "|executiontype            |              4              |\n" +
                "\n" +
                "|lastshares               |              0              |\n" +
                "\n" +
                "|operatorid               |             CFE             |\n" +
                "\n" +
                "|orderid                  |        172H1JDVCPVW         |\n" +
                "\n" +
                "|orderquantity            |             10              |\n" +
                "\n" +
                "|price                    |            11.00            |\n" +
                "\n" +
                "|securitytype             |             FUT             |\n" +
                "\n" +
                "|sequencenumber           |            1822             |\n" +
                "\n" +
                "|side                     |              2              |\n" +
                "\n" +
                "|symbol                   |           000001            |\n" +
                "\n" +
                "|targetcomputerid         |            FOOA             |\n" +
                "\n" +
                "|targetsubid              |            0001             |\n" +
                "\n" +
                "|transacttime             | 2022-02-02T15:12:02.979000  |\n" +
                "\n" +
                "|                     |              2              |\n" +
                "\n" +
                "|symbol                   |                       |\n" +
                "\n" +
                "|targetcomputerid         |            FOOA             |\n" +
                "\n" +
                "|something else              |            0001             |\n" +
                "\n" +
                "|             | 2022-02-02T15:12:02.979000  |\n" +
                "\n" +
                "+-------------------------+-----------------------------+";

        return actualMessage;
    }
}
