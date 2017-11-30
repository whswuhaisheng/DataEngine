package com.niujiacun.music.business.interfaces;


/**
 * Created by Administrator on 2017/11/25.
 */
public interface IMusicQueueService {

    public  void addUncrawledMusic(String e);

    public  String getTopMusicUrl();

    public  void addCrawledMusic(String e);

    public  boolean isMusicCrawled(String id);

    public  boolean isUncrawledMusicQueueEmpty();

    public  void printAll();

    public  int getCrawledMusicSize();

}
