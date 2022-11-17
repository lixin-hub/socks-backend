package com.lx.goodservice.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lx.common.base.Entity;
import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class GoodPrice extends Entity<GoodPrice> {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private double price;
    private String  goodId;
    private Date updateTime;
    private boolean isNew;


}
