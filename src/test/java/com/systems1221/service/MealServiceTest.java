package com.systems1221.service;

import com.systems1221.dto.DishForMealDto;
import com.systems1221.dto.MealDto;
import com.systems1221.exception.MealNotFoundException;
import com.systems1221.exception.UserNotFoundException;
import com.systems1221.model.Dish;
import com.systems1221.model.Meal;
import com.systems1221.model.User;
import com.systems1221.repository.DishRepository;
import com.systems1221.repository.MealRepository;
import com.systems1221.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
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
public class MealServiceTest {

    @Mock
    private MealRepository mealRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DishRepository dishRepository;

    @InjectMocks
    private MealService mealService;

    private MealDto mealDto;

    @BeforeEach
    void setup() {
        mealDto = new MealDto();
        mealDto.setUserId(1L);
        List<DishForMealDto> dishes = new ArrayList<>();
        dishes.add(new DishForMealDto(1L));
        mealDto.setDishes(dishes);
    }

    @Test
    void testCreateMeal() {
        // Arrange
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        List<Dish> dishesFromDb = new ArrayList<>();
        dishesFromDb.add(new Dish());
        dishesFromDb.add(new Dish());
        when(dishRepository.findDishesByIds(List.of(1L))).thenReturn(dishesFromDb);

        Meal mealForDb = new Meal();
        mealForDb.setDate(LocalDate.now());
        mealForDb.setUser(user);
        mealForDb.setDishes(dishesFromDb);
        when(mealRepository.save(any(Meal.class))).thenReturn(mealForDb);

        // Act
        MealDto createdMealDto = mealService.createMeal(mealDto);

        // Assert
        assertEquals(mealDto.getUserId(), createdMealDto.getUserId());
        assertEquals(2, createdMealDto.getDishes().size());
        verify(mealRepository, times(1)).save(any(Meal.class));
    }

    @Test
    void testCreateMealUserNotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> mealService.createMeal(mealDto));
    }

    @Test
    void testGetMeal() {
        // Arrange
        Meal mealFromDb = new Meal();
        mealFromDb.setId(1L);
        List<Dish> dishList = new ArrayList<>();
        dishList.add(new Dish(1L,"sup", 100, 10.0,10.0,10.0));
        mealFromDb.setDishes(dishList);
        User user = new User(1L,
                "Test",
                "test@bk.com",
                20,
                75.0,
                175.0,
                MAINTENANCE,
                MALE,
                1200);
        mealFromDb.setUser(user);
        when(mealRepository.findById(1L)).thenReturn(Optional.of(mealFromDb));

        // Act
        MealDto retrievedMealDto = mealService.getMeal(1L);

        // Assert
        assertEquals(1L, retrievedMealDto.getId());
        verify(mealRepository, times(1)).findById(1L);
    }

    @Test
    void testGetMealNotFound() {
        // Arrange
        when(mealRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(MealNotFoundException.class, () -> mealService.getMeal(1L));
    }

    @Test
    void testUpdateMeal() {
        // Arrange
        Meal mealFromDb = new Meal();
        mealFromDb.setId(1L);
        when(mealRepository.findById(1L)).thenReturn(Optional.of(mealFromDb));

        List<Dish> dishesFromDb = new ArrayList<>();
        dishesFromDb.add(new Dish());
        when(dishRepository.findDishesByIds(List.of(1L))).thenReturn(dishesFromDb);

        // Act
        mealService.updateMeal(1L, mealDto);

        // Assert
        verify(mealRepository, times(1)).findById(1L);
        verify(mealRepository, times(1)).save(any(Meal.class));
    }

    @Test
    void testUpdateMealNotFound() {
        // Arrange
        when(mealRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(MealNotFoundException.class, () -> mealService.updateMeal(1L, mealDto));
    }

    @Test
    void testDeleteMeal() {
        // Arrange
        when(mealRepository.existsById(1L)).thenReturn(true);

        // Act
        mealService.deleteMeal(1L);

        // Assert
        verify(mealRepository, times(1)).existsById(1L);
        verify(mealRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteMealNotFound() {
        // Arrange
        when(mealRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(MealNotFoundException.class, () -> mealService.deleteMeal(1L));
    }

    @Test
    void testFindAllMealsForToday() {
        // Arrange
        when(userRepository.existsById(1L)).thenReturn(true);
        User user = new User(1L,
                "Test",
                "test@bk.com",
                20,
                75.0,
                175.0,
                MAINTENANCE,
                MALE,
                1200);
        List<Dish> dishList = new ArrayList<>();
        dishList.add(new Dish(1L,"sup", 100, 10.0,10.0,10.0));
        List<Meal> mealsListFromDb = new ArrayList<>();
        Meal meal = new Meal();
        meal.setId(1L);
        meal.setDate(LocalDate.now());
        meal.setUser(user);
        meal.setDishes(dishList);
        mealsListFromDb.add(meal);
        when(mealRepository.findByUserIdAndDate(1L, LocalDate.now())).thenReturn(mealsListFromDb);

        // Act
        Collection<MealDto> mealsDtoList = mealService.findAllMealsForToday(1L);

        // Assert
        assertEquals(1, mealsDtoList.size());
        assertEquals(1L, mealsDtoList.iterator().next().getId());
        verify(mealRepository, times(1)).findByUserIdAndDate(1L, LocalDate.now());
    }

    @Test
    void testFindAllMealsForTodayUserNotFound() {
        // Arrange
        when(userRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> mealService.findAllMealsForToday(1L));
    }
}
