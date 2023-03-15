package com.yuhua.amlsys.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuhua.amlsys.common.vo.Result;
import com.yuhua.amlsys.sys.entity.Role;
import com.yuhua.amlsys.sys.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@RequestMapping("/role")
public class RoleController {

    @Resource
    private IRoleService roleService;

    @GetMapping("/list")
    public Result<Map<String,Object>> getRoleList(@RequestParam(value="roleName",required = false)  String roleName,
                                                  @RequestParam(value="pageNo")  Long pageNo,
                                                  @RequestParam(value="pageSize")  Long pageSize){

        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        //查询条件
        wrapper.eq(StringUtils.hasLength(roleName),Role::getRoleName,roleName);
        //按ID降序排序
        wrapper.orderByDesc(Role::getRoleId);

        Page<Role> page = new Page<>(pageNo,pageSize);
        roleService.page(page,wrapper);

        Map<String,Object> data = new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());
        return Result.success(data);
    }

    @PostMapping("/add")
    public Result<?> addRole(@RequestBody Role role){
        roleService.addRole(role);
        return Result.success("新增角色成功");
    }

    @PutMapping("/update")
    public Result<?> updateUser(@RequestBody Role role){
        System.out.println("更新的角色信息为："+ role);
        roleService.updateRole(role);
        return Result.success("修改角色信息成功");
    }

    @GetMapping("/{roleId}")
    public Result<Role> getRoleById(@PathVariable("roleId") Integer roleId){
        Role role = roleService.getRoleById(roleId);
        System.out.println("根据角色ID获取到的信息：" + role);
        return Result.success(role);
    }

    @DeleteMapping("/{roleId}")
    public Result<Role> deleteRoleById(@PathVariable("roleId") Integer roleId){
        roleService.deleteRoleById(roleId);
        return Result.success("删除角色成功");
    }

    @GetMapping("/all")
    public Result<List<Role>> getAllRole(){
        List<Role> rolesList = roleService.list();
        return Result.success(rolesList);
    }


}
