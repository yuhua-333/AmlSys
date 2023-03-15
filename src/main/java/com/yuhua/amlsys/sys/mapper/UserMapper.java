package com.yuhua.amlsys.sys.mapper;

import com.yuhua.amlsys.sys.entity.User;
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
public interface UserMapper extends BaseMapper<User> {

    List<String> getRoleNamesByUserId(Integer userId);
}
