package com.example.demo.repository;

import com.example.demo.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {


    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveAndSelect() {
        User user = new User();
        user.setName("song");

        User savedUser = userRepository.save(user);

        Optional<User> selectedUser = userRepository.findById(savedUser.getId());

        System.out.println(selectedUser.get());

    }


}