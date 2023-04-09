package com.Bootcamp.Ejercicio1.Spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Ejercicio1SpringApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Ejercicio1SpringApplication.class, args);
		LaptopRepository laptopRepository = context.getBean(LaptopRepository.class);
		laptopRepository.deleteAll();
		laptopRepository.save(new Laptop(null, "Asus",500));
		laptopRepository.save(new Laptop(null, "Lenovo",400));
		laptopRepository.save(new Laptop(null, "MSI",800));
	}

}
