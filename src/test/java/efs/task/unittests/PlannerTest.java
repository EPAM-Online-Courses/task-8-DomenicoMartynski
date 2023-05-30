package efs.task.unittests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import static org.junit.jupiter.api.Assertions.*;

public class PlannerTest {

    Planner planner;

    @BeforeEach
    void init() {
        planner = new Planner();
    }

    //utwórz test parametryzowany sprawdzający poprawność wyliczenia dziennego zapotrzebowania kalorii dla wszystkich wartości typu wyliczeniowego efs.task.unittests.ActivityLevel, obliczenie dla użytkownika efs.task.unittests.TestConstants.TEST_USER ->
    //oczekiwania: prawidłowe wartości zapotrzebowania kalorii dla użytkownika efs.task.unittests.TestConstants.TEST_USER znajdziesz w mapie efs.task.unittests.TestConstants.CALORIES_ON_ACTIVITY_LEVEL;
    @ParameterizedTest(name = "Calculate daily calcories demand given activity level of {0}")
    @EnumSource(ActivityLevel.class)
    void shouldCalculateDailyCaloriesDemand(ActivityLevel activityLevel){
        //given
        double expectedCalories = TestConstants.CALORIES_ON_ACTIVITY_LEVEL.get(activityLevel);
        User user = TestConstants.TEST_USER;

        //when
        int foundCalories = planner.calculateDailyCaloriesDemand(user, activityLevel);

        //then
        assertEquals(expectedCalories, foundCalories);
    }
    //utwórz test sprawdzający poprawność wyliczenia dziennego zapotrzebowania na składniki odżywcze (dailyIntake) dla użytkownika efs.task.unittests.TestConstants.TEST_USER ->
    // oczekiwania: prawidłowe wartości zapotrzebowania na składniki odżywcze dla efs.task.unittests.TestConstants.TEST_USER takie jak w efs.task.unittests.TestConstants.TEST_USER_DAILY_INTAKE;
    @Test
    void shouldCalculateDailyIntake() {
        // given
        DailyIntake expectedIntake = TestConstants.TEST_USER_DAILY_INTAKE;
        User user = TestConstants.TEST_USER;

        // when
        DailyIntake foundIntake = planner.calculateDailyIntake(user);

        // then
        assertEquals(expectedIntake.getCalories(), foundIntake.getCalories());
        assertEquals(expectedIntake.getProtein(), foundIntake.getProtein());
        assertEquals(expectedIntake.getFat(), foundIntake.getFat());
        assertEquals(expectedIntake.getCarbohydrate(), foundIntake.getCarbohydrate());

    }
}
