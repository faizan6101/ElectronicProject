package com.lcwd.electronic.store.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@Builder
@Entity
@Table(name="cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartItemId;
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private  int quantity;
    private double totalPrice;

//maping cart

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Cart_id")
    private Cart cart;

}
