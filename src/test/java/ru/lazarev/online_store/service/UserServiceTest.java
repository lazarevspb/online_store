package ru.lazarev.online_store.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.lazarev.online_store.repositories.UserRepository;
import ru.lazarev.online_store.services.UserService;

import java.util.Optional;

import ru.lazarev.online_store.model.users.User;

@SpringBootTest(classes = UserService.class)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    UserRepository userRepository;

    @Test
    void findByUsername() {
        User mockUser = new User();
        mockUser.setUsername("mockUser");
        mockUser.setId(100L);
        mockUser.setFirstName("mockUserFirstName");
        mockUser.setEmail("email@mail.com");

        Mockito
                .doReturn(Optional.of(mockUser))
                .when(userRepository)
                .findByUsername("mockUser");

        User u = userService.findByUsername("mockUser").get();
        Mockito.verify(userRepository, Mockito.times(1))
                .findByUsername(ArgumentMatchers.eq("mockUser"));
        Assertions.assertEquals("email@mail.com", u.getEmail());
        Assertions.assertEquals(100L, u.getId());
        Assertions.assertEquals("mockUserFirstName", u.getFirstName());
    }
}
