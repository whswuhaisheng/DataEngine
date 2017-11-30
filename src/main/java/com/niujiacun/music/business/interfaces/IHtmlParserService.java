package com.niujiacun.music.business.interfaces;


import com.niujiacun.music.model.MusicCommentMessage;

import java.io.IOException;

/**
 * Created by Administrator on 2017/11/25.
 */
public interface IHtmlParserService {

    //歌单列表页获取所有歌单URL
    public  void parseAndSaveMusicListUrl(String html);

    //歌曲列表页获取所有歌曲ID
    public  void parseMusicListAndGetMusics(String url) throws IOException;

    //通过歌曲ID获取评论API，网易对其进行了加密
    public MusicCommentMessage parseCommentMessage(String songId) throws Exception;

}
