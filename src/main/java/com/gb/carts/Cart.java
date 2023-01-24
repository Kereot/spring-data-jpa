package com.gb.carts;

import com.gb.dto.ProductInCartDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    private List<ProductInCartDto> cart = new ArrayList<>();
}
