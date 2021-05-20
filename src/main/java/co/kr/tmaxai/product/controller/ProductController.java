package co.kr.tmaxai.product.controller;

import co.kr.tmaxai.product.domain.Product;
import co.kr.tmaxai.product.dto.ProductDto;
import co.kr.tmaxai.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping
    public ResponseEntity<?> postProduct(@RequestBody ProductDto productDto) throws Exception {
        Long productId = productService.createProduct(productDto);
        return new ResponseEntity<>(productId, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        List<Product> products = productService.retrieveAllProducts();

        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = product.toProductDto();
            productDtos.add(productDto);
        }

        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        Product product = productService.retrieveProduct(id);
        ProductDto productDto = product.toProductDto();
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        productService.updateProduct(id, productDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
