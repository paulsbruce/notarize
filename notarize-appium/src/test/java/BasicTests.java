import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.logging.Level;

import static org.junit.Assert.assertEquals;

/**
 * Created by paul on 7/6/17.
 */
@RunWith(JUnit4.class)
public class BasicTests extends TestBase {

    @Before
    public void setUp() {
        // test-specific setup
    }

    @Test
    public void addNumbers() {

        id("digit_1").click();

        id("op_add").click();

        id("digit_6").click();

        logger.log(Level.INFO, "result: '" + id("result").getText() + "'");

        assertEquals(id("result").getText(), "7");
    }

    @After
    public void tearDown() {
        // test-specific cleanup
    }
}
