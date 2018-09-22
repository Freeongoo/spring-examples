package example.aop.typepointcut.annotation;

import example.customannotation.SpendTime;
import org.springframework.stereotype.Component;

@Component
public class Runner {

    @SpendTime
    public void run(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
