package sudoku_solver;

import java.io.BufferedReader;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Test;


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
         * Tests the write to file (done in netbeans)
         * Eclipse doesn't need a main method to run tests
         * @param args 
         */
        public static void main (String[]args) {
            FormulaTest form = new FormulaTest ();
            form.testWritetoFile();
        }
	
	/**
	 * The writetoFile method in formula takes in this case a txt file and uses the formulaList (instance of
         * Sudoku) and writes that instance it a newly created cnf file
	 * Takes a cnf .txt file and makes a formula and then takes that
	 * formula and makes a .cnf file. The result the same s28.cnf file minus some of the original comments
	 */
	@Test
	public void testWritetoFile () {
		File file = new File("C:/Users/Owner/Dropbox/group/Brute Force SAT solver project/Brute Force SAT solver project/s20 - copy.cnf");
		Formula formula = readFromFile(file.toString());
		formula.writeToFile(file,"NAME OF FILE");
	}
        
        /**
	 * This method loads and returns a formula from a file specification
	 * @param filename name of the file containing the formula
	 * @return the Formula
	 */
	public Formula readFromFile(String filename)
	{
		BufferedReader br = null;
		String line = null;
		int numVars, numClauses, var;
		Formula f = new Formula();
		try {
			br = new BufferedReader(new FileReader(filename));
			while((line = br.readLine()) != null){          
				if(line.startsWith("c"))
					continue;
				if(line.length() == 0)
					continue;
				if(line.startsWith("p cnf")){
					line = line.replaceAll("\\s+", " ");
					String[] data = line.split("[ \t]");
					numVars = Integer.parseInt(data[2]);
					numClauses = Integer.parseInt(data[3]);
					f.setNumVariables(numVars);
					//f.setNumClauses(numClauses);
				}
				else{
					Scanner sc = new Scanner(line);
					Clause c = new Clause();
					while(sc.hasNextInt()){
						var = sc.nextInt();
						if(var == 0)
							break;
						else if(var > 0){
							c.add(new Literal(var));
						}  
						else{
							c.add(new Literal(var));
						}
					}
					f.addClause(c);
					sc.close();
				}
			}
			br.close();
		} catch (FileNotFoundException ex) {
			System.out.println("file I/O error: " + ex);
		} catch (IOException ex) {
			System.out.println("file I/O error: " + ex);
		}
		return f;
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
	

}