package com.lx.goodservice.pojo;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class GoodPrice {

    private String id;
    private double price;
    private Date updateTime;
    private boolean isNew;


}
