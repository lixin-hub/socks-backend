package com.lx.userservice.pojo.permission;

import com.lx.common.base.Entity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Builder
@EqualsAndHashCode(callSuper = true)
public class Permission extends Entity<Permission> {

  private String id;
  private String permission;
  private String description;



}
