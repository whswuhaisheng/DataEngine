package com.niujiacun.music.business.interfaces;

import org.apache.http.client.ClientProtocolException;
import java.io.IOException;

/**
 * Created by Administrator on 2017/11/25.
 * 根据URL获取HTML文本
 */
public interface IHtmlFetcherService {
    public  String fetch(String url) throws ClientProtocolException, IOException;
}
