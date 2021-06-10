package ru.lazarev.online_store.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.lazarev.online_store.model.users.User;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    void findByUsername() {
        final User user = userRepository.findByUsername("bob1").get();
        Assertions.assertEquals("bob1@gmail.com", user.getEmail());
        final User user2 = userRepository.findByUsername("bob2").get();
        Assertions.assertEquals("bob2@gmail.com", user2.getEmail());
    }
}
