# Используем официальный образ Tomcat с поддержкой Java 17
FROM tomcat:9.0-jdk17-temurin

# Удаляем дефолтные приложения Tomcat (опционально)
RUN rm -rf /usr/local/tomcat/webapps/*

# Копируем ваш WAR файл в директорию для деплоя Tomcat
COPY target/touristapp.war /usr/local/tomcat/webapps/ROOT.war

# Expose порт, на котором работает Tomcat
EXPOSE 8080

# Стартуем Tomcat
CMD ["catalina.sh", "run"]