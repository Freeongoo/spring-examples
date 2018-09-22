package example.aop.annotation;

import example.customannotation.AccessDeny;
import example.customannotation.RangeValidate;
import org.springframework.stereotype.Component;

@Component
public class Data {
    private String info;
    private int num;
    private int numForRange;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @AccessDeny
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNumForRange() {
        return numForRange;
    }

    @RangeValidate(from = RangeValidate.ZERO)
    public void setNumForRange(int numForRange) {
        this.numForRange = numForRange;
    }
}
