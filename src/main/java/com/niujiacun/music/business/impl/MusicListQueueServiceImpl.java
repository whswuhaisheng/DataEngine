package com.niujiacun.music.business.impl;

import com.niujiacun.music.business.interfaces.IMusicListQueueService;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Administrator on 2017/11/25.
 */
@Service("musicListQueueService")
public class MusicListQueueServiceImpl implements IMusicListQueueService {

    private static Queue<String> uncrawledMusicList = new ConcurrentLinkedQueue<String>();

    @Override
    public Queue<String> getUncrawledMusicList() {
        return uncrawledMusicList;
    }

    @Override
    public void addMusicList(String e) {
        uncrawledMusicList.offer(e);
    }

    @Override
    public String getTopMusicList() {
        if (!uncrawledMusicList.isEmpty()) {
            return uncrawledMusicList.poll();
        }
        return null;
    }

    @Override
    public boolean isUncrawledMusicListEmpty() {
        return uncrawledMusicList.isEmpty();
    }

    @Override
    public void printAll() {
        while (!uncrawledMusicList.isEmpty()) {
            System.out.println(uncrawledMusicList.poll());
        }
    }
}
