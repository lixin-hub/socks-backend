package com.lx.userservice.pojo.permission;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lx.common.base.Entity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Builder
@EqualsAndHashCode(callSuper = true)
public class RRolePermission extends Entity<RRolePermission> {
    @TableId(type = IdType.AUTO)
    private String id;
    private String roleCode;
    private String permissionCode;

}
