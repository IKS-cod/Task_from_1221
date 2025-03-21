package com.systems1221.controller;

import com.systems1221.dto.InfoSumCaloriesAndCountMealInDayDto;
import com.systems1221.dto.ListMealOfAllTimeDto;
import com.systems1221.dto.MessageDto;
import com.systems1221.dto.UserDto;
import com.systems1221.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
@Tag(name = "Отчеты")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/caloriesAndCountMeal/{userId}")
    @Operation(summary = "Получение информации о калориях и кол-ве приемов пищи за текущий день")
    public InfoSumCaloriesAndCountMealInDayDto getInfoSumCaloriesAndCountMealInDay(@PathVariable long userId) {
        return reportService.getInfoSumCaloriesAndCountMealInDay(userId);
    }

    @GetMapping("/checkingUserFitNorm/{userId}")
    @Operation(summary = "Проверка, уложился ли пользователь в свою дневную норму калорий")
    public MessageDto getCheckingUserFitNormCalorieInDay(@PathVariable long userId) {
        return reportService.getCheckingUserFitNormCalorieInDay(userId);
    }
    // public boolean getCheckingUserFitNormCalorieInDay(UserDto userDto);


    @GetMapping("/history/{userId}")
    @Operation(summary = "Получение информации обо всех приемах пищи по дням")
    public ListMealOfAllTimeDto getListMealOfAllTime(@PathVariable long userId) {
        return reportService.getListMealOfAllTime(userId);
    }


}
