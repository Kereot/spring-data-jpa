package com.gb.api;

import com.gb.entities.Product;
import com.gb.services.ProductGenerator;
import com.gb.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/name")
    public Product getByName(@RequestParam String name) {
        return productService.findByName(name).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("")
    public List<Product> findAll() {
        return productService.findAll();
    }

    @GetMapping("/add_random")
    public Product saveRandom() {
        return productService.save(ProductGenerator.generate());
    }

    @PostMapping("/add")
    public Product addProduct(@RequestBody Product product){
        return productService.save(product);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
    }

    @GetMapping("/min/{price}")
    public List<Product> findAllByPriceGreaterThan(@PathVariable String price) {
        return productService.findAllByPriceGreaterThan(Float.valueOf(price)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/max/{price}")
    public List<Product> findAllByPriceLessThan(@PathVariable String price) {
        return productService.findAllByPriceLessThan(Float.valueOf(price)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/between")
    public List<Product> findAllByPriceBetween(@RequestParam Float priceMin, @RequestParam Float priceMax) {
        return productService.findAllByPriceBetween(priceMin, priceMax).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
