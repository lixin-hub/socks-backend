package com.lx.goodservice.pojo;

import com.lx.Entity;
import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GoodDetail extends Entity<GoodDetail> {

  private String id;
  private String goodDetail;
  private String lastDetail;
  private Date updateTime;



}
