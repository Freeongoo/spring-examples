package hello.simplelogic;

import hello.simplelogic.data.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyService {
    @Autowired
    private Data data;

    public String getMainData() {
        data.setInfo("info");

        return data.getInfo();
    }
}
