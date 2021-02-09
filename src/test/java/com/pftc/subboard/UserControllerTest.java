package com.pftc.subboard;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

import java.util.Optional;

import com.pftc.subboard.exceptions.UserNotFoundException;
import com.pftc.subboard.user.User;
import com.pftc.subboard.user.UserController;
import com.pftc.subboard.user.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    private UserController userController;

    @Test
    public void testOne() {
        // User à récupérer par la méthode find by
        Optional<User> returnedUser = Optional.of(new User());

        // Configure mock
        doReturn(returnedUser).when(userRepositoryMock).findById(anyLong());

        // Perform thes test
        User actualUser = userController.getOne(5L);

        // Junit asserts
        assertEquals(returnedUser.get(), actualUser);
        
    }

    @Test
    public void testOneFail() {
        // Configure mock
        doReturn(Optional.empty()).when(userRepositoryMock).findById(anyLong());

        // Perform thes test
        assertThrows(UserNotFoundException.class, () -> userController.getOne(5L));        
    }

}
