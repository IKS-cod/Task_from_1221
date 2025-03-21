package com.systems1221.service;

import com.systems1221.dto.DishDto;
import com.systems1221.exception.DishNotFoundException;
import com.systems1221.exception.IllegalNameForDishDtoException;
import com.systems1221.mapper.Mappers;
import com.systems1221.model.Dish;
import com.systems1221.repository.DishRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DishServiceTest {

    @Mock
    private DishRepository dishRepository;

    @Mock
    private Mappers mappers;

    @Mock
    private ValidNameForDishDto validNameForDishDto;

    @Mock
    private ValidCalories validCalories;

    @Mock
    private ValidCarbs validCarbs;

    @Mock
    private ValidFats validFats;

    @Mock
    private ValidProteins validProteins;

    @InjectMocks
    private DishService dishService;

    private DishDto dishDto;

    @BeforeEach
    void setup() {
        dishDto = new DishDto(1L,"Test Dish", 100, 10.0, 5.0, 20.0);
    }

    @Test
    void testCreateDish() {
        // Arrange
        Dish dishForDb = new Dish();
        dishForDb.setName(dishDto.getName());
        dishForDb.setCalories(dishDto.getCalories());
        dishForDb.setCarbs(dishDto.getCarbs());
        dishForDb.setFats(dishDto.getFats());
        dishForDb.setProteins(dishDto.getProteins());

        when(mappers.toDish(dishDto)).thenReturn(dishForDb);
        when(dishRepository.save(any(Dish.class))).thenReturn(dishForDb);
        when(mappers.toDishDto(dishForDb)).thenReturn(dishDto);

        when(validNameForDishDto.isValidNameForDishDto(dishDto)).thenReturn(true);
        when(validCalories.isValidCalories(dishDto)).thenReturn(true);
        when(validCarbs.isValidCarbs(dishDto)).thenReturn(true);
        when(validFats.isValidFats(dishDto)).thenReturn(true);
        when(validProteins.isValidProteins(dishDto)).thenReturn(true);

        // Act
        DishDto createdDishDto = dishService.createDish(dishDto);

        // Assert
        assertEquals(dishDto, createdDishDto);
        verify(dishRepository, times(1)).save(any(Dish.class));
    }

    @Test
    void testCreateDishInvalidName() {
        // Arrange
        when(validNameForDishDto.isValidNameForDishDto(dishDto)).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalNameForDishDtoException.class, () -> dishService.createDish(dishDto));
    }

    @Test
    void testGetDish() {
        // Arrange
        Dish dishFromDb = new Dish();
        dishFromDb.setId(1L);
        dishFromDb.setName(dishDto.getName());
        when(dishRepository.findById(1L)).thenReturn(Optional.of(dishFromDb));
        when(mappers.toDishDto(dishFromDb)).thenReturn(dishDto);

        // Act
        DishDto retrievedDishDto = dishService.getDish(1L);

        // Assert
        assertEquals(dishDto, retrievedDishDto);
        verify(dishRepository, times(1)).findById(1L);
    }

    @Test
    void testGetDishNotFound() {
        // Arrange
        when(dishRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DishNotFoundException.class, () -> dishService.getDish(1L));
    }

    @Test
    void testUpdateDish() {
        // Arrange
        Dish dishFromDb = new Dish();
        dishFromDb.setId(1L);
        when(dishRepository.findById(1L)).thenReturn(Optional.of(dishFromDb));

        when(validNameForDishDto.isValidNameForDishDto(dishDto)).thenReturn(true);
        when(validCalories.isValidCalories(dishDto)).thenReturn(true);
        when(validCarbs.isValidCarbs(dishDto)).thenReturn(true);
        when(validFats.isValidFats(dishDto)).thenReturn(true);
        when(validProteins.isValidProteins(dishDto)).thenReturn(true);

        // Act
        dishService.updateDish(1L, dishDto);

        // Assert
        verify(dishRepository, times(1)).findById(1L);
        verify(dishRepository, times(1)).save(any(Dish.class));
    }

    @Test
    void testUpdateDishNotFound() {
        // Arrange
        when(validNameForDishDto.isValidNameForDishDto(dishDto)).thenReturn(true);
        when(validCalories.isValidCalories(dishDto)).thenReturn(true);
        when(validCarbs.isValidCarbs(dishDto)).thenReturn(true);
        when(validFats.isValidFats(dishDto)).thenReturn(true);
        when(validProteins.isValidProteins(dishDto)).thenReturn(true);

        when(dishRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DishNotFoundException.class, () -> dishService.updateDish(1L, dishDto));
    }

    @Test
    void testDeleteDish() {
        // Arrange
        when(dishRepository.existsById(1L)).thenReturn(true);

        // Act
        dishService.deleteDish(1L);

        // Assert
        verify(dishRepository, times(1)).existsById(1L);
        verify(dishRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteDishNotFound() {
        // Arrange
        when(dishRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(DishNotFoundException.class, () -> dishService.deleteDish(1L));
    }

    @Test
    void testFindAllDishes() {
        // Arrange
        List<Dish> dishesListFromDb = new ArrayList<>();
        Dish dish = new Dish();
        dish.setId(1L);
        dishesListFromDb.add(dish);
        when(dishRepository.findAll()).thenReturn(dishesListFromDb);
        when(mappers.toDishDto(any(Dish.class))).thenReturn(dishDto);

        // Act
        Collection<DishDto> dishesDtoList = dishService.findAllDishes();

        // Assert
        assertEquals(1, dishesDtoList.size());
        assertEquals(dishDto, dishesDtoList.iterator().next());
        verify(dishRepository, times(1)).findAll();
    }
}
