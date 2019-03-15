package com.bengshiwei.zzj.app.utils;

import com.bengshiwei.zzj.app.reptile.MovieReptile;
import com.bengshiwei.zzj.app.reptile.zuidam3u8.M3U8MovieReptile;
import us.codecraft.webmagic.Spider;

import java.util.Timer;
import java.util.TimerTask;

public class ZuiDaVideoReptileUtils {

    public static void startReptile(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Spider.create(new MovieReptile()).addUrl("http://www.zuidazyw.com/?m=vod-type-id-1.html").run();
                Spider.create(new MovieReptile()).addUrl("http://www.zuidazyw.com/?m=vod-type-id-2.html").run();
                Spider.create(new MovieReptile()).addUrl("http://www.zuidazyw.com/?m=vod-type-id-3.html").run();
                Spider.create(new MovieReptile()).addUrl("http://www.zuidazyw.com/?m=vod-type-id-4.html").run();
            }
        }, 1000,60*1000*60*6);
    }

    /**
     * 爬取一次
     */
    public static void oneReptile(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Spider.create(new MovieReptile()).addUrl("http://www.zuidazyw.com/?m=vod-type-id-1.html").run();
                Spider.create(new MovieReptile()).addUrl("http://www.zuidazyw.com/?m=vod-type-id-2.html").run();
                Spider.create(new MovieReptile()).addUrl("http://www.zuidazyw.com/?m=vod-type-id-3.html").run();
                Spider.create(new MovieReptile()).addUrl("http://www.zuidazyw.com/?m=vod-type-id-4.html").run();
                Spider.create(new M3U8MovieReptile()).addUrl("http://www.zuidazyw.com/?m=vod-type-id-1.html").run();
                Spider.create(new M3U8MovieReptile()).addUrl("http://www.zuidazyw.com/?m=vod-type-id-2.html").run();
                Spider.create(new M3U8MovieReptile()).addUrl("http://www.zuidazyw.com/?m=vod-type-id-3.html").run();
                Spider.create(new M3U8MovieReptile()).addUrl("http://www.zuidazyw.com/?m=vod-type-id-4.html").run();
            }
        }, 1000);
    }
}
