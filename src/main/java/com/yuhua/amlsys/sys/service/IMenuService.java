package com.yuhua.amlsys.sys.service;

import com.yuhua.amlsys.sys.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuhua
 * @since 2023-03-02
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> getAllMenu();

    List<Menu> getMenuListByUserId(Integer userId);

    void setMenuChildrenByUserId(Integer userId, List<Menu> menuList);

    void setMenuChildren(List<Menu> menuList);
}
