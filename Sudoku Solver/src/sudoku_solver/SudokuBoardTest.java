package sudoku_solver;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SudokuBoardTest {

	
	@Test
	public void testCreateBoard() {
		SudokuBoard board = new SudokuBoard(3);
		board.createBoard();
		assertEquals(board.getNumClauses(),11988);
		//for (int i=0;i < 729;i++) {
		//	System.out.println(board.getFormulaList().get(i));
		//}
	}

	@Test
	public void gatherCellLiterals() 
	{
		SudokuBoard testBoard = new SudokuBoard(3);
		ArrayList <Literal> trialList = new ArrayList<Literal>();
		trialList.addAll(testBoard.gatherCellLiterals(5));
		assertTrue(trialList.contains(new Literal(5)));
		assertTrue(trialList.contains(new Literal(86)));
		assertTrue(trialList.contains(new Literal(167)));
		assertTrue(trialList.contains(new Literal(248)));
		assertTrue(trialList.contains(new Literal(329)));
		assertTrue(trialList.contains(new Literal(410)));
		assertTrue(trialList.contains(new Literal(491)));
		assertTrue(trialList.contains(new Literal(572)));
		assertTrue(trialList.contains(new Literal(653)));
	}

	@Test
	public void gatherRowLiterals() 
	{
		SudokuBoard testBoard = new SudokuBoard(3);
		ArrayList <Literal> trialList = new ArrayList<Literal>();
		trialList.addAll(testBoard.gatherRowLiterals(5));
		assertTrue(trialList.contains(new Literal(5)));
		assertTrue(trialList.contains(new Literal(6)));
		assertTrue(trialList.contains(new Literal(7)));
		assertTrue(trialList.contains(new Literal(8)));
		assertTrue(trialList.contains(new Literal(9)));
		assertTrue(trialList.contains(new Literal(10)));
		assertTrue(trialList.contains(new Literal(11)));
		assertTrue(trialList.contains(new Literal(12)));
		assertTrue(trialList.contains(new Literal(13)));

	}
	
	@Test
	public void gatherColumnLiterals() 
	{
		SudokuBoard testBoard = new SudokuBoard(3);
		ArrayList <Literal> trialList = new ArrayList<Literal>();
		trialList.addAll(testBoard.gatherColumnLiterals(14));
		assertTrue(trialList.contains(new Literal(14)));
		assertTrue(trialList.contains(new Literal(23)));
		assertTrue(trialList.contains(new Literal(32)));
		assertTrue(trialList.contains(new Literal(41)));
		assertTrue(trialList.contains(new Literal(50)));
		assertTrue(trialList.contains(new Literal(59)));
		assertTrue(trialList.contains(new Literal(68)));
		assertTrue(trialList.contains(new Literal(77)));
		assertTrue(trialList.contains(new Literal(86)));

	}
	
	@Test
	public void gatherBlockLiterals() 
	{
		SudokuBoard testBoard = new SudokuBoard(3);
		ArrayList <Literal> trialList = new ArrayList<Literal>();
		trialList.addAll(testBoard.gatherBlockLiterals(15));
		assertTrue(trialList.contains(new Literal(15)));
		assertTrue(trialList.contains(new Literal(16)));
		assertTrue(trialList.contains(new Literal(17)));
		assertTrue(trialList.contains(new Literal(24)));
		assertTrue(trialList.contains(new Literal(25)));
		assertTrue(trialList.contains(new Literal(26)));
		assertTrue(trialList.contains(new Literal(33)));
		assertTrue(trialList.contains(new Literal(34)));
		assertTrue(trialList.contains(new Literal(35)));
		
	}


}
