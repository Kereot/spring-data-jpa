package com.gb.api;

import com.gb.carts.Cart;
import com.gb.converters.ProductConverter;
import com.gb.dto.ProductDto;
import com.gb.dto.ProductInCartDto;
import com.gb.entities.Product;
import com.gb.services.ProductGenerator;
import com.gb.services.ProductService;
import com.gb.validators.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductConverter productConverter;
    private final ProductValidator productValidator;
    private final Cart cart;

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

    @GetMapping("/add_random")
    public ProductDto saveRandom() {
        return productConverter.entityToDto(productService.save(ProductGenerator.generate()));
    }

    @GetMapping("/cart")
    public Page<ProductInCartDto> loadCart() {
        return new PageImpl<>(cart.getCart(), PageRequest.of(0, 10), cart.getCart().size());
    }

    @PostMapping()
    public ProductDto addProduct(@RequestBody ProductDto productDto){
        productValidator.validate(productDto);
        Product product = productConverter.dtoToEntity(productDto);
        product.setId(null);
        product = productService.save(product);
        return productConverter.entityToDto(product);
    }

    @PostMapping("/cart")
    public void addToCart(@RequestBody ProductDto productDto){
        int quantity = 0;
        for (int i = 0; i < cart.getCart().size(); i++) {
            if (Objects.equals(cart.getCart().get(i).getId(), productDto.getId())) {
                quantity = cart.getCart().get(i).getQuantity();
                cart.getCart().remove(i);
                break;
            }
        }
        cart.getCart().add(productConverter.productDtoToProductInCartDto(productDto, quantity + 1));
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

    @DeleteMapping("/cart/{id}")
    public void deleteProductFromCart(@PathVariable Long id){
        for (int i = 0; i < cart.getCart().size(); i++) {
            ProductInCartDto productInCartDto = cart.getCart().get(i);
            if (Objects.equals(productInCartDto.getId(), id)) {
                productInCartDto.setQuantity(productInCartDto.getQuantity() - 1);
                if (productInCartDto.getQuantity() == 0) {
                    cart.getCart().remove(i);
                }
                break;
            }
        }
    }


}
