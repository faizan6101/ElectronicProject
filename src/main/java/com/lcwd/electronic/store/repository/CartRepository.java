package com.lcwd.electronic.store.repository;

import com.lcwd.electronic.store.entities.Cart;
import com.lcwd.electronic.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Integer> {


 Optional<Cart> findByUser(User user);
}
