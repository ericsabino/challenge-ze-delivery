package br.com.zedelivery.parceiroze;

import com.bedatadriven.jackson.datatype.jts.JtsModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ParceirozeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParceirozeApplication.class, args);
	}

}
