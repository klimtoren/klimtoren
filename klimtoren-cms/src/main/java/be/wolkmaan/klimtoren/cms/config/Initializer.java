package be.wolkmaan.klimtoren.cms.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

public class Initializer implements WebApplicationInitializer {
	
	@Override
	public void onStartup(ServletContext container) throws ServletException {
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(WebAppConfig.class, SecurityConfig.class);

		container.addListener(new ContextLoaderListener(rootContext));
		//ctx.setServletContext(servletContext);
                
                //servletContext.addListener(new EnvironmentLoaderListener());
                
                
                // Create the dispatcher servlet's Spring application context
                AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
                dispatcherContext.setServletContext(container);
                dispatcherContext.setParent(rootContext);
                dispatcherContext.register(WebAppConfig.class);
 
 
                // Register and map the dispatcher servlet
                ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
                dispatcher.setLoadOnStartup(1);
                dispatcher.addMapping("/");

 
                container.addFilter("shiroFilter", new DelegatingFilterProxy("shiroFilterBean", dispatcherContext))
                           .addMappingForUrlPatterns(null, false, "/*");

		
        }
}