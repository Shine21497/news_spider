package lab409.dao;

import lab409.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by Administrator on 2016/9/2.
 */
@Mapper
public interface UserMapper {

    @Select("select * from user where name = #{name}")
    User findUserByName(@Param("name")String name);
}