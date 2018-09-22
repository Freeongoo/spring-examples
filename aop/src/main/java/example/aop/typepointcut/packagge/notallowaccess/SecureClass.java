package example.aop.typepointcut.packagge.notallowaccess;

import org.springframework.stereotype.Component;

@Component
public class SecureClass {
    public String getPassword() {
        return "123456";
    }
    public String getPass() {
        return "321";
    }
}
