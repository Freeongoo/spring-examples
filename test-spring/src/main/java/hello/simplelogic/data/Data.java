package hello.simplelogic.data;

import org.springframework.stereotype.Component;

@Component
public class Data {
    private String info;

    public String getInfo() {
        return "getInfo from Data: " + info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
