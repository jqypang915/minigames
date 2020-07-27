package tictactoe;


import java.util.*;
import java.util.Scanner;

public class TicTacToeGame {

    // Tictactoe board for game
    private char[][] board;


    // Creates Empty Board
    public void createBoard() {

        board = new char[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    // Prints the Current Orientation of the Board
    public void printBoard() {
        System.out.println("  -------------");
        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                System.out.print("1 ");
            } else if (i == 1) {
                System.out.print("2 ");
            } else {
                System.out.print("3 ");
            }
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
            System.out.println("  -------------");
        }
        System.out.println("    1   2   3");

    }


    // Set move
    public boolean move(int x, int y, int player) {

        boolean isvalid = validmove(x, y);
        if (!isvalid) {
            return false;
        } else {
            x--;
            y--;
            char mark;
            if (player == 1) {
                mark = 'X';
            } else {
                mark = 'O';
            }
            board[y][x] = mark;
            return true;
        }
    }

    //Makes sure a numeric value is received
    public int numericmove() {
        while (true) {
            int numInput;
            try {
                Scanner input = new Scanner(System.in);
                numInput = input.nextInt();
                if (!(numInput > 0 && numInput <= 3)) {
                    System.out.println("Invalid input; re-enter coordinate");
                    continue;
                }
                return numInput;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input; re-enter coordinate");
            }
        }
    }

    // Valid Move and is within board boundaries
    public boolean validmove(int x, int y) {
        // Values of x and y To check if in bounds
        final int xmove = x;
        final int ymove = y;
        // Values of x and y to check if spot is empty
        int xcoor = x - 1;
        int ycoor = y -1;
        // All Possible Moves for x and y
        int[] possiblemoves = new int[]{1,2,3};

        if (Arrays.stream(possiblemoves).anyMatch(i ->i == xmove)
                && Arrays.stream(possiblemoves).anyMatch(i ->i == ymove)) {
            if (board[ycoor][xcoor] == ' ') {
                return true;
            }
        }
        return false;
    }


    // Checks to see if the game has been won
    public char checkwin() {
        if (checkcolumnwin() != ' ') {
            return checkcolumnwin();
        } else if (checkdiagonalwin() != ' ') {
            return checkdiagonalwin();
        } else if (checkrowwin() != ' ') {
            return checkrowwin();
        } else if (checktie()){
            return 'T';
        } else {
            return ' ';
        }
    }

    // A check for tie if no wins have been found
    private boolean checktie() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    // Check if win in row
    private char checkrowwin() {
        for (int y = 0; y < 3; y++) {
            if ((board[0][y] != ' ') && (board[1][y] == board[0][y])
                    && (board[2][y] == board[0][y])) {
                return board[0][y];
            }
        }
        return ' ';
    }

    // Check if win in column
    private char checkcolumnwin() {
        for (int x = 0; x < 3; x++) {
            if ((board[x][0] != ' ') && (board[x][1] == board[x][0])
                    && (board[x][2] == board[x][0])) {
                return board[x][2];
            }
        }
        return ' ';
    }

    // Check if win diagonal
    private char checkdiagonalwin() {
        if ((board[0][0] != ' ') && (board[1][1] == board[0][0])
                && (board[2][2] == board[0][0])) {
            return board[1][1];
        } else if ((board[0][2] != ' ') && (board[1][1] == board[0][2])
                && (board[2][0] == board[0][2])) {
            return board[1][1];
        } else {
            return ' ';
        }
    }

    // All functions below are for minimax ai player

    // Ai's best move
    public void bestMove() {
        // AI will always be X
        double best = Double.NEGATIVE_INFINITY;
        // Default move, but will be changed since any values is greater than negative infinity
        int[] aimove = new int[]{1,1};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = 'X';
                    double score = minimax(board, 0, false);
                    board[i][j] = ' ';
                    if (score > best) {
                        best = score;
                        aimove = new int[]{i, j};
                    }
                }
            }
        }
        board[aimove[0]][aimove[1]] = 'X';
    }

    // AI based off Minimax Algorithm
    public double minimax(char[][] board, int depth, boolean ismax) {
        // Scores for Minimax comparison
        Map<Character, Integer> scores = new HashMap<Character, Integer>()
        {{
            put('X', 10);
            put('T', 0);
            put('O', -10);
        }};
        char result = checkwin();
        if (result != ' ') {
            return scores.get(result);
        }
        if (ismax) {
            // ai option
            double bestscore = Double.NEGATIVE_INFINITY;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // check to see if spot open
                    if (board[i][j] == ' ') {
                        board[i][j] = 'X';
                        double score = minimax(board, depth + 1, false);
                        // replacing test move
                        board[i][j] = ' ';
                        bestscore = Math.max(score, bestscore);
                    }
                }
            }
            return bestscore;
        } else {
            // human option
            double bestscore = Double.POSITIVE_INFINITY;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // check to see if spot open
                    if (board[i][j] == ' ') {
                        board[i][j] = 'O';
                        double score = minimax(board, depth + 1, true);
                        // replacing test move
                        board[i][j] = ' ';
                        bestscore = Math.min(score, bestscore);
                    }
                }
            }
            return bestscore;
        }

    }


}
