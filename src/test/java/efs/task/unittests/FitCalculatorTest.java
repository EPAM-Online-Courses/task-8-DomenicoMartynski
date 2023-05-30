package efs.task.unittests;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class FitCalculatorTest {

    @Test
    void shouldReturnTrue_whenDietRecommended() {
        //given
        double weight = 89.2;
        double height = 1.72;

        //when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        //then
        assertTrue(recommended);
    }

    //analogicznie do istniejącego testu shouldReturnTrue_whenDietRecommended utwórz test sprawdzający przypadek, dla którego isBMICorrect zwraca false (np. wzrost 1.72, waga 69.5).
    @Test
    void shouldReturnFalse_whenDietRecommended() {
        //given
        double weight = 69.5;
        double height = 1.72;

        //when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        //then
        assertFalse(recommended);
    }
    //utwórz test dla przypadku: wzrost użytkownika równy 0.0, waga dowolna -> oczekiwanie rezultat: metoda rzuca wyjątkiem IllegalArgumentException.
    @Test
    void shouldThrowIllegalArgumentException_whenHeightIsZero(){
        //given
        double weight = 69.5;
        double height = 0.0;

        //then
        assertThrows(IllegalArgumentException.class, () -> FitCalculator.isBMICorrect(weight,height));

    }
    //Korzystając z adnotacji @ParameterizedTest oraz @ValueSource utwórz test dla przypadku: wybrany wzrost, waga jako parametr-minimum 3 różne wartości -> oczekiwany rezultat dla wszystkich wag metoda zwraca true.
    @ParameterizedTest(name = "Correct BMI for weight={0}")
    @ValueSource(doubles = {90.0, 92.5, 95.0, 100.0})
    void shouldReturnTrue_forDifferentWeights(double weight) {
        // given
        double height = 1.72;

        // when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        // then
        assertTrue(recommended);
    }
    //Korzystając z adnotacji @ParameterizedTest oraz @CsvSource utwórz test dla przypadków: wzrost i waga jako para parametrów, minimum 3 różne wartości -> oczekiwany rezultat dla wszystkich par wartości metoda zwraca false.
    @ParameterizedTest(name = "Incorrect BMI for weight={0} and height={1}")
    @CsvSource({"50, 1.70,",
                "53, 1.75",
                "55, 1.72",
                "60, 1.80"
    })
    void shouldReturnFalse_forDifferentData(double weight, double height) {
        // when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        // then
        assertFalse(recommended);
    }
    //Korzystając z adnotacji @ParameterizedTest oraz @CsvFileSource utwórz test dla przypadków: wzrost i waga jako para parametrów pobierane z pliku resources.data.csv-> oczekiwany
    //rezultat dla wszystkich par wartości metoda zwraca false.
    @ParameterizedTest(name = "<File> Incorrect BMI for weight={0} and height={1}")
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    void shouldReturnFalse_forDifferentDataFromFile(double weight, double height){
        //when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        //then
        assertFalse(recommended);
    }
    //utwórz test dla przypadku: dla listy efs.task.unittests.TestConstants.TEST_USERS_LIST -> oczekiwania: użytkownik z najgorszym wynikiem BMI waga: 97.3, wzrost 1.79;
    @Test
    void shouldReturnUserWithWorstBMI(){
        //given
        List<User> users = TestConstants.TEST_USERS_LIST;
        User expectedUser = new User(1.79, 97.3);
        //when
        User foundUser = FitCalculator.findUserWithTheWorstBMI(users);

        //then
        assertEquals(expectedUser.getHeight(), foundUser.getHeight());
        assertEquals(expectedUser.getWeight(), foundUser.getWeight());
    }
    //utwórz test dla przypadku: pusta lista użytkowników -> oczekiwania: metoda zwraca null;
    @Test
    void shouldReturnNull_whenEmptyUserList() {
        //given
        List<User> emptyList = new ArrayList<>();

        //when
        User foundUser = FitCalculator.findUserWithTheWorstBMI(emptyList);

        //then
        assertNull(foundUser);
    }
    //utwórz test dla przypadku: dla listy efs.task.unittests.TestConstants.TEST_USERS_LIST -> oczekiwania: efs.task.unittests.TestConstants.TEST_USERS_BMI_SCORE;
    @Test
    void shouldCalculateBMIScore(){
        //given
        List<User> users = TestConstants.TEST_USERS_LIST;
        double[] expectedBMIScore = TestConstants.TEST_USERS_BMI_SCORE;

        //when
        double[] foundBMIScore = FitCalculator.calculateBMIScore(users);

        //then
        assertArrayEquals(expectedBMIScore, foundBMIScore);
    }
}