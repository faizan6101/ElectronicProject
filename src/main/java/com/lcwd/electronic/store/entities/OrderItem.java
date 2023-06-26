package com.lcwd.electronic.store.entities;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderItemId;
    private Integer quantity;
    private Integer totalPrice;
    @OneToOne
    private Product product;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;


}


