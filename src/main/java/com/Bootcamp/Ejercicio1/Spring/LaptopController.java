package com.Bootcamp.Ejercicio1.Spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LaptopController {
    @Autowired
    private LaptopRepository laptopRepository;

    @GetMapping("/laptops")
    public ResponseEntity<List<Laptop>> listLaptop(){
        return ResponseEntity.ok(laptopRepository.findAll());
    }

    @GetMapping("laptops/{id}")
    public ResponseEntity<?> findLaptopById(@PathVariable long id){
      return findLaptop(id);
    }

    @PostMapping("/create")
    public ResponseEntity<?> crearLaptop(@RequestBody Laptop laptop){
        if (laptop.getId() == null)
            return new ResponseEntity<>(laptopRepository.save(laptop), HttpStatus.CREATED);
        return new ResponseEntity<>("El id de la lapto no se debe definir", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateLaptop(@RequestBody Laptop laptop){
        if (laptop.getId() != null ){
            ResponseEntity<?> response = findLaptop(laptop.getId());
            if (response.getStatusCode() == HttpStatus.OK)
                return new ResponseEntity<>(laptopRepository.save(laptop), HttpStatus.OK);
            return response;
        }
        return new ResponseEntity<>("Se debe especificar un id para la laptop", HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteLaptopById(@PathVariable long id){
        ResponseEntity<?> response = findLaptop(id);
        if (response.getStatusCode() == HttpStatus.OK){
            laptopRepository.deleteById(id);
            return new ResponseEntity<>("Laptop borrada", HttpStatus.NO_CONTENT);
        }
        return response;
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteLaptopAll(){
        laptopRepository.deleteAll();
        return new ResponseEntity<>("Se han borrado todas las laptos", HttpStatus.NO_CONTENT);
    }

    private ResponseEntity<?> findLaptop(long id){
        Optional<Laptop> optionalLaptop = laptopRepository.findById(id);
        if (optionalLaptop.isPresent())
            return optionalLaptop.map((l)-> new ResponseEntity<>(l, HttpStatus.OK)).get();
        return new ResponseEntity<>("Laptop no encontrada", HttpStatus.NOT_FOUND);
    }
}
