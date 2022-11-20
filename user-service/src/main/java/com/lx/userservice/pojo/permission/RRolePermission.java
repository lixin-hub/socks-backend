package com.lx.userservice.pojo.permission;


import com.lx.common.base.Entity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Builder
@EqualsAndHashCode(callSuper = true)
public class RRolePermission extends Entity<RRolePermission> {

  private String id;
  private long roleCode;
  private long permissionCode;

}
