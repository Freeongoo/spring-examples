package example.aop.typepointcut.annotation;

import example.exceptions.InvalidRangeValueRuntimeException;
import example.exceptions.NotAllowAccessRuntimeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataTest {
    @Autowired
    private Data data;

    @Test
    public void getInfo() {
        String info = "info";
        data.setInfo(info);

        assertThat(data.getInfo(), equalTo(info));
    }

    @Test(expected = NotAllowAccessRuntimeException.class)
    public void getNum() {
        int num = 321;
        data.setNum(num);

        assertThat(data.getNum(), equalTo(num));
    }

    @Test
    public void getNumForRange() {
        int num = 2;
        data.setNumForRange(num);

        assertThat(data.getNumForRange(), equalTo(num));
    }

    @Test(expected = InvalidRangeValueRuntimeException.class)
    public void getNumForRange_WhenMoreThenMaxShort() {
        int num = 33333;
        data.setNumForRange(num);

        assertThat(data.getNumForRange(), equalTo(num));
    }
}