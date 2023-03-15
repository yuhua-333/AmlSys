package com.yuhua.amlsys.sys.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;;
import com.yuhua.amlsys.sys.entity.Menu;
import com.yuhua.amlsys.sys.entity.User;
import com.yuhua.amlsys.sys.entity.UserRole;
import com.yuhua.amlsys.sys.mapper.UserMapper;
import com.yuhua.amlsys.sys.mapper.UserRoleMapper;
import com.yuhua.amlsys.sys.service.IMenuService;
import com.yuhua.amlsys.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuhua
 * @since 2023-03-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private UserRoleMapper userRoleMapper;

    @Autowired
    private IMenuService menuService;

    @Override
    public Map<String, Object> login(User user) {
        //根据用户名查找用户
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,user.getUsername());
        wrapper.eq(User::getPassword,user.getPassword());
        User loginUser = this.baseMapper.selectOne(wrapper);

        //如果能查得到用户，则生成一个Token，并将其存入redis
        if(loginUser != null){
            //生成Token
            String key = "user::" + UUID.randomUUID();
            //存入Redis
            loginUser.setPassword(null);
            redisTemplate.opsForValue().set(key,loginUser,30, TimeUnit.MINUTES);
            //返回数据
            Map<String, Object> data = new HashMap<>();
            data.put("token",key);
            return data;
        }
        return null;
    }

    @Override
    public Map<String, Object> getUserInfo(String token) {
        //从Redis里读取token
        Object obj = redisTemplate.opsForValue().get(token);
        //反序列化
        User loginUser = JSON.parseObject(JSON.toJSONString(obj),User.class);
        if(obj != null){
            Map<String, Object> data =  new HashMap<>();
            data.put("name",loginUser.getUsername());

            //角色
            List<String> roleList = this.baseMapper.getRoleNamesByUserId(loginUser.getUserId());
            System.out.println("根据用户ID查询的roleList:"+roleList);
            data.put("roles", roleList);

            //权限列表getMenuListByUserId
            List<Menu> menuList = menuService.getMenuListByUserId(loginUser.getUserId());
            data.put("menuList",menuList);

            return data;
        }
        return null;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete(token);
    }

    @Override
    @Transactional //不止一个表的查询要加这个注解
    public void addUser(User user) {
        this.save(user);
        List<Integer> roleList = user.getRoleIdList();
        if (roleList != null){
            for (Integer roleId : roleList){
                userRoleMapper.insert(new UserRole(null, user.getUserId(), roleId));
            }
        }
    }

    @Override
    public User getUserById(Integer id){
        User user = this.getById(id);
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId,id);
        List<UserRole> userRoleList = userRoleMapper.selectList(wrapper);
        List<Integer> roleIdList =
                userRoleList.stream().map(userRole -> {
                    return userRole.getRoleId();
                })
                        .collect(Collectors.toList());

        System.out.println("打印roleIdList"+roleIdList);
        user.setRoleIdList(roleIdList);
        return user;
    }

    @Override
    @Transactional
    public void updateUser(User user){
        this.updateById(user);
        //删除原有角色
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId,user.getUserId());
        userRoleMapper.delete(wrapper);
        //设置新角色
        List<Integer> roleList = user.getRoleIdList();
        if (roleList != null){
            for (Integer roleId : roleList){
                userRoleMapper.insert(new UserRole(null, user.getUserId(), roleId));
            }
        }
    }

    @Override
    @Transactional
    public void deleteUserById(Integer id){
        this.removeById(id);

        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId,id);
        userRoleMapper.delete(wrapper);
    }

}
