package com.librarysystem.services;

import com.librarysystem.models.User;
import com.librarysystem.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testuser");
        user.setPasswordHash("plainPassword");
    }

    @Test
    void testRegisterUser_Success() {
        String rawPassword = "plainPassword";
        String encodedPassword = "encodedPassword";
        user.setPasswordHash(rawPassword);

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        // Use an ArgumentCaptor to capture the user object passed to save
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(userRepository.save(userCaptor.capture())).thenAnswer(inv -> inv.getArgument(0));

        User registeredUser = userService.registerUser(user);

        assertNotNull(registeredUser);
        assertEquals("testuser", registeredUser.getUsername());
        assertEquals(encodedPassword, registeredUser.getPasswordHash());
        assertEquals(User.UserRole.USER, registeredUser.getRole());

        verify(userRepository, times(1)).findByUsername("testuser");
        verify(passwordEncoder, times(1)).encode(rawPassword);
        verify(userRepository, times(1)).save(any(User.class));

        // Optional: More detailed assertion on the captured user
        User captured = userCaptor.getValue();
        assertEquals(User.UserRole.USER, captured.getRole());
    }

    @Test
    void testRegisterUser_UsernameAlreadyExists() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.registerUser(user);
        });

        assertEquals("Username already exists: testuser", exception.getMessage());

        verify(userRepository, times(1)).findByUsername("testuser");
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }
}
