package example.exceptions;

import java.util.HashMap;
import java.util.Map;

public class BaseException extends Exception {

    private static final long serialVersionUID = 1384744601435568104L;
    protected ERROR_CODES code;

    public BaseException (String message, ERROR_CODES code){
        super(message);
        this.code = code;
    }

    public Map<String, ?> getMapForResponse (){
        HashMap<String, Object> retVal = new HashMap<>();
        retVal.put("status", "error");
        retVal.put("code", code.getValue());
        retVal.put("message", getMessage());
        return retVal;
    }
}
