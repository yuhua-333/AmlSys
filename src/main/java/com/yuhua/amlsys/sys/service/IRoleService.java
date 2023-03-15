package com.yuhua.amlsys.sys.service;

import com.yuhua.amlsys.sys.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuhua
 * @since 2023-03-02
 */
public interface IRoleService extends IService<Role> {

    @Transactional
    void addRole(Role role);

    Role getRoleById(Integer id);

    @Transactional
    void updateRole(Role role);

    @Transactional
    void deleteRoleById(Integer id);
}
