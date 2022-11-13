package com.lx.goodservice.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lx.Entity;
import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GoodInfo extends Entity<GoodInfo>{
  @TableId(type = IdType.ASSIGN_UUID)
  private String id;
  private String goodName;
  private String goodImage;
  private Date upTime;
  private Date downTime;
  //使用装箱类，防止初始化为0
  private Long stoke;

}
