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
# Конфигурация
**Переменные среды**

Для работы приложения требуется настроить ключи в файле .env. А именно:

- IAM_TOKEN: Токен для доступа к Yandex Translate API
- FOLDER_ID: ID папки для Yandex Translate API
- API-URL: URL Yandex Translate

# Запуск приложения

Данный этап выполняется только после настройки .env

**Использование Docker Compose**

Приложение настроено для работы с Docker Compose, что позволяет легко запустить его вместе с необходимыми зависимостями (PostgreSQL).

Откройте терминал и перейдите в директорию проекта.

Запустите команду:

```bash
docker-compose up --build
```
Это создаст и запустит контейнеры для приложения и базы данных.


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
