package bo.com.tesla;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TeslaApplication implements CommandLineRunner {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(TeslaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String administrador = "administrador";
		String recaudador = "recaudador";
		String entidad = "entidad";
		System.out.println("administrador  "+passwordEncoder.encode(administrador));
		System.out.println("recaudador  "+passwordEncoder.encode(recaudador));
		System.out.println("entidad  "+passwordEncoder.encode(entidad));

	}

}
