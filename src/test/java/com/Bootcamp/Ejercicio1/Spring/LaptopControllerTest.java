package com.Bootcamp.Ejercicio1.Spring;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LaptopControllerTest {
    private TestRestTemplate testRestTemplate;
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    @Autowired
    LaptopRepository laptopRepository;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:"+port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
        laptopRepository.deleteAll();
        laptopRepository.save(new Laptop(null, "Asus",500));
        laptopRepository.save(new Laptop(null, "Lenovo",400));
        laptopRepository.save(new Laptop(null, "MSI",800));
    }


    @Test
    void listadoLaptop() {
        ResponseEntity<Laptop[]> response = testRestTemplate.getForEntity("/api/laptops", Laptop[].class);
        List<Laptop>laptops = Arrays.asList(response.getBody());
        assertEquals(3, laptops.size());
    }

    @Test
    void findLaptopById() {
        ResponseEntity<Laptop[]> response = testRestTemplate.getForEntity("/api/laptops", Laptop[].class);
        long id = response.getBody()[0].getId();
        ResponseEntity<Laptop> responseLaptop = testRestTemplate.getForEntity("/api/laptops/"+id, Laptop.class);
        Laptop laptop = responseLaptop.getBody();
        assertEquals(id, laptop.getId());

    }

    @Test
    void crearLaptop() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        String json = "{\n" +
                "    \"marca\": \"GigaByte\",\n" +
                "    \"precio\": 1500\n" +
                "  }";
        HttpEntity<String>request = new HttpEntity<>(json, headers);
        ResponseEntity<Laptop>response = testRestTemplate.exchange("/api/create", HttpMethod.POST, request, Laptop.class);
        Laptop laptop = response.getBody();
        assertEquals("GigaByte",laptop.getMarca());
    }

    @Test
    void updateLaptop() {
        ResponseEntity<Laptop[]> responseId = testRestTemplate.getForEntity("/api/laptops", Laptop[].class);
        long id = responseId.getBody()[0].getId();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        String json = "{\n" +
                "    \"id\":"+id+",\n"+
                "    \"marca\": \"GigaByte\",\n" +
                "    \"precio\": 1500\n" +
                "  }";
        HttpEntity<String>request = new HttpEntity<>(json, headers);
        ResponseEntity<Laptop>response = testRestTemplate.exchange("/api/update", HttpMethod.PUT, request, Laptop.class);
        System.out.println(response.getBody());
        Laptop laptop = response.getBody();
        assertEquals("GigaByte", laptop.getMarca());


    }

    @Test
    void deleteLaptopById() {
        ResponseEntity<Laptop[]> responseId = testRestTemplate.getForEntity("/api/laptops", Laptop[].class);
        long id = responseId.getBody()[0].getId();
        ResponseEntity<String> response = testRestTemplate.exchange("/api/delete/"+id, HttpMethod.DELETE,null, String.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

    }

    @Test
    void deleteLaptopAll() {
        ResponseEntity<String> response = testRestTemplate.exchange("/api/deleteAll", HttpMethod.DELETE,null, String.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}