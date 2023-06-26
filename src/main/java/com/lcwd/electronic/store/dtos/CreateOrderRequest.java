package com.lcwd.electronic.store.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class CreateOrderRequest {

    @NotBlank(message = "cart id is required")
    private Integer cartId;

    @NotBlank(message = "user id is required")
    private Integer userId;

    private String orderStatus="PENDING";
    private String paymentStatus="NOT-PAID";
    @NotBlank(message = "billingAddress is required")
    private String billingAddress;

    @NotBlank(message = "billingPhone is required")
    private String billingPhone;

    @NotBlank(message = "billingName is required")
    private String billingName;


}
