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
public class GoodDetail extends Entity<GoodDetail> {
  @TableId(type = IdType.INPUT)
  private String id;
  private String goodIntroduce;
  private String lastIntroduce;
  private String catOneLevel;
  private String catTowLevel;
  private String catThreeLevel;

}
