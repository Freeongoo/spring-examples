package examples.scheduler;

import org.awaitility.Duration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScheduledTasksTest {

    @SpyBean
    private ScheduledTasks scheduledTasks;

    @Test
    public void scheduleTaskWithFixedRate_WhenTenSeconds() {
        await().atMost(Duration.TEN_SECONDS)
                .untilAsserted(
                        () -> verify(scheduledTasks, times(5))
                        .scheduleTaskWithFixedRate()
                );
    }

    @Test
    public void scheduleTaskWithFixedRate_WhenThreeSeconds() {
        await().atMost(new Duration(3, SECONDS))
                .untilAsserted(
                        () -> verify(scheduledTasks, times(2))
                        .scheduleTaskWithFixedRate()
                );
    }

    @Test
    public void scheduleTaskWithFixedDelay_WhenFourteenSeconds() {
        await().atMost(new Duration(14, SECONDS))
                .untilAsserted(
                        () -> verify(scheduledTasks, times(2))
                                .scheduleTaskWithFixedDelay()
                );
    }

    @Test
    public void scheduleTaskWithFixedDelay_WhenSixSeconds() {
        await().atMost(new Duration(6, SECONDS))
                .untilAsserted(
                        () -> verify(scheduledTasks, times(0))
                                .scheduleTaskWithFixedDelay()
                );
    }

    @Test
    public void scheduleTaskWithInitialDelay_WhenTenSeconds() {
        await().atMost(Duration.TEN_SECONDS)
                .untilAsserted(
                        () -> verify(scheduledTasks, times(2))
                                .scheduleTaskWithInitialDelay()
                );
    }

    @Test
    public void scheduleTaskWithInitialDelay_WhenFiveSeconds() {
        await().atMost(Duration.FIVE_SECONDS)
                .untilAsserted(
                        () -> verify(scheduledTasks, times(0))
                                .scheduleTaskWithInitialDelay()
                );
    }

    @Test
    public void scheduleTaskWithCronExpression_WhenOneSecond() {
        await().atMost(Duration.ONE_SECOND)
                .untilAsserted(
                        () -> verify(scheduledTasks, times(1))
                                .scheduleTaskWithCronExpression()
                );
    }

    @Test
    public void scheduleTaskWithCronExpression_WhenFiveSeconds() {
        await().atMost(Duration.FIVE_SECONDS)
                .untilAsserted(
                        () -> verify(scheduledTasks, times(5))
                                .scheduleTaskWithCronExpression()
                );
    }

    @Test
    public void scheduleTaskWithCronExpression_When500MilSeconds() {
        await().atMost(new Duration(500, MILLISECONDS))
                .untilAsserted(
                        () -> verify(scheduledTasks, times(0))
                                .scheduleTaskWithCronExpression()
                );
    }
}