package lab409.spider;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes.Name;

import lab409.utils.UserAgents;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Selectable;

public class Musical_Fes_Spider implements PageProcessor {
    private static int fes_num=0;
    private static int page_num=1;
    private static ArrayList<String> urls=new ArrayList();
    private static String[] festivals={ "扬州瓜洲音乐节","镇江长江音乐节", "北京国际音乐节", "上海简单生活节", "杭州西湖音乐节","成都热波音乐节", "长沙橘洲音乐节", "张北草原音乐节", "舟山东海音乐节", "草莓音乐节", "迷笛音乐节", "南京森林音乐"};
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000)
            .setDomain("http://search.sina.com.cn/")
            .setUserAgent(UserAgents.getuseragent())
            ;

    @Override
    public void process(Page page) {
        List<String> allUrls = page.getHtml().xpath("//div[@class='box-result clearfix']//h2/a/@href").all();
        for (int i = 0; i < allUrls.size(); i++)
        {
            if(!urls.contains(allUrls.get(i)))
            {
                urls.add(allUrls.get(i));
            }
        }
        String next=page.getHtml().xpath("//a[@title='下一页']").toString();
        if(next!=null)
        {
            System.out.println("有下一页:"+next);
            page_num++;
            if(page_num>50&&fes_num<2)
            {
                fes_num++;
                page_num=1;
                page.addTargetRequest("http://search.sina.com.cn/?q="+festivals[fes_num]+"&range=all&c=news&sort=time&col=&source=&from=&country=&size=&time=&a=&page="+page_num);
            }
            page.addTargetRequest("http://search.sina.com.cn/?q="+festivals[fes_num]+"&range=all&c=news&sort=time&col=&source=&from=&country=&size=&time=&a=&page="+page_num);
        }
        else if(fes_num<2)
        {
            fes_num++;
            page_num=1;
            page.addTargetRequest("http://search.sina.com.cn/?q="+festivals[fes_num]+"&range=all&c=news&sort=time&col=&source=&from=&country=&size=&time=&a=&page="+page_num);
        }
        else
        {
            for(int i=0;i<urls.size();i++)
            {
                System.out.println(urls.get(i));
            }
            Spider.create(new News_Spider()).addUrl(urls.get(0)).addPipeline(new ConsolePipeline()).addPipeline(new MysqlPipline()).thread(1).run();
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            Spider.create(new Musical_Fes_Spider()).addUrl("http://search.sina.com.cn/?q=扬州瓜洲音乐节&range=all&c=news&sort=time&col=&source=&from=&country=&size=&time=&a=&page=2").thread(1).run();
        }catch(Exception e)
        {

            e.printStackTrace();
            Spider.create(new News_Spider()).startUrls(urls).addPipeline(new ConsolePipeline()).thread(1).run();
        }
    }

}
