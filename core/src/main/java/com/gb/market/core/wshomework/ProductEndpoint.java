package com.gb.market.core.wshomework;

import com.gb.market.core.wshomework.generated.GetAllProductsRequest;
import com.gb.market.core.wshomework.generated.GetAllProductsResponse;
import com.gb.market.core.wshomework.generated.GetProductByIdRequest;
import com.gb.market.core.wshomework.generated.GetProductByIdResponse;
import com.gb.market.core.entities.Product;
import com.gb.market.core.services.ProductService;
import jakarta.xml.bind.JAXBElement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.namespace.QName;

@Endpoint
@RequiredArgsConstructor
@Slf4j
public class ProductEndpoint {
    private static final String NAMESPACE_URI = "http://www.gb.com/products";
    private final ProductService productService;
    private final WsProductConverter wsProductConverter;


// I'm getting "No adapter for endpoint" error with this one.
//
//    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByIdRequest")
//    @ResponsePayload
//    public GetProductByIdResponse getProductById(@RequestPayload GetProductByIdRequest request) {
//        GetProductByIdResponse response = new GetProductByIdResponse();
//        Product product = productService.findById(request.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//        response.setProduct(wsProductConverter.entityToProductWs(product));
//        return response;
//    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByIdRequest")
    @ResponsePayload
    // this one doesn't get Id
    public JAXBElement<GetProductByIdResponse> getProductById(@RequestPayload JAXBElement<GetProductByIdRequest> request) {
        GetProductByIdResponse response = new GetProductByIdResponse();
        log.info(String.valueOf(request.isNil())); // it is not null
        log.info(String.valueOf(request.getValue().getId())); // it is null
        request.getValue().setId(1L); // works
        Product product = productService.findById(request.getValue().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        response.setProduct(wsProductConverter.entityToProductWs(product));
        JAXBElement<GetProductByIdResponse> jax = new JAXBElement<>(QName.valueOf("GetProductByIdResponse"), GetProductByIdResponse.class, response);
        log.info(jax.getValue().getProduct().getName());
        return jax;
    }

// I'm getting "No adapter for endpoint" error with this one.
//
//    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
//    @ResponsePayload
//    public GetAllProductsResponse getAllProducts(@RequestPayload GetAllProductsRequest request) {
//        GetAllProductsResponse response = new GetAllProductsResponse();
//        productService.findAllForWs().stream()
//                .map(wsProductConverter::entityToProductWs)
//                .forEach(response.getProducts()::add);
//        return response;
//    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
    @ResponsePayload
    public JAXBElement<GetAllProductsResponse> getAllProducts(@RequestPayload JAXBElement<GetAllProductsRequest> request) {
        GetAllProductsResponse response = new GetAllProductsResponse();
        productService.findAllForWs().stream()
                .map(wsProductConverter::entityToProductWs)
                .forEach(response.getProducts()::add);
        JAXBElement<GetAllProductsResponse> jax = new JAXBElement<>(QName.valueOf("GetAllProductsResponse"), GetAllProductsResponse.class, response);
        log.info(jax.getValue().getProducts().get(0).getName());
        return jax;
    }
}
