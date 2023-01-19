package com.lx.userservice.conf;

//import com.lx.common.conf.JwtFilter;
import com.lx.common.conf.JwtFilter;
import com.lx.common.conf.JwtRealm;
import org.apache.shiro.mgt.DefaultSubjectFactory;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Autowired
    MyRealm myRealm;
    @Autowired
    JwtRealm jwtRealm;

    //    认证过滤器：
//    anon：无需认证即可访问，游客身份。
//    authc：必须认证（登录）才能访问。
//    authcBasic：需要通过 httpBasic 认证。
//    user：不一定已通过认证，只要是曾经被 Shiro 记住过登录状态的用户就可以正常发起请求，比如 rememberMe。
//
//    授权过滤器:
//    perms：必须拥有对某个资源的访问权限（授权）才能访问。
//    role：必须拥有某个角色权限才能访问。
//    port：请求的端口必须为指定值才可以访问。
//    rest：请求必须是 RESTful，method 为 post、get、delete、put。
//    ssl：必须是安全的 URL 请求，协议为 HTTPS。
    @Bean
    public ShiroFilterFactoryBean filterFactoryBean(@Qualifier("manager") DefaultWebSecurityManager manager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(manager);

        Map<String, Filter> map = new LinkedHashMap<>();
//        设置jwt过滤器
        map.put("jwt", jwtFilterRegBean().getFilter());
        factoryBean.setFilters(map);
        factoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition().getFilterChainMap());
//        设置登录页面
        factoryBean.setLoginUrl("/auth/login");

        return factoryBean;
    }
    /**
     * 在此处配置过滤器链
     */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        //添加拦截器，对路由进行限制
        /*
            anon:无需认证可以直接访问
            auth:必须认证才能访问
            user:必须拥有 记住我功能才能用
            perms:拥有对某个资源的权限才能访问
            role:拥有某个角色权限才能访问
         */
        //这些请求不通过jwtFilter
        chainDefinition.addPathDefinition("/auth/hasPhone/**","anon");
        chainDefinition.addPathDefinition("/auth/**","anon");
        // 所有请求通过我们自己的JWT Filter
        chainDefinition.addPathDefinition("/**","jwt");
        return chainDefinition;
    }
    /**
     * 配置JwtFilter过滤器,并设置为未注册状态
     */
    public FilterRegistrationBean<JwtFilter> jwtFilterRegBean() {
        FilterRegistrationBean<JwtFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        //添加JwtFilter  并设置为未注册状态
        filterRegistrationBean.setFilter(new JwtFilter());
        filterRegistrationBean.setEnabled(true);
        return filterRegistrationBean;
    }
    @Bean("manager")
    public DefaultWebSecurityManager manager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealms(Arrays.asList(myRealm, jwtRealm));
//        manager.setRememberMeManager(rememberMeManager());
        return manager;
    }

    @Bean
    public SubjectFactory subjectFactory() {
        return new DefaultSubjectFactory();
    }


    /**
     * 开启注解方式控制访问url
     *
     * @RequiresRoles 等同于 roles
     * @RequiresPermissions 等同于 perms
     * @RequiresAuthentication 等同于 authc
     * @RequiresUser 等同于 user
     * @RequiresGuest 跟 @RequiresUser 完全相反，即未登录也不能使用记住我功能，只能是游客
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(manager());
        return authorizationAttributeSourceAdvisor;
    }
}