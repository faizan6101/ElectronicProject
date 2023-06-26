package com.lcwd.electronic.store.dtos;

import com.lcwd.electronic.store.entities.Category;
import lombok.*;

import javax.persistence.Id;
import java.util.Date;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDto {
    @Id
    private Integer productId;
    private String title;

    private String description;
    private Double price;
    private Double discountedPrice;
    private Integer quantity;
    private Date addedDate;
    private boolean live;
    private boolean stock;
    private String  productImageName;

private CategoryDto category;
}
