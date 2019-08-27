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
@MapperScan(basePackages = MySqlDatasourceConfig2.PACKAGE,
        sqlSessionFactoryRef = "mysqlSqlSessionFactoryTwo")
public class MySqlDatasourceConfig2 {
    // oracledao扫描路径
    static final String PACKAGE = "com.fush.mapper.db2";
    // mybatis mapper扫描路径
    static final String MAPPER_LOCATION = "classpath:mapper/db2/*.xml";

    @Bean(name = "mysqlDataSourceDbTwo")
    @ConfigurationProperties("spring.datasource.druid.db2")
    public DataSource mysqlDataSourceDbTwo() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "mysqlTransactionManagerTwo")
    public DataSourceTransactionManager mysqlTransactionManagerTwo() {
        return new DataSourceTransactionManager(mysqlDataSourceDbTwo());
    }

    @Bean(name = "mysqlSqlSessionFactoryTwo")
    public SqlSessionFactory mysqlSqlSessionFactoryTwo(@Qualifier("mysqlDataSourceDbTwo") DataSource dataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        //如果不使用xml的方式配置mapper，则可以省去下面这行mapper location的配置。
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(MySqlDatasourceConfig2.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}