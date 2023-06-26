//package com.lcwd.electronic.store.service.serviceImpl;
//
//import com.lcwd.electronic.store.dtos.AddItemToCartRequest;
//import com.lcwd.electronic.store.dtos.CartDto;
//import com.lcwd.electronic.store.entities.Cart;
//import com.lcwd.electronic.store.entities.CartItem;
//import com.lcwd.electronic.store.entities.Product;
//import com.lcwd.electronic.store.entities.User;
//import com.lcwd.electronic.store.exception.BadApiRequest;
//import com.lcwd.electronic.store.exception.ResourceNotFoundException;
//import com.lcwd.electronic.store.repository.CartItemRepository;
//import com.lcwd.electronic.store.repository.CartRepository;
//import com.lcwd.electronic.store.repository.ProductRepository;
//import com.lcwd.electronic.store.repository.UserRepository;
//import com.lcwd.electronic.store.service.CartService;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//import java.util.List;
//import java.util.NoSuchElementException;
//import java.util.UUID;
//import java.util.concurrent.atomic.AtomicReference;
//import java.util.stream.Collectors;
//@Service
//public class CartServiceImpl implements CartService {
//
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private CartRepository cartRepository;
//
//    @Autowired
//    private CartItemRepository cartItemRepository;
//
//    @Autowired
//    private ModelMapper mapper;
//
//
//    @Override
//    public CartDto addItemToCart(Integer userId, AddItemToCartRequest request) {
//        int quantity = request.getQuantity();
//        int productId = request.getProductId();
//        if (quantity<=0){
//            throw new BadApiRequest("Request quantity is not valid");
//        }
//        //fetch the product
//        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product not found"));
//        //fetch the user from db
//        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found"));
//
//        //case1 :cart for user is not available: we will create the cart and then add item
//        Cart cart = null;
//        try {
//            cart = cartRepository.findByUser(user).get();
//        } catch (NoSuchElementException e) {
//            cart = new Cart();
//           cart.setCardId();
//            cart.setCreatedAt(new Date());
//        }
//
//        //perform cart operations
//        //if cart item already present;then update
//        AtomicReference<Boolean> updated = new AtomicReference<>(false);
//        List<CartItem> items = cart.getItems();
//
//        items = items.stream().map(item -> {
//            if (item.getProduct().getProductId().equals(productId)) {
//                item.setQuantity(quantity);
//                item.setTotalPrice(quantity * product.getPrice());
//                updated.set(true);
//            }
//            return item;
//        }).collect(Collectors.toList());
//        //cart.setItems(updatedItems);
//
//        //create items
//        if (!updated.get()) {
//            CartItem cartItem = CartItem.builder()
//                    .quantity(quantity)
//                    .totalPrice(quantity * product.getDiscountedPrice())
//                    .cart(cart)
//                    .product(product)
//                    .build();
//            cart.getItems().add(cartItem);
//        }
//            cart.setUser(user);
//            Cart updateCart = cartRepository.save(cart);
//
//
//            return mapper.map(updateCart, CartDto.class);
//        }
//
//    @Override
//    public void removeItemFromCart(Integer userId, int cartItem) {
//       //condition
//        CartItem cartItem1 = cartItemRepository.findById(cartItem).orElseThrow(() -> new ResourceNotFoundException("cartItem Not Found"));
//        cartItemRepository.delete(cartItem1);
//
//    }
//
//    @Override
//    public void clearCart(Integer userId) {
//        //fetch the user from db
//
//        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found"));
//        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("cart of given user not found!!!"));
//        cart.getItems().clear();
//        cartRepository.save(cart);
//
//    }
//
//    @Override
//    public CartDto getCartByUser(Integer userId) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found"));
//        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("cart of given user not found!!!"));
//
//        return mapper.map(cart,CartDto.class);
//    }
//}
