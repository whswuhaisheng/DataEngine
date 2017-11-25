package com.niujiacun.music.business.impl;

import com.niujiacun.music.business.interfaces.ITopMusicCalculateService;
import com.niujiacun.music.common.utils.Constants;
import com.niujiacun.music.model.MusicCommentMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/11/25.
 */
@Service("topMusicCalculateService")
public class TopMusicCalculateServiceImpl implements ITopMusicCalculateService {

    static List<MusicCommentMessage> ms = new ArrayList<MusicCommentMessage>();
    static List<MusicCommentMessage> msl = new ArrayList<MusicCommentMessage>();

    //获取歌曲
    @Override
    public  List<MusicCommentMessage> getTopMusic(MusicCommentMessage mcm) {

        int topSize = ms.size();

        if (topSize == 0) {
            ms.add(mcm);
        }

        if (topSize > 0 && topSize < Constants.TOP_MUSIC_COUNT) {
            for (int j = 0; j < topSize; j++) {
                if (mcm.getCommentCount() > ms.get(j).getCommentCount()) {
                    ms.add(j, mcm);
                    break;
                }

                if (j == topSize - 1) {
                    ms.add(mcm);
                }
            }
        }
        if (topSize >= Constants.TOP_MUSIC_COUNT) {
            for (int j = 0; j < topSize; j++) {
                if (mcm.getCommentCount() > ms.get(j).getCommentCount()) {
                    ms.add(j, mcm);
                    ms.remove(topSize);
                    break;
                }
            }
        }

        return ms;
    }
    //获取评论数大于该值的歌曲
    @Override
    public  List<MusicCommentMessage> getMusicCommentsCountMore(MusicCommentMessage mcm) {

        int size = msl.size();

        if (mcm.getCommentCount() > Constants.COMMENTS_LIMIT) {
            if (size == 0) {
                msl.add(mcm);
            } else {
                for (int i = 0; i < size; i++) {
                    if (mcm.getCommentCount() > msl.get(i).getCommentCount()) {
                        msl.add(i, mcm);
                        break;
                    }

                    if (i == size - 1) {
                        msl.add(mcm);
                    }
                }
            }
        }

        return msl;
    }

}
