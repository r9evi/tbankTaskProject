# Translation Application

Translation Application - это приложение для перевода текста с использованием Yandex Translate API. Оно включает в себя Spring Boot сервер, который взаимодействует с базой данных PostgreSQL.

## Требования

- Docker
- Docker Compose
- JDK 21

## Установка
1. Убедитесь, что у вас установлен Docker и Docker Compose.
2. Склонируйте репозиторий:

```bash
git clone https://github.com/r9evi/tbankTaskProject.git
cd tbankTaskProject/translate
```

# Запуск приложения
**Использование Docker Compose**

Приложение настроено для работы с Docker Compose, что позволяет легко запустить его вместе с необходимыми зависимостями (PostgreSQL).

Откройте терминал и перейдите в директорию проекта.

Запустите команду:

```bash
docker-compose up --build
```
Это создаст и запустит контейнеры для приложения и базы данных.

# Конфигурация
**Переменные среды**

Для настройки приложения используются следующие переменные среды:

- SPRING_DATASOURCE_URL: URL базы данных
- SPRING_DATASOURCE_USERNAME: Имя пользователя базы данных
- SPRING_DATASOURCE_PASSWORD: Пароль базы данных
- SPRING_LIQUIBASE_CHANGELOG: Путь к файлу changelog Liquibase
- IAM_TOKEN: Токен для доступа к Yandex Translate API
- FOLDER_ID: ID папки для Yandex Translate API

Пример использования переменных среды в Docker Compose:
```yaml
services:
  app:
    build: .
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/postgres
      SPRING_DATASOURCE_USERNAME: postgres
      SPRING_DATASOURCE_PASSWORD: postgres
      SPRING_LIQUIBASE_CHANGELOG: classpath:db/changelog/db.changelog-master.yaml
      IAM_TOKEN: ${IAM_TOKEN}
      FOLDER_ID: ${FOLDER_ID}
    ports:
      - "8080:8080"
```

# Использование
После запуска приложения, оно будет доступно по адресу http://localhost:8080/swagger-ui/index.html#/.

Для дальнешей работы с API используйте UI OpenAPI
