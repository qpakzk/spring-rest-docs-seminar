package co.kr.tmaxai.product.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id @GeneratedValue
    private Long id;

    private String name;

    private String description;

    private int quantity;

    public void update(Product product) {
        name = product.name;
        description = product.description;
        quantity = product.quantity;
    }
}
