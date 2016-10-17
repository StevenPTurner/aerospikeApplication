package com.tweetspike;


public class TweetspikeMain
{
    String hostIP = "localhost";
    int hostPort = 3000;
    Tweetspike tweetSpike = new Tweetspike(hostIP, hostPort);

    public static void main( String[] args ) {
        TweetspikeMain app = new TweetspikeMain();
        app.run();
    }

    private void run() {
        tweetSpike = new Tweetspike(hostIP, hostPort);
        tweetSpike.menu();
    }


}
