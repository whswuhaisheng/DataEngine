package com.niujiacun.music.business.interfaces;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Administrator on 2017/11/25.
 */
public interface IMusicListQueueService {

    public  Queue<String> getUncrawledMusicList();

    public  void addMusicList(String e);

    public  String getTopMusicList();

    public  boolean isUncrawledMusicListEmpty();

    public  void printAll();


}
