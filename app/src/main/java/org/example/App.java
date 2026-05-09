

package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Tic-Tac-Toe!");

        boolean playAgain = true;

        while (playAgain) {

            int gameType = promptGameType(scanner);

            TicTacToeGame game =
                    new TicTacToeGame(scanner, gameType);

            game.play();

            playAgain = promptPlayAgain(scanner);
        }

        System.out.println("Goodbye!");
    }

    public String getGreeting() {

        return "Welcome to Tic-Tac-Toe!";
    }

    private static int promptGameType(Scanner scanner) {

        while (true) {

            System.out.println(
                    "\nWhat kind of game would you like to play?\n");

            System.out.println("1. Human vs. Human");
            System.out.println("2. Human vs. Computer");
            System.out.println("3. Computer vs. Human");

            System.out.print("\nWhat is your selection? ");

            String input = scanner.nextLine();

            try {

                int choice = Integer.parseInt(input);

                if (choice >= 1 && choice <= 3) {

                    return choice;
                }

            } catch (Exception e) {

            }

            System.out.println(
                    "\nThat is not a valid selection!");
        }
    }

    private static boolean promptPlayAgain(Scanner scanner) {

        while (true) {

            System.out.print(
                    "\nWould you like to play again (yes/no)? ");

            String input =
                    scanner.nextLine().trim().toLowerCase();

            if (input.equals("yes")) {

                return true;
            }

            if (input.equals("no")) {

                return false;
            }

            System.out.println(
                    "That is not a valid entry!");
        }
    }
}


/* ======================================================
                    GAME CLASS
====================================================== */

class TicTacToeGame {

    private final Scanner scanner;
    private final Board board;

    private char currentPlayer;

    private final boolean computerX;
    private final boolean computerO;

    private final Random random;

    TicTacToeGame(Scanner scanner, int gameType) {

        this.scanner = scanner;

        this.board = new Board();

        this.currentPlayer = 'X';

        this.random = new Random();

        computerX = (gameType == 3);

        computerO = (gameType == 2);

        if (computerX) {

            System.out.println(
                    "\nGreat! The computer will go first.");
        }

        if (computerO) {

            System.out.println(
                    "\nGreat! You will go first.");
        }
    }

    void play() {

        System.out.println();

        board.print();

        while (true) {

            if (isComputerTurn()) {

                int move = computerMove();

                board.place(move, currentPlayer);

                System.out.println(
                        "\nComputer chooses " + move);

            } else {

                Integer move = promptMove();

                if (move == null ||
                        !board.place(move, currentPlayer)) {

                    System.out.println(
                            "\nThat is not a valid move! Try again.");

                    continue;
                }
            }

            System.out.println();

            board.print();

            if (board.isWinner(currentPlayer)) {

                System.out.println(
                        "\nPlayer " + currentPlayer + " wins!");

                return;
            }

            if (board.isDraw()) {

                System.out.println("\nIt's a draw!");

                return;
            }

            currentPlayer =
                    (currentPlayer == 'X') ? 'O' : 'X';
        }
    }

    private boolean isComputerTurn() {

        return (currentPlayer == 'X' && computerX)
                || (currentPlayer == 'O' && computerO);
    }

    private Integer promptMove() {

        System.out.print("\nWhat is your move? ");

        String input = scanner.nextLine();

        try {

            int move = Integer.parseInt(input);

            if (move >= 1 && move <= 9) {

                return move;
            }

        } catch (Exception e) {

        }

        return null;
    }


    /* ======================================================
                        COMPUTER AI
    ====================================================== */

    private int computerMove() {

        char computer = currentPlayer;

        char opponent =
                (computer == 'X') ? 'O' : 'X';

        // First move -> corner

        if (board.totalMoves() == 0) {

            int[] corners = {1, 3, 7, 9};

            return corners[random.nextInt(4)];
        }

        // Second move -> center

        if (board.totalMoves() == 1 &&
                board.isEmpty(5)) {

            return 5;
        }

        // Winning move

        Integer winMove =
                findWinningMove(computer);

        if (winMove != null) {

            return winMove;
        }

        // Block opponent

        Integer blockMove =
                findWinningMove(opponent);

        if (blockMove != null) {

            return blockMove;
        }

        // Random move

        List<Integer> moves =
                board.availableMoves();

        return moves.get(
                random.nextInt(moves.size()));
    }

    private Integer findWinningMove(char player) {

        for (int move : board.availableMoves()) {

            board.place(move, player);

            boolean win =
                    board.isWinner(player);

            board.remove(move);

            if (win) {

                return move;
            }
        }

        return null;
    }
}


/* ======================================================
                    BOARD CLASS
====================================================== */

class Board {

    private final char[] cells;

    Board() {

        cells = new char[9];
    }

    boolean place(int position, char player) {

        if (position < 1 || position > 9) {

            return false;
        }

        int index = position - 1;

        if (cells[index] != '\0') {

            return false;
        }

        cells[index] = player;

        return true;
    }

    void remove(int position) {

        cells[position - 1] = '\0';
    }

    boolean isEmpty(int position) {

        return cells[position - 1] == '\0';
    }

    int totalMoves() {

        int count = 0;

        for (char c : cells) {

            if (c != '\0') {

                count++;
            }
        }

        return count;
    }

    List<Integer> availableMoves() {

        List<Integer> moves = new ArrayList<>();

        for (int i = 0; i < 9; i++) {

            if (cells[i] == '\0') {

                moves.add(i + 1);
            }
        }

        return moves;
    }

    boolean isWinner(char player) {

        int[][] lines = {

                {0,1,2},
                {3,4,5},
                {6,7,8},

                {0,3,6},
                {1,4,7},
                {2,5,8},

                {0,4,8},
                {2,4,6}
        };

        for (int[] line : lines) {

            if (cells[line[0]] == player &&
                cells[line[1]] == player &&
                cells[line[2]] == player) {

                return true;
            }
        }

        return false;
    }

    boolean isDraw() {

        for (char c : cells) {

            if (c == '\0') {

                return false;
            }
        }

        return true;
    }

    void print() {

        System.out.println(formatRow(0));

        System.out.println(
                "  -----+-----+-----");

        System.out.println(formatRow(3));

        System.out.println(
                "  -----+-----+-----");

        System.out.println(formatRow(6));
    }

    private String formatRow(int start) {

        return "    " +
                display(start) +
                "  |  " +
                display(start + 1) +
                "  |  " +
                display(start + 2);
    }

    private String display(int index) {

        if (cells[index] == '\0') {

            return String.valueOf(index + 1);
        }

        return String.valueOf(cells[index]);
    }
}
