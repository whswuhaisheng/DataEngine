package com.niujiacun.music.business.impl;

import com.niujiacun.music.business.interfaces.IMusicDataService;
import com.niujiacun.music.dao.MusicDao;
import com.niujiacun.music.model.Music;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Administrator on 2017/11/25.
 */

@Service("musicDataService")
public class MusicDataServiceImpl implements IMusicDataService{

    @Autowired
    private MusicDao musicDao;

    Logger logger = Logger.getLogger(MusicDataServiceImpl.class);

    @Override
    public void addMusicData() {

        Music music = new Music();
        music.setInsertTime(new Date());
        music.setMusicComment("孙立军");
        music.setMusicLinkUrl("hhhhhhhhhh");

        musicDao.insert(music);

    }
}
