package lab409.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.Iterator;
import java.util.Vector;

import com.mysql.jdbc.Statement;

public class Dao extends BaseDao {

    private static final Dao dao;
    

    static {
        dao = new Dao();
    }

    public static Dao getInstance() {
        return dao;
    }

    // 相册
   /* public Vector selectAlbum(int id) {
        return super.selectOnlyNote("select * from tb_album where id=" + id);
    }

    public Vector selectAlbums(int fatherId) {
        return super.selectSomeNote("select * from tb_album where father_id=" + fatherId);
    }*/
	public Boolean inertNews(String title,String body,String publish_time,String writer,String datasource,String url,boolean yangzhou,boolean zhenjiang,boolean beijing,boolean shanghai,boolean hangzhou,boolean chengdu,boolean changsha,boolean zhangbei,boolean zhoushan,boolean caomei,boolean midi,boolean nanjing)
	{
		try
		{
			Connection conn = JDBC.getConnection();
			String sql="insert into news (`title`,`body`,`publish_time`,`writer`,`datasource`,`url`,`yangzhou_guazhou`,`zhenjiang_changjiang`,`beijing_guoji`,`shanghai_jiandan`,`hangzhou_xihu`,`chengdu_rebo`,`changsha_juzhou`,`zhangbei_caoyuan`,`zhoushan_donghai`,`caomei`,`midi`,`nanjing_senlin`) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt=conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, title);
			pstmt.setString(2, body);
			pstmt.setString(3, publish_time);
			pstmt.setString(4, writer);
			pstmt.setString(5, datasource);
			pstmt.setString(6,url);
			pstmt.setBoolean(7,yangzhou);
			pstmt.setBoolean(8,zhenjiang);
			pstmt.setBoolean(9,beijing);
			pstmt.setBoolean(10,shanghai);
			pstmt.setBoolean(11,hangzhou);
			pstmt.setBoolean(12,chengdu);
			pstmt.setBoolean(13,changsha);
			pstmt.setBoolean(14,zhangbei);
			pstmt.setBoolean(15,zhoushan);
			pstmt.setBoolean(16,caomei);
			pstmt.setBoolean(17,midi);
			pstmt.setBoolean(18,nanjing);
			return super.longHaul(pstmt,conn);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
}


