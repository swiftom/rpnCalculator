import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CalculatorDefaultImplTest {
   static CalculatorDefaultImpl calculator ;

    @BeforeClass
    public static void runBefore() {
        calculator = new CalculatorDefaultImpl();
    }

    @Before
    public void initCalculator() {
        calculator.reset();
    }

    @Test
    public void test1() {
        String ret = calculator.executeAndReturn("5   2  0 -0 -0.00000000000000000000  111 ");

        Assert.assertEquals("", "5 2 0 0 0 111", ret);

    }

    @Test
    public void test2() {
        String ret = calculator.executeAndReturn("11 Undo");

        Assert.assertEquals("", "", ret);
    }

    @Test
    public void test3() {
        String ret = calculator.executeAndReturn("111 222 + sqrt **");

        Assert.assertEquals("", "", ret);

    }

    @Test
    public void test4() {
        String ret = calculator.executeAndReturn("1 0 /");

        Assert.assertEquals("", "", ret);

    }

    @Test
    public void test5() {
        String ret = calculator.executeAndReturn("1 2 3 + - 10 0.5 10000 * /");

        Assert.assertEquals("", "-4 0.002", ret);

    }

    @Test
    public void test6() {
        String ret = calculator.executeAndReturn("1 2 -3 sqrt");

        Assert.assertEquals("", "1 2", ret);

    }

    @Test
    public void test7() {
        String ret = calculator.executeAndReturn("2 sqrt clear 9 sqrt undo undo undo undo");

        Assert.assertEquals("", "2", ret);

    }

    @Test
    public void test8() {
        String ret = calculator.executeAndReturn("2 sqrt clear clear 9 sqrt undo undo undo undo");

        Assert.assertEquals("", "2", ret);

    }

    @Test
    public void test9() {
        String ret = calculator.executeAndReturn("1 2 -3 sqrt * undo");

        Assert.assertEquals("", "1 2", ret);

    }

    @Test
    public void test10() {
        //insufficient parameters
        String ret = calculator.executeAndReturn("12  22 33 44 + - * /");

        Assert.assertEquals("", "-660", ret);

    }

    @Test
    public void test11() {
        String ret = calculator.executeAndReturn("12  1 1000000000000 /");

        Assert.assertEquals("", "12 0.000000000001", ret);

    }

    @Test
    public void test12() {
        String ret = calculator.executeAndReturn("0.000200000");

        Assert.assertEquals("", "0.0002", ret);

    }

    @Test
    public void test13() {
        String ret = calculator.executeAndReturn("9223372036854775807 9223372036854775807 9223372036854775807 9223372036854775807 * * *");

        Assert.assertEquals("", "7237005577332262210834635695349653859421902880380109739573089701262786560001", ret);

    }

    @Test
    public void test14() {
        String ret = calculator.executeAndReturn("-9223372036854775808 -9223372036854775808 -9223372036854775808 + * * *");

        Assert.assertEquals("", "170141183460469231731687303715884105728", ret);

    }
}