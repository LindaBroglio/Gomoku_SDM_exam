import Gomoku.CLI;
import Gomoku.GUI;
import Gomoku.Exceptions.InputExceptions.QuitException;

import javax.swing.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to play Gomoku with a GUI or CLI? Enter 'GUI' or 'CLI':");
        String interfaceType = scanner.nextLine().trim();

        if ("GUI".equalsIgnoreCase(interfaceType)) {
            SwingUtilities.invokeLater(GUI::new);
        } else if ("CLI".equalsIgnoreCase(interfaceType)) {
            CLI cli;
            try {
                cli = new CLI();
                cli.playFromCommandLine();
            } catch (QuitException e) {
                System.out.println(e.getMessage());
                System.exit(0);
            }
        } else {
            System.out.println("Invalid input. Please enter 'GUI' or 'CLI'.");
        }
    }
}
