package com.lx.goodservice.pojo;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class GoodInfo {

  private String id;
  private String goodName;
  private String goodImage;
  private Date upTime;
  private Date downTime;
  private long stoke;


}
