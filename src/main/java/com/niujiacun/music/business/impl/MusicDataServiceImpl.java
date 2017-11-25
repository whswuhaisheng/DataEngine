package com.niujiacun.music.business.impl;

import com.niujiacun.music.business.interfaces.*;
import com.niujiacun.music.common.utils.Constants;
import com.niujiacun.music.dao.MusicDao;
import com.niujiacun.music.model.Music;
import com.niujiacun.music.model.MusicCommentMessage;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/11/25.
 */

@Service("musicDataService")
public class MusicDataServiceImpl implements IMusicDataService{

    @Autowired
    private MusicDao musicDao;
    @Autowired
    private IMusicListQueueService musicListQueueService;
    @Autowired
    private IMusicQueueService musicQueueService;
    @Autowired
    private ITopMusicCalculateService topMusicCalculateService;
    @Autowired
    private IHtmlFetcherService htmlFetcherServiceImpl;
    @Autowired
    private IHtmlParserService htmlParserService;

    private int totalMusicList = Constants.MUSIC_LIST_COUNT;
    private int limit = Constants.PER_PAGE;
    private int offset =Constants.OFFSET;
    private HSSFWorkbook commentMessageWorkbook = new HSSFWorkbook();
    private List<MusicCommentMessage> ms = null;
    private List<MusicCommentMessage> msl = null;
    Logger logger = Logger.getLogger(MusicDataServiceImpl.class);

    @Override
    public void addMusicData() {

        try {
            //初始化待爬取的歌单URL队列
            initUncrawledMusicListQueue();
            //记录所有爬取出来的歌曲数，包含重复歌曲
            int count = 0;
            //开始根据歌单爬取
            while (!musicListQueueService.isUncrawledMusicListEmpty()) {

                //填充待爬取歌曲队列
                fillUncrawledMusicQueue(musicListQueueService.getTopMusicList());

                //歌曲队列为空就返回上层循环填充歌曲队列
                while (!musicQueueService.isUncrawledMusicQueueEmpty()) {

                    //取出待爬取歌曲ID
                    String songId = musicQueueService.getTopMusicUrl();
                    //判断是否已经爬取过
                    if (!musicQueueService.isMusicCrawled(songId)) {
                        //获取到爬取结果，歌曲信息
                        MusicCommentMessage mcm = getCommentMessage(songId);

                        //判断是否加入Top歌曲列表
                        ms = topMusicCalculateService.getTopMusic(mcm);

                        //歌曲评论数是否大于某个值进行收录
                        msl = topMusicCalculateService.getMusicCommentsCountMore(mcm);

                        //向歌曲信息Excel插入数据
                        // GenerateExcelUtils.generateCommentMessageExcelProcess(commentMessageWorkbook, commentMessageSheet, mcm, count);

                        //生成歌曲评论Excel
                        // GenerateExcelUtils.generateCommentsExcel(mcm);

                        //加入已经爬取的队列，供以后查重判断
                        musicQueueService.addCrawledMusic(songId);
                        count++;
                    }
                }
            }

            Music music = new Music();
            music.setInsertTime(new Date());
            music.setMusicComment("孙立军");
            music.setMusicLinkUrl("hhhhhhhhhh");

            musicDao.insert(music);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*
    * 循环请求获取所有歌单
    * */
    public void initUncrawledMusicListQueue() throws ClientProtocolException, IOException {

        if (totalMusicList > limit) {
            int tmpLimit = limit;
            int tmpOffset = offset;

            while (totalMusicList > tmpOffset) {

                String suffix = "limit=" + tmpLimit + "&offset=" + tmpOffset;
                tmpOffset += tmpLimit;

                if (tmpOffset + tmpLimit > totalMusicList) {
                    tmpLimit =  totalMusicList - tmpOffset;
                }

                htmlParserService.parseAndSaveMusicListUrl(htmlFetcherServiceImpl.fetch(Constants.SOURCE_URL + suffix));
            }   } else {
            String suffix = "limit=" + totalMusicList + "&offset=" + offset;
            htmlParserService.parseAndSaveMusicListUrl(htmlFetcherServiceImpl.fetch(Constants.SOURCE_URL + suffix));
        }
    }

    //填充要爬取的歌曲队列
    public void fillUncrawledMusicQueue(String musicListUrl) throws IOException {
        htmlParserService.parseMusicListAndGetMusics(musicListUrl);
    }

    //由于反爬的存在， 一旦被禁止爬取， 休眠几秒后再进行爬取
    public MusicCommentMessage getCommentMessage(String songId) {
        try {
            MusicCommentMessage mc = htmlParserService.parseCommentMessage(songId);

            if (mc == null) {
                logger.info("warining: be interceptted by net ease music server..");
                Thread.sleep((long) (Math.random() * 30000));

                //递归
                return getCommentMessage(songId);
            } else {
                return mc;
            }
        } catch (Exception e) {
            logger.info("error: be refused by net ease music server..");
            return getCommentMessage(songId);
        }
    }


}
