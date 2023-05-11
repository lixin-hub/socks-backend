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
    public static int NEW =1;
    public static int NOT_NEW=0;
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private Double price;
    private String  goodId;
    private Date updateTime;
    private int isNew;


}
