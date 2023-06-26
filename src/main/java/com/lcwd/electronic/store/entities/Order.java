package com.lcwd.electronic.store.entities;



import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "oders")
public class Order {
    @Id
    private Integer orderId;


    //pending,dispatched,delivered
    //enum se b krskte h
    private String orderStatus;

    //not-paid,paid
    //enum
    //boolean-false=>not paid || true=>paid
    private String paymentStatus;

    private int orderAmount;

    @Column(length = 1000)
    private String billingAddress;

    private String billingPhone;

    private String billingName;
    private Date orderedDate;

    private Date deliveredDate;

    //user
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER)
    private List<OrderItem> orderItems=new ArrayList<>();
}

