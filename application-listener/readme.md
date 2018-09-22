# Events in Spring

Run and see in log when events is called.


ContextStartedEvent
Это событие публикуется, когда ApplicationContext запущен через метод start() интерфейса ConfigurableApplicationContext. После получения этого события мы можем выполнить необходимые нам действия (например, записать то-то в базу данных и т.д.).

ContextRefreshedEvent
Это событие публикуется когда ApplicationContext обновлён или инициализирован. Оно может быть вызвано использованием метода refresh() интерфейса ConfigurableApplicationContext.

ContextStoppedEvent
Это событие публикуется, когда ApplicationContext остановлен методом stop() интерфейса ConfigurableApplicationContext. Мы также можем дать команду выполнить определённую работу после получения этого события.

ContextClosedEvent
Публикуется, когда ApplicationContext закрыт методом close() интерфейса ConfigurableApplicationContext. Закрытие контекста – это конец файла. После этого он не может быть перезапущен или обновлен.

RequestHandledEvent
Это специальное событие, которое информирует нас о том, что все бины HTTP-запроса были обслужены (ориентирован на веб).