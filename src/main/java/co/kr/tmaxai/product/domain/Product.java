package co.kr.tmaxai.product.domain;

import co.kr.tmaxai.product.dto.ProductDto;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id @GeneratedValue
    private Long id;

    private String name;

    private String description;

    private int quantity;

    public ProductDto toProductDto() {
        return ProductDto.builder()
                .name(name)
                .description(description)
                .quantity(quantity)
                .build();
    }

    public void update(ProductDto productDto) {
        Product newProduct = productDto.toProduct();
        name = newProduct.name;
        description = newProduct.description;
        quantity = newProduct.quantity;
    }
}