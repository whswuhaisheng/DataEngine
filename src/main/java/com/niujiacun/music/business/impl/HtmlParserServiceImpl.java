package com.niujiacun.music.business.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.google.common.collect.ImmutableMap;
import com.niujiacun.music.business.interfaces.IHtmlParserService;
import com.niujiacun.music.business.interfaces.IMusicListQueueService;
import com.niujiacun.music.business.interfaces.IMusicQueueService;
import com.niujiacun.music.common.utils.Constants;
import com.niujiacun.music.common.utils.EncryptUtils;
import com.niujiacun.music.model.MusicComment;
import com.niujiacun.music.model.MusicCommentMessage;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/25.
 */
@Service("htmlParserService")
public class HtmlParserServiceImpl implements IHtmlParserService {

    @Autowired
    private IMusicListQueueService musicListQueueService;
    @Autowired
    private IMusicQueueService musicQueueServicm;

    @Override
    public void parseAndSaveMusicListUrl(String html) {

        Document doc = Jsoup.parse(html);
        Element content = doc.getElementById("m-pl-container");
        Elements as = content.select("li > div > a.msk");

        for (Element a : as) {
            musicListQueueService.addMusicList(Constants.DOMAIN + a.attr("href"));
        }

    }

    @Override
    public void parseMusicListAndGetMusics(String url) throws IOException {

        Document doc = Jsoup.connect(url).get();
        Element content = doc.getElementById("song-list-pre-cache");
        Elements as = content.select("ul.f-hide li a");

        for (Element a : as) {
            String suffix = a.attr("href");
            musicQueueServicm.addUncrawledMusic(suffix.substring(suffix.indexOf("id=") + 3));
        }

    }

    @Override
    public MusicCommentMessage parseCommentMessage(String songId) throws Exception {
        String songUrl = Constants.DOMAIN + "/song?id=" + songId;
        URL uri = new URL(songUrl);
        Document msdoc = Jsoup.parse(uri, 3000);

        String secKey = new BigInteger(100, new SecureRandom()).toString(32).substring(0, 16);
        String encText = EncryptUtils.aesEncrypt(EncryptUtils.aesEncrypt(Constants.TEXT, "0CoJUm6Qyw8W8jud"), secKey);
        String encSecKey = EncryptUtils.rsaEncrypt(secKey);
        Connection.Response response = Jsoup
                .connect(Constants.NET_EASE_COMMENT_API_URL + songId + "/?csrf_token=")
                .method(Connection.Method.POST).header("Referer", Constants.BASE_URL)
                .data(ImmutableMap.of("params", encText, "encSecKey", encSecKey)).execute();


        Object res = JSON.parse(response.body());

        if (res == null) {
            return null;
        }
        MusicCommentMessage musicCommentMessage = new MusicCommentMessage();

        int commentCount = (Integer) JSONPath.eval(res, "$.total");
        int hotCommentCount = (Integer)JSONPath.eval(res, "$.hotComments.size()");
        int latestCommentCount = (Integer)JSONPath.eval(res, "$.comments.size()");

        musicCommentMessage.setSongTitle(msdoc.title());
        musicCommentMessage.setSongUrl(songUrl);
        musicCommentMessage.setCommentCount(commentCount);
        List<MusicComment> ls = new ArrayList<MusicComment>();
        if (commentCount != 0 && hotCommentCount != 0) {

            for (int i = 0; i < hotCommentCount; i++) {
                String nickname = JSONPath.eval(res, "$.hotComments[" + i + "].user.nickname").toString();
                String time = EncryptUtils.stampToDate((Long)JSONPath.eval(res, "$.hotComments[" + i + "].time"));
                String content = JSONPath.eval(res, "$.hotComments[" + i + "].content").toString();
                String appreciation = JSONPath.eval(res, "$.hotComments[" + i + "].likedCount").toString();
                ls.add(new MusicComment("hotComment", nickname, time, content, appreciation));
            }
        } else if (commentCount != 0) {

            for (int i = 0; i < latestCommentCount; i++) {
                String nickname = JSONPath.eval(res, "$.comments[" + i + "].user.nickname").toString();
                String time = EncryptUtils.stampToDate((Long)JSONPath.eval(res, "$.comments[" + i + "].time"));
                String content = JSONPath.eval(res, "$.comments[" + i + "].content").toString();
                String appreciation = JSONPath.eval(res, "$.comments[" + i + "].likedCount").toString();
                ls.add(new MusicComment("latestCommentCount", nickname, time, content, appreciation));
            }
        }

        musicCommentMessage.setComments(ls);
        return musicCommentMessage;
    }
}
