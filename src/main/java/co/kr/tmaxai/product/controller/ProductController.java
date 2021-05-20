package co.kr.tmaxai.product.controller;

import co.kr.tmaxai.product.domain.Product;
import co.kr.tmaxai.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> postProduct(@RequestBody Product productDto) throws Exception {
        Long productId = productService.createProduct(productDto);
        return new ResponseEntity<>(productId, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        List<Product> products = productService.retrieveAllProducts();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        Product product = productService.retrieveProduct(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
