
## Автоматизация тестирования api-сервиса <a href="https:\\petstore.swagger.io"> petstore.swagger.io  <a/>



## Содержание:

- [Стек ](#стек)
- [Проверки](#готовые-проверки)
- [Запуск тестов](#запуск-тестов)
- [Билды в Jenkins](#Инфраструктура-в-jenkins)
- [Allure-репорты](#allure-репорты)


## Стек

<p align="center">
<img width="6%" title="IntelliJ IDEA" src="images/logos/Intelij_IDEA.svg">
<img width="6%" title="Java" src="images/logos/Java.svg">
<img width="6%" title="Docker" src="images/logos/docker.svg">
<img width="6%" title="Rst" src="images/logos/Rest-Assured.svg">
<img width="6%" title="Allure Report" src="images/logos/Allure_Report.svg">
<img width="6%" title="Gradle" src="images/logos/Gradle.svg">
<img width="6%" title="JUnit5" src="images/logos/JUnit5.svg">
<img width="6%" title="GitHub" src="images/logos/GitHub.svg">
<img width="6%" title="Jenkins" src="images/logos/Jenkins.svg">
</p>

Тесты написаны на <code>Java 17</code> с <code>JUnit 5</code> и <code>Gradle</code>.

Для API тестов <code>REST Assured</code>
Запуск тестов локальный и на Jenkins.

Jenkins развернут в <code>Docker</code>
Подключена конфига в <code>Jenkins</code> с формированием Allure-отчета.

## Готовые проверки

- GET /pet/{petId} - получение pet по petID, положительные и негативные кейсы
- POST /pet - создание записи pet , положительные и негативные кейсы (в сервисе найден баг, позволяющий создать запись со всеми null, и получить Id)
- POST /pet/{petId} - апдейт записи pet по petId, позитивные и негативные кейсы
- DELETE /pet/{petId} - удаление записи pet по petId, позитивные и негативные кейсы


## Запуск тестов

Локальный и Jenkins параметр запуска тестов (Gradle)
```
./gradlew clean test
```




## Инфраструктура в Jenkins
<p align="center"><img title="Jenkins Build" src="images/screenshots/jenkins.png"></p>

## Allure-репорты
С детализацией шагов и содержимым HTTP запросов/ответов
<p align="center">
<img style="vertical-align:middle"  src="images/screenshots/allure.png"> 
</p>

### Main Page

<p align="center">
<img title="Allure Overview" src="images/screenshots/alluremain.png">
</p>



