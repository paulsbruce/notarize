import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.logging.Level;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(Parameterized.class)
public class DataTests_Eval extends TestBase {

    String rawExpression;
    boolean isPositiveCase;

    public DataTests_Eval(String rawExpression, String isPositiveCase) {
        // will fail type casting if input data is invalid
        this.rawExpression = rawExpression;
        this.isPositiveCase = Boolean.parseBoolean(isPositiveCase);
    }


    @Parameterized.Parameters
    public static Collection testData() throws IOException {
        return DataUtils.getTestData("src/test/resources/DataTests_Eval.csv");
    }



    @Test
    public void evaluateExpression() {

        String[] parts = rawExpression.split("=");
        if(parts.length != 2)
            throw new IllegalArgumentException("Expression '" + rawExpression + "' does not contain exactly two parts when split by '=' character.");

        typeExpression(parts[0]);

        String expected = parts[1];
        logger.log(Level.INFO, "expected: '" + expected + "'");

        if(isPositiveCase)
            assertEquals(id("result").getText(), expected);
        else
            assertNotEquals(id("result").getText(), expected);
    }
}
