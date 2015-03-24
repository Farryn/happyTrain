package config;

import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@Configuration
@ComponentScan({ "controllers", "entities", "dao" , "services"})
@EnableTransactionManagement
@Import({ SecurityConfig.class })
public class AppConfig extends WebMvcConfigurerAdapter{

	@Bean
    public SessionFactory sessionFactory() {
            LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource());
            builder.scanPackages("entities")
                  .addProperties(getHibernateProperties());

            return builder.buildSessionFactory();
    }

	private Properties getHibernateProperties() {
	            Properties prop = new Properties();
	            prop.put("hibernate.format_sql", "true");
	            prop.put("hibernate.show_sql", "true");
	            prop.put("hibernate.dialect", 
	                "org.hibernate.dialect.MySQL5Dialect");
	            prop.put("hibernate.current_session_context_class", "thread");
	            return prop;
	    }
	
	@Bean(name = "dataSource")
	public BasicDataSource dataSource() {
	
		BasicDataSource ds = new BasicDataSource();
	        ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost/happytrain");
		ds.setUsername("root");
		return ds;
	}
	
	//Create a transaction manager
	@Bean
	    public HibernateTransactionManager txManager() {
	            return new HibernateTransactionManager(sessionFactory());
	    }
	
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
	
}