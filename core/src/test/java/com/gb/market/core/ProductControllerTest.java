package com.gb.market.core;

import com.gb.market.core.converters.ProductConverter;
import com.gb.market.core.entities.Product;
import com.gb.market.core.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ProductConverter productConverter;

    @MockBean
    ProductService productService;

    @Test
    @WithMockUser(username = "admin", authorities = "READ_AND_WRITE")
    public void getById() throws Exception {
        Product product = new Product(1L, "One", BigDecimal.valueOf(10), null, null);
        given(productService.findById(1L)).willReturn(Optional.of(product));

        mockMvc.perform(get("/api/v1/products/" + "1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.name", is(product.getName()))
                );
    }
}
