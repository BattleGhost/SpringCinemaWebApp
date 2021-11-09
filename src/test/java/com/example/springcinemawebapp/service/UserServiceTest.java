package com.example.springcinemawebapp.service;

import com.example.springcinemawebapp.exception.NotEnoughMoneyException;
import com.example.springcinemawebapp.model.User;
import com.example.springcinemawebapp.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    @Test
    public void reduceUserBalanceShouldChangeUserBalance() {
        long initialBalance = 200;
        long subtract = 100;
        Long newBalance = initialBalance - subtract;
        User user = User.builder().balance(initialBalance).build();
        service.reduceUserBalance(user, subtract);
        assertEquals(newBalance, user.getBalance());
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void reduceUserBalanceShouldThrowExceptionOnNegativeBalance() {
        long initialBalance = 100;
        long subtract = 200;
        User user = User.builder().balance(initialBalance).build();
        service.reduceUserBalance(user, subtract);
    }
}