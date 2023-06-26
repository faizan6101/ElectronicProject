package com.lcwd.electronic.store.service.serviceImpl;

import com.lcwd.electronic.store.config.AppConstant;
import com.lcwd.electronic.store.dtos.CategoryDto;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.entities.Category;
import com.lcwd.electronic.store.exception.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.Helper;
import com.lcwd.electronic.store.repository.CategoryRepository;
import com.lcwd.electronic.store.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        log.info("Initiating Dao call for create Category");
        Category category = mapper.map(categoryDto, Category.class);
        category.setIsactive(AppConstant.YES);

        Category save = categoryRepository.save(category);
        log.info("completed Dao call for create Category");

        return mapper.map(save,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        log.info("Initiating Dao call for update Category");
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category not found exception"));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category save = categoryRepository.save(category);
        log.info("completed Dao call for update Category");
        return mapper.map(save,CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        log.info("Initiating Dao call for delete Category");
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException());
        log.info("completed Dao call for delete Category");
        categoryRepository.delete(category);
    }

    @Override
    public PageableResponse<CategoryDto> getAllCategory(int pageNumber, int pageSize, String sortBy, String sortDir) {
        log.info("Initiating Dao call for getAll Category");

       Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Category> page = categoryRepository.findAll(pageable);
        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);
        log.info("completed Dao call for getAll Category");

        return pageableResponse;
    }

    @Override
    public CategoryDto getSingleCategory(Integer categoryId) {
        log.info("Initiating Dao call for get Category");
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException());
        log.info("completed Dao call for get Category");
        return mapper.map(category,CategoryDto.class);
    }
}
