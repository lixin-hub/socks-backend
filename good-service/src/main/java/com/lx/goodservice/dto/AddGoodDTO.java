package com.lx.goodservice.dto;

import lombok.Data;

@Data
public class AddGoodDTO {
    String goodName;
    Float goodPrice;
    Integer goodStoke;//库存
    // 商品所属分类数组
    String goodCat;
    // 商品详情描述
    String goodIntroduce;
    String pic;
    //商品属性
    String attrs;
}
