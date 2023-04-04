package com.gb.market.core.identity;

import org.springframework.stereotype.Component;

@Component
public class ProductIdentityMap<Long, Product> extends LimitedMap<Long, Product> {

    public ProductIdentityMap() {
        super(10);
    }
}
