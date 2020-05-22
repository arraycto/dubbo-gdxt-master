package com.iqilu.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * Druid监控配置 - 01
 * url: http://localhost:port/druid/login.html
 *
 * @author zhangyicheng
 * @date 2020/05/20
 */
@WebFilter(filterName = "druidWebStatFilter", urlPatterns = "/*",
        initParams = {
                // 忽略资源
                @WebInitParam(name = "exclusions", value = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")
        }
)
public class DruidStatFilter extends WebStatFilter {
    @Bean
    public FilterRegistrationBean<WebStatFilter> getFilterRegistrationBean() {
        FilterRegistrationBean<WebStatFilter> filter = new FilterRegistrationBean<WebStatFilter>();
        filter.setFilter(new WebStatFilter());
        filter.setName("druidWebStatFilter");
        filter.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
        filter.addUrlPatterns("/*");
        return filter;
    }

    @Bean
    public ServletRegistrationBean<StatViewServlet> getServletRegistrationBean() {
        ServletRegistrationBean<StatViewServlet> servlet = new ServletRegistrationBean<>(new StatViewServlet(),
                "/druid/*");
		// DruidStatViewServlet
        servlet.setName("druidStatViewServlet");
        servlet.addInitParameter("resetEnable", "false");
        return servlet;
    }
}  