package be.wolkmaan.klimtoren.cms.config;

import com.google.common.collect.Lists;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan("be.wolkmaan.klimtoren")
public class WebAppConfig extends WebMvcConfigurerAdapter {
    private static final List<String> STATIC_NONSECURED_RESOURCES = 
            Lists.newArrayList(
                    "css", 
                    "js", 
                    "sound", 
                    "fonts", 
                    "img", 
                    "ajax", 
                    "security", 
                    "resources");
    
    @Autowired
    private DataSource dataSource;
    @Resource
    private Environment env;
    @Autowired
    private WebSecurityManager securityManager;
   

    /**
     * Since we don't have any controller logic, simpler to just define
     * controller for page using View Controller. Note: had to extend
     * WebMvcConfigurerAdapter to get this functionality
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/new").setViewName("new");
        registry.addViewController("/admin/users").setViewName("admin/users");
        registry.addViewController("/login").setViewName("/security/login");
        registry.addViewController("/logout").setViewName("/security/logout");
        registry.addViewController("/register").setViewName("/security/register");
        registry.addViewController("/forgotpassword").setViewName("/security/forgotpassword");

    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/view/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        STATIC_NONSECURED_RESOURCES.stream()
                .forEach((r)-> {
                   registry.addResourceHandler("/" + r + "/**").addResourceLocations("/" + r + "/");
                });
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("i18n/messages");
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterBean(){
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        
        Map<String, String> definitionsMap = new LinkedHashMap<>();
        
        definitionsMap.put("/security/login.jsp", "authc");
        definitionsMap.put("/logout", "logout");
        definitionsMap.put("/forgotpassword", "anon");
        STATIC_NONSECURED_RESOURCES.stream()
                .forEach(r -> {
                    definitionsMap.put("/" + r + "/**", "anon");
                });
        
        definitionsMap.put("/**", "authc");
        shiroFilter.setFilterChainDefinitionMap(definitionsMap);
        
        shiroFilter.setLoginUrl("/security/login.jsp");
        shiroFilter.setSuccessUrl("/");
        shiroFilter.setSecurityManager(securityManager);
        
        return shiroFilter;
    }
}
