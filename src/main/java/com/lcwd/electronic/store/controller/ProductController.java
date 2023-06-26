package com.lcwd.electronic.store.controller;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.ProductDto;
import com.lcwd.electronic.store.payloads.ApiResponse;
import com.lcwd.electronic.store.payloads.ImageResponse;
import com.lcwd.electronic.store.service.FileService;
import com.lcwd.electronic.store.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @Autowired
    private FileService fileService;

    @Value("${product.image.path}")
    private String imagePath;

    //create
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
        log.info("Entering request for createProduct");
        ProductDto createProduct = service.create(productDto);
        log.info("Completed request for createProduct ");
        return new ResponseEntity<>(createProduct, HttpStatus.CREATED);

    }

    //update
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto,@PathVariable Integer productId){
        log.info("Entering request for updateProduct");
        ProductDto updateProduct = service.update(productDto, productId);
        log.info("Completed request for updateProduct ");
        return new ResponseEntity<>(updateProduct, HttpStatus.OK);

    }

    //delete
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Integer productId){
        log.info("Entering request for deleteProduct");
      service.delete(productId);
        ApiResponse responseMessage = ApiResponse.builder().message("product is deleted sucessfuly").status(HttpStatus.OK).success(true).build();
        log.info("Completed request for deleteProduct ");
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    //get single
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Integer productId){
        log.info("Entering request for getProduct");
        ProductDto getProduct = service.get(productId);
        log.info("Completed request for getProduct ");
        return new ResponseEntity<>(getProduct, HttpStatus.OK);

    }

    //get all
    @GetMapping
    public ResponseEntity<PageableResponse> getAll(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false)int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
    ){
        log.info("Entering request for getAllProduct");
        PageableResponse<ProductDto> pageableResponse = service.getAll(pageNumber, pageSize, sortBy, sortDir);
        log.info("Completed request for getAllProduct ");

        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);

    }

    //get all live
    @GetMapping("/live")
    public ResponseEntity<PageableResponse> getAllLive(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false)int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
    ){
        log.info("Entering request for getAllLive");
        PageableResponse<ProductDto> pageableResponse = service.getAllLive(pageNumber, pageSize, sortBy, sortDir);
        log.info("Completed request for getAllLive ");
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);

    }
    //search all
    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponse> search(
            @PathVariable String query,//query=subtitle
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false)int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
    ){
        log.info("Entering request for searchByTitle ");
        PageableResponse<ProductDto> pageableResponse = service.searchByTitle(query,pageNumber, pageSize, sortBy, sortDir);
        log.info("Completed request for searchByTitle ");
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);

    }


    //upload image
    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage
    (@PathVariable Integer productId,
     @RequestParam("productImage")MultipartFile image)
            throws IOException {
        log.info("Entering request for uploadProductImage ");
        String fileName = fileService.uploadImage(image, imagePath);
        ProductDto productDto = service.get(productId);
        productDto.setProductImageName(fileName);
        ProductDto update = service.update(productDto, productId);

        ImageResponse response = ImageResponse.builder()
                .imageName(update.getProductImageName())
                .message("Product image is successfully uploaded")
                .status(HttpStatus.CREATED)
                .success(true)
                .build();
        log.info("Completed request for uploadProductImage ");
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
//serve image
@GetMapping("/image/{productId}")
public void serveUserImage(@PathVariable Integer productId, HttpServletResponse response) throws IOException {
    log.info("Entering request for serveUserImage");
    ProductDto productDto = service.get(productId);
    log.info("user image name:{}",productDto.getProductImageName());

    InputStream resource = fileService.getResource(imagePath, productDto.getProductImageName());
    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
    log.info("Completed request for serveUserImage");
    StreamUtils.copy(resource,response.getOutputStream());
}



}
