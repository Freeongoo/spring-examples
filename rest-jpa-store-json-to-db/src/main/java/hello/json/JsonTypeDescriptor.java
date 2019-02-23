package hello.json;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.MutableMutabilityPlan;
import org.hibernate.usertype.DynamicParameterizedType;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Properties;

public class JsonTypeDescriptor extends AbstractTypeDescriptor<Object> implements DynamicParameterizedType {

    private static final long serialVersionUID = 5497042304211233177L;
    private Class<?> jsonObjectClass;

    @Override
    public void setParameterValues(Properties parameters) {
        jsonObjectClass = ((ParameterType) parameters.get(PARAMETER_TYPE)).getReturnedClass();
    }

    public JsonTypeDescriptor() {
        super(Object.class, new MutableMutabilityPlan<Object>() {

            private static final long serialVersionUID = -3230637802949300360L;

            @Override
            protected Object deepCopyNotNull(Object value) {
                return JacksonUtil.clone(value);
            }
        });
    }

    @Override
    public boolean areEqual(Object one, Object another) {
        if (one == another) {
            return true;
        }
        if (one == null || another == null) {
            return false;
        }
        return JacksonUtil.toJsonNode(JacksonUtil.toString(one)).equals(JacksonUtil.toJsonNode(JacksonUtil.toString(another)));
    }

    @Override
    public String toString(Object value) {
        if (value instanceof Clob) {
            return getStringFromCLOB((Clob) value);
        } else
            return JacksonUtil.toString(value);
    }


    private String getStringFromCLOB(Clob clob) {

        InputStreamReader st = null;
        try {
            st = new InputStreamReader(clob.getAsciiStream(), Charset.forName("UTF-8"));
            StringBuffer sb = new StringBuffer();

            int length = (int) clob.length();

            if (length > 0) {
                char[] buffer = new char[length];
                while ((st.read(buffer)) != -1)
                    sb.append(buffer);
                return sb.toString();
            }
            return null;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                st.close();
            } catch (Exception e) {
            }
        }

    }

    @Override
    public Object fromString(String string) {
        return JacksonUtil.fromString(string, jsonObjectClass);
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public <X> X unwrap(Object value, Class<X> type, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        if (String.class.isAssignableFrom(type)) {
            return (X) toString(value);
        }
        if (Object.class.isAssignableFrom(type)) {
            return (X) JacksonUtil.toJsonNode(toString(value));
        }
        throw unknownUnwrap(type);
    }

    @Override
    public <X> Object wrap(X value, WrapperOptions options) {
        if (value == null) {
            return null;
        }

        if (value instanceof Clob)
            return fromString(getStringFromCLOB((Clob) value));
        else
            return fromString(value.toString());
    }
}