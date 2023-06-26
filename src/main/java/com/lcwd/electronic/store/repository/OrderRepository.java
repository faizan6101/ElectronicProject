package com.lcwd.electronic.store.repository;

import com.lcwd.electronic.store.entities.Order;
import com.lcwd.electronic.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {
List<Order> findByUser(User user);

}
