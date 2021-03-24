package bo.com.tesla.security.aut;

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
		
		http.headers().disable()
		.authorizeRequests()
		.antMatchers(HttpMethod.GET,"/api/ReportEntidad/findDeudasByArchivoIdAndEstado/{archivoId}/{export}").permitAll()
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
		config.addAllowedOrigin(url);		
		config.setAllowCredentials(true);			
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
	
			
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
