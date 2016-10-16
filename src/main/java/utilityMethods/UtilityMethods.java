package utilityMethods;

import com.aerospike.client.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class UtilityMethods {

    public static void aerospikeInsertIntoSet(String username, String message)
    {
        String key = getFormattedDate("dd-MM-dd HH:mm:ss:SSS");
        AerospikeClient client = new AerospikeClient("127.0.0.1", 3000);

        Key primaryKey = new Key("test", "thingy2", key);
        Bin user = new Bin("USER", username);
        Bin userMessage = new Bin("MESSAGE", message);

        // Write a record
        client.put(null, primaryKey, user, userMessage);

        client.close();
    }

    //dd-MM-dd HH:mm:ss:SSS
    private static String getFormattedDate(String format)
    {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

}
