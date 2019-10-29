package com.example.demo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MonoTest extends AbstractTest {

    @Test
    void mono() {
        // Создаем объект
        Mono<User> monoPeter = Mono.just(peter);

        // Блокируем текущий поток до тех пор пока не получим объект
        User peter2 = monoPeter.block();

        // Проверяем, что мы получили ожидаемый объект
        assertEquals(peter, peter2);
    }

    @Test
    void blockMono() {
        Mono<User> monoPeter = Mono.just(peter);

        // Блокируем текущий поток до тех пока мы не получим и не обработаем данные
        String name = monoPeter.map(User::getFirstName).block();
        assertEquals(name, "Peter");
    }
}
