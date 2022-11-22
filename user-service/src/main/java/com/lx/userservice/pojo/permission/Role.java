package com.lx.userservice.pojo.permission;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lx.common.base.Entity;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Builder
@EqualsAndHashCode(callSuper = true)
public class Role extends Entity<Role> {
  @TableId(type = IdType.AUTO)
  private String id;
  private String roleName;
  private String description;
  //权限列表
  @TableField(exist = false)
  private List<Permission> permissions;

}
