package org.proteininformationresource.pirsitepredict.config;

import static org.springframework.context.annotation.ComponentScan.Filter;

import java.util.concurrent.Executor;

import org.proteininformationresource.pirsitepredict.Application;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan(basePackageClasses = Application.class, excludeFilters = @Filter({Controller.class, Configuration.class}))
class ApplicationConfig {
	
//	@Bean
//	public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
//		PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
//		ppc.setLocation(new ClassPathResource("/persistence.properties"));
//		return ppc;
//	}
//	
//	@Bean
//	public static PropertyPlaceholderConfigurer pirSitePredictPropertyPlaceholderConfigurer() {
//		PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
//		ppc.setLocation(new ClassPathResource("/pirsitepredict.properties"));
//		return ppc;
//	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer properties(){
	   PropertySourcesPlaceholderConfigurer pspc =
	      new PropertySourcesPlaceholderConfigurer();
	   Resource[] resources = new ClassPathResource[ ]
	      { new ClassPathResource( "persistence.properties" ), new ClassPathResource( "pirsitepredict.properties" )};
	  pspc.setLocations( resources );
	  pspc.setIgnoreUnresolvablePlaceholders( true );
	  //System.out.println(pspc.toString());
	  return pspc;
	}
	
	 @Bean
	    public Executor getAsyncExecutor() {
	        //log.debug("Creating Async Task Executor");
	        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	        // to customize with your requirements
	        executor.setCorePoolSize(5);
	        executor.setMaxPoolSize(40);
	        executor.setQueueCapacity(100);
	        executor.setThreadNamePrefix("MyExecutor-");
	        executor.initialize();
	        return executor;
	    }
	
}