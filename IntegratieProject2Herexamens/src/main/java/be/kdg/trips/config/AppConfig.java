package be.kdg.trips.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.Properties;

@EnableWebMvc
@Configuration
@ComponentScan({ "be.kdg.trips.*" })
@EnableTransactionManagement
@EnableGlobalMethodSecurity(prePostEnabled=true)
@PropertySource("classpath:application.properties")
public class AppConfig extends WebMvcConfigurerAdapter {


	@Bean
    public SessionFactory sessionFactory() {

        LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource());
        builder
        	.scanPackages("be.kdg.trips.model")
            .addProperties(getHibernateProperties());

        return builder.buildSessionFactory();
    }

	private Properties getHibernateProperties() {
        Properties prop = new Properties();
        prop.put("hibernate.format_sql", "true");
        prop.put("hibernate.show_sql", "true");
        prop.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		prop.put("hibernate.hbm2ddl.auto", "update");

		return prop;
    }
	
	@Bean(name = "dataSource")
	public BasicDataSource dataSource() {
		
		BasicDataSource ds = new BasicDataSource();
	    ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/herexamen_ip2");
		ds.setUsername("herexamen");
		ds.setPassword("herexamen");

		return ds;
	}
	
	@Bean
    public HibernateTransactionManager txManager() {
        return new HibernateTransactionManager(sessionFactory());
    }
		
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");

	}

	@Bean(name="MailSender")
	public JavaMailSenderImpl mailSender(){
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername("integratieprojectherexamen@gmail.com");
		mailSender.setPassword("herexamen");
		Properties prop = new Properties();
		prop.put("mail.transport.protocol","smtp");
		prop.put("mail.smtp.auth",true);
		prop.put("mail.smtp.starttls.enable",true);
		prop.put("mail.debug",true);
		mailSender.setJavaMailProperties(prop);
		return mailSender;
	}

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();

		return resolver;
	}

}