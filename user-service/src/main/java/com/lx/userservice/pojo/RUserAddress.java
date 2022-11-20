package com.lx.userservice.pojo;


import com.lx.common.base.Entity;
import lombok.*;


@Data
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class RUserAddress extends Entity<RUserAddress> {

    private String id;
    private String userId;
    private String detail;
    private String addr;


}
