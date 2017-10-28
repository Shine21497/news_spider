package lab409.spider;

import lab409.dao.Dao;
import lab409.dao.NewsMapper;
import lab409.model.Musical_Fes_News;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;
import java.util.Date;

public class MysqlPipline implements Pipeline {
    @Resource
    private NewsMapper newsDao;
    @Override
    public void process(ResultItems resultItems, Task task) {

        Musical_Fes_News news=(Musical_Fes_News)resultItems.get("newsofpage");
        if(news!=null) {
            System.out.println("insert in news");
            Dao.getInstance().inertNews(news.getTitle(),news.getBody(),news.getPublish_time(),news.getWriter(),news.getDatasource(),news.getUrl(),news.isYangzhou(),news.isZhenjiang(),news.isBeijing(),news.isShanghai(),news.isHangzhou(),news.isChengdu(),news.isChangsha(),news.isZhangbei(),news.isZhoushan(),news.isCaomei(),news.isMidi(),news.isNanjing());
        }

    }
}
