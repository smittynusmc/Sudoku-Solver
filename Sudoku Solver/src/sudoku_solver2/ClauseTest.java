package sudoku_solver2;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Test;

/**
 * Test suite for a clause
 * 
 * @author Adam Tucker
 * @author Dennis Kluader
 * @author Umair Chaudhry
 * @version 03/30/2016
 *
 */

public class ClauseTest {
	
	/**
	 * Test adding a list as the collection of literals
	 */
	@Test
	public void testAddList() 
	{
		Clause clause1 = new Clause();
		Clause clause2 = new Clause();
		Clause clause3 = new Clause();
		Clause clause4 = new Clause();

		Literal onet = new Literal(1);
		Literal twot = new Literal(2);
		Literal threet = new Literal(3);
		Literal threef = new Literal(-3);
		ArrayList <Literal> testList=new ArrayList<Literal>();
		testList.add(onet);
		testList.add(twot);
		testList.add(threet);
		clause2.addList(testList);
		clause1.add(onet);
		clause1.add(twot);
		clause1.add(threet);
		assertEquals(clause1,clause2);	
		clause3.add(onet);
		clause3.add(twot);
		clause3.add(threef);
		clause4.add(onet);
		clause4.add(twot);
		clause4.add(threet);
		assertFalse(clause3.equals(clause4));
	}
	
	/**
	 * Test how a clause evaluates its literal and the satisfiability of itself
	 */
	@Test
	public void testEvaluateClause()
	{
		Clause clause1 = new Clause();
		Clause clause2 = new Clause();
		Clause clause3 = new Clause();
		Clause clause4 = new Clause();
		
		Literal onet = new Literal(1);
		Literal onef = new Literal(-1);
		Literal twot = new Literal(2);
		Literal twof = new Literal(-2);
		Literal threet = new Literal(3);

		clause1.add(onet);
		clause1.add(twot);
		clause1.add(threet);
		Clause result1 = clause1.evaluateClause(onef);

		clause2.add(twot);
		clause2.add(threet);		
		assertEquals(result1,clause2);

		Clause result2 = clause1.evaluateClause(twof);

		clause4.add(onet);
		clause4.add(twot);
		clause4.add(threet);
		assertEquals(clause1,clause4);
		
		clause3.add(onet);
		clause3.add(threet);
		assertEquals(clause3,result2);

		Clause result3 = clause1.evaluateClause(onet);
		assertEquals(null,result3);

		Clause result4 = clause1.evaluateClause(threet);
		assertEquals(null,result4);

	}
	
	/**
	 * Test a clauses retrieves its attributes
	 */
	@Test
	public void testGetValues() 
	{
		Clause clause1 = new Clause();
		
		Literal onet = new Literal(1);
		Literal twot = new Literal(2);
		Literal threet = new Literal(3);
		
		ArrayList <Literal> testList=new ArrayList<Literal>();
		testList.add(onet);
		testList.add(twot);
		testList.add(threet);
		
		clause1.add(onet);
		clause1.add(twot);
		clause1.add(threet);
		assertEquals(clause1.getValues(),testList);
	
	}
	
	/**
	 * Test a clauses ability to clear its literals
	 */
	public void testClearClause()
	{
		Clause clause1 = new Clause();
		Literal onet = new Literal(1);
		Literal twot = new Literal(2);
		Literal threet = new Literal(3);
		clause1.add(onet);
		clause1.add(twot);
		clause1.add(threet);
		clause1.clearClause();
		assertTrue(clause1.size()==0);
	}
	
	/**
	 * Test a clauses size
	 */
	@Test
	public void testSize() 
	{
		Clause clause1 = new Clause();
		Literal onet = new Literal(1);
		Literal twot = new Literal(2);
		Literal threet = new Literal(3);
		clause1.add(onet);
		assertTrue(clause1.size()==1);
		clause1.add(twot);
		assertTrue(clause1.size()==2);
		clause1.add(threet);
		assertTrue(clause1.size()==3);
		clause1.clearClause();
		assertTrue(clause1.size()==0);
	}
}
