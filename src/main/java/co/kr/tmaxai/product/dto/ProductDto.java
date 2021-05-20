package co.kr.tmaxai.product.dto;

import co.kr.tmaxai.product.domain.Product;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
