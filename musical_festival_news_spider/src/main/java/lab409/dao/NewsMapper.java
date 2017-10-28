package lab409.dao;
import lab409.model.Musical_Fes_News;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface NewsMapper {
    @Insert("insert into news (`title`,`body`,`publish_time`,`writer`,`datasource`,`url`,`yangzhou_guazhou`,`zhenjiang_changjiang`,`beijing_guoji`,`shanghai_jiandan`,`hangzhou_xihu`,`chengdu_rebo`,`changsha_juzhou`,`zhangbei_caoyuan`,`zhoushan_donghai`,`caomei`,`midi`,`nanjing_senlin`) values (#{title},#{body},#{publish_time},#{writer},#{datasource},#{url},#{yangzhou},#{zhenjiang},#{beijing},#{shanghai},#{hangzhou},#{chengdu},#{changsha},#{zhangbei},#{zhoushan},#{caomei},#{midi},#{nanjing})")
     public Boolean inertNews(String title,String body,String publish_time,String writer,String datasource,String url,boolean yangzhou,boolean zhenjiang,boolean beijing,boolean shanghai,boolean hangzhou,boolean chengdu,boolean changsha,boolean zhangbei,boolean zhoushan,boolean caomei,boolean midi,boolean nanjing);
}
