package com.yuhua.amlsys.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yuhua.amlsys.sys.entity.Role;
import com.yuhua.amlsys.sys.entity.RoleMenu;
import com.yuhua.amlsys.sys.mapper.RoleMapper;
import com.yuhua.amlsys.sys.mapper.RoleMenuMapper;
import com.yuhua.amlsys.sys.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuhua
 * @since 2023-03-02
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Transactional
    @Override
    public void addRole(Role role){
        this.save(role);
        for (Integer menuId : role.getMenuIdList()){
            roleMenuMapper.insert(new RoleMenu(null,role.getRoleId(),menuId));
        }
    }

    @Override
    public Role getRoleById(Integer id){
        Role role = this.getById(id);
        List<Integer> menuIdList = roleMenuMapper.getMenuIdListByRoleId(id);
        role.setMenuIdList(menuIdList);
        return role;
    }

    @Override
    @Transactional
    public void updateRole(Role role) {
        // 更新role表
        this.updateById(role);
        // 清除原有权限
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId,role.getRoleId());
        roleMenuMapper.delete(wrapper);
        //新增权限
        for (Integer menuId : role.getMenuIdList()) {
            roleMenuMapper.insert(new RoleMenu(null,role.getRoleId(),menuId));
        }
    }

    @Override
    @Transactional
    public void deleteRoleById(Integer id){
        this.removeById(id);

        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId,id);
        roleMenuMapper.delete(wrapper);
    }

}
