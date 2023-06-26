package com.lcwd.electronic.store.service.serviceImpl;

import com.lcwd.electronic.store.config.AppConstant;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.exception.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.Helper;

import com.lcwd.electronic.store.repository.UserRepository;
import com.lcwd.electronic.store.service.UserServiceI;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
public class UserServiceImpl implements UserServiceI {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${user.profile.image.path}")
    private String imagePath;
    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("Initiating Dao call for save user data");

        //dto to entity
        User user = dtoToEntity(userDto);
        user.setIsactive(AppConstant.YES);
        User savedUser= userRepository.save(user);
//entity to userDto
        UserDto newDto = entityToDto(savedUser);
        log.info("completed dao call for save user");
        return newDto;
    }


    @Override
    public UserDto updateuser(UserDto userDto, Integer userId) {
        log.info("Initiating Dao call for update user");
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setEmail(userDto.getEmail());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());

        User save = userRepository.save(user);
        UserDto updateDto = entityToDto(user);


        log.info("completed Dao call for update user");
        return updateDto;
    }




    @Override
    public void deleteUser(Integer userId) {
        log.info("Initiating Dao call for Delete user");
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with Goven UserId"));

        //delete user profile image

        String fullPath = imagePath + user.getImageName();
        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
        //delete user
        userRepository.delete(user);
        user.setIsactive(AppConstant.NO);
        log.info("completed Dao call for Delete user");
    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {
        log.info("Initiating Dao call for getAll user");
        Sort sort = Sort.by(sortBy);

        PageRequest pageable = PageRequest.of(pageNumber, pageSize,sort);

        Page<User> page = userRepository.findAll(pageable);
        PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);

        log.info("completed Dao call for getAll user ");

        return response;

    }

    @Override
    public UserDto getUser(Integer userId) {
        log.info("Initiating Dao call for getUser");
        User usersid = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("userId not found"));

        UserDto userDtoId = entityToDto(usersid);
        log.info("completed Dao call for getUser");
        return userDtoId;
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        log.info("Initiating Dao for searchUser");
        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDto> collect = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        log.info("completed Dao call for searchUser");
        return collect;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        log.info("Initiating Dao call for getUserByEmail");
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("user not found by email!!"));
        log.info("completed Dao call for getUserByEmail");

        return entityToDto(user);
    }


    //===================================================================
    //Conversion
    private User dtoToEntity(UserDto userDto) {

        return modelMapper.map(userDto, User.class);
    }

    private UserDto entityToDto(User savedUser) {

        return modelMapper.map(savedUser, UserDto.class);

    }
    


}
