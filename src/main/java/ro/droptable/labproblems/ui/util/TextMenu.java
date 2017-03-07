package ro.droptable.labproblems.ui.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vlad on 06.03.2017.
 */
@Deprecated
public class TextMenu {
    private Map<String, Command> commands;

    public TextMenu() {
        commands = new HashMap<>();
    }

    public void addCommand(Command c) {
        commands.put(c.getKey(), c);
    }

    private void printMenu() {
        commands.forEach((key, description) -> System.out.format("%3s. %s\n", key, description));
    }

    public void show() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                printMenu();
                System.out.print("Your option: ");
                String key = bufferedReader.readLine().trim();
                Command cmd = commands.get(key);
                if (cmd == null) {
                    System.out.println("Wrong command!");
                    continue;
                }
                cmd.execute();
            }
        } catch (IOException e) {
            e.printStackTrace(); // TODO: do something else
        }
    }
}
