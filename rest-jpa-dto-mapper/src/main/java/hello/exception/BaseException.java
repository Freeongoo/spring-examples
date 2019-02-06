package hello.exception;

import java.util.HashMap;
import java.util.Map;

public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 2087230193117227158L;
    protected ErrorCode code;

    public BaseException (String message, ErrorCode code){
        super(message);
        this.code = code;
    }

    public Map<String, ?> getMapForResponse() {
        Map<String, Object> retVal = new HashMap<>();

        retVal.put("status", "error");
        retVal.put("code", code.getValue());
        retVal.put("message", getMessage());

        return retVal;
    }
}
