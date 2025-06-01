package ru.aston.intensive.hibernate;

import lombok.extern.slf4j.Slf4j;
import ru.aston.intensive.hibernate.entity.User;
import ru.aston.intensive.hibernate.exc.AppException;
import ru.aston.intensive.hibernate.service.UserService;
import java.util.Scanner;

@Slf4j
public class ConsoleApp {

    static UserService userService = new UserService();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            try {
                runner();
            } catch (AppException exc) {
                log.error(exc.getMessage());
            }
        }
    }

    private static void runner() {
        System.out.println(showMenu());
        String choice = scanner.nextLine();

        switch (Integer.parseInt(choice)) {
            case 1:
                createUser();
                System.out.println(System.lineSeparator());
                break;
            case 2:
                User user = getUser();
                System.out.println("Найден пользователь: " + user);
                System.out.println(System.lineSeparator());
                break;
            case 3:
                updateUser();
                System.out.println(System.lineSeparator());
                break;
            case 4:
                deleteUser();
                System.out.println(System.lineSeparator());
                break;
            case 5:
                System.exit(0);
            default:
                log.warn("Unknown menu id: {}", choice);
                System.out.println("Номер операции не идентифицируется");
        }
    }

    public static String showMenu() {
        return "Введите номер операции\n" +
            "1. Добавить нового пользователя\n" +
            "2. Получить данные о пользователе\n" +
            "3. Обновить данные пользователя\n" +
            "4. Удалить пользователя\n" +
            "5. Выйти\n";
    }

    public static void createUser() {
        log.info("Creating new user");

        System.out.print("Введите имя: ");
        String name = scanner.nextLine();

        System.out.print("Введите почту: ");
        String email = scanner.nextLine();

        userService.saveUser(name, email);
    }

    public static User getUser() {
        log.info("Getting existing user");

        System.out.print("Введите id пользователя: ");

        Long userId = getUserId(scanner.nextLine());
        return userService.getUserById(userId);
    }

    public static void updateUser() {
        log.info("Updating existing user");

        System.out.print("Введите id пользователя: ");
        Long userId = getUserId(scanner.nextLine());

        System.out.print("Введите новое имя: ");
        String name = scanner.nextLine();

        System.out.print("Введите новую почту: ");
        String email = scanner.nextLine();

        userService.updateUser(userId, name, email);
    }

    public static void deleteUser() {
        log.info("Deleting existing user");

        System.out.print("Введите id пользователя: ");
        Long userId = getUserId(scanner.nextLine());

        userService.deleteUserById(userId);
    }

    private static Long getUserId(String id) {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new AppException(String.format("Invalid id format: %s", id));
        }
    }

}
