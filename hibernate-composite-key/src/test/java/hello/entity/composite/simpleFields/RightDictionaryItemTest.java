package hello.entity.composite.simpleFields;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractJpaTest;
import org.junit.Test;

import javax.persistence.PersistenceException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@DatabaseSetup({"/right_dictionary_item.xml"})
public class RightDictionaryItemTest extends AbstractJpaTest {

    @Test
    public void create_WithCompositeKey() {
        RightDictionaryItem item = new RightDictionaryItem();
        RightDictionaryItemId rightDictionaryItemId = new RightDictionaryItemId("id", 123L);
        item.setRightDictionaryItemId(rightDictionaryItemId);

        RightDictionaryItem itemFromDb = entityManager.persistFlushFind(item);
        assertThat(itemFromDb.getRightDictionaryItemId().getId(), equalTo("id"));
        assertThat(itemFromDb.getRightDictionaryItemId().getSpecificProtectedObjectId(), equalTo(123L));
    }

    @Test(expected = PersistenceException.class)
    public void create_WithoutCompositeKey() {
        RightDictionaryItem item = new RightDictionaryItem();
        RightDictionaryItem itemFromDb = entityManager.persistFlushFind(item);
    }

    @Test
    public void getById_WhenExist() {
        RightDictionaryItemId rightDictionaryItemId = new RightDictionaryItemId("some id", 22L);

        RightDictionaryItem rightDictionaryItem = entityManager.find(RightDictionaryItem.class, rightDictionaryItemId);
        assertThat(rightDictionaryItem.getRightDictionaryItemId().getId(), equalTo("some id"));
    }

    @Test
    public void getById_WhenNotExist() {
        RightDictionaryItemId rightDictionaryItemId = new RightDictionaryItemId("notExist", -1L);

        RightDictionaryItem rightDictionaryItem = entityManager.find(RightDictionaryItem.class, rightDictionaryItemId);
        assertThat(rightDictionaryItem, equalTo(null));
    }
}