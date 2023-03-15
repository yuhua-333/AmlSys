package com.yuhua.amlsys;

import com.yuhua.amlsys.sys.entity.User;
import com.yuhua.amlsys.sys.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class AmlSysApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Test
    void testSelectAllUser() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    void testAdd(){
        User user = new User(null,"testadd","testadd",1,0);
        userMapper.insert(user);

    }

    @Test
    void testUpdate(){
        User user = new User(6,"afterupdate","afterupdate",1,0);
        userMapper.updateById(user);
    }

    @Test
    void testDelete(){
        User user = new User(6,"afterupdate","afterupdate",1,0);
        userMapper.deleteById(user);

    }

}
