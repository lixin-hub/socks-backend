package com.lx.goodservice.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lx.common.base.Entity;
import com.lx.common.annotation.EntityField;
import com.lx.goodservice.service.GoodCategoryService;
import com.lx.goodservice.service.GoodDetailService;
import lombok.*;

import java.util.Date;

/**
 * @author LIXIN
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class GoodInfo extends Entity<GoodInfo>{
  /**
   * 使用uuid生成id
   */
  @TableId(type = IdType.ASSIGN_UUID)
  private String id;
  private String goodName;
  private String goodImage;
  private Date upTime;
  private Date downTime;

  /**
   * 使用装箱类，防止初始化为0
   */
  private Long stoke;
  /**
   * 商品价格
   */
  @TableField(exist = false)
  private float price;

  @TableField(exist = false)
  @EntityField(service = GoodDetailService.class)
  private GoodDetail goodDetail;
  @TableField(exist = false)
  @EntityField(service = GoodCategoryService.class)
  private GoodCategory goodCategory;

}
