package com.iqilu.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * 数据源配置
 *
 * @author zhangyicheng
 * @date 2020/05/20
 */
@Configuration
@MapperScan(basePackages = "com.iqilu.dao.mysqldao",
        sqlSessionFactoryRef = "slaveSqlSessionFactory01")
public class MysqlDataSourceConfig {

    /**
     * 绑定yml文件的配置参数
     *
     * @author zhangyicheng
     * @date 2020/05/20
     */
    @Bean(name = "slaveDataSource01")
    @ConfigurationProperties(prefix = "spring.datasource.database-mysql")
    public DataSource slaveDataSource() {

        DataSource druidDataSource = DataSourceBuilder.create().build();
        return druidDataSource;
    }

    /**
     * 绑定mapper的配置文件
     *
     * @author zhangyicheng
     * @date 2020/05/20
     */
    @Bean(name = "slaveSqlSessionFactory01")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("slaveDataSource01") DataSource dataSource) throws Exception {

        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:mybatis/mybatis-mysql/*.xml"));

        return sessionFactoryBean.getObject();
    }

}
