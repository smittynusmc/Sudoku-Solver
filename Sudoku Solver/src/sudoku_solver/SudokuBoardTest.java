package sudoku_solver;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Test;

public class SudokuBoardTest {

	@Test
	public void testSudokuBoard() {
		SudokuBoard board = new SudokuBoard();
		board.createBoard();
		File file = new File (Formula.getFileName());
		board.writeToFile(file,"NAME OF FILE");
	}

	@Test
	public void testCreateBoard() {
		SudokuBoard board = new SudokuBoard();
		board.createBoard();
		//for (int i=0;i < 729;i++) {
		//	System.out.println(board.getFormulaList().get(i));
		//}
	}

	@Test
	public void gatherCellLiterals() {
		SudokuBoard board = new SudokuBoard();
		System.err.println("Cell Test");
		for(int i=1; i<=(board.SIZE_SIXTH); i++) {
			if(i<=(board.SIZE_FOURTH)) {
				List <Literal> fetchedList;
				fetchedList = board.gatherCellLiterals(i);
				board.finalizeClause(fetchedList);
				System.out.println(fetchedList);
			}

		}
		for (int i = 0; i < board.getFormulaList().size();i++) {
			System.out.println(board.getFormulaList().get(i));
		}
	}

	@Test
	public void gatherRowLiterals() {
		SudokuBoard board = new SudokuBoard();
		System.err.println("Row Test");
		for(int i=1; i<=(board.SIZE_SIXTH); i++) {
			if(i%(board.SIZE_SQUARED)==1)
			{
				List<Literal> fetchedList;
				fetchedList = board.gatherRowLiterals(i);
				board.finalizeClause(fetchedList);
				System.out.println(fetchedList);

			}
		}
		for (int i = 0; i < board.getFormulaList().size();i++) {
			System.out.println(board.getFormulaList().get(i));
		}
	}
	
	@Test
	public void gatherColumnLiterals() {
		SudokuBoard board = new SudokuBoard();
		System.err.println("Column Test");
		for(int i=1; i<=(board.SIZE_SIXTH); i++) {
			if((((i-1)/(board.SIZE_SQUARED)))%(board.SIZE_SQUARED)==0)//if (row%size^2==0)
			{
				List<Literal> fetchedList;
				fetchedList = board.gatherColumnLiterals(i);
				board.finalizeClause(fetchedList);
				System.out.println(fetchedList);
			}
		}
		for (int i = 0; i < board.getFormulaList().size();i++) {
			System.out.println(board.getFormulaList().get(i));
		}
	}
	
	@Test
	public void gatherBlockLiterals() {
		SudokuBoard board = new SudokuBoard();
		System.err.println("Block Test");
		for(int i=1; i<=(board.SIZE_SIXTH); i++) {
			if(((i/(board.SIZE_SQUARED))+1)%(board.SIZE)==1 && i%board.SIZE==1)
			{
				List<Literal> fetchedList;
				fetchedList = board.gatherBlockLiterals(i);
				board.finalizeClause(fetchedList);
				System.out.println(fetchedList);
			}
		}
		for (int i = 0; i < board.getFormulaList().size();i++) {
			System.out.println(board.getFormulaList().get(i));
		}
	}

	@Test
	public void testFinalizeClause() {
		fail("Not yet implemented");
	}

}
