package co.kr.tmaxai.product.domain;

import co.kr.tmaxai.product.dto.ProductDto;
import lombok.*;

import javax.persistence.Column;
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
    @Column(unique = true)
    private Long id;

    @Column(unique = true)
    private String name;

    private String description;

    private int quantity;

    public void update(String name, String description, int quantity) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
    }

    public ProductDto toProductDto() {
        return ProductDto.builder()
                .name(name)
                .description(description)
                .quantity(quantity)
                .build();
    }
}
