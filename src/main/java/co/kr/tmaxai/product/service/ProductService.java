package co.kr.tmaxai.product.service;

import co.kr.tmaxai.product.domain.Product;
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

    public Long createProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        return savedProduct.getId();
    }

    @Transactional(readOnly = true)
    public Product retrieveProduct(Long productId) {
        return productRepository.findById(productId).get();
    }

    @Transactional(readOnly = true)
    public List<Product> retrieveAllProducts() {
        return productRepository.findAll();
    }
}
