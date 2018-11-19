package createJson;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class CreateJsonByStringTest {

    @Test
    public void createByString_WhenSimpleInField() throws IOException {
        String jsonStr = "{\"id\": 1}";
        System.out.println(jsonStr);

        // check is valid
        ClassWithId classWithId = new ObjectMapper().readValue(jsonStr, ClassWithId.class);
        System.out.println(classWithId);

        // expected
        ClassWithId expected = new ClassWithId();
        expected.setId(1);

        assertThat(classWithId, equalTo(expected));
    }

    @Test(expected = JsonParseException.class)
    public void createByString_WhenInvalidFormat_WhenNotSetDoubleQuotes() throws IOException {
        String jsonStr = "{id: 1}";
        System.out.println(jsonStr);

        // check is valid
        ClassWithId classWithId = new ObjectMapper().readValue(jsonStr, ClassWithId.class);
    }

    @Test(expected = JsonParseException.class)
    public void createByString_WhenInvalidFormat_WhenUseSingleQuotes() throws IOException {
        String jsonStr = "{'id': 1}";
        System.out.println(jsonStr);

        // check is valid
        ClassWithId classWithId = new ObjectMapper().readValue(jsonStr, ClassWithId.class);
    }

    @Test
    public void createByString_WhenExistDateFieldAndCollectionObject() throws IOException, ParseException {
        String jsonStr = "{\"id\": 1, \"date\": \"2017-02-02T10:10:10\", \"classWithIds\": [{\"id\": 1}]}";
        System.out.println(jsonStr);

        // check is valid
        ClassTwoField classTwoField = new ObjectMapper().readValue(jsonStr, ClassTwoField.class);
        System.out.println(classTwoField);

        // expected
        ClassTwoField expected = new ClassTwoField();
        List<ClassWithId> classWithIds = new ArrayList<>();
        ClassWithId classWithId = new ClassWithId();
        classWithId.setId(1);
        classWithIds.add(classWithId);
        expected.setId(1);
        expected.setDate(parseISO("2017-02-02T10:10:10"));
        expected.setClassWithIds(classWithIds);

        assertThat(classTwoField, equalTo(expected));
    }

    private static class ClassWithId {
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("ClassWithId{");
            sb.append("id=").append(id);
            sb.append('}');
            return sb.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ClassWithId that = (ClassWithId) o;
            return id == that.id;
        }

        @Override
        public int hashCode() {

            return Objects.hash(id);
        }
    }

    private static class ClassTwoField {
        private int id;
        private Date date;
        private Collection<ClassWithId> classWithIds;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss", timezone = "Europe/Moscow")
        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Collection<ClassWithId> getClassWithIds() {
            return classWithIds;
        }

        public void setClassWithIds(Collection<ClassWithId> classWithIds) {
            this.classWithIds = classWithIds;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ClassTwoField that = (ClassTwoField) o;
            return id == that.id &&
                    Objects.equals(date, that.date) &&
                    Objects.equals(classWithIds, that.classWithIds);
        }

        @Override
        public int hashCode() {

            return Objects.hash(id, date, classWithIds);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("ClassTwoField{");
            sb.append("id=").append(id);
            sb.append(", date=").append(date);
            sb.append(", classWithIds=").append(classWithIds);
            sb.append('}');
            return sb.toString();
        }
    }

    private static Date parse(String dateStr, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(dateStr);
    }

    private static Date parseISO(String dateStr) throws ParseException {
        return parse(dateStr, "yyyy-MM-dd'T'hh:mm:ss");
    }
}
