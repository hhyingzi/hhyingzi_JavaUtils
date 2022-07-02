package TempProject;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class TestTest extends Test {
    @ParameterizedTest
    @CsvSource({"2, 1", "3, 1", "5, 1"})
    void testGetSquareTrue(int a, int b) {
        Test test = new Test();
        int result = test.getSquare(a, b);
        System.out.println("test result: " + result);
        assertEquals(a+b, result);
    }
}