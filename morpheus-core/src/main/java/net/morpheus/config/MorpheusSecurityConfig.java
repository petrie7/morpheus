package net.morpheus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;

@Configuration
@EnableWebSecurity
public class MorpheusSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .ldapAuthentication()
                .userDnPatterns("uid={0},ou=People,dc=isp,dc=company,dc=com")
                .groupSearchBase("ou=groups,ou=morpheus,ou=Applications,dc=isp,dc=company,dc=com")
                .groupSearchFilter("(&(objectClass=groupOfNames)(member={0}))")
                .contextSource(contextSource());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .authorizeRequests().antMatchers("/").authenticated()
                .and().csrf().disable();
    }

    @Bean
    public BaseLdapPathContextSource contextSource() {
        DefaultSpringSecurityContextSource contextSource = new DefaultSpringSecurityContextSource("ldap://localhost:51616");
        contextSource.setPooled(false);
        contextSource.setUserDn("uid=appauth,ou=auth,ou=morpheus,ou=Applications,dc=isp,dc=company,dc=com");
        contextSource.setPassword("secret");

        return contextSource;
    }
}
