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
			System.err.println("Problem writing to the file " + nameOfSudoku);
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
