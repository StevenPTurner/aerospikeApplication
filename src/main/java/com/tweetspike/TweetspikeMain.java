package com.tweetspike;


public class TweetspikeMain
{
    String hostIP = "192.168.0.2";
    int hostPort = 3000;
    Tweetspike tweetSpike = new Tweetspike(hostIP, hostPort);

    public static void main( String[] args )
    {
       TweetspikeMain app = new TweetspikeMain();
       app.run();
    }

    public void run()
    {
        tweetSpike = new Tweetspike(hostIP, hostPort);
        tweetSpike.menu();
    }


}
