package co.kr.tmaxai.product.dto;

import co.kr.tmaxai.product.domain.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {
    private String name;
    private String description;
    private int quantity;

    public Product toProduct() {
        return Product.builder()
                .name(name)
                .description(description)
                .quantity(quantity)
                .build();
    }
}
