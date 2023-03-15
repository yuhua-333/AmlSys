package com.yuhua.amlsys.sys.service;

import com.yuhua.amlsys.sys.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuhua
 * @since 2023-03-02
 */
public interface IUserService extends IService<User> {

    Map<String, Object> login(User user);

    Map<String, Object> getUserInfo(String token);

    void logout(String token);

    @Transactional
    void addUser(User user);

    User getUserById(Integer id);

    @Transactional
    void updateUser(User user);

    @Transactional
    void deleteUserById(Integer id);
}
