package com.systems1221.repository;

import com.systems1221.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByUserIdAndDate(Long userId, LocalDate mealDate);

    @Query("SELECT COALESCE(COUNT(DISTINCT m.id), 0) FROM Meal m WHERE m.user.id = :userId AND m.date = :date")
    Integer countMeals(@Param("userId") Long userId, @Param("date") LocalDate date);

    @Query("SELECT COALESCE(SUM(d.calories), 0) FROM Meal m JOIN m.dishes d WHERE m.user.id = :userId AND m.date = :date")
    Double sumCalories(@Param("userId") Long userId, @Param("date") LocalDate date);

    @Query("SELECT m FROM Meal m WHERE m.user.id = :userId")
    List<Meal> getMealsByUserId(@Param("userId") Long userId);

}
