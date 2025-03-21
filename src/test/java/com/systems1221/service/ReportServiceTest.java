package com.systems1221.service;

import com.systems1221.dto.InfoSumCaloriesAndCountMealInDayDto;
import com.systems1221.dto.ListMealOfAllTimeDto;
import com.systems1221.dto.MessageDto;
import com.systems1221.exception.UserNotFoundException;
import com.systems1221.model.Dish;
import com.systems1221.model.Meal;
import com.systems1221.model.User;
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
import java.util.List;
import java.util.Optional;

import static com.systems1221.enums.Gender.MALE;
import static com.systems1221.enums.Goal.MAINTENANCE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @Mock
    private MealRepository mealRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReportService reportService;

    private long userId = 1L;

    @BeforeEach
    void setup() {
    }

    @Test
    void testGetInfoSumCaloriesAndCountMealInDay() {
        // Arrange
        double sumCalories = 1000.0;
        int countMeal = 3;
        when(mealRepository.sumCalories(userId, LocalDate.now())).thenReturn(sumCalories);
        when(mealRepository.countMeals(userId, LocalDate.now())).thenReturn(countMeal);

        // Act
        InfoSumCaloriesAndCountMealInDayDto infoSumCaloriesAndCountMealInDayDto =
                reportService.getInfoSumCaloriesAndCountMealInDay(userId);

        // Assert
        assertEquals(sumCalories, infoSumCaloriesAndCountMealInDayDto.getCalories());
        assertEquals(countMeal, infoSumCaloriesAndCountMealInDayDto.getCountMeal());
        verify(mealRepository, times(1)).sumCalories(userId, LocalDate.now());
        verify(mealRepository, times(1)).countMeals(userId, LocalDate.now());
    }

    @Test
    void testGetListMealOfAllTime() {
        // Arrange
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
        meal.setUser(user);
        meal.setDishes(dishList);
        mealsListFromDb.add(meal);
        when(mealRepository.getMealsByUserId(userId)).thenReturn(mealsListFromDb);

        // Act
        ListMealOfAllTimeDto listMealOfAllTimeDto = reportService.getListMealOfAllTime(userId);

        // Assert
        assertEquals(1, listMealOfAllTimeDto.getListMealDto().size());
        verify(mealRepository, times(1)).getMealsByUserId(userId);
    }

    @Test
    void testGetCheckingUserFitNormCalorieInDay() {
        // Arrange
        User userFromDb = new User();
        userFromDb.setId(userId);
        userFromDb.setDailyCalorieNorm(2000.0);
        when(userRepository.findById(userId)).thenReturn(Optional.of(userFromDb));
        when(mealRepository.sumCalories(userId, LocalDate.now())).thenReturn(1900.0);

        // Act
        MessageDto messageDto = reportService.getCheckingUserFitNormCalorieInDay(userId);

        // Assert
        String result = String.format("Количество калорий в пределах нормы! %.1f", 1900.0);
        assertEquals(result, messageDto.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verify(mealRepository, times(1)).sumCalories(userId, LocalDate.now());
    }

    @Test
    void testGetCheckingUserFitNormCalorieInDayUserNotFound() {
        // Arrange
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> reportService.getCheckingUserFitNormCalorieInDay(userId));
    }

    @Test
    void testGetCheckingUserFitNormCalorieInDayCaloriesInNorm() {
        // Arrange
        User userFromDb = new User();
        userFromDb.setId(userId);
        userFromDb.setDailyCalorieNorm(2000.0);
        when(userRepository.findById(userId)).thenReturn(Optional.of(userFromDb));
        when(mealRepository.sumCalories(userId, LocalDate.now())).thenReturn(1900.0);

        // Act
        MessageDto messageDto = reportService.getCheckingUserFitNormCalorieInDay(userId);

        // Assert
        String result = String.format("Количество калорий в пределах нормы! %.1f", 1900.0);
        assertEquals(result, messageDto.getMessage());
    }

    @Test
    void testGetCheckingUserFitNormCalorieInDayCaloriesAboveNorm() {
        // Arrange
        User userFromDb = new User();
        userFromDb.setId(userId);
        userFromDb.setDailyCalorieNorm(2000.0);
        when(userRepository.findById(userId)).thenReturn(Optional.of(userFromDb));
        when(mealRepository.sumCalories(userId, LocalDate.now())).thenReturn(2200.0);

        // Act
        MessageDto messageDto = reportService.getCheckingUserFitNormCalorieInDay(userId);

        // Assert
        String result = String.format("Количество калорий в пределах нормы! %.1f", 2200.0);
        assertEquals(result, messageDto.getMessage());
    }
}
