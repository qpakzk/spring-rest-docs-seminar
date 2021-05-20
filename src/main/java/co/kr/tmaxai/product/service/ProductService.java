package co.kr.tmaxai.product.service;

import co.kr.tmaxai.product.domain.Product;
import co.kr.tmaxai.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public Product retrieveProduct(Long id) {
        return productRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public List<Product> retrieveAllProducts() {
        return productRepository.findAll();
    }

    public Product updateProduct(Long id, String name, String description, int quantity) {
        Product product = productRepository.findById(id).get();
        product.update(name, description, quantity);
        return product;
    }

    public Product deleteProduct(Long id) {
        Product product = productRepository.findById(id).get();
        productRepository.delete(product);
        return product;
    }
}
