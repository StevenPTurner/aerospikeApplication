package com.tweetspike;

public class User {
    String username;
    String gender;
    String region;
    long lastTweeted;
    int tweetCount;

    public void setUsername(String username){ this.username = username; }
    public void setGender(String gender){ this.gender = gender; }
    public void setRegion(String region){ this.region = region; }
    public void setLastTweeted(long lastTweeted){ this.lastTweeted = lastTweeted; }
    public void setTweetCount(int tweetCount){ this.tweetCount = tweetCount; }

    public String getUsername(){ return username; }
    public String getGender(){ return gender; }
    public String getRegion(){ return region; }
    public long getLastTweeted(){ return lastTweeted; }
    public int getTweetCount(){ return tweetCount; }

    public void setUser(String username, String gender, String region, long lastTweeted, int tweetCount) {
        setUsername(username);
        setGender(gender);
        setRegion(region);
        setLastTweeted(lastTweeted);
        setTweetCount(tweetCount);
    }



}
