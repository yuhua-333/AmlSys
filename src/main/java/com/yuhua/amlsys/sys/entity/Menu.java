package com.yuhua.amlsys.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuhua
 * @since 2023-03-02
 */
@Data
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Integer menuId;
    private String component;
    private String path;
    private String redirect;
    private String name;
    private String title;
    private String icon;
    private Integer parentId;
    private String isLeaf;
    private Boolean hidden;

    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Menu> children;

    @TableField(exist = false)
    private Map<String,Object> meta = new HashMap<>();
    public Map<String,Object> getMeta(){
        meta.put("title",this.title);
        meta.put("icon",this.icon);
        return this.meta;
    }

}
