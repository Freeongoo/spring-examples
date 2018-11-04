package hello.simplelogic;

import hello.simplelogic.data.Data;
import hello.simplelogic.util.ConcatStr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyServiceWithTwoDeps {

    @Autowired
    private Data data;

    @Autowired
    private ConcatStr concatStr;

    public String getMainData() {
        String s = concatStr.get("one", "two");

        data.setInfo("info" + s);

        return data.getInfo();
    }
}
