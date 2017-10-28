package lab409.controller;

import lab409.dao.UserMapper;
import lab409.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping({"/home"})
public class UserController {
    @Autowired
    UserMapper userMapper;

    @RequestMapping(value = "/user")
    @ResponseBody
    public String user(){
        User user = userMapper.findUserByName("小王");
        return user.getName()+"-----"+user.getAge();
    }
}