package com.gb.market.core.api;

import com.gb.market.api.dto.ProductDto;
import com.gb.market.core.aspect.Timer;
import com.gb.market.core.converters.ProductConverter;
import com.gb.market.core.entities.Product;
import com.gb.market.core.services.ProductGenerator;
import com.gb.market.core.services.ProductService;
import com.gb.market.core.validators.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductConverter productConverter;
    private final ProductValidator productValidator;

    @GetMapping("/{id}")
    public ProductDto getById(@PathVariable Long id) {
        Product product = productService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return productConverter.entityToDto(product);
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
                productConverter::entityToDto
        );
    }

    @Timer
    @GetMapping("/add_random")
    public ProductDto saveRandom() {
        return productConverter.entityToDto(productService.save(ProductGenerator.generate()));
    }

    @PostMapping()
    public ProductDto addProduct(@RequestBody ProductDto productDto){
        productValidator.validate(productDto);
        Product product = productConverter.dtoToEntity(productDto);
        product.setId(null);
        product = productService.save(product);
        return productConverter.entityToDto(product);
    }

    @PutMapping()
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        productValidator.validate(productDto);
        Product product = productService.update(productDto);
        return productConverter.entityToDto(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
    }

}
