package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.ProductDto;
import com.lcwd.electronic.store.entities.Category;
import com.lcwd.electronic.store.entities.Product;
import com.lcwd.electronic.store.repository.CategoryRepository;
import com.lcwd.electronic.store.repository.ProductRepository;
import com.lcwd.electronic.store.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductServiceImplTest {
    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    Product product;

    Category category;

    @BeforeEach
    public void init (){
 product=Product.builder()
        .title("LCD TV")
        .description("brazel-less frame ")
        .price(2600.500)
        .quantity(1)
        .discountedPrice(2200.500)
        .stock(true)
        .live(true)
        .build();

    }

    @Test
    public void createProductTest(){
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
        ProductDto productDto = productService.create(mapper.map(product, ProductDto.class));

        Assertions.assertNotNull(productDto);
        Assertions.assertEquals("LCD TV",productDto.getTitle());

    }

    @Test
    public void updateProductTest(){
        Integer productId=1;


        Mockito.when(productRepository.findById(Mockito.any())).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(product)).thenReturn(product);
        ProductDto productDto = mapper.map(product, ProductDto.class);

        ProductDto update = productService.update(productDto, productId);
        Assertions.assertEquals(productDto.getTitle(),update.getTitle());
    }

@Test
    public void deleteProductTest(){
        Integer productId=1;
Mockito.when(productRepository.findById(Mockito.any())).thenReturn(Optional.of(product));
     productService.delete(Mockito.any());
    Mockito.verify(productRepository,Mockito.times(1)).delete(product);
    }

    @Test
    public void getProduct(){
        Integer productId=1;
        ProductDto productDto = mapper.map(product, ProductDto.class);
        Mockito.when(productRepository.findById(Mockito.any())).thenReturn(Optional.of(product));
        ProductDto productDto1 = productService.get(productId);
        Assertions.assertEquals(productDto.getProductId(),productDto1.getProductId());
    }


    @Test
    public void getAllProduct(){
      Product  product1 =Product.builder()
                .title("LCD TV")
                .description("brazel-less frame ")
                .price(2600.500)
                .quantity(1)
                .discountedPrice(2200.500)
                .stock(true)
                .live(true)
                .build();
        Product  product2 =Product.builder()
                .title("TV")
                .description("Bravia QLED ")
                .price(2600.500)
                .quantity(1)
                .discountedPrice(2200.500)
                .stock(true)
                .live(true)
                .build();
        Product product3=Product.builder()
                .title("regrigerator")
                .description("750l double decker")
                .price(2600.500)
                .quantity(1)
                .discountedPrice(2200.500)
                .stock(true)
                .live(true)
                .build();

        List<Product> products = Arrays.asList(product1, product2, product3);
        Page<Product>page=new PageImpl<>(products);

        Mockito.when(productRepository.findAll((Pageable)Mockito.any() )).thenReturn(page);
        PageableResponse<ProductDto> all = productService.getAll(1, 10, "name", "asc");
        Assertions.assertEquals(3,all.getContent().size());
    }

    @Test
    public void getAllLiveTest(){
        Product  product1 =Product.builder()
                .title("LCD TV")
                .description("brazel-less frame ")
                .price(2600.500)
                .quantity(1)
                .discountedPrice(2200.500)
                .stock(true)
                .live(true)
                .build();
        Product  product2 =Product.builder()
                .title("TV")
                .description("Bravia QLED ")
                .price(2600.500)
                .quantity(1)
                .discountedPrice(2200.500)
                .stock(true)
                .live(true)
                .build();
        Product product3=Product.builder()
                .title("regrigerator")
                .description("750l double decker")
                .price(2600.500)
                .quantity(1)
                .discountedPrice(2200.500)
                .stock(true)
                .live(true)
                .build();

        List<Product> products = Arrays.asList(product1, product2, product3);
        Page<Product>page=new PageImpl<>(products);

        Mockito.when(productRepository.findByLiveTrue((Pageable)Mockito.any() )).thenReturn(page);
        PageableResponse<ProductDto> all = productService.getAllLive(1, 10, "name", "asc");
        Assertions.assertEquals(3,all.getContent().size());

    }

//    @Test
//    public void searchByTitleTest(){
//        Product  product1 =Product.builder()
//                .title("LCD TV")
//                .description("brazel-less frame ")
//                .price(2600.500)
//                .quantity(1)
//                .discountedPrice(2200.500)
//                .stock(true)
//                .live(true)
//                .build();
//        Product  product2 =Product.builder()
//                .title("TV")
//                .description("Bravia QLED ")
//                .price(2600.500)
//                .quantity(1)
//                .discountedPrice(2200.500)
//                .stock(true)
//                .live(true)
//                .build();
//        Product product3=Product.builder()
//                .title("regrigerator")
//                .description("750l double decker")
//                .price(2600.500)
//                .quantity(1)
//                .discountedPrice(2200.500)
//                .stock(true)
//                .live(true)
//                .build();
//        String search="TV";
//          List<Product> products = Arrays.asList(product1, product2, product3);
//          Page<Product>page=new PageImpl<>(products);
//
//          Mockito.when(productRepository.findByTitleContaining(search,(Pageable) Mockito.any())).thenReturn(page);
//          PageableResponse<ProductDto> pageableResponse = productService.searchByTitle(search,1,2,"title","asc");
//          Assertions.assertEquals(3, pageableResponse.getContent().size());
//
//    }


//    @Test
//    public void updateCategoryTest(){
//        Integer productId=1; Integer categoryId=1;
//        category= Category.builder()
//                .title("hello world")
//                .description("This category contains topics")
//                .coverImage("abc.png")
//                .build();
//     //   Mockito.when(productRepository.findById(Mockito.any())).thenReturn(Optional.of(product));
//    //    Mockito.when(categoryRepository.findById(Mockito.any())).thenReturn(Optional.of(category));
//        Mockito.when(productRepository.save(product)).thenReturn(product);
//
//        ProductDto productDto = productService.updateCategory(productId, categoryId);
//        Assertions.assertNotNull(productDto);
//
//    }

}
