package lab409.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class JDBC {

    private static final String DRIVERCLASS = "com.mysql.jdbc.Driver";// 数据库驱动
    private static final String URL = "jdbc:mysql://localhost:3306/db_bigsearch?characterEncoding=utf8&useSSL=true";// 数据库URL
    private static final ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();// 用来保存数据库连接
    private static Connection conn = null;// 数据库连接
    

    static { // 通过静态方法加载数据库驱动，并且在数据库不存在的情况下创建数据库

        try {
            Class.forName(DRIVERCLASS); // 加载数据库驱动

            /*File db_album = new File("db_album");// 创建数据库文件对象

            if (!db_album.exists()) {// 数据库文件不存在

                createDatabase();// 创建数据库

            }*/
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createDatabase() throws Exception {

        String[] sqls = new String[1];// 定义创建数据库的SQL语句

        sqls[0] = "create table tb_user (username varchar(20) not null,password varchar(20) not null,primary key (username))";
        conn = DriverManager.getConnection(URL,"root","zhaoliang");// 创建数据库连接
        threadLocal.set(conn);// 保存数据库连接

        Statement stmt = conn.createStatement();// 创建数据库连接状态对象

        for (int i = 0; i < sqls.length; i++) {// 遍历SQL数组创建数据库

            stmt.execute(sqls[i]);// 执行SQL语句

        }
        stmt.close();// 关闭数据库连接状态对象

    }

    protected static Connection getConnection() { // 创建数据库连接的方法

        conn = (Connection) threadLocal.get(); // 从线程中获得数据库连接

        if (conn == null) { // 没有可用的数据库连接

            try {
                conn = DriverManager.getConnection(URL,"root","zhaoliang");// 创建新的数据库连接

                threadLocal.set(conn); // 将数据库连接保存到线程中

            } catch (Exception e) {
                String[] infos = {"未能成功连接数据库！", "请确认本软件是否已经运行！"};
                System.out.println("未能成功连接数据库！请确认本软件是否已经运行");
                System.exit(0);// 关闭系统

                e.printStackTrace();
            }
        }
        return conn;
    }

    protected static boolean closeConnection() { // 关闭数据库连接的方法

        boolean isClosed = true; // 默认关闭成功

        conn = (Connection) threadLocal.get(); // 从线程中获得数据库连接

        threadLocal.set(null); // 清空线程中的数据库连接

        if (conn != null) { // 数据库连接可用

            try {
                conn.close(); // 关闭数据库连接

            } catch (SQLException e) {
                isClosed = false; // 关闭失败

                e.printStackTrace();
            }
        }
        return isClosed;
    }
}
