package com.lx.userservice.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.lx.common.base.Entity;
import com.lx.userservice.pojo.permission.Role;
import lombok.*;

import java.util.List;

/**
 * user_info
 *
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserInfo extends Entity<UserInfo> {
    /**
     * 用户id
     */
    private String id;

    /**
     * 简介
     */
    private String note;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 头像
     */
    private String headImage;

    /**
     * 角色
     */
    @TableField(exist = false)
    private List<Role> roles;
    @TableField(exist = false)
    private String username;

}