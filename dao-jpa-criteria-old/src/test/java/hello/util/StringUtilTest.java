package hello.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringUtilTest {

    @Test
    public void convertToBoolean_WhenSomeString_ShouldBeTrue() {
        String s = "some-string";
        boolean bool = StringUtil.convertToBoolean(s);

        assertTrue(bool);
    }

    @Test
    public void convertToBoolean_WhenEmptyString_ShouldBeFalse() {
        String s = "";
        boolean bool = StringUtil.convertToBoolean(s);

        assertFalse(bool);
    }

    @Test
    public void convertToBoolean_WhenSpaces_ShouldBeFalse() {
        String s = "  ";
        boolean bool = StringUtil.convertToBoolean(s);

        assertFalse(bool);
    }

    @Test
    public void convertToBoolean_WhenStringFalse_ShouldBeFalse() {
        String s = "false";
        boolean bool = StringUtil.convertToBoolean(s);

        assertFalse(bool);
    }

    @Test
    public void convertToBoolean_WhenStringFalseCaps_ShouldBeFalse() {
        String s = "FALSE";
        boolean bool = StringUtil.convertToBoolean(s);

        assertFalse(bool);
    }

    @Test
    public void convertToBoolean_WhenStringFalseWithSpaces_ShouldBeFalse() {
        String s = " false  ";
        boolean bool = StringUtil.convertToBoolean(s);

        assertFalse(bool);
    }

    @Test
    public void convertToBoolean_WhenStringZero_ShouldBeFalse() {
        String s = "0";
        boolean bool = StringUtil.convertToBoolean(s);

        assertFalse(bool);
    }

    @Test
    public void convertToBoolean_WhenStringZeroWithSpaces_ShouldBeFalse() {
        String s = " 0  ";
        boolean bool = StringUtil.convertToBoolean(s);

        assertFalse(bool);
    }

    //TODO: may be change
    @Test
    public void convertToBoolean_WhenStringZeroDoubleWithSpaces_ShouldBeTrue() {
        String s = " 0.0  ";
        boolean bool = StringUtil.convertToBoolean(s);

        assertTrue(bool);
    }

    @Test
    public void convertToBoolean_WhenStringNotZero_ShouldBeTrue() {
        String s = " 1  ";
        boolean bool = StringUtil.convertToBoolean(s);

        assertTrue(bool);
    }
}