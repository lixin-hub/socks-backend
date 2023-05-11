package com.lx.goodservice.dto;

import com.lx.goodservice.pojo.GoodAttribute;
import com.lx.goodservice.pojo.RGoodPics;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class AddGoodDTO {
    String id;
    String goodName;
    Double goodPrice;
    Integer goodStoke;//库存
    // 商品所属分类数组
    String goodCat;
    // 商品详情描述
    String goodIntroduce;
    //图片数组
    List<String> pics;
    GoodAttribute goodAttribute;
    //商品属性
    List<Attr> attrs;
    //商品分区
    Integer block;
    //属性
    private String attrType;

    public List<RGoodPics> getRGoodPics() {
        if (pics.size() == 0)
            return null;
        List<RGoodPics> rGoodPics = new ArrayList<>(pics.size());
        for (String pic : pics) {
            if (!StringUtils.isBlank(pic))
                rGoodPics.add(RGoodPics.builder().goodPic(pic).build());
        }
        return rGoodPics;
    }

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
