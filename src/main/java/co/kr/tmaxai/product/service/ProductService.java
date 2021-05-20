package co.kr.tmaxai.product.service;

import co.kr.tmaxai.product.domain.Product;
import co.kr.tmaxai.product.dto.ProductDto;
import co.kr.tmaxai.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public Long createProduct(ProductDto productDto) {
        Product product = productDto.toProduct();
        Product savedProduct = productRepository.save(product);
        return savedProduct.getId();
    }

    public void updateProduct(Long productId, ProductDto productDto) {
        Product product = productRepository.findById(productId).get();
        product.update(productDto);
    }

    @Transactional(readOnly = true)
    public Product retrieveProduct(Long productId) {
        return productRepository.findById(productId).get();
    }

    @Transactional(readOnly = true)
    public List<Product> retrieveAllProducts() {
        return productRepository.findAll();
    }

    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).get();
        productRepository.delete(product);
    }
}
