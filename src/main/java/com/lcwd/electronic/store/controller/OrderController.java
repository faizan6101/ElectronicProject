//package com.lcwd.electronic.store.controller;
//
//import com.lcwd.electronic.store.dtos.CreateOrderRequest;
//import com.lcwd.electronic.store.dtos.OrderDto;
//import com.lcwd.electronic.store.dtos.PageableResponse;
//import com.lcwd.electronic.store.entities.Order;
//import com.lcwd.electronic.store.payloads.ApiResponse;
//import com.lcwd.electronic.store.service.OrderService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.List;
//
//@RestController
//@RequestMapping("/orders")
//public class OrderController {
//
//    @Autowired
//    private OrderService orderService;
//
//
//    @PostMapping
//    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest){
//
//        OrderDto order = orderService.createOrder(createOrderRequest);
//       return new ResponseEntity<>(order, HttpStatus.CREATED);
//
//    }
//
//
//    @DeleteMapping("/{orderId}")
//    public ResponseEntity<ApiResponse> removeOrder(@PathVariable Integer orderId ){
//        orderService.removeOrder(orderId);
//        ApiResponse apiResponse = ApiResponse.builder()
//                .message("order is removed")
//                .status(HttpStatus.OK)
//                .success(true)
//                .build();
//        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
//    }
//
//
////get orders of user
//
//    @GetMapping("/users/{userId}")
//    public ResponseEntity<List<OrderDto>> getOrdersOfUser(@PathVariable Integer userId){
//        List<OrderDto> ordersOfUser = orderService.getOrdersOfUser(userId);
//        return new ResponseEntity<>(ordersOfUser,HttpStatus.OK);
//    }
//
//    @GetMapping
//    public ResponseEntity<PageableResponse<OrderDto>> getOrders(
//     @RequestParam(value = "pageNumber",defaultValue = "0",required = false)int pageNumber,
//    @RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize,
//    @RequestParam(value = "sortBy",defaultValue = "title",required = false)String sortBy,
//    @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
//
//    ){
//        PageableResponse<OrderDto> orders = orderService.getOrders(pageNumber, pageSize, sortBy, sortDir);
//
//        return new ResponseEntity<>(orders,HttpStatus.OK);
//    }
//}
