Приложение для отслеживания питания

Это приложение предназначено для отслеживания питания пользователей, включая управление пользователями, приёмами пищи и блюдами. Оно использует Spring Boot в качестве основной технологии и PostgreSQL в качестве базы данных.

Требования

Java 17 или выше
Maven 3.8 или выше
Docker и Docker Compose

Сборка приложения

Клонирование репозитория:
git clone https://github.com/IKS-cod/Task_from_1221.git

Сборка проекта с помощью Maven:
cd your-app
mvn clean package

Запуск базы данных PostgreSQL с помощью Docker Compose
Запуск контейнера PostgreSQL:
docker-compose up -d
Запуск приложения
Настройка переменных окружения:
В приложении настроены переменные окружения для подключения к базе данных, такие как DB_URL, DB_USER, DB_PASSWORD.

Запуск приложения:
java -jar target/test-work-from-systems1221-0.0.1-SNAPSHOT.jar
