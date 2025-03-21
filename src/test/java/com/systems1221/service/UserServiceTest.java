package com.systems1221.service;

import com.systems1221.dto.UserDto;
import com.systems1221.enums.Gender;
import com.systems1221.enums.Goal;
import com.systems1221.exception.IllegalAgeException;
import com.systems1221.exception.IllegalEmailException;
import com.systems1221.exception.UserNotFoundException;
import com.systems1221.mapper.Mappers;
import com.systems1221.model.User;
import com.systems1221.repository.UserRepository;
import com.systems1221.service.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.systems1221.enums.Gender.MALE;
import static com.systems1221.enums.Goal.MAINTENANCE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Mappers mappers;

    @Mock
    private ValidEmail validEmail;

    @Mock
    private ValidAge validAge;

    @Mock
    private ValidWeight validWeight;

    @Mock
    private ValidGender validGender;

    @Mock
    private ValidHeight validHeight;

    @Mock
    private ValidNameForUserDto validNameForUserDto;

    @Mock
    private ValidGoal validGoal;

    @InjectMocks
    private UserService userService;

    private UserDto userDto;

    @BeforeEach
    void setup() {
        userDto = new UserDto(1L,
                "John Doe",
                "john@example.com",
                30,
                70.0,
                175.0,
                MAINTENANCE,
                MALE,
                1200);
    }

    @Test
    void testCreateUser() {
        // Arrange
        User userForDb = new User();
        userForDb.setName(userDto.getName());
        userForDb.setEmail(userDto.getEmail());
        userForDb.setAge(userDto.getAge());
        userForDb.setWeight(userDto.getWeight());
        userForDb.setHeight(userDto.getHeight());
        userForDb.setGender(userDto.getGender());
        userForDb.setGoal(userDto.getGoal());

        when(mappers.toUser(userDto)).thenReturn(userForDb);
        when(userRepository.save(any(User.class))).thenReturn(userForDb);
        when(mappers.toUserDto(userForDb)).thenReturn(userDto);

        when(validEmail.isValidEmail(userDto)).thenReturn(true);
        when(validAge.isValidAge(userDto)).thenReturn(true);
        when(validWeight.isValidWeight(userDto)).thenReturn(true);
        when(validGender.isValidGender(userDto)).thenReturn(true);
        when(validHeight.isValidHeight(userDto)).thenReturn(true);
        when(validNameForUserDto.isValidNameForUserDto(userDto)).thenReturn(true);
        when(validGoal.isValidGoal(userDto)).thenReturn(true);

        // Act
        UserDto createdUserDto = userService.createUser(userDto);

        // Assert
        assertEquals(userDto, createdUserDto);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testGetUser() {
        // Arrange
        User userFromDb = new User();
        userFromDb.setId(1L);
        userFromDb.setName(userDto.getName());
        userFromDb.setEmail(userDto.getEmail());

        when(userRepository.findById(1L)).thenReturn(Optional.of(userFromDb));
        when(mappers.toUserDto(userFromDb)).thenReturn(userDto);

        // Act
        UserDto retrievedUserDto = userService.getUser(1L);

        // Assert
        assertEquals(userDto, retrievedUserDto);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserNotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.getUser(1L));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateUser() {
        // Arrange
        User userFromDb = new User();
        userFromDb.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(userFromDb));
        when(validEmail.isValidEmail(userDto)).thenReturn(true);
        when(validAge.isValidAge(userDto)).thenReturn(true);
        when(validWeight.isValidWeight(userDto)).thenReturn(true);
        when(validGender.isValidGender(userDto)).thenReturn(true);
        when(validHeight.isValidHeight(userDto)).thenReturn(true);
        when(validNameForUserDto.isValidNameForUserDto(userDto)).thenReturn(true);
        when(validGoal.isValidGoal(userDto)).thenReturn(true);

        // Act
        userService.updateUser(1L, userDto);

        // Assert
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testDeleteUser() {
        // Arrange
        when(userRepository.existsById(1L)).thenReturn(true);

        // Act
        userService.deleteUser(1L);

        // Assert
        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUserNotFound() {
        // Arrange
        when(userRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1L));
        verify(userRepository, times(1)).existsById(1L);
    }

    @Test
    void testFindAllUsers() {
        // Arrange
        List<User> usersListFromDb = new ArrayList<>();
        usersListFromDb.add(new User());

        when(userRepository.findAll()).thenReturn(usersListFromDb);
        when(mappers.toUserDto(any(User.class))).thenReturn(userDto);

        // Act
        Collection<UserDto> usersDtoList = userService.findAllUsers();

        // Assert
        assertEquals(1, usersDtoList.size());
        assertEquals(userDto, usersDtoList.iterator().next());
        verify(userRepository, times(1)).findAll();
    }


}