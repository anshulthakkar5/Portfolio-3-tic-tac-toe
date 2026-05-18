package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void appHasAGreeting() {

        App app = new App();

        assertNotNull(app.getGreeting());
        assertEquals("Welcome to Tic-Tac-Toe!", app.getGreeting());
    }

    @Test
    void testPlaceMove() {

        Board board = new Board();

        assertTrue(board.place(1, 'X'));
        assertFalse(board.place(1, 'O'));
    }

    @Test
    void testWinnerRow() {

        Board board = new Board();

        board.place(1, 'X');
        board.place(2, 'X');
        board.place(3, 'X');

        assertTrue(board.isWinner('X'));
    }

    @Test
    void testDraw() {

        Board board = new Board();

        char[] moves = {'X','O','X','X','O','O','O','X','X'};

        for (int i = 0; i < 9; i++) {
            board.place(i + 1, moves[i]);
        }

        assertTrue(board.isDraw());
    }

    @Test
    void testEvensOnly() {

        int[] input = {1,2,3,4,5,6};
        int[] expected = {2,4,6};

        assertArrayEquals(expected, StreamUtilities.evensOnly(input));
    }

    @Test
    void testOddsOnly() {

        int[] input = {1,2,3,4,5,6};
        int[] expected = {1,3,5};

        assertArrayEquals(expected, StreamUtilities.oddsOnly(input));
    }

    @Test
    void testAddFive() {

        int[] input = {1,2,3};
        int[] expected = {6,7,8};

        assertArrayEquals(expected, StreamUtilities.addFive(input));
    }

    @Test
    void testSquareNumbers() {

        int[] input = {1,2,3,4};
        int[] expected = {1,4,9,16};

        assertArrayEquals(expected, StreamUtilities.squareNumbers(input));
    }

    @Test
    void testLinkedList() {

        SinglyLinkedList<String> list = new SinglyLinkedList<>();

        list.add("A");
        list.add("B");

        assertTrue(list.contains("A"));
        assertTrue(list.contains("B"));
        assertFalse(list.contains("C"));
    }

    @Test
    void testBinaryTree() {

        BinaryTree<Integer> tree = new BinaryTree<>();

        tree.insert(10);
        tree.insert(5);
        tree.insert(15);

        assertTrue(tree.contains(10));
        assertTrue(tree.contains(5));
        assertTrue(tree.contains(15));
        assertFalse(tree.contains(20));
    }
}
