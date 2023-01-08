package com.gb.api;

import com.gb.dto.ProductDto;
import com.gb.entities.Product;
import com.gb.services.ProductGenerator;
import com.gb.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ProductDto getById(@PathVariable Long id) {
        return productService.findById(id).map(ProductDto::new).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping()
    public Page<ProductDto> find(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "min_price", required = false) Float minPrice,
            @RequestParam(name = "max_price", required = false) Float maxPrice,
            @RequestParam(name = "name", required = false) String name
    ) {
        if (page < 1) {
            page = 1;
        }
        return productService.find(minPrice, maxPrice, name, page).map(
                ProductDto::new
        );
    }

    @GetMapping("/add_random")
    public ProductDto saveRandom() {
        return new ProductDto(productService.save(ProductGenerator.generate()));
    }

    @PostMapping()
    public ProductDto addProduct(@RequestBody Product product){
        product.setId(null);
        return new ProductDto(productService.save(product));
    }

    @PutMapping()
    public ProductDto updateProduct(@RequestBody Product product) {
        return new ProductDto(productService.save(product));
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
    }


}
