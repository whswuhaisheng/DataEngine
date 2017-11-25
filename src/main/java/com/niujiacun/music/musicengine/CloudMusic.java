package com.niujiacun.music.musicengine;

import com.niujiacun.music.business.impl.MusicDataServiceImpl;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2017/11/25.
 */
public class CloudMusic {
    public static ConfigurableApplicationContext context = null;
    public static void main(String[] args )
    {
        context = new ClassPathXmlApplicationContext("spring-context.xml");
        System.out.println( "-------------网易云音乐我来了--------------" );
        MusicDataServiceImpl musicDataService = (MusicDataServiceImpl)context.getBean("musicDataService");
        musicDataService.addMusicData();

    }
}
