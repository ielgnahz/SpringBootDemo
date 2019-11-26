package com.example.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = ExampleDataSourceConfiguration.PACKAGE, sqlSessionFactoryRef = "exampleSqlSessionFactory")
public class ExampleDataSourceConfiguration {

    static final String PACKAGE = "com.example.dao.mapper.example";
    static final String MAPPER_LOCATION = "classpath:mapper/example/*.xml";

    @Autowired
    private Environment environment;

    @Bean(name = "exampleDataSource")
    public DataSource exampleDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(environment.getProperty("example.datasource.url"));
        dataSource.setDriverClassName(environment.getProperty("example.datasource.driver"));
        dataSource.setUsername(environment.getProperty("example.datasource.username"));
        dataSource.setPassword(environment.getProperty("example.datasource.password"));
        return dataSource;
    }

    @Bean(name = "exampleTransactionManager")
    public DataSourceTransactionManager exampleTransactionManager(){
        return new DataSourceTransactionManager(exampleDataSource());
    }

    @Bean(name = "exampleSqlSessionFactory")
    public SqlSessionFactory exampleSqlSessionFactory(@Qualifier("exampleDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(ExampleDataSourceConfiguration.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

}
