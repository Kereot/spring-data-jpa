package com.gb.wshomework;

import com.gb.entities.Product;
import com.gb.wshomework.generated.ProductWs;
import org.springframework.stereotype.Component;

@Component
public class WsProductConverter {

    public ProductWs entityToProductWs(Product product) {
        ProductWs productWs = new ProductWs();
        productWs.setId(product.getId());
        productWs.setName(product.getName());
        productWs.setPrice(product.getPrice());
        return productWs;
    }
}
