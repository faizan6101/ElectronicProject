package com.lcwd.electronic.store.controller;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.payloads.ApiResponse;
import com.lcwd.electronic.store.payloads.ImageResponse;
import com.lcwd.electronic.store.service.FileService;
import com.lcwd.electronic.store.service.UserServiceI;
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
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/users")

public class UserController {


    @Autowired
    private UserServiceI userServiceI;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        log.info("Entering request for save user data");
        UserDto user = userServiceI.createUser(userDto);
        log.info("completed the request for save user data");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer userId) {
        log.info("Entering request for update user");
        UserDto updateuser = userServiceI.updateuser(userDto, userId);
        log.info("completed request for update user");
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId) {
        log.info("Entering request for delete user");
        userServiceI.deleteUser(userId);
        ApiResponse message = ApiResponse.builder().message("User deleted ").success(true).status(HttpStatus.OK).build();
        log.info("completed request for delete user");
        return new ResponseEntity<ApiResponse>(message, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pagesize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir


    ) {
        log.info("Entering request for getAllUser");
        PageableResponse<UserDto> allUser = userServiceI.getAllUser(pageNumber,pagesize,sortBy,sortDir);

        log.info("completed request for getAllUser");
        return new ResponseEntity<>(allUser, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Integer userId) {
        log.info("Entering request for getUser");
        UserDto user = userServiceI.getUser(userId);
        log.info("completed request for getUser");
        return new ResponseEntity<>(user, HttpStatus.OK);

    }
    @GetMapping("/email/{emailId}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String emailId) {
        log.info("Entering request for getUserByEmail");
        UserDto userByEmail = userServiceI.getUserByEmail(emailId);
        log.info("completed request for getUserByEmail");
        return new ResponseEntity<>(userByEmail, HttpStatus.OK);
    }



@GetMapping("/search/{keyword}")
public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword){
             log.info("Entering request for searchUser");
            List<UserDto> userDtos = userServiceI.searchUser(keyword);
            log.info("completed request for searchUser");
            return new ResponseEntity<>(userDtos,HttpStatus.OK);
}


//upload user image
@PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage")MultipartFile image,Integer userId) throws IOException {
    log.info("Entering request for uploadUserImage");
    String imageName = fileService.uploadImage(image, imageUploadPath);

    UserDto user = userServiceI.getUser(userId);
    user.setImageName(imageName);
    UserDto updateuser = userServiceI.updateuser(user, userId);

    ImageResponse imageResponse=ImageResponse.builder().imageName(imageName).success(true).status(HttpStatus.CREATED).build();
    log.info("completed request for uploadUserImage");
    return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);

    }

    //serve user image
@GetMapping("/image/{userId}")
public void serveUserImage(@PathVariable Integer userId, HttpServletResponse response) throws IOException {
    log.info("Entering request for serveUserImage");
    UserDto user = userServiceI.getUser(userId);
    log.info("user image name:{}",user.getImageName());

    InputStream resource = fileService.getResource(imageUploadPath, user.getImageName());
    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
    log.info("Completed request for serveUserImage");
    StreamUtils.copy(resource,response.getOutputStream());
}
}