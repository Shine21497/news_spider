package lab409.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class BaseDao {

	
    // 查询多个记录
    public Vector selectSomeNote(PreparedStatement pstmt) {
        Vector<Vector<Object>> rsV = new Vector<Vector<Object>>();// 创建结果集向量

        Connection conn = JDBC.getConnection();// 获得数据库连接

        try {
           
            ResultSet rs = pstmt.executeQuery();// 执行SQL语句获得查询结果

            int columnCount = rs.getMetaData().getColumnCount();// 获得查询数据表的列数

            while (rs.next()) {// 遍历结果集

                Vector<Object> rowV = new Vector<Object>();// 创建行向量

                for (int column = 1; column <= columnCount; column++) {
                    rowV.add(rs.getObject(column));// 添加列值

                }
                rsV.add(rowV);// 将行向量添加到结果集向量中

            }
            rs.close();// 关闭结果集对象

            pstmt.close();// 关闭连接状态对象

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rsV;// 返回结果集向量

    }

    // 查询单个记录
    public Vector selectOnlyNote(PreparedStatement pstmt) {
        Vector<Object> rowV = null;
        
        try {
           
            ResultSet rs = pstmt.executeQuery();
            int columnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                rowV = new Vector<Object>();
                for (int column = 1; column <= columnCount; column++) {
                    rowV.add(rs.getObject(column));
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowV;
    }

    // 查询多个值
    public Vector selectSomeValue(PreparedStatement pstmt) {
        Vector<Object> valueV = new Vector<Object>();
        try {
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                valueV.add(rs.getObject(1));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return valueV;
    }

    // 查询单个值
    public Object selectOnlyValue(PreparedStatement pstmt) {
        Object value = null;
        try {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                value = rs.getObject(1);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return value;
    }

    // 插入、修改、删除记录
    public boolean longHaul(PreparedStatement pstmt,Connection conn) {
        boolean isLongHaul = true;// 默认持久化成功

        //Connection conn = JDBC.getConnection();// 获得数据库连接

        try {
            conn.setAutoCommit(false);// 设置为手动提交

            Statement stmt = conn.createStatement();// 创建连接状态对象

            pstmt.executeUpdate();// 执行SQL语句

            pstmt.close();// 关闭连接状态对象

            conn.commit();// 提交持久化

        } catch (SQLException e) {
            isLongHaul = false;// 持久化失败

            try {
                conn.rollback();// 回滚

            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return isLongHaul;// 返回持久化结果

    }
    public Long longHaulforId(PreparedStatement pstmt,Connection conn) {
        Long result = null;// 默认持久化成功

        //Connection conn = JDBC.getConnection();// 获得数据库连接

        try {
            conn.setAutoCommit(false);// 设置为手动提交

            Statement stmt = conn.createStatement();// 创建连接状态对象

            pstmt.executeUpdate();// 执行SQL语句
            
            ResultSet rs = pstmt.getGeneratedKeys(); 
            if (rs.next()) { 
                    //知其仅有一列，故获取第一列 
                    result= rs.getLong(1); 
            } 
            
            pstmt.close();// 关闭连接状态对象

            conn.commit();// 提交持久化

        } catch (SQLException e) {
            // 持久化失败

            try {
                conn.rollback();// 回滚

            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return result;// 返回持久化结果

    }
}
