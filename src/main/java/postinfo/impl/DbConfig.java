package postinfo.impl;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class DbConfig {

    @Bean
    public DataSource getDataSource() throws IOException {
        Properties properties = new Properties();
        ClassPathResource databasePropResource = new ClassPathResource("database.properties");
        properties.load(databasePropResource.getInputStream());
        String user = properties.getProperty("db.user");
        String pass = properties.getProperty("db.pass");
        String driver = properties.getProperty("db.driver");
        String url = properties.getProperty("db.url");
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driver)
                .url(url)
                .username(user)
                .password(pass);
        return dataSourceBuilder.build();
    }

    @Bean
    public PlatformTransactionManager getTransactionManager() throws IOException {
        return new DataSourceTransactionManager(getDataSource());
    }
}

