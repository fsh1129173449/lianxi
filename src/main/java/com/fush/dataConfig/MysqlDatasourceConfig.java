package com.fush.dataConfig;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = MysqlDatasourceConfig.PACKAGE, sqlSessionFactoryRef = "mysqlSqlSessionFactoryOne")
public class MysqlDatasourceConfig {

    // mysqldao扫描路径
    static final String PACKAGE = "com.fush.mapper.db1";
    // mybatis mapper扫描路径
    static final String MAPPER_LOCATION = "classpath:mapper/db1/*.xml";

    @Primary
    @Bean(name = "mysqlDataSourceDbOne")
    @ConfigurationProperties("spring.datasource.druid.db1")
    public DataSource mysqlDataSourceDbOne() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "mysqlTransactionManagerOne")
    @Primary
    public DataSourceTransactionManager mysqlTransactionManagerOne() {
        return new DataSourceTransactionManager(mysqlDataSourceDbOne());
    }

    @Bean(name = "mysqlSqlSessionFactoryOne")
    @Primary
    public SqlSessionFactory mysqlSqlSessionFactoryOne(@Qualifier("mysqlDataSourceDbOne") DataSource dataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        //如果不使用xml的方式配置mapper，则可以省去下面这行mapper location的配置。
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(MysqlDatasourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}