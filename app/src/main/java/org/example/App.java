package org.example;

import java.util.*;
import java.util.stream.*;

public class App {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Tic-Tac-Toe!");

        boolean playAgain = true;

        while (playAgain) {

            TicTacToeGame game = new TicTacToeGame(scanner);
            game.play();

            System.out.print("Would you like to play again (yes/no)? ");
            String response = scanner.nextLine().trim().toLowerCase();

            playAgain = response.equals("yes");
        }

        System.out.println("Goodbye!");
    }

    public String getGreeting() {
        return "Welcome to Tic-Tac-Toe!";
    }
}

class TicTacToeGame {

    private final Scanner scanner;
    private final Board board;
    private char currentPlayer;

    TicTacToeGame(Scanner scanner) {
        this.scanner = scanner;
        this.board = new Board();
        this.currentPlayer = 'X';
    }

    void play() {

        while (true) {

            board.print();

            System.out.print("Player " + currentPlayer + ", enter your move (1-9): ");

            String input = scanner.nextLine();

            int move;

            try {
                move = Integer.parseInt(input);
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid input! Try again.");
                continue;
            }

            if (!board.place(move, currentPlayer)) {
                System.out.println("Invalid move! Try again.");
                continue;
            }

            if (board.isWinner(currentPlayer)) {
                board.print();
                System.out.println("Player " + currentPlayer + " wins!");
                return;
            }

            if (board.isDraw()) {
                board.print();
                System.out.println("It's a draw!");
                return;
            }

            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }
    }
}

class Board {

    private final char[] cells;

    Board() {
        cells = new char[9];
    }

    boolean place(int position, char player) {

        if (position < 1 || position > 9) {
            return false;
        }

        if (cells[position - 1] != '\0') {
            return false;
        }

        cells[position - 1] = player;
        return true;
    }

    boolean isWinner(char player) {

        int[][] wins = {
                {0,1,2},
                {3,4,5},
                {6,7,8},
                {0,3,6},
                {1,4,7},
                {2,5,8},
                {0,4,8},
                {2,4,6}
        };

        for (int[] combo : wins) {

            if (cells[combo[0]] == player &&
                cells[combo[1]] == player &&
                cells[combo[2]] == player) {

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

        for (int i = 0; i < 9; i++) {

            if (cells[i] == '\0') {
                System.out.print(i + 1);
            }
            else {
                System.out.print(cells[i]);
            }

            if ((i + 1) % 3 == 0) {
                System.out.println();
            }
            else {
                System.out.print(" | ");
            }
        }

        System.out.println();
    }
}

// STREAM API ADD-ONS

class StreamUtilities {

    public static int[] evensOnly(int[] numbers) {
        return Arrays.stream(numbers)
                .filter(n -> n % 2 == 0)
                .toArray();
    }

    public static int[] oddsOnly(int[] numbers) {
        return Arrays.stream(numbers)
                .filter(n -> n % 2 != 0)
                .toArray();
    }

    public static int[] addFive(int[] numbers) {
        return Arrays.stream(numbers)
                .map(n -> n + 5)
                .toArray();
    }

    public static int[] squareNumbers(int[] numbers) {
        return Arrays.stream(numbers)
                .map(n -> n * n)
                .toArray();
    }
}

// GENERIC LINKED LIST

class Node<T> {

    private T data;
    private Node<T> next;

    public Node(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }
}

class SinglyLinkedList<T> {

    private Node<T> head;

    public void add(T data) {

        Node<T> newNode = new Node<>(data);

        if (head == null) {
            head = newNode;
            return;
        }

        Node<T> current = head;

        while (current.getNext() != null) {
            current = current.getNext();
        }

        current.setNext(newNode);
    }

    public boolean contains(T data) {

        Node<T> current = head;

        while (current != null) {

            if (current.getData().equals(data)) {
                return true;
            }

            current = current.getNext();
        }

        return false;
    }
}

// GENERIC BINARY TREE

class TreeNode<T> {

    T data;
    TreeNode<T> left;
    TreeNode<T> right;

    TreeNode(T data) {
        this.data = data;
    }
}

class BinaryTree<T extends Comparable<T>> {

    private TreeNode<T> root;

    public void insert(T data) {
        root = insertRecursive(root, data);
    }

    private TreeNode<T> insertRecursive(TreeNode<T> current, T data) {

        if (current == null) {
            return new TreeNode<>(data);
        }

        if (data.compareTo(current.data) < 0) {
            current.left = insertRecursive(current.left, data);
        }
        else if (data.compareTo(current.data) > 0) {
            current.right = insertRecursive(current.right, data);
        }

        return current;
    }

    public boolean contains(T data) {
        return containsRecursive(root, data);
    }

    private boolean containsRecursive(TreeNode<T> current, T data) {

        if (current == null) {
            return false;
        }

        if (current.data.equals(data)) {
            return true;
        }

        if (data.compareTo(current.data) < 0) {
            return containsRecursive(current.left, data);
        }

        return containsRecursive(current.right, data);
    }
}

