package sudoku_solver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.reader.Reader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;


/**
 * A formula is a conjunction (and) of clauses, or a single clause, in which
 * the formula evaluates itself and returns true if satisfiable and false otherwise,
 * Recursively travels up and down the formula to solve the formula using backtracking
 * 
 * @author Adam Tucker
 * @author Dennis Kluader
 * @author Umair Chaudhry
 * @version 03/30/2016
 *
 */

public class Formula {

	//The end of line marker for a cnf file which marks the end of a clause
	//in a boolean formula
	public static final int CNF_END_OF_LINEMARKER = 0;

	//The filename/location for a new file
	private static String fileName = ".cnf";

	//The directory for a new file
	private static String directory = "C:/Dennis_Umair_Adam_Sudoku_CNF";

	// list of clauses in CNF 
	private List<Clause> formulaList;

	// The current partial or completed state for this formula
	private List <Literal> successState;

	//index for backtracking
	int lastIndex;

	//for return value test  
	boolean isEmpty;

	// number of variables in the formula
	int numVariables;

	// number of clauses in the formula
	int numClauses;

	//True if any clause is empty
	boolean hasEmptyClause = false;

	//keeps track of the smallest sized clause in the list 
	private int minClauseSize;

	//keeps track of the index the first smallest clause
	@SuppressWarnings("unused")
	private int minClauseIndex;


	/**
	 * This constructor takes no arguments
	 */
	public Formula()
	{
		formulaList = new ArrayList<Clause>(13000);
		successState = new ArrayList <Literal> (1000);
		isEmpty = true; 
		numVariables = 0;
		minClauseSize=Integer.MAX_VALUE;
		minClauseIndex=-1;
	}

	public static void main ( String [] args ) throws IOException {
		long start = System.currentTimeMillis();
		File file = new File (args[0]);
		String path = file.getAbsolutePath();
		SudokuBoard board = SudokuBoard.readFromSudokuFile(path);
		//board.readFromSudokuFile("C:\Users\Adam Tucker\git2\SudokuSolver\Sudoku Solver\src\sudoku_solver");
		board.createBoard();
		Random random = new Random();
		File dir = new File (directory);
                String destFileName = args[0].toString() + "_" + start + fileName;
		File cnfFile = new File (dir, destFileName);
		board.writeToFile(cnfFile,destFileName);
		ISolver solver = SolverFactory . newDefault ();
		solver . setTimeout (3600); // 1 hour timeout
		Reader reader = new DimacsReader ( solver );
		// CNF filename is given on the command line
		try {
			String cnf = cnfFile.getAbsolutePath();
			IProblem problem = reader . parseInstance (cnf);
			if ( problem . isSatisfiable ()) {
				System . out . println ("Satisfiable !");
				//Gets the array of integers that are the solution to the formula
				int[] solutionArray = problem.primeImplicant();
                                List <Integer> posArray = new ArrayList <Integer> ();
				for (int i=0;i<solutionArray.length;i++) {
					if (i%((board.SIZE_SQUARED)*(board.SIZE_SQUARED))==0) {
						System.out.println();
					}
					if (solutionArray[i]>0) {
                                                // Adds true values to a list
                                                posArray.add(solutionArray[i]);
						System.out.print(solutionArray[i] + " ");
					}
				}
                                System.out.println();

				board.convertSatModelToSudokuBoard(problem.model());

                                System.out.println("Completed board:");
                                int[][][] solArray = board.printSudokuBoard();
                                board.saveSudokuResultToFile(solArray,destFileName);

                                System.out.println();
                                
			} else {
				System . out . println (" Unsatisfiable !");
			}
		} catch ( FileNotFoundException e) {
			e.printStackTrace();
		} catch ( ParseFormatException e) {
			e.printStackTrace();
		} catch ( IOException e) {
			e.printStackTrace();
		} catch ( ContradictionException e) {
			System .out . println (" Unsatisfiable ( trivial )!");
		} catch ( TimeoutException e) {
			System .out . println (" Timeout , sorry !");
		}
                long end = System.currentTimeMillis();
                System.err.println("\nThe runtime is " + (end-start)/1000.0 + " seconds");
	}

	public List<Clause> getFormulaList() {
		return formulaList;
	}

	public void setFormulaList(List<Clause> formulaList) {
		this.formulaList = formulaList;
	}

	/**
	 * This method adds a clause to this formula
	 * @param c The Clause to be added
	 */
	public void addClause(Clause c)
	{
		if (this.isEmpty == true)
			isEmpty=false;
		formulaList.add(c);
		if(c.size()<minClauseSize)
		{
			minClauseSize = c.size();
			minClauseIndex = formulaList.size()+1;
			if(minClauseSize==0)
			{
				hasEmptyClause=true;
			}
		}

	}

	/**
	 * Empty of the formula has been solved
	 * @return True if the collection is empty and false otherwise
	 */
	public boolean isEmpty () {
		return isEmpty;
	}

	/**
	 * This method returns the number of variables in the formula
	 * @return The number of variables
	 */
	public int getNumVariables() 
	{
		return numVariables;
	}

	/**
	 * This method sets the number of variables in the formula
	 * @param numVariables The number of variables
	 */
	public void setNumVariables(int numVariables) 
	{
		this.numVariables = numVariables;
	}

	/**
	 * This method returns the number of clauses in the formula
	 * @return the number of clauses
	 */
	public int getNumClauses() 
	{
		return formulaList.size();
	}

	/**
	 * Gets the last index
	 * @return The last index
	 */
	public int getLastIndex() {
		return lastIndex;
	}

	/**
	 * Sets the last index
	 * @param lastIndex The integer last index will be set as
	 */
	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}

	/**
	 * Gets the success state
	 * @return The success state
	 */
	public List<Literal> getSuccessState() {
		Collections.sort(successState);
		return successState;
	}

	/**
	 * Recursive backtracking call to solve the formula
	 * @return True if the formula is satisfiable and false otherwise
	 */
	public boolean runSolver()
	{
		//Base case if empty formula is satisfiable
		if(this.isEmpty())
		{
			return true;			
		}
		//Base case 2 if there is empty clause backtrack through the tree
		//to try different solutions
		else if(this.hasEmptyClause)
		{
			return false;
		}

		//Recursive element
		else{
			// Creates a child formula with true (a formula that has evaluated classes on the
			// current level, removed satisfied clauses and removes unnecessary literals
			Formula testTrue = createChild(true);
			if(testTrue.runSolver())
			{	
				//Found a solution and adds to successState
				successState.addAll(testTrue.getSuccessState());
				successState.add(new Literal(lastIndex+1,true));
				return true;
			}
			// Creates a child formula with true (a formula that has evaluated classes on the
			// current level, removed satisfied clauses and removes unnecessary literals
			Formula testFalse = createChild(false);
			if(testFalse.runSolver())
			{	
				// Found a solution and adds to successState
				successState.addAll(testFalse.getSuccessState());
				successState.add(new Literal(lastIndex+1,false));
				return true;
			}
			else 
			{
				return false;
			}
		}
	}

	/**
	 * Creates a child formula to be tested by the solver
	 * As the solver recursively travels through the collection
	 * each new child will have a new formula evaluated by the create child method
	 * @param givenBoolean The value to evaluate with the current formula
	 * @return The new formula that has been evaluated at the current level
	 * with any satisfied clause removed and unnecessary literal removed
	 */
	public Formula createChild(boolean givenBoolean)
	{
		//new formula with modified formula
		Formula child= new Formula();
		child.setLastIndex(this.lastIndex+1);
		//Creating Literal based on index# and value given. Using lastIndex until 
		//another option becomes available.
		Literal testLiteral = new Literal(this.lastIndex+1,givenBoolean);
		//iterator to evaluate each clause in Formula
		Iterator <Clause> itty = this.formulaList.iterator();
		//current working Clause
		Clause testClause; 
		//modified clause for child
		Clause resultClause;

		//loop to evaluate each Clause
		while(itty.hasNext())
		{
			testClause=itty.next();
			// Sends the clause to be evaluated
			resultClause=testClause.evaluateClause(testLiteral);
			// If returnClause is null the clause was satisfied
			// otherwise add the evaluate clause to the child formula
			if(resultClause!=null)
			{
				child.addClause(resultClause);
			}

		}
		return child;
	}

	/**
	 * This method loads and returns a formula from a file specification
	 * @param filename Name of the file containing the formula
	 * @return The parsed Formula
	 */
	public static Formula readFromFile(String filename)
	{
		BufferedReader br = null;
		String line = null;
		@SuppressWarnings("unused")
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
					Clause c = new Clause();
					while (!(line.contains(" 0"))) {
						line = line + br.readLine();
					}
					Scanner sc = new Scanner(line);
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
	 * Takes a formula and makes a new cnf file 
	 * @param fileToWrite The file to be made into a cnf file
	 */
	public void writeToFile (File fileToWrite, String nameOfSudoku) {
		try {    
			fileToWrite.getParentFile().mkdirs();
			Writer fileWriter = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(fileToWrite.toString()), "utf-8"));
			fileWriter.write("c This file illustrates a Sudoku board named " + nameOfSudoku + "\n");
			fileWriter.write("p cnf " + numVariables + " " + this.formulaList.size() + " \n");
			for (Clause myClause: this.formulaList) {
				String lines = myClause.toString() + CNF_END_OF_LINEMARKER + "\n";
                                fileWriter.write(lines);
			}
			fileWriter.close();
		} catch (IOException e) {
			System.err.println("Problem writing to the file statsTest.txt");
		}
	}

	/**
	 * Gets the filename/location for the a new .cnf file
	 * @return The filename/location
	 */
	public static String getFileName() {
		return fileName;
	}

	/**
	 * Sets the filename/location for a new .cnf file
	 * @param fileName The new filename/location
	 */
	public static void setFileName(String fileName) {
		Formula.fileName = fileName;
	}

	/**
	 * Returns a string as {{1 true or 2 true} and (3 false and 4 false}}
	 * @return The contents of the formula in a string format
	 */
	public String toString()
	{
		String result = "{ ";
		for (int i=0;i<formulaList.size()-1;i++)
		{
			if (formulaList.get(i)==null) {
				result += "[]" + " and ";
			}
			result += formulaList.get(i) + " and ";
		}
		result += formulaList.get(formulaList.size()-1)+" }" + "\n";
		return result;
	}

}
