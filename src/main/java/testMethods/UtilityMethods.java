package testMethods;

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

    public static String getInput(){

        Scanner user_input = new Scanner(System.in);
        String input = "";
        try
        {
            input = user_input.nextLine();
            //user_input.nextLine();
        }
        catch(Error e)
        {
            System.out.println("lolwot: " + e);
        }

        return input;
    }

    //dd-MM-dd HH:mm:ss:SSS
    private static String getFormattedDate(String format)
    {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

//    String username = "";
//    String message = "";
//
//        do{
//        System.out.print("Enter your username: ");
//        username = getInput();
//        System.out.println();
//        if (username.equals("exit")) { break; }
//
//        do {
//            System.out.print("Enter your message: ");
//            message = getInput();
//            System.out.println();
//            if (message.equals("logout")) { break; }
//            aerospikeInsertIntoSet(username, message);
//        } while (true);
//
//    } while (true);

}
