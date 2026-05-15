package MephiPackage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        System.out.println("Запуск Spring Boot приложения...");
        SpringApplication.run(Application.class, args);
        System.out.println("Приложение запущено!");
    }
}