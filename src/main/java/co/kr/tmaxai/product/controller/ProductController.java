package co.kr.tmaxai.product.controller;

import co.kr.tmaxai.product.domain.Product;
import co.kr.tmaxai.product.dto.ProductDto;
import co.kr.tmaxai.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/product/new")
    public ResponseEntity<?> createProduct(@RequestBody ProductDto productDto) throws Exception {
        Product product = productDto.toProduct();
        Long id = productService.createProduct(product);
        Map<String, Long> response = new HashMap<>();
        response.put("id", id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts() {
        List<Product> products = productService.retrieveAllProducts();
        List<ProductDto> productDtos = new ArrayList<>(products.size());
        for (Product product : products) {
            productDtos.add(product.toProductDto());
        }
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        Product product = productService.retrieveProduct(id);
        return new ResponseEntity<>(product.toProductDto(), HttpStatus.OK);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        Product product = productService.updateProduct(id, productDto.getName(), productDto.getDescription(), productDto.getQuantity());
        return new ResponseEntity<>(product.toProductDto(), HttpStatus.OK);
    }

    @PostMapping("/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        Product product = productService.retrieveProduct(id);
        Map<String, Long> response = new HashMap<>();
        response.put("id", id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
