package lab409.spider;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.jar.Attributes.Name;

import lab409.model.Musical_Fes_News;
import lab409.utils.UserAgents;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Selectable;

public class News_Spider implements PageProcessor {
    private static String[] festivals={ "扬州瓜洲音乐节","镇江长江音乐节", "北京国际音乐节", "上海简单生活节", "杭州西湖音乐节","成都热波音乐节", "长沙橘洲音乐节", "张北草原音乐节", "舟山东海音乐节", "草莓音乐节", "迷笛音乐节", "南京森林音乐"};
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000)
            .setUserAgent(UserAgents.getuseragent())
            ;
    private static ArrayList<String> allurls;
    private static int url_num=1;
    public News_Spider()
    {
        super();
    }
    @Override
    public void process(Page page) {

        Musical_Fes_News mfn=new Musical_Fes_News();
        String title=page.getHtml().xpath("//h1[@id='artibodyTitle']/text()").toString();
        if(title==null)
        {
            title=page.getHtml().xpath("//h1[@id='main_title']/text()").toString();
        }
        mfn.setTitle(title);
        page.putField("title",title);
        String writer=page.getHtml().xpath("//span[@id='media_name']/a/text()").toString();
        page.putField("writer",writer);
        if(writer==null)
        {
            writer=page.getHtml().xpath("//span[@class='source']/a/text()").toString();
        }
        mfn.setWriter(writer);
        String publishtime=page.getHtml().xpath("//span[@id='pub_date']/text()").toString();
        if(publishtime==null)
        {
            publishtime=page.getHtml().xpath("//span[@class='titer']/text()").toString();
        }
        page.putField("publishtime",publishtime);
        mfn.setPublish_time(publishtime);
        List<String> allps=page.getHtml().xpath("//div[@id='artibody']/p/text()").all();
        String passage="";
        for(int i=0;i<allps.size();i++)
        {
            passage=passage+allps.get(i);
        }
        page.putField("passage",passage);
        mfn.setBody(passage);
        if(passage.contains(festivals[0]))
        {
            mfn.setYangzhou(true);
        }
        else if(passage.contains(festivals[1]))
        {
            mfn.setZhenjiang(true);
        }
        else if(passage.contains(festivals[2]))
        {
            mfn.setBeijing(true);
        }
        else if(passage.contains(festivals[3]))
        {
            mfn.setShanghai(true);
        }
        else if(passage.contains(festivals[4]))
        {
            mfn.setHangzhou(true);
        }
        else if(passage.contains(festivals[5]))
        {
            mfn.setChengdu(true);
        }
        else if(passage.contains(festivals[6]))
        {
            mfn.setChangsha(true);
        }
        else if(passage.contains(festivals[7]))
        {
            mfn.setZhangbei(true);
        }
        else if(passage.contains(festivals[8]))
        {
            mfn.setZhoushan(true);
        }
        else if(passage.contains(festivals[9]))
        {
            mfn.setCaomei(true);
        }
        else if(passage.contains(festivals[10]))
        {
            mfn.setMidi(true);
        }
        else if(passage.contains(festivals[11]))
        {
            mfn.setNanjing(true);
        }
        mfn.setDatasource("新浪");
        mfn.setUrl(page.getUrl().toString());
        page.putField("newsofpage",mfn);
        page.putField("testobject",new Date());


    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            ArrayList<String> urls=new ArrayList<String>();
            FileInputStream f = new FileInputStream("D:/data/urls.txt");
            Scanner scanner=new Scanner(f);
            while(scanner.hasNext())
            {
                urls.add(scanner.nextLine());
            }

            Spider.create(new News_Spider()).startUrls(urls).addPipeline(new ConsolePipeline()).addPipeline(new MysqlPipline()).thread(1).run();
        }catch(Exception e)
        {

            e.printStackTrace();

        }
    }

}
