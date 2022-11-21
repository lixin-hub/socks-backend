package com.lx.userservice.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.lx.common.base.Entity;
import lombok.*;

/**
 * login_user
 *
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Builder
@EqualsAndHashCode(callSuper = true)
public class LoginUser extends Entity<LoginUser> {
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码：加密
     */
    private String password;
    //记住我
    @TableField(exist = false)
    private boolean rememberMe;


}