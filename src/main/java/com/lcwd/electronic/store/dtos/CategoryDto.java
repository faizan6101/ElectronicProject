package com.lcwd.electronic.store.dtos;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CategoryDto {

    private Integer categoryId;


    @NotBlank(message = "title is required!!")
    @Size(min = 4,message = "title must be of min 4 character")
    private String title;

    @NotBlank(message = "description required")
    private String description;

    private String coverImage;
}
