package com.lx.userservice.pojo.permission;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lx.common.base.TreeEntity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Builder
@EqualsAndHashCode(callSuper = true)
public class Permission extends TreeEntity<Permission> {

    @TableId(type = IdType.AUTO)
    private String id;
    private String permission;
    private String description;
    private String note;
    //重写父类属性，加入查询条件
    private String parent;

}
