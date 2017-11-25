package com.niujiacun.utils.service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Administrator on 2017/11/25.
 */
public class MusicListQueueService {

    private static Queue<String> uncrawledMusicList = new ConcurrentLinkedQueue<String>();

    public static Queue<String> getUncrawledMusicList() {
        return uncrawledMusicList;
    }

    public static void addMusicList(String e) {
        uncrawledMusicList.offer(e);
    }

    public static String getTopMusicList() {
        if (!uncrawledMusicList.isEmpty()) {
            return uncrawledMusicList.poll();
        }

        return null;
    }

    public static boolean isUncrawledMusicListEmpty() {
        return uncrawledMusicList.isEmpty();
    }

    public static void printAll() {
        while (!uncrawledMusicList.isEmpty()) {
            System.out.println(uncrawledMusicList.poll());
        }
    }


}
