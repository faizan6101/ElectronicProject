//package com.lcwd.electronic.store.service.serviceImpl;
//
//import com.lcwd.electronic.store.dtos.CreateOrderRequest;
//import com.lcwd.electronic.store.dtos.OrderDto;
//import com.lcwd.electronic.store.dtos.PageableResponse;
//import com.lcwd.electronic.store.entities.*;
//import com.lcwd.electronic.store.exception.BadApiRequest;
//import com.lcwd.electronic.store.exception.ResourceNotFoundException;
//import com.lcwd.electronic.store.helper.Helper;
//import com.lcwd.electronic.store.repository.CartRepository;
//import com.lcwd.electronic.store.repository.OrderRepository;
//import com.lcwd.electronic.store.repository.UserRepository;
//import com.lcwd.electronic.store.service.OrderService;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//import java.util.List;
//import java.util.UUID;
//import java.util.concurrent.atomic.AtomicReference;
//import java.util.stream.Collectors;
//
//@Service
//public class OrderServiceImpl implements OrderService {
//
//
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private OrderRepository orderRepository;
//    @Autowired
//    private CartRepository cartRepository;
//    @Autowired
//    private ModelMapper mapper;
//
//    @Override
//    public OrderDto createOrder(CreateOrderRequest orderDto) {
//        Integer userId = orderDto.getUserId();
//        Integer cartId = orderDto.getCartId();
//        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found with given Id!!"));
//
//        //fetch cart
//        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("cart with given id not found on server!!"));
//        List<CartItem>  cartItems = cart.getItems();
//        if (cartItems.size()<=0){
//            throw new BadApiRequest("invalid number of items in cart");
//        }
//
//        Order order = Order.builder()
//                .billingName(orderDto.getBillingName())
//                .billingPhone(orderDto.getBillingPhone())
//                .billingAddress(orderDto.getBillingAddress())
//                .orderedDate(new Date())
//                .deliveredDate(null)
//                .paymentStatus(orderDto.getPaymentStatus())
//                .orderStatus(orderDto.getOrderStatus())
//                .orderId(orderDto.getCartId())
//                .user(user)
//                .build();
//
//        //orderItmes,amount set ni kiye
//        AtomicReference<Integer> orderAmount= new AtomicReference<>(0 );
//        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
//
//            //cartItmem -> convert into -> OrderItem
//            OrderItem orderItem = OrderItem.builder()
//                    .quantity(cartItem.getQuantity())
//                    .product(cartItem.getProduct())
//                    .totalPrice(cartItem.getQuantity() * cartItem.getProduct().getDiscountedPrice())
//                    .order(order)
//                    .build();
//            return new OrderItem();
//        }).collect(Collectors.toList());
//        order.setOrderItems(orderItems);
//        order.setOrderAmount(orderAmount.get());
//
//        cart.getItems().clear();
//        cartRepository.save(cart);
//        Order saveOrder = orderRepository.save(order);
//
//        return mapper.map(saveOrder,OrderDto.class);
//    }
//
//    @Override
//    public void removeOrder(Integer orderId) {
//        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("order is not found"));
//        orderRepository.delete(order);
//
//    }
//
//    @Override
//    public List<OrderDto> getOrdersOfUser(Integer userId) {
//
//        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found!!"));
//
//        List<Order> orders = orderRepository.findByUser(user);
//        List<OrderDto> orderDtos = orders.stream().map(order -> mapper.map(order, OrderDto.class)).collect(Collectors.toList());
//        return orderDtos;
//    }
//
//    @Override
//    public PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
//        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
//
//        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
//
//        Page<Order> page = orderRepository.findAll(pageable);
//        return Helper.getPageableResponse(page,OrderDto.class);
//    }
//}
