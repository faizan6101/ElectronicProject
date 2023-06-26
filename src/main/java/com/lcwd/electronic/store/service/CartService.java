//package com.lcwd.electronic.store.service;
//
//import com.lcwd.electronic.store.dtos.AddItemToCartRequest;
//import com.lcwd.electronic.store.dtos.CartDto;
//import org.springframework.stereotype.Service;
//
//
//public interface CartService {
//    //add item to cart
//
//    //case1 :cart for user is not available: we will create the cart and then add item
//
//    //case2: cart available add the item to cart
//
//    CartDto addItemToCart(Integer userId, AddItemToCartRequest request);
//
//    //remove item from cart
//
//    void removeItemFromCart(Integer userId,int cartItem);
//
//    //remove all item from cart
//    void clearCart(Integer userId);
//    CartDto getCartByUser(Integer userId);
//}
