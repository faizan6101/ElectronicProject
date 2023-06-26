package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.CategoryDto;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.entities.Category;
import com.lcwd.electronic.store.repository.CategoryRepository;
import com.lcwd.electronic.store.service.CategoryService;
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
public class CategoryServiceImplTest {
@MockBean
private CategoryRepository categoryRepository;

@Autowired
private CategoryService categoryService;

    @Autowired
    private ModelMapper mapper;

    Category category;

    @BeforeEach
    public void init() {
         category= Category.builder()
                 .title("hello world")
                 .description("This category contains topics")
                 .coverImage("abc.png")
                 .build();


    }

    @Test
    public void createCategoryTest(){
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);
        CategoryDto categoryDto = categoryService.createCategory(mapper.map(category, CategoryDto.class));
        System.out.println(categoryDto.getTitle());

        Assertions.assertEquals("hello world",categoryDto.getTitle());
     Assertions.assertNotNull(categoryDto);
    }

    @Test
    public void updateCategoryTest(){
        Integer categoryId=1;
        CategoryDto categoryDto = CategoryDto.builder()
                .title("hello world")
                .description("This category contains topics")
                .coverImage("abc.png")
                .build();
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        Mockito.when(categoryRepository.save(category)).thenReturn(category);
        CategoryDto updateCategory = categoryService.updateCategory(categoryDto, categoryId);
        Assertions.assertNotNull(updateCategory);

    }
    @Test
    public void deleteCategoryTest(){
        Mockito.when(categoryRepository.findById(Mockito.any())).thenReturn(Optional.of(category));
        categoryService.deleteCategory(Mockito.any());
        Mockito.verify(categoryRepository,Mockito.times(1)).delete(category);
    }

    @Test
    public void getAllCategoryTest(){
        Category  category1 = Category.builder()
                .title("Mobile world")
                .description("This category contains topics")
                .coverImage("abc.png")
                .build();
        Category   category2 = Category.builder()
                .title("Galaxy world")
                .description("This category contains topics")
                .coverImage("abc.png")
                .build();

        Category category3 = Category.builder()
                .title("universe")
                .description("This category contains topics")
                .coverImage("abc.png")
                .build();
        List<Category> categoryList = Arrays.asList(category1, category2, category3);
        Page<Category>page=new PageImpl<>(categoryList);

        Mockito.when(categoryRepository.findAll((Pageable)Mockito.any())).thenReturn(page);

        PageableResponse<CategoryDto> allCategory = categoryService.getAllCategory(1, 2, "name", "asc");

        Assertions.assertEquals(3,allCategory.getContent().size());
    }

    @Test
    public void getCategoryTest(){
        Integer categoryId=1;
        CategoryDto dto = mapper.map(category, CategoryDto.class);

        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        CategoryDto categoryDto = categoryService.getSingleCategory(categoryId);
        Assertions.assertNotNull(categoryDto);
        Assertions.assertEquals(dto.getCategoryId(),categoryDto.getCategoryId());
    }
}

