package bo.com.tesla.security.aut;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Value("${proy.allowed.origins}")
	private String url;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()		
		.antMatchers("/", "/resources/**","/resources/public/**","/resources/templates/**","/resources/templates/css/**","/static/js/**","/static/css/**","/public/css/**")
        .permitAll()
        .antMatchers("/js/**").permitAll()
        .antMatchers("/css/**").permitAll()
        .antMatchers("/img/**").permitAll()        
        .antMatchers(HttpMethod.POST,"/home").permitAll()
		.anyRequest().authenticated()
		.and().cors().configurationSource(corsConfigurationSource());
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		//config.setAllowedOrigins(Arrays.asList("http://localhost:8080"));
		config.addAllowedOrigin("http://localhost:8080");
		//config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowCredentials(true);		
		/*config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization",
												"Cache-Control","Access-Control-Allow-Origin",
												"Origin","Accept",
												"X-Requested-With","Access-Control-Request-Method",
												"Access-Control-Request-Headers","Access-Control-Allow-Headers",
												"multipart/form-data"));*/
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		//config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
			
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
	
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter(){
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;	
	}

}
