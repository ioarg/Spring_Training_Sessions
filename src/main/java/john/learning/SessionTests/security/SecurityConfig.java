package john.learning.SessionTests.security;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:/security.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    Environment env;

    //Helper - parse a property value to an int
    private int getIntProperty(String propName) {
        return Integer.parseInt(env.getProperty(propName));
    }

    @Bean
    public DataSource securityDatasource(){
        ComboPooledDataSource dsource = new ComboPooledDataSource();
        //Setup JDBC Connection
        try {
            dsource.setDriverClass(env.getProperty("security.datasource.driver"));
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        dsource.setJdbcUrl(env.getProperty("security.datasource.url"));
        dsource.setUser(env.getProperty("security.datasource.username"));
        dsource.setPassword(env.getProperty("security.datasource.password"));
        //Setup Connection Pooling
        dsource.setInitialPoolSize(getIntProperty("security.connection_pool.initialPoolSize"));
        dsource.setMaxPoolSize(getIntProperty("security.connection_pool.maxPoolSize"));
        dsource.setMinPoolSize(getIntProperty("security.connection_pool.minPoolSize"));
        dsource.setMaxIdleTime(getIntProperty("security.connection_pool.maxIdleTime"));
        return dsource;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication().dataSource(securityDatasource());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/home")
                    .loginProcessingUrl("/authenticateUsers")
                    .defaultSuccessUrl("/welcomepage")
                    .failureUrl("/unauthorized")
                    .permitAll()
                .and()
                .logout()
                    .permitAll()
                .and()
                .exceptionHandling()
                    .accessDeniedPage("/unauthorized");
    }
}
