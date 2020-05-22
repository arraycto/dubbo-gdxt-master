package com.iqilu.config;

import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Druid监控配置 - 02
 *
 * @author zhangyicheng
 * @date 2020/05/20
 */
@Configuration
public class DruidStatViewServlet extends StatViewServlet {

///	private static final long serialVersionUID = 1L;

    @Bean
    public ServletRegistrationBean<StatViewServlet> druidServlet() {

        ServletRegistrationBean<StatViewServlet> servletRegistrationBean =
                new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        // IP白名单 (没有配置或者为空，则允许所有访问)
///		servletRegistrationBean.addInitParameter("allow", "192.168.16.110, 127.0.0.1");
        // IP黑名单 (存在共同时，deny优先于allow)
///		servletRegistrationBean.addInitParameter("deny", "127.0.0.1");
        servletRegistrationBean.addInitParameter("loginUsername", "root");
        servletRegistrationBean.addInitParameter("loginPassword", "root");
        // 禁用HTML页面上的"Reset All"功能
        servletRegistrationBean.addInitParameter("resetEnable", "true");

        return servletRegistrationBean;

    }
}