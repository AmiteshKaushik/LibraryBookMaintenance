package bookmaintenance.springboot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages={"bookmaintenance"})
public class SpringBootRestApiApp {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestApiApp.class, args);
	}
}
