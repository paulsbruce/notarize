import com.opencsv.CSVReader;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by paul on 7/6/17.
 */
@RunWith(value = Parameterized.class)
public class DataTests_Add extends TestBase {

    private BigDecimal firstNumber,secondNumber,result;
    private boolean isPositiveCase;

    public DataTests_Add(String firstNumber, String secondNumber, String result, String isPositiveCase) {
        // will fail type casting if input data is invalid
        this.firstNumber = new BigDecimal(firstNumber);
        this.secondNumber = new BigDecimal(secondNumber);
        this.result = new BigDecimal(result);
        this.isPositiveCase = Boolean.parseBoolean(isPositiveCase);
    }


    @Parameterized.Parameters
    public static Collection testData() throws IOException {
        return DataUtils.getTestData("src/test/resources/DataTests_Add.csv");
    }



    @Test
    public void addNumbers() {

        typeNumber(firstNumber);
        pressAdd();
        typeNumber(secondNumber);

        String expected = result.toString();
        logger.log(Level.INFO, "expected: '" + expected + "'");

        if(isPositiveCase)
            assertEquals(id("result").getText(), expected);
        else
            assertNotEquals(id("result").getText(), expected);
    }
}
