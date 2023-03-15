package com.yuhua.amlsys.sys.mapper;

import com.yuhua.amlsys.sys.entity.RoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yuhua
 * @since 2023-03-02
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    List<Integer> getMenuIdListByRoleId(Integer id);
}
