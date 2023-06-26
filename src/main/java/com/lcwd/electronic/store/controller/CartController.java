//package com.lcwd.electronic.store.controller;
//
//import com.lcwd.electronic.store.dtos.AddItemToCartRequest;
//import com.lcwd.electronic.store.dtos.CartDto;
//import com.lcwd.electronic.store.payloads.ApiResponse;
//import com.lcwd.electronic.store.service.CartService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/carts")
//public class CartController {
//
//    @Autowired
//    private CartService cartService;
//
//    //add items to cart
//    @PostMapping("/{userId}")
//    public ResponseEntity<CartDto>addItemToCart(@PathVariable Integer userId, @RequestBody AddItemToCartRequest request){
//        CartDto cartDto = cartService.addItemToCart(userId, request);
//
//        return new ResponseEntity<>(cartDto, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{userId}/items/{itemId}")
//    public ResponseEntity<ApiResponse>removeItemFromCart(@PathVariable Integer userId,@PathVariable int cartItem){
//        cartService.removeItemFromCart(userId,cartItem);
//        ApiResponse response = ApiResponse.builder()
//                .message("item is removed")
//                .success(true)
//                .status(HttpStatus.OK)
//                .build();
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{userId}")
//    public ResponseEntity<ApiResponse>clearCart(@PathVariable Integer userId){
//        cartService.clearCart(userId);
//        ApiResponse response = ApiResponse.builder()
//                .message("cart cleared")
//                .success(true)
//                .status(HttpStatus.OK)
//                .build();
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//    @GetMapping("/{userId}")
//    public ResponseEntity<CartDto>getCart(@PathVariable Integer userId){
//        CartDto cartDto = cartService.getCartByUser(userId);
//
//        return new ResponseEntity<>(cartDto, HttpStatus.OK);
//    }
//}
