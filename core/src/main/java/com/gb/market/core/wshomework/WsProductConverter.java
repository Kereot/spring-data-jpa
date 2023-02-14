package com.gb.market.core.wshomework;

import com.gb.market.core.wshomework.generated.ProductWs;
import com.gb.market.core.entities.Product;
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
