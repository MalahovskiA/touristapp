# Используем базовый образ Tomcat 10 с JDK 17
FROM tomcat:10.1.28-jre17

# Копируем ваш WAR файл в директорию для деплоя Tomcat
COPY target/touristApp.war /usr/local/tomcat/webapps/touristApp.war

# Копируем wait-for-it.sh в контейнер
COPY wait-for-it.sh /usr/local/bin/wait-for-it
RUN chmod +x /usr/local/bin/wait-for-it

# Экспортируем порт, на котором работает Tomcat
EXPOSE 8080

# Установите переменные среды (если они есть) в формате "ключ=значение"
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/postgres
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=admin

# Запуск Tomcat с ожиданием базы данных
CMD ["wait-for-it", "db:5432", "--", "catalina.sh", "run"]