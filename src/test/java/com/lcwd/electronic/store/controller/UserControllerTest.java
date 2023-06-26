package com.lcwd.electronic.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.payloads.ImageResponse;
import com.lcwd.electronic.store.repository.UserRepository;
import com.lcwd.electronic.store.service.FileService;
import com.lcwd.electronic.store.service.UserServiceI;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {


    @MockBean
    private UserServiceI userService;

    @MockBean
    private FileService fileService;
    @MockBean
    private MultipartFile multipartFile;

    @MockBean
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    User user;

    ImageResponse imageResponse;
    @BeforeEach
    public void init() {

        user = User.builder()
                .name("faizan")
                .email("abc@gmail.com")
                .password("12345")
                .gender("male")
                .about("this is test practise")
                .imageName("abc.png")
                .build();
    }
    @Test
    public void createUserTest() throws Exception {
        //users+post+user data as json
        //data as json+status created

        UserDto dto = mapper.map(user, UserDto.class);
        Mockito.when(userService.createUser(Mockito.any())).thenReturn(dto);

        //actual result for url

        this.mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void updateUserTest() throws Exception {
        //users/id+put+json

        UserDto dto = mapper.map(user, UserDto.class);

        Mockito.when(userService.updateuser(Mockito.any(),Mockito.anyInt())).thenReturn(dto);
        Integer userId=2;
        this.mockMvc.perform(MockMvcRequestBuilders.put("/users/"+userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(user))
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk())
               .andExpect(jsonPath("$.name").exists());
    }


    @Test
    public void getAllUsersTest() throws Exception {
    UserDto obj1 = UserDto.builder().name("faizan").email("faizan6101@gmail.com").password("12345").imageName("abc.pm=ng").about("abcgf").build();
    UserDto obj2 = UserDto.builder().name("naved").email("naved@gmail.com").password("12345").imageName("abc.pm=ng").about("abcgf").build();
    UserDto obj3 = UserDto.builder().name("shubham").email("shubham@gmail.com").password("12345").imageName("abc.pm=ng").about("abcgf").build();
    UserDto obj4 = UserDto.builder().name("akash").email("akash@gmail.com").password("12345").imageName("abc.pm=ng").about("abcgf").build();


    PageableResponse<UserDto> pageableResponse=new PageableResponse<>();
        pageableResponse.setContent(Arrays.asList(obj1,obj2,obj3,obj4));
        pageableResponse.setLastPage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(100);
        pageableResponse.setTotalElements(1000);
        Mockito.when(userService.getAllUser(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk());


    }
    @Test
    public void getUserTest() throws Exception {

        Integer userId=1;
        UserDto dto = mapper.map(user, UserDto.class);

        Mockito.when(userService.getUser(userId)).thenReturn(dto);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/"+userId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)

        ).andDo(print())
                .andExpect(status().isOk());

    }

//  @Test
//    public void deleteUserTest() throws Exception {
//      Integer userId=1;
//
//Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//
//this.mockMvc.perform(MockMvcRequestBuilders.delete("/users/"+userId)).andExpect(status().isOk());
//  }


  @Test
  public void getUserByEmailTest() throws Exception {
      UserDto dto = mapper.map(user, UserDto.class);
        String emailId="abc@gmail.com";
        Mockito.when(userService.getUserByEmail(emailId)).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/email/"+emailId)
                .contentType(MediaType.APPLICATION_JSON)

                .accept(MediaType.APPLICATION_JSON)

        ).andDo(print())
                .andExpect(status().isOk());

    }
@Test
public void searchUserTest() throws Exception {
    UserDto dto = mapper.map(user, UserDto.class);

    String keyword="faizan";
     Mockito.when(userService.searchUser(Mockito.anyString())).thenReturn(List.of(dto));
     mockMvc.perform(MockMvcRequestBuilders.get("/users/search/"+keyword)
             .contentType(MediaType.APPLICATION_JSON)
             .accept(MediaType.APPLICATION_JSON)
     ).andDo(print()).andExpect(status().isOk());
}

//@Test
//public void uploadImageTest() throws Exception {
//        Integer userId=1;
//       Mockito.when(fileService.uploadImage(multipartFile,Mockito.anyString())).thenReturn(Mockito.any());
//    mockMvc.perform(MockMvcRequestBuilders.multipart("/users/image/"+userId)
//            .contentType(MediaType.APPLICATION_JSON)
//            .accept(MediaType.APPLICATION_JSON)
//    ).andDo(print()).andExpect(status().isOk());
//}




    private String convertObjectToJsonString(Object user)
       {
           try {
               return new ObjectMapper().writeValueAsString(user);
                } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



}
