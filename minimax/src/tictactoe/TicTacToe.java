package tictactoe;
import java.util.*;

public class TicTacToe {

    // Scanner for input
    static Scanner input;


    public static void main(String[] args) {
        // Input Scanner
        input = new Scanner(System.in);
        // one or two player option
        String option;
        // y or n option to plaay
        String again;

        while (true) {
            System.out.println("Welcome! Set number of players: [one/two]");
            while (true) {
                option = input.next();
                if (option.equals("one") || option.equals("two")) {
                    break;
                }
                System.out.println("Incorrect Input: Set number of players: [one/two]");
            }
            System.out.println("Number of Players: " + option);
            if (option.equals("two")) {
                twoplayers();
            } else {
                oneplayer();
            }
            while (true) {
                System.out.println("play again [y/n]?");
                again = input.next();
                if (again.equals("n") || again.equals("y")) {
                    break;
                } else {
                    System.out.println("Come on man... just say y or n");
                }
            }
            if (again.equals("n")) {
                break;
            }
        }
        System.out.println("Thanks for Playing");
    }

    // Runs a human to human game
    public static void twoplayers() {

        // Counts how many moves done
        int move = 0;
        TicTacToeGame game = new TicTacToeGame();
        game.createBoard();
        game.printBoard();

        while (true) {
            int turn = move%2 + 1;
            System.out.println("Player " + Integer.toString(turn) + "'s turn");
            while (true) {
                int hori;
                int vert;
                System.out.println("Move: Enter Horizontal Coordinate");
                hori = game.numericmove();
                System.out.println("Move: Enter Vertical Coordinate");
                vert = game.numericmove();
                boolean validmove = game.move(hori, vert, turn);
                if (validmove) {
                    break;
                }
                System.out.println("Move is invalid. Please enter valid move");
            }
            game.printBoard();
            move++;
            // Checks to see if game has ended
            if (game.checkwin() == 'X') {
                System.out.println("Player 1 won the game!");
                break;
            } else if (game.checkwin() == 'O') {
                System.out.println("Player 2 won the game!");
                break;
            } else if (game.checkwin() == 'T') {
                System.out.println("Tie!");
                break;
            }
        }
    }

    public static void oneplayer() {
        int move = 0;
        TicTacToeGame game = new TicTacToeGame();
        game.createBoard();
        System.out.println("AI will move first. Human is Player 2");
        game.printBoard();

        while (true) {
            int turn = move%2 + 1;
            if (turn == 1) {
                System.out.println("AI Moves");
                game.bestMove();

            } else {
                System.out.println("Player's turn");
                while (true) {
                    int hori;
                    int vert;
                    System.out.println("Move: Enter Horizontal Coordinate");
                    hori = game.numericmove();
                    System.out.println("Move: Enter Vertical Coordinate");
                    vert = game.numericmove();
                    boolean validmove = game.move(hori, vert, 2);
                    if (validmove) {
                        break;
                    }
                    System.out.println("Move is invalid. Please enter valid move");
                }
            }
            game.printBoard();
            move++;

            // checks if winner has been found
            if (game.checkwin() == 'X') {
                System.out.println("AI Wins");
                break;
            } else if (game.checkwin() == 'T') {
                System.out.println("Tie");
                break;
            } else if (game.checkwin() == 'O') {
                System.out.println("Player wins");
                break;
            }
        }
    }

}
