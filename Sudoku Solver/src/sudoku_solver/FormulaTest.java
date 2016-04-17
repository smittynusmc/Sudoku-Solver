package sudoku_solver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;

import org.junit.Test;

import org.sat4j.core.*;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ISolver;

/**
 * Test a suite for a formula
 * 
 * @author Adam Tucker
 * @author Dennis Kluader
 * @author Umair Chaudhry
 * @version 03/30/2016
 *
 */

public class FormulaTest {
	
	/**
	 * Test the creation of a empty formula
	 */
	@Test
	public void testFormula() {

		
		Formula testForm = new Formula();
		assertTrue(testForm.isEmpty());
		
		assertEquals(testForm.getLastIndex(),0);
			
	}
	
	/**
	 * Takes a cnf .txt file and makes a formula and then takes that
	 * formula and makes a .cnf file
	 */
	@Test
	public void testWritetoFile () {
		File file = new File("C:/Users/Adam Tucker/Google Drive/School/Spring 2016/DAA/sat4j-sat4j-sat-v20130419/s28.cnf");
		Formula formula = Formula.readFromFile(file.toString());
		Formula.writeToFile(formula);
		//String[] array = null;
		//array[0] = file.toString();
		//ISolver solver = SolverFactory.newDefault();
		//solver.
	}
	
	/**
	 * Tests adding a clause to a formula
	 */
	@Test
	public void testAddClause() {
		Clause clause1 = new Clause();
		Clause clause2 = new Clause();
		Clause clause3 = new Clause();
		
		Literal onet = new Literal(1);
		Literal onef = new Literal(-1);
		Literal twot = new Literal(2);
		Literal twof = new Literal(-2);
		Literal threet = new Literal(3);
		Literal threef = new Literal(-3);
	
		clause1.add(onet);
		clause1.add(twot);
		
		clause2.add(onef);
		clause2.add(twot);
		clause2.add(threet);
		
		clause3.add(onet);
		clause3.add(twof);
		clause3.add(threef);
		
		Formula testForm = new Formula();
		
		testForm.addClause(clause1);
		assertEquals(testForm.getNumClauses(),1);
		assertFalse(testForm.isEmpty());
		testForm.addClause(clause2);
		assertEquals(testForm.getNumClauses(),2);
		testForm.addClause(clause3);
		assertEquals(testForm.getNumClauses(),3);
		assertFalse(testForm.hasEmptyClause);
		
		Clause clause4=new Clause();
		testForm.addClause(clause4);
		assertTrue(testForm.hasEmptyClause);
		
		}
	
	/**
	 * Test if a formula is empty
	 */
	@Test
	public void testIsEmpty() {
		
		Clause clause1 = new Clause();
		
		Literal onet = new Literal(1);
		Literal twot = new Literal(2);
		
		clause1.add(onet);
		clause1.add(twot);
		Formula testForm = new Formula();
		assertTrue(testForm.isEmpty());
		testForm.addClause(clause1);
		assertEquals(testForm.getNumClauses(),1);
		assertFalse(testForm.isEmpty());
	}
	
	/**
	 * Test retrieving the number of clauses in a formula
	 */
	@Test
	public void testGetNumClauses() {
		Clause clause1 = new Clause();
		Clause clause2 = new Clause();
		Clause clause3 = new Clause();
		
		Literal onet = new Literal(1);
		Literal onef = new Literal(-1);
		Literal twot = new Literal(2);
		Literal twof = new Literal(-2);
		Literal threet = new Literal(3);
		Literal threef = new Literal(-3);
	
		clause1.add(onet);
		clause1.add(twot);
		
		clause2.add(onef);
		clause2.add(twot);
		clause2.add(threet);
		
		clause3.add(onet);
		clause3.add(twof);
		clause3.add(threef);
		
		Formula testForm = new Formula();
		
		testForm.addClause(clause1);
		assertEquals(testForm.getNumClauses(),1);
		testForm.addClause(clause2);
		assertEquals(testForm.getNumClauses(),2);
		testForm.addClause(clause3);
		assertEquals(testForm.getNumClauses(),3);
		Clause clause4=new Clause();
		testForm.addClause(clause4);
		assertEquals(testForm.getNumClauses(),4);
		
		Formula child1t = testForm.createChild(true);
		Formula child1f = testForm.createChild(false);
		assertEquals(child1t.getNumClauses(),2);
		assertEquals(child1f.getNumClauses(),3);
		
	}
	
	/**
	 * Test retrieving the last index of formula
	 */
	@Test
	public void testGetLastIndex() {
		Clause clause1 = new Clause();
		Clause clause2 = new Clause();
		Clause clause3 = new Clause();
		
		Literal onet = new Literal(1);
		Literal onef = new Literal(-1);
		Literal twot = new Literal(2);
		Literal twof = new Literal(-2);
		Literal threet = new Literal(3);
		Literal threef = new Literal(-3);
	
		clause1.add(onet);
		clause1.add(twot);
		
		clause2.add(onef);
		clause2.add(twot);
		clause2.add(threet);
		
		clause3.add(onet);
		clause3.add(twof);
		clause3.add(threef);
		
		Formula testForm = new Formula();
		
		testForm.addClause(clause1);
		testForm.addClause(clause2);
		testForm.addClause(clause3);
		
		assertEquals(testForm.getLastIndex(),0);
		Formula child1t = testForm.createChild(true);
		Formula child1f = testForm.createChild(false);
		assertEquals(child1t.getLastIndex(),1);
		assertEquals(child1f.getLastIndex(),1);
		
	}
	
	/**
	 * Test the recursive solver and checks to see if successState matches
	 */
	@Test
	public void testRunSolver() {
		
		Clause clause1 = new Clause();
		Clause clause2 = new Clause();
		Clause clause3 = new Clause();
		
		Clause clause5 = new Clause();
		Clause clause6 = new Clause();
		Clause clause7 = new Clause();
		
		Literal onet = new Literal(1);
		Literal onef = new Literal(-1);
		Literal twot = new Literal(2);
		Literal twof = new Literal(-2);
		Literal threet = new Literal(3);
		Literal threef = new Literal(-3);
	
		clause1.add(onet);
		clause1.add(twot);
		
		clause2.add(onef);
		clause2.add(twot);
		clause2.add(threet);
		
		clause3.add(onet);
		clause3.add(twof);
		clause3.add(threef);
		
		Formula testForm = new Formula();
		testForm.addClause(clause1);
		testForm.addClause(clause2);
		testForm.addClause(clause3);
		Clause clause4=new Clause();
		testForm.addClause(clause4);
		assertFalse(testForm.runSolver());
		
		Formula test2 = new Formula();
		clause7.add(onet);
		clause5.add(twot);
		clause6.add(threet);
		test2.addClause(clause5);
		test2.addClause(clause6);
		test2.addClause(clause7);
		assertTrue(test2.runSolver());
		assertEquals(test2.getSuccessState().toString(),"[1 true, 2 true, 3 true]");
	}

}