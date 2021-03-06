package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FluxTest extends AbstractTest {

    @Test
    void flux() {
        // Создаем поток данных для выгрузки наших
        Flux<User> fluxUsers = Flux.just(peter, lois, brain);

        // Получаем данные и обрабатываем по мере поступления
        fluxUsers.subscribe(System.out::println);
    }

    // complete - не выводится так как было прокинуто исключение, которое мы обработали и вывели
    @Test
    void fluxError_WhenHandleError() {
        Flux<Integer> ints = Flux.range(1, 6)
                .map(i -> {
                    if (i <= 3) return i;
                    throw new RuntimeException("Got to 4");
                });
        ints.subscribe(
                i -> System.out.println(i),
                error -> System.err.println("Error: " + error),
                () -> System.out.println("complete")
        );
    }

    @Test
    void fluxError_WhenNotHandleError() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            Flux<Integer> ints = Flux.range(1, 6)
                    .map(i -> {
                        if (i <= 3) return i;
                        throw new RuntimeException("Got to 4");
                    });
            ints.subscribe(
                    i -> System.out.println(i)
            );
        });
    }

    @Test
    void fluxWithRequestCount() {
        Flux<Integer> ints = Flux.range(1, 4);
        ints.subscribe(i -> System.out.println(i),
                error -> System.err.println("Error " + error),
                () -> System.out.println("Done"),
                sub -> sub.request(2));
    }

    @Test
    void fluxFilter() {
        Flux<User> userFlux = Flux.just(peter, lois, brain);

        // Фильтруем и оставляем одного Питера
        userFlux
                .filter(user -> user.getFirstName().equals("Peter"))
                .subscribe(user -> assertEquals(user, peter));
    }

    @Test
    void fluxMap() {
        Flux<User> userFlux = Flux.just(peter, lois, brain);

        // Преобразуем тип User в String
        userFlux
                .map(User::getFirstName)
                .subscribe(System.out::println);
    }

    // ничего не выведет - поскольку это не демон процесс и получается, что сначала будет ждать 1 секунду,
    // а потом уже будет выводить = но уже выводить нечего
    @Test
    public void fluxDelayElements() {
        Flux<User> userFlux = Flux.just(peter, lois, brain);

        // Добавляем задержку получения данных в 1 секунду и только после этого производим обработку событий
        userFlux.delayElements(Duration.ofSeconds(1))
                .subscribe(System.out::println);
    }

    @Test
    void fluxDelayElementsCountDownLatch() throws Exception {
        // Создаем счечик и заводим его на единицу
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Flux<User> userFlux = Flux.just(peter, lois, brain);

        // Запускаем userFlux со срабатыванием по прошествию одной секунды
        // и устанавлием сбрасывание счетчика при завершении
        userFlux
                .delayElements(Duration.ofSeconds(1))
                .doOnComplete(countDownLatch::countDown)
                .subscribe(System.out::println); // вывод каждую секунду

        // Ожидаем сброса счетчика
        countDownLatch.await();
    }
}
