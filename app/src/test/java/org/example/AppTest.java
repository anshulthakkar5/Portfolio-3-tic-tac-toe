

package org.example;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void appHasGreeting() {

        App app = new App();

        assertNotNull(app.getGreeting());
    }

    @Test
    void greetingCorrect() {

        App app = new App();

        assertEquals(
                "Welcome to Tic-Tac-Toe!",
                app.getGreeting());
    }

    @Test
    void placeMoveWorks() {

        Board board = new Board();

        assertTrue(board.place(1, 'X'));
    }

    @Test
    void cannotPlaceTakenSpot() {

        Board board = new Board();

        board.place(1, 'X');

        assertFalse(board.place(1, 'O'));
    }

    @Test
    void invalidMoveLow() {

        Board board = new Board();

        assertFalse(board.place(0, 'X'));
    }

    @Test
    void invalidMoveHigh() {

        Board board = new Board();

        assertFalse(board.place(10, 'X'));
    }

    @Test
    void rowWinnerDetected() {

        Board board = new Board();

        board.place(1, 'X');
        board.place(2, 'X');
        board.place(3, 'X');

        assertTrue(board.isWinner('X'));
    }

    @Test
    void columnWinnerDetected() {

        Board board = new Board();

        board.place(1, 'O');
        board.place(4, 'O');
        board.place(7, 'O');

        assertTrue(board.isWinner('O'));
    }

    @Test
    void diagonalWinnerDetected() {

        Board board = new Board();

        board.place(1, 'X');
        board.place(5, 'X');
        board.place(9, 'X');

        assertTrue(board.isWinner('X'));
    }

    @Test
    void drawDetected() {

        Board board = new Board();

        char[] moves = {

                'X','O','X',
                'X','O','O',
                'O','X','X'
        };

        for (int i = 0; i < 9; i++) {

            board.place(i + 1, moves[i]);
        }

        assertTrue(board.isDraw());
    }

    @Test
    void boardNotDraw() {

        Board board = new Board();

        board.place(1, 'X');

        assertFalse(board.isDraw());
    }

    @Test
    void availableMovesCorrect() {

        Board board = new Board();

        board.place(1, 'X');
        board.place(5, 'O');

        List<Integer> moves =
                board.availableMoves();

        assertEquals(7, moves.size());

        assertFalse(moves.contains(1));

        assertFalse(moves.contains(5));
    }

    @Test
    void emptySpaceDetected() {

        Board board = new Board();

        assertTrue(board.isEmpty(1));
    }

    @Test
    void occupiedSpaceDetected() {

        Board board = new Board();

        board.place(1, 'X');

        assertFalse(board.isEmpty(1));
    }

    @Test
    void removeMoveWorks() {

        Board board = new Board();

        board.place(1, 'X');

        board.remove(1);

        assertTrue(board.isEmpty(1));
    }

    @Test
    void totalMovesCorrect() {

        Board board = new Board();

        board.place(1, 'X');
        board.place(2, 'O');
        board.place(3, 'X');

        assertEquals(3, board.totalMoves());
    }
}
