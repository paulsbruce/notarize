import org.junit.Test;

public class BasicTests extends AndroidSetup {

    @Test
    public void firstTest() {

        id("digit_1").click();

        id("op_add").click();

        id("digit_6").click();

        assert id("result").getText() == "7";
    }
}