package com.lx.userservice.pojo.permission;


import com.lx.common.base.Entity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Builder
@EqualsAndHashCode(callSuper = true)
public class RUserRole extends Entity<RUserRole> {

  private String id;
  private long roleCode;
  private String userId;

}
