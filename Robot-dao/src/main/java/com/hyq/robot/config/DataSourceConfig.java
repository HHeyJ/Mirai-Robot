package com.hyq.robot.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import static org.springframework.transaction.TransactionDefinition.ISOLATION_DEFAULT;

/**
 * @author nanke
 * @date 2020/4/4 下午2:35
 */
@Configuration
@MapperScan(basePackages = {FilePathConstant.DAO_PACKAGE},sqlSessionFactoryRef = "sessionFactory")
public class DataSourceConfig {

    @javax.annotation.Resource
    private DataSourceProperty dataSourceProperty;

    /*********************
     *                   *
     *      数据源        *
     *                   *
     *********************/

    @Bean(name = "dataSource")
    public DruidDataSource getDataSource() {

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(dataSourceProperty.getDriverClass());
        dataSource.setUrl(dataSourceProperty.getUrl());
        dataSource.setUsername(dataSourceProperty.getName());
        dataSource.setPassword(dataSourceProperty.getPassWord());
        return dataSource;
    }

    /*********************
     *                   *
     *      事务源        *
     *                   *
     *********************/

    @Bean(name = "transactionTemplate")
    public TransactionTemplate teambuyTransactionTemplate(@Qualifier("dataSource") DruidDataSource dataSource) {

        DataSourceTransactionManager transcationManager = new DataSourceTransactionManager(dataSource);
        TransactionTemplate transactionTemplate = new TransactionTemplate();
        transactionTemplate.setIsolationLevel(ISOLATION_DEFAULT);
        transactionTemplate.setTimeout(10);
        transactionTemplate.setTransactionManager(transcationManager);
        return transactionTemplate;
    }

    @Bean("sessionFactory")
    public SqlSessionFactory sessionFactory(@Qualifier("dataSource") DruidDataSource dataSource) throws Exception {

        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(FilePathConstant.MAPPER_PACKAGE));
        return sessionFactory.getObject();
    }
}
