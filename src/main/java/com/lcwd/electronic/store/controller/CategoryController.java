package com.lcwd.electronic.store.controller;

import com.lcwd.electronic.store.dtos.CategoryDto;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.ProductDto;
import com.lcwd.electronic.store.payloads.ApiResponse;
import com.lcwd.electronic.store.service.CategoryService;
import com.lcwd.electronic.store.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<CategoryDto>createCategory (@RequestBody CategoryDto categoryDto){
        log.info("Entering request for createCategory");
        CategoryDto categoryDto1 = categoryService.createCategory(categoryDto);
        log.info("completed request for createCategory");
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);

    }
    @PutMapping("/{categoryId}")
public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable Integer categoryId){
        log.info("Entering request for updateCategory");
        CategoryDto update = categoryService.updateCategory(categoryDto, categoryId);
        log.info("completed request for updateCategory");
        return new ResponseEntity<>(update,HttpStatus.OK);
    }
@DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId){
    log.info("Entering request for delete Category");
        categoryService.deleteCategory(categoryId);
    ApiResponse response = ApiResponse.builder().message("category is deleted ").status(HttpStatus.OK).success(true).build();
    log.info("completed request for delete Category");
    return new ResponseEntity<>(response,HttpStatus.OK);
}

    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAll(

            @RequestParam(value = "pageNumber",defaultValue = "0",required = false)int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir){
        log.info("Entering request for getAll Category");
        PageableResponse<CategoryDto> pageableResponse = categoryService.getAllCategory(pageNumber, pageSize, sortBy, sortDir);
        log.info("completed request for getAll Category");
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getsingleCategory(@PathVariable Integer categoryId){
        log.info("Entering request for getsingle category");
        CategoryDto categoryDto = categoryService.getSingleCategory(categoryId);
        log.info("completed request for getsingle");
        return new ResponseEntity<>(categoryDto,HttpStatus.OK);

    }

    //create product with category

    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto> createProductWithCategory(
            @PathVariable("categoryId")Integer categoryId,
            @RequestBody ProductDto dto
            ){
        ProductDto withCategory = productService.createWithCategory(dto, categoryId);
        return new ResponseEntity<>(withCategory,HttpStatus.CREATED);
    }

//update category of product

    @PutMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<ProductDto> updateCategoryofProduct(
        @PathVariable Integer categoryId,
        @PathVariable Integer productId
){
    ProductDto productDto = productService.updateCategory(productId, categoryId);
    return new ResponseEntity<>(productDto,HttpStatus.OK);
}


}
