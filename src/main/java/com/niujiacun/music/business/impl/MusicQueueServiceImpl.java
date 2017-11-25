package com.niujiacun.music.business.impl;

import com.niujiacun.music.business.interfaces.IMusicQueueService;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Administrator on 2017/11/25.
 */
@Service("musicQueueService")
public class MusicQueueServiceImpl implements IMusicQueueService {

    private static Queue<String> uncrawledMusics = new ConcurrentLinkedQueue<String>();
    private static Queue<String> crawledMusics = new ConcurrentLinkedQueue<String>();

    @Override
    public void addUncrawledMusic(String e) {
        uncrawledMusics.offer(e);
    }

    @Override
    public  String getTopMusicUrl() {
        if (!uncrawledMusics.isEmpty()) {
            return uncrawledMusics.poll();
        }

        return null;
    }

    @Override
    public void addCrawledMusic(String e) {
        crawledMusics.offer(e);
    }

    @Override
    public boolean isMusicCrawled(String id) {
        return crawledMusics.contains(id);
    }

    @Override
    public boolean isUncrawledMusicQueueEmpty() {
        return uncrawledMusics.isEmpty();
    }

    @Override
    public void printAll() {
        while (!uncrawledMusics.isEmpty()) {
            System.out.println(uncrawledMusics.poll());
        }
    }

    @Override
    public int getCrawledMusicSize() {
        return crawledMusics.size();
    }

}
