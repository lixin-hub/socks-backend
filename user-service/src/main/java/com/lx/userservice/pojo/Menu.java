package com.lx.userservice.pojo;

import com.lx.common.base.TreeEntity;
import lombok.*;

import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu extends TreeEntity<Menu> {
    String id;
    String path;
    String name;
    int order;
    String parent;
}
