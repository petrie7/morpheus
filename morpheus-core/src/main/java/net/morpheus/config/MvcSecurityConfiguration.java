package net.morpheus.config;

import net.morpheus.controller.ViewController;
import net.morpheus.service.EmployeeDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

@SuppressWarnings("unused")
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
public class MvcSecurityConfiguration extends WebMvcConfigurerAdapter {

    @Resource
    private EmployeeDetailsService employeeDetailsService;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
    }

    @Bean
    public ViewController viewController() {
        return new ViewController(employeeDetailsService);
    }
}
