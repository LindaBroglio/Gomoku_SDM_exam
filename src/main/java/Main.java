import Gomoku.CLI;
import Gomoku.GUI;
import Gomoku.Exceptions.InputExceptions.QuitException;

import javax.swing.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello! Do you want to play Gomoku with a GUI or CLI? Enter 'GUI' or 'CLI' (or 'quit' to exit):");
        while (true) {
            String interfaceType = scanner.nextLine().trim();

            if ("quit".equalsIgnoreCase(interfaceType)) {
                System.out.println("You decided to exit the game before starting, ciao!");
                System.exit(0);
            } else if ("GUI".equalsIgnoreCase(interfaceType)) {
                SwingUtilities.invokeLater(GUI::new);
                break;
            } else if ("CLI".equalsIgnoreCase(interfaceType)) {
                CLI cli;
                try {
                    cli = new CLI();
                    cli.playFromCommandLine();
                } catch (QuitException e) {
                    System.out.println(e.getMessage());
                    System.exit(0);
                }
                break;
            } else {
                System.out.println("Invalid input. Please enter 'GUI' or 'CLI' (or 'quit' to exit).");
            }
        }
    }
}