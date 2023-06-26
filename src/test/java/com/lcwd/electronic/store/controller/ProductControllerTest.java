package com.lcwd.electronic.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.ProductDto;
import com.lcwd.electronic.store.entities.Product;
import com.lcwd.electronic.store.repository.ProductRepository;
import com.lcwd.electronic.store.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    Product product;

    @BeforeEach
    public void init() {
        product = Product.builder()
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
    public void createProductTest() throws Exception {
        ProductDto productDto = mapper.map(product, ProductDto.class);
        Mockito.when(this.productService.create(Mockito.any())).thenReturn(productDto);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(product))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());
    }

    @Test
    public void updateProductTest() throws Exception {
        Integer productId = 1;
        ProductDto productDto = mapper.map(product, ProductDto.class);
        Mockito.when(productService.update(Mockito.any(), Mockito.anyInt())).thenReturn(productDto);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/products/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(product))
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());

    }


    @Test
    public void getAllProductTest() throws Exception {
        ProductDto product1 = ProductDto.builder()
                .title("LCD TV")
                .description("brazel-less frame ")
                .price(2600.500)
                .quantity(1)
                .discountedPrice(2200.500)
                .stock(true)
                .live(true)
                .build();
        ProductDto product2 = ProductDto.builder()
                .title("LCD TV")
                .description("brazel-less frame ")
                .price(2600.500)
                .quantity(1)
                .discountedPrice(2200.500)
                .stock(true)
                .live(true)
                .build();
        ProductDto product3 = ProductDto.builder()
                .title("LCD TV")
                .description("brazel-less frame ")
                .price(2600.500)
                .quantity(1)
                .discountedPrice(2200.500)
                .stock(true)
                .live(true)
                .build();
        PageableResponse<ProductDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(Arrays.asList(product1, product2, product3));
        pageableResponse.setLastPage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(100);
        pageableResponse.setTotalElements(1000);
        Mockito.when(productService.getAll(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);


        this.mockMvc.perform(MockMvcRequestBuilders.get("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void singleProductTest() throws Exception {
        Integer productId=1;
        Mockito.when(productRepository.findById(Mockito.any())).thenReturn(Optional.of(product));

        productService.get(productId);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/products/"+productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }


    @Test
    public void getAllLiveTest() throws Exception {
        ProductDto product1 = ProductDto.builder()
                .title("LCD TV")
                .description("brazel-less frame ")
                .price(2600.500)
                .quantity(1)
                .discountedPrice(2200.500)
                .stock(true)
                .live(true)
                .build();
        ProductDto product2 = ProductDto.builder()
                .title("LCD TV")
                .description("brazel-less frame ")
                .price(2600.500)
                .quantity(1)
                .discountedPrice(2200.500)
                .stock(true)
                .live(true)
                .build();
        ProductDto product3 = ProductDto.builder()
                .title("LCD TV")
                .description("brazel-less frame ")
                .price(2600.500)
                .quantity(1)
                .discountedPrice(2200.500)
                .stock(true)
                .live(true)
                .build();
        PageableResponse<ProductDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(Arrays.asList(product1, product2, product3));
        pageableResponse.setLastPage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(100);
        pageableResponse.setTotalElements(1000);
        Mockito.when(productService.getAllLive(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);


        this.mockMvc.perform(MockMvcRequestBuilders.get("/products/live")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void getAllSearchTest() throws Exception {

        String query="LCD TV";
        ProductDto product1 = ProductDto.builder()
                .title("LCD TV")
                .description("brazel-less frame ")
                .price(2600.500)
                .quantity(1)
                .discountedPrice(2200.500)
                .stock(true)
                .live(true)
                .build();
        ProductDto product2 = ProductDto.builder()
                .title("LCD TV")
                .description("brazel-less frame ")
                .price(2600.500)
                .quantity(1)
                .discountedPrice(2200.500)
                .stock(true)
                .live(true)
                .build();
        ProductDto product3 = ProductDto.builder()
                .title("LCD TV")
                .description("brazel-less frame ")
                .price(2600.500)
                .quantity(1)
                .discountedPrice(2200.500)
                .stock(true)
                .live(true)
                .build();
        PageableResponse<ProductDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(Arrays.asList(product1, product2, product3));
        pageableResponse.setLastPage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(100);
        pageableResponse.setTotalElements(1000);
        Mockito.when(productService.searchByTitle(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);


        this.mockMvc.perform(MockMvcRequestBuilders.get("/products//search/"+query)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


@Test
public void deleteProductTest() throws Exception {
        Integer productId=1;
        Mockito.when(productRepository.findById(Mockito.any())).thenReturn(Optional.of(product));

        productService.delete(productId);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/products/"+productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

}


    private String convertObjectToJsonString(Object product) {
        try {
            return new ObjectMapper().writeValueAsString(product);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}