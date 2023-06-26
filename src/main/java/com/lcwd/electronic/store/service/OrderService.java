//package com.lcwd.electronic.store.service;
//
//import com.lcwd.electronic.store.dtos.CreateOrderRequest;
//import com.lcwd.electronic.store.dtos.OrderDto;
//import com.lcwd.electronic.store.dtos.PageableResponse;
//import com.lcwd.electronic.store.service.serviceImpl.OrderServiceImpl;
//
//import java.util.List;
//
//public interface OrderService   {
//
//    //create order
//    OrderDto createOrder(CreateOrderRequest orderDto);
//
//    //remove order
//    void removeOrder(Integer orderId);
//
//    //get order of user
//    List<OrderDto> getOrdersOfUser(Integer userId);
//
//    //get order
//    PageableResponse<OrderDto> getOrders(int pageNumber,int pageSize,String sortBy,String sortDir);
//    //order methods
//}
