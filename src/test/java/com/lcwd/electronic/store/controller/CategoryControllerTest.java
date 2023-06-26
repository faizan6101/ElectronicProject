package com.lcwd.electronic.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcwd.electronic.store.dtos.CategoryDto;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.entities.Category;
import com.lcwd.electronic.store.repository.CategoryRepository;
import com.lcwd.electronic.store.service.CategoryService;
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
public class CategoryControllerTest {

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    Category category;

    @BeforeEach
    public void init() {
         category= Category.builder()
                 .title("hello world")
                 .description("testing ")
                 .coverImage("abc.png")
                 .build();
    }


    @Test
    public void createCategoryTest() throws Exception {

        CategoryDto categoryDto = mapper.map(category, CategoryDto.class);

        Mockito.when(categoryService.createCategory(Mockito.any())).thenReturn(categoryDto);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/categories")
        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(category))
        .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.title").exists());
    }

    @Test
    public void updateCategoryTest() throws Exception {
        CategoryDto categoryDto = mapper.map(category, CategoryDto.class);
        Mockito.when(categoryService.updateCategory(Mockito.any(),Mockito.anyInt())).thenReturn(categoryDto);
        Integer categoryId=1;
        this.mockMvc.perform(MockMvcRequestBuilders.put("/categories/"+categoryId)

                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(category))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());
    }

    @Test
    public void deleteCategoryTest() throws Exception {
        Integer categoryId=1;
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/categories/"+categoryId))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllCAtegoriesTest() throws Exception {
        CategoryDto  category1 = CategoryDto.builder()
                .title("Mobile world")
                .description("This category contains topics")
                .coverImage("abc.png")
                .build();
        CategoryDto   category2 = CategoryDto.builder()
                .title("Galaxy world")
                .description("This category contains topics")
                .coverImage("abc.png")
                .build();

        CategoryDto category3 = CategoryDto.builder()
                .title("universe")
                .description("This category contains topics")
                .coverImage("abc.png")
                .build();

        PageableResponse<CategoryDto> pageableResponse =new PageableResponse<>();
        pageableResponse.setContent(Arrays.asList(category1,category2,category3));
        pageableResponse.setLastPage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(100);
        pageableResponse.setTotalElements(1000);

        Mockito.when(categoryService.getAllCategory(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getSingleCategoryTest() throws Exception {
        Integer categoryId=1;

        CategoryDto categoryDto = mapper.map(category, CategoryDto.class);
Mockito.when(categoryService.getSingleCategory(Mockito.anyInt())).thenReturn(categoryDto);

this.mockMvc.perform(MockMvcRequestBuilders.get("/categories/"+categoryId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
    }



    private String convertObjectToJsonString(Object category) {
        try {
            return new ObjectMapper().writeValueAsString(category);
        } catch (Exception e) {
            e.printStackTrace();
        return null;
    }

}}
