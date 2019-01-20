package pathMatcher;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

public class PathMatcherTest {

    private PathMatcher pathMatcher;

    @Before
    public void setUp() throws Exception {
        pathMatcher = new AntPathMatcher();
    }

    @Test
    public void oneChar() {
        pathMatcher = new AntPathMatcher();

        String pattern = "/?";
        String sample = "/a";

        Assert.assertTrue(pathMatcher.match(pattern, sample));

    }

    @Test
    public void oneChar_WhenMore() {
        pathMatcher = new AntPathMatcher();

        String pattern = "/?";
        String sample = "/aa";

        Assert.assertFalse(pathMatcher.match(pattern, sample));

    }

    @Test
    public void oneCharOrMany_WhenMany() {
        String pattern = "/?*";
        String sample = "/a123123";

        Assert.assertTrue(pathMatcher.match(pattern, sample));

    }

    @Test
    public void oneCharOrMany_WhenManyWithDirs() {
        String pattern = "/?*";
        String sample = "/a123123/23/2";

        Assert.assertFalse(pathMatcher.match(pattern, sample));

    }

    @Test
    public void oneCharOrMany_WhenOneChar() {
        String pattern = "/?*";
        String sample = "/1";

        Assert.assertTrue(pathMatcher.match(pattern, sample));

    }

    @Test
    public void oneCharOrMany_WhenNull() {
        String pattern = "/?*";
        String sample = "/";

        Assert.assertFalse(pathMatcher.match(pattern, sample));

    }

    @Test
    public void invalidFormat_WhenNotCorrectPattern() {
        String pattern = "/.+";
        String sample = "/123";

        Assert.assertFalse(pathMatcher.match(pattern, sample));

    }

    @Test
    public void nullOrAnyDirs() {
        String pattern = "/**";
        String sample = "/aa/bb/cc";

        Assert.assertTrue(pathMatcher.match(pattern, sample));

    }

    @Test
    public void nullOrAnyDirs_WhenNull() {
        String pattern = "/**";
        String sample = "/";

        Assert.assertTrue(pathMatcher.match(pattern, sample));

    }

    @Test
    public void nullOrAnyChars() {
        String pattern = "/*";
        String sample = "/aafdsdfw44";

        Assert.assertTrue(pathMatcher.match(pattern, sample));

    }

    @Test
    public void nullOrAnyChars_WhenNull() {
        String pattern = "/*";
        String sample = "/";

        Assert.assertTrue(pathMatcher.match(pattern, sample));

    }

    @Test
    public void nullOrAnyChars_WhenExistNewDirs() {
        String pattern = "/*";
        String sample = "/aa/bb/cc";

        Assert.assertFalse(pathMatcher.match(pattern, sample));

    }

    @Test
    public void test() {
        String pattern = "/{a}/{b}/{c}";
        String sample = "/aa/bb/cc";

        Assert.assertTrue(pathMatcher.match(pattern, sample));
    }
}
