package com.lx.goodservice.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lx.common.base.Entity;
import lombok.*;

/**
 * @author LIXIN
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class RGoodPics extends Entity<RGoodPics> {
    /**
     * 使用uuid生成id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String goodId;
    private String goodPic;
    private String status="0";
}
