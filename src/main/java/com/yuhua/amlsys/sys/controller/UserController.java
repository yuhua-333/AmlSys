package com.yuhua.amlsys.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuhua.amlsys.common.vo.Result;
import com.yuhua.amlsys.sys.entity.User;
import com.yuhua.amlsys.sys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yuhua
 * @since 2023-03-02
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/all")
    public Result<List<User>> getAllUser(){
        List<User> list = userService.list();
        return Result.success(list,"查询全部用户信息成功");
    }

    @PostMapping("/login")
    public Result<Map<String,Object>> login(@RequestBody User user){
        Map<String,Object> data = userService.login(user);
        if (data != null){
            return Result.success(data);
        }
        return Result.fail(2002,"用户名或密码错误");
    }

    @GetMapping("/info")
    public Result<Map<String,Object>> getUserInfo(@RequestParam("token") String token){
        Map<String,Object> data = userService.getUserInfo(token);
        if (data != null){
            return Result.success(data);
        }
        return Result.fail(2003,"登录信息无效，请重新登录");
    }

    @PostMapping("/logout")
    public Result<?> logout(@RequestHeader("X-Token") String token){
        userService.logout(token);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<Map<String,Object>> getUserList(@RequestParam(value="username",required = false)  String username,
                                             @RequestParam(value="pageNo")  Long pageNo,
                                             @RequestParam(value="pageSize")  Long pageSize){

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(username),User::getUsername,username);

        //按ID降序排序
        wrapper.orderByDesc(User::getUserId);

        Page<User> page = new Page<>(pageNo,pageSize);
        userService.page(page,wrapper);

        Map<String,Object> data = new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());
        return Result.success(data);
    }

    @PostMapping("/add")
    public Result<?> addUser(@RequestBody User user){
        user.setDeleted(0);
        userService.addUser(user);
        return Result.success("新增用户成功");
    }

    @PutMapping("/update")
    public Result<?> updateUser(@RequestBody User user){
        //传入为空时，save()方法该字段不会更新
        user.setPassword(null);
        System.out.println("更新的用户信息为："+ user);
        userService.updateUser(user);
        return Result.success("修改用户信息成功");
    }

    @GetMapping("/{userId}")
    public Result<User> getUserById(@PathVariable("userId") Integer userId){
        User user = userService.getUserById(userId);
        System.out.println("根据用户ID获取到的信息：" + user);
        return Result.success(user);
    }

    @DeleteMapping("/{userId}")
    public Result<User> deleteUserById(@PathVariable("userId") Integer userId){
        userService.deleteUserById(userId);
        return Result.success("删除用户成功");
    }
}
