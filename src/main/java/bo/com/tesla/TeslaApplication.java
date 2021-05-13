package bo.com.tesla;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class TeslaApplication implements CommandLineRunner {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(TeslaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String union = "union";
		String recaudador = "recaudador";
		String entidad = "entidad";
		System.out.println("ACallejas42  "+passwordEncoder.encode("ACallejas42"));
		System.out.println("ecamargo  "+passwordEncoder.encode("ecamargo"));
		System.out.println("entidad  "+passwordEncoder.encode(entidad));

	}
	/*@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/deudaCliente/upload").allowedOrigins("http://localhost:8080").allowedMethods("GET", "POST","PUT", "DELETE");
            }
        };
    }*/

}
