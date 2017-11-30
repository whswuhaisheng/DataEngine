package com.niujiacun.music.business.interfaces;


import com.niujiacun.music.model.MusicCommentMessage;

import java.util.List;

/**
 * Created by Administrator on 2017/11/25.
 */
public interface ITopMusicCalculateService {
    //获取歌曲
    public List<MusicCommentMessage> getTopMusic(MusicCommentMessage mcm);
    //获取评论数大于该值的歌曲
    public  List<MusicCommentMessage> getMusicCommentsCountMore(MusicCommentMessage mcm);

}
