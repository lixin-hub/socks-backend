package com.lx.goodservice.pojo;

import com.lx.Entity;
import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GoodPrice extends Entity<GoodPrice> {

    private String id;
    private double price;
    private Date updateTime;
    private boolean isNew;


}
