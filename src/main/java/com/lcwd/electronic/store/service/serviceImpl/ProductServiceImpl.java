package com.lcwd.electronic.store.service.serviceImpl;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.ProductDto;
import com.lcwd.electronic.store.entities.Category;
import com.lcwd.electronic.store.entities.Product;
import com.lcwd.electronic.store.exception.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.Helper;
import com.lcwd.electronic.store.repository.CategoryRepository;
import com.lcwd.electronic.store.repository.ProductRepository;
import com.lcwd.electronic.store.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
@Autowired
private ProductRepository repository;

@Autowired
private CategoryRepository categoryRepository;

@Autowired
private ModelMapper mapper;

    @Override
    public ProductDto create(ProductDto productDto) {
        log.info("Initiating Dao call for create Product");
        Product product = mapper.map(productDto, Product.class);
        product.setAddedDate(new Date());
        Product save = repository.save(product);
        log.info("completed Dao call for create Product");
        return mapper.map(save,ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, Integer productId) {
        log.info("Initiating Dao call for update Product");
        Product product = repository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product not found of given Id!!"));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isLive());
        product.setProductImageName(productDto.getProductImageName());
        Product updatedProduct = repository.save(product);
        log.info("completed Dao call for update Product");
        return mapper.map(updatedProduct,ProductDto.class);
    }

    @Override
    public void delete(Integer productId) {
        log.info("Initiating Dao call for delete Product");
        Product product = repository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product not found of given Id!!"));
        log.info("completed Dao call for delete Product");
        repository.delete(product);
    }

    @Override
    public ProductDto get(Integer productId) {
        log.info("Initiating Dao call for get Product");
        Product product = repository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product not found of given Id!!"));
        log.info("completed Dao call for get Product");
        return mapper.map(product,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        log.info("Initiating Dao call for getAll Product");
   Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
       Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = repository.findAll(pageable);
        log.info("completed Dao call for getAll Product");
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir) {
        log.info("Initiating Dao call for getAllLive Product");
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = repository.findByLiveTrue(pageable);
        log.info("completed Dao call for getAllLive Product");
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir) {
        log.info("Initiating Dao call for searchByTitle Product");
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = repository.findByTitleContaining(subTitle,pageable);
        log.info("completed Dao call for searchByTitle Product");
        return Helper.getPageableResponse(page,ProductDto.class);

    }

    @Override
    public ProductDto createWithCategory(ProductDto productDto, Integer categoryId) {
        //fetch category from DB
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category not found"));
        Product product = mapper.map(productDto, Product.class);
        product.setAddedDate(new Date());
        product.setCategory(category);
        Product save = repository.save(product);

        return mapper.map(save,ProductDto.class);
            }

            //update category of product
    @Override
    public ProductDto updateCategory(Integer productId, Integer categoryId) {
        //product fetch
        Product product = repository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product of given id not found !!"));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("product of given id not found !!"));
        product.setCategory(category);
        Product savedProduct = repository.save(product);

        return mapper.map(savedProduct,ProductDto.class);
    }


//    public PageableResponse<ProductDto> getAllOfCategory(Integer categoryId){
//        repository.findByCategory(category);
//    }
}
