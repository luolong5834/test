import bb.Calculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

;

/**
 * <p></p>
 *
 * @author luolong
 * @date 2019/2/28
 */
public class Test1 {

    @Test
    @DisplayName("1 + 1 = 2")
    void addsTwoNumbers() {
        Calculator calculator = new Calculator();
        assertEquals(3, calculator.add(1, 1), "1 + 1 should equal 2");
    }

}
