package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.repository.UserRepository;
import com.lcwd.electronic.store.service.UserServiceI;
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
class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;
    @Autowired
    private UserServiceI userService;
    @Autowired
    private ModelMapper mapper;

    User user;


    @BeforeEach
    public void init() {

        user = User.builder()
                .name("faizan")
                .email("abc@gmail.com")
                .gender("male")
                .imageName("abc.png")
                .build();

    }

    @Test
    public void createUserTest() {

        //arrange
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        //Act

        UserDto user1 = userService.createUser(mapper.map(user, UserDto.class));
        System.out.println(user1.getName());
        //Assert
        Assertions.assertEquals("faizan", user1.getName());
        Assertions.assertNotNull(user1);

    }

    @Test
    public void updateUserTest(){

        UserDto userDto = UserDto.builder()
                .name("faizan khan")
                .email("faizan@gmail.com")
                .gender("male")
                .imageName("xyz.png")
                .build();
Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        UserDto updateuser = userService.updateuser(userDto, Mockito.any());

        System.out.println(updateuser.getName());
        Assertions.assertNotNull(userDto);
    }

//    @Test
//    public void deleteUserTest(){
//  // Integer userId=1;
//        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
//
//        userService.deleteUser(Mockito.any());
//
//
//
//        Mockito.verify(userRepository,Mockito.times(1)).delete(user);
//
//    }

    @Test
    public void getAllUsersTest(){
        User user1 = User.builder()
                .name("shahrukh")
                .email("abc@gmail.com")
                .gender("male")
                .imageName("abc.png")
                .build();

        User user2 = User.builder()
                .name("anwaz")
                .email("abc@gmail.com")
                .gender("male")
                .imageName("abc.png")
                .build();
List<User> userList= Arrays.asList(user,user1,user2);

        Page<User>page=new PageImpl<>(userList);
        Mockito.when(userRepository.findAll((Pageable)Mockito.any())).thenReturn(page);

        PageableResponse<UserDto> allUser = userService.getAllUser(1, 2, "name", "asc");

        Assertions.assertEquals(3,allUser.getContent().size());
    }



    @Test
    public void getUserByIdTest(){
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        UserDto user1 = userService.getUser(Mockito.any());
        Assertions.assertNotNull(user1);
        Assertions.assertEquals(user.getName(),user1.getName(),"name not matched");
    }



    @Test
    public void getUserByEmailTest(){
        String emailId="faizan6101@gmail.com";
        Mockito.when(userRepository.findByEmail(emailId)).thenReturn(Optional.of(user));
        UserDto userByEmail = userService.getUserByEmail(emailId);
        Assertions.assertNotNull(userByEmail);
        Assertions.assertEquals(user.getEmail(),userByEmail.getEmail());
    }
    @Test
    public void searchUserTest(){
        User user1 = User.builder()
                .name("ubaid")
                .email("abc@gmail.com")
                .gender("male")
                .imageName("abc.png")
                .build();

        User user2 = User.builder()
                .name("komail")
                .email("abc@gmail.com")
                .gender("male")
                .imageName("abc.png")
                .build();
        User user3 = User.builder()
                .name("shoaib")
                .email("abc@gmail.com")
                .gender("male")
                .imageName("abc.png")
                .build();
        String keyword="ubaid";

        Mockito.when(userRepository.findByNameContaining(keyword)).thenReturn(Arrays.asList(user,user1,user2,user3));
        List<UserDto> userDtos = userService.searchUser(keyword);
        Assertions.assertEquals(4,userDtos.size());

    }




}
