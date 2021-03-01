# Выпускной проект курса OTUS Java Developer. Professional
https://otus.ru/lessons/java-professional

## Telegram-bot поиска ближайшего работающего банкомата

### Постановка задачи
Разработать сервис, позволяющий найти ближайший работающий банкомат с функцией выдачи денег.

Практическое применение: Найти гарантированно, к примеру, ночью, где можно снять наличные из банкомата. 

### Подход к решению
Есть доступное API банкоматов https://api.alfabank.ru/node/237, 
где возможно выполнить запрос GET /atms/status, получив список банкоматов, 
используя ключ AvailableNow, фильтруем список работающих АТМ с функцией выдачи денег,
далее при помощи Coordinates вычисляем ближайший банкомат по отношению к нашей позиции. 
Текущие координаты получаем из Telegram.

### ТЗ 
Приложение должно:
1. Принимать запросы клиента из Telegram
2. Находить ближайший работающий банкомат, в котором доступна выдача наличных денег
3. Направлять клиенту ответ в Telegram, содержащий адрес ближайшего банкомата

Архитектура приложения:
1. Три микросервиса: клиент для Telegram, клиент для ATM, сервис бизнес-логики
2. Взаимодействие между мс по REST API (если время останется, Messaging)
3. Хранение данных in-memory (если время будет, хранить историю запросов в локальной БД)

Цели проекта:
1. Исследовать API Telegram
2. Практика построения микросервисов
3. Практика работы с REST, OpenAPI

Технологический стек:
- Spring Boot 2+
- REST, OpenAPI
- опционально PostgreSQL, RabbitMQ/Kafka

### Работа с приложением
Доступ к Telegram-боту: http://t.me/atm_lookup_service_bot

### Использованные материалы
#### TelegramBot
инструкция по созданию базового функционала для бота
https://habr.com/ru/post/476306/

#### API банкоматов
https://api.alfabank.ru/node/237
https://api.alfabank.ru/man_cert_rsa

### Опции дебага
для просмотра SSL
-Djavax.net.debug=all

### SSL
#### получить crt из pfx
openssl pkcs12 -in apidevelopers.pfx -clcerts -nokeys -out apidevelopers.crt
#### сформировать keystore
openssl pkcs12 -export -in apidevelopers.crt -inkey apidevelopers.key -certfile apidevelopers.crt  -out apidevelopers.p12
