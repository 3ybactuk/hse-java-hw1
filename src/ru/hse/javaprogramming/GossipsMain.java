package ru.hse.javaprogramming;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main class containing io handling and other driver code.
 */
public class GossipsMain {

    /**
     * Checks if CLI argument "m" is passed correctly.
     * @param args first argument must be m - amount of maximum gossips.
     */
    public static void main(String[] args) {
        Gossipers gossipers;
        try {
            gossipers = new Gossipers(Integer.parseUnsignedInt(args[0]), 100);
        } catch (NumberFormatException e) {
            System.out.println("Error: CLI argument m (max messages until gossiper is tired) must be an unsigned integer number.");
            return;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: CLI argument m (max messages until gossiper is tired) must be passed to program.");
            return;
        }

        iohandler(gossipers);
    }

    /**
     * Handles user command input.
     */
    public static void iohandler(Gossipers gossipers) {
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            System.out.println("Waiting for input... type \"help\" to see info about commands.");
            System.out.print("> ");

            if (!scanner.hasNext()) {
                quit();
                return;
            }

            input = scanner.nextLine();

            List<String> cmd = parseCommand(input);

            try {
                switch (cmd.get(0)) {
                    case "create" -> create(cmd.get(1), cmd.get(2), gossipers);
                    case "link" -> link(cmd.get(1), cmd.get(2), gossipers);
                    case "unlink" -> unlink(cmd.get(1), cmd.get(2), gossipers);
                    case "message" -> message(cmd.get(1), cmd.get(2), gossipers);
                    case "gossips" -> gossips(gossipers);
                    case "listeners" -> listeners(cmd.get(1), gossipers);
                    case "about" -> about();
                    case "help" -> help();
                    case "quit" -> {
                        quit();
                        return;
                    }
                    default -> System.out.println("Error: unknown command.");
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Error: incorrect arguments for command \"" + cmd.get(0) + "\".");
            }
        }
    }

    /**
     * Parses line, splitting it into the command. Args inside the quotes are not splitted.
     * @param line line to parse
     * @return List of arguments
     */
    public static List<String> parseCommand(String line) {
        List<String> result = new ArrayList<>();
        int start = 0;
        boolean inQuotes = false;

        for (int cur = 0; cur < line.length(); cur++) {
            if (line.charAt(cur) == '\"') {
                inQuotes = !inQuotes;
            } else if (line.charAt(cur) == ' ' && !inQuotes) {
                result.add(line.substring(start, cur).replace("\"", ""));
                start = cur + 1;
            }
        }

        result.add(line.substring(start).replace("\"", ""));
        return result;
    }

    /**
     * Create a gossiper instance with chosen name.
     * Possible types:
     * 1. null
     * 2. censor
     * 3. spammer
     * 4. simple
     * 5. deduplicator
     * Max amount of gossipers = 100.
     *
     * @param name gossiper name
     * @param type gossiper type
     * @param gossipers Gossipers object (gossipers list)
     */
    public static void create(String name, String type, Gossipers gossipers) {
        gossipers.create(name, type);
    }

    /**
     * Links gossiper-listener from gossiper-spreader
     * @param name1 gossiper-spreader
     * @param name2 gossiper-listener
     * @param gossipers Gossipers object (gossipers list)
     */
    public static void link(String name1, String name2, Gossipers gossipers) {
        gossipers.linkOrUnlink(name1, name2, true);
    }

    /**
     * Unlinks gossiper-listener from gossiper-spreader
     * @param name1 gossiper-spreader
     * @param name2 gossiper-listener
     * @param gossipers Gossipers object (gossipers list)
     */
    public static void unlink(String name1, String name2, Gossipers gossipers) {
        gossipers.linkOrUnlink(name1, name2, false);
    }

    /**
     * Send the message to gossiper by the name
     * @param name gossiper's name
     * @param message message text
     * @param gossipers Gossipers object (gossipers list)
     */
    public static void message(String name, String message, Gossipers gossipers) {
        gossipers.message(name, message);
    }

    /**
     * Alphabetically output the names of gossipers
     * @param gossipers Gossipers object (gossipers list)
     */
    public static void gossips(Gossipers gossipers) {
        gossipers.gossips();
    }

    /**
     * Alphabetically output the listeners of a gossiper
     * @param name gossiper name
     * @param gossipers Gossipers object (gossipers list)
     */
    public static void listeners(String name, Gossipers gossipers) {
        gossipers.listeners(name);
    }

    /**
     * Exit from the app.
     */
    public static void quit() {
        System.out.println("See you again!");
    }

    /**
     * Author's credentials.
     */
    public static void about() {
        System.out.println("""
                Работу выполнил:
                Студент группы БПИ211-2
                Шубин Никита Васильевич.
                """);
    }

    /**
     * Prints out all possible commands and how to use them.
     */
    public static void help() {
        System.out.println("""
                сreate      <name>  <type>
                                Создать экземпляр сплетницы с указанным
                                именем.
                                Возможные значения type:
                                1. null
                                2. censor
                                3. spammer
                                4. simple
                                5. deduplicator
                                Максимальное количество сплетниц – 100. При
                                превышении максимального числа сплетниц
                                приложение печатает сообщение об ошибке и
                                ожидает ввод новой команды
                link        <name1> <name2>
                                Зарегистрировать сплетницу с именем
                                <name2> слушателем сообщений от сплетницы
                                с именем <name1>
                unlink      <name1> <name2>
                                Убрать сплетницу с именем <name2> из
                                слушателей сообщений от сплетницы с
                                именем <name1>
                message     <name>  <message>
                                Отправить указанное сообщение сплетнице с
                                именем <name>
                gossips
                                Напечатать имена всех имеющихся сплетниц в
                                алфавитном порядке;
                listeners   <name>
                                Напечатать имена всех слушателей сообщений
                                от сплетницы с именем <name> в алфавитном
                                порядке
                quit
                                Выйти из приложения
                about
                                Вывести имя автора шедевра (разработанного
                                приложения), его группу и подгруппу
                help
                                Вывести список доступных команд в
                                приложении
                """);
    }
}
