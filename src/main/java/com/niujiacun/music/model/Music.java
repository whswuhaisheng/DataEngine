package com.niujiacun.music.model;

import java.util.Date;

/**
 * Created by Administrator on 2017/11/25.
 */
public class Music {
    private Integer id;
    private String musicLinkUrl;
    private String musicComment;
    private Date insertTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMusicLinkUrl() {
        return musicLinkUrl;
    }

    public void setMusicLinkUrl(String musicLinkUrl) {
        this.musicLinkUrl = musicLinkUrl;
    }

    public String getMusicComment() {
        return musicComment;
    }

    public void setMusicComment(String musicComment) {
        this.musicComment = musicComment;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
}
