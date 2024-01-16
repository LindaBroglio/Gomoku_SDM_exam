import it.units.gomoku.CLI;
import it.units.gomoku.GUI;
import it.units.gomoku.exceptions.inputexceptions.QuitException;

import javax.swing.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello! Do you want to play gomoku with a GUI or CLI? Enter 'GUI' or 'CLI' (or 'quit' to exit):");
        boolean continueLoop = true;
        while (continueLoop) {
            String interfaceType = scanner.nextLine().trim();
            if ("quit".equalsIgnoreCase(interfaceType)) {
                System.out.println("You decided to exit the game before starting, ciao!");
                System.exit(0);
            } else if ("GUI".equalsIgnoreCase(interfaceType)) {
                SwingUtilities.invokeLater(GUI::new);
                continueLoop = false;
            } else if ("CLI".equalsIgnoreCase(interfaceType)) {
                CLI cli;
                try {
                    cli = new CLI();
                    cli.playFromCommandLine();
                } catch (QuitException e) {
                    System.out.println(e.getMessage());
                    System.exit(0);
                }
                continueLoop = false;
            } else {
                System.out.println("Invalid input. Please enter 'GUI' or 'CLI' (or 'quit' to exit).");
            }
        }
    }
}
