package com.tweetspike;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.AerospikeException;
import com.aerospike.client.policy.ClientPolicy;

import java.util.Scanner;


//**
public class Tweetspike {
    private AerospikeClient tweetspikeClient;

    public Tweetspike(String hostIP, int hostPort)
    {
        ClientPolicy cPolicy = new ClientPolicy();
        cPolicy.timeout = 500;
        tweetspikeClient = new AerospikeClient(cPolicy, hostIP, hostPort);
    }

    public void menu()
    {
        Scanner input = new Scanner(System.in);

        try {
            System.out.println("INFO: Connecting to Aerospike cluster...");

            // Establish connection to Aerospike server

            if (tweetspikeClient == null || !tweetspikeClient.isConnected()) {
                System.out.print("\nERROR: Connection to Aerospike cluster failed! Please check the server settings and try again!");
                //console.readLine();
            } else {
                System.out.print("\nINFO: Connection to Aerospike cluster succeeded!\n");
                // Create instance of UserService
                UserService us = new UserService(tweetspikeClient, input);
                // Create instance of TweetService
                // TweetService ts = new TweetService(client);
                // Create instance of UtilityService
                // UtilityService util = new UtilityService(client);
                // Present options
                System.out.print("\nWhat would you like to do:\n");
                System.out.print("1> Create A User And A Tweet\n");
                System.out.print("2> Read A User Record\n");
                System.out.print("3> Batch Read Tweets For A User\n");
                System.out.print("4> Scan All Tweets For All Users\n");
                System.out.print("5> Record UDF -- Update User Password\n");
                System.out.print("6> Query Tweets By Username And Users By Tweet Count Range\n");
                System.out.print("7> Stream UDF -- Aggregation Based on Tweet Count By Region\n");
                System.out.print("0> Exit\n");
                System.out.print("\nSelect 0-7 and hit enter: ");

                int feature = input.nextInt();
                input.nextLine();

                if (feature != 0) {
                    switch (feature) {
                        case 1:
                            System.out.print("\n********** Your Selection: Create User And A Tweet **********\n");
                            us.createUser();
                            // ts.createTweet();
                            break;
                        case 2:
                            System.out.print("\n********** Your Selection: Read A User Record **********\n");
                            // us.getUser();
                            break;
                        case 3:
                            System.out.print("\n********** Your Selection: Batch Read Tweets For A User **********\n");
                            // us.batchGetUserTweets();
                            break;
                        case 4:
                            System.out.print("\n********** Your Selection: Scan All Tweets For All Users **********\n");
                            // ts.scanAllTweetsForAllUsers();
                            break;
                        case 5:
                            System.out.print("\n********** Your Selection: Record UDF -- Update User Password **********\n");
                            // us.updatePasswordUsingUDF();
                            break;
                        case 6:
                            System.out.print("\n********** Your Selection: Query Tweets By Username And Users By Tweet Count Range **********\n");
                            // ts.queryTweets();
                            break;
                        case 7:
                            System.out.print("\n********** Your Selection: Stream UDF -- Aggregation Based on Tweet Count By Region **********\n");
                            // us.aggregateUsersByTweetCountByRegion();
                            break;
                        case 12:
                            System.out.print("\n********** Create Users **********\n");
                            // us.createUsers();
                            break;
                        case 23:
                            System.out.print("\n********** Create Tweets **********\n");
                            // ts.createTweets();
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (AerospikeException e) {
            System.out.print("AerospikeException - Message: " + e.getMessage() + "\n");
            System.out.print("AerospikeException - StackTrace: " + e + "\n");
        } catch (Exception e) {
            System.out.print("Exception - Message: " + e.getMessage() + "\n");
            System.out.print("Exception - StackTrace: " + e + "\n");
        } finally {
            if (tweetspikeClient != null && tweetspikeClient.isConnected()) {
                tweetspikeClient.close(); // Close Aerospike server connection
            }
            System.out.print("\n\nINFO: Press any key to exit...\n");
            //console.readLine();
        }
    }

    //Maybe pass parameters
    protected void finalize() throws Throwable {
        if (this.tweetspikeClient != null){
            this.tweetspikeClient.close();
        }
    }
}
