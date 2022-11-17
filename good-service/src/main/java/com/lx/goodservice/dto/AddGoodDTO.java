package com.lx.goodservice.dto;

import com.lx.goodservice.pojo.GoodAttribute;
import lombok.Data;

import java.util.List;

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
    GoodAttribute goodAttribute;
    //商品属性
    List<Attr> attrs;
    //属性
    private String attrType;

    public static class Attr {
        String attrId;
        String attrValue;

        public Attr(String attrId, String attrValue) {
            this.attrId = attrId;
            this.attrValue = attrValue;
        }

        public String getAttrId() {
            return attrId;
        }

        public void setAttrId(String attrId) {
            this.attrId = attrId;
        }

        public String getAttrValue() {
            return attrValue;
        }

        public void setAttrValue(String attrValue) {
            this.attrValue = attrValue;
        }
    }
}
