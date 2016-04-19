package sudoku_solver;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class SudokuBoardTest {

	@Test
	public void testSudokuBoard() {
		SudokuBoard board = new SudokuBoard();
		board.createBoard();
		File file = new File (Formula.getFileName());
		board.writeToFile(file);
	}

	@Test
	public void testCreateBoard() {
		SudokuBoard board = new SudokuBoard();
		board.createBoard();
		for (int i=0;i < 729;i++) {
			System.out.println(board.getFormulaList().get(i));
		}
	}

	@Test
	public void testFinalizeClause() {
		fail("Not yet implemented");
	}

}
