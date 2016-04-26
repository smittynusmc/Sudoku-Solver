package sudoku_solver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SudokuBoard extends Formula {
	/**
	 * The SudokuBoard uses the structure of the SAT solver formula class to 
	 * store and create all the necessary clauses to represent an empty Sudoku board.
	 * 
	 *  Additional clauses will be added as the Sudoku puzzle information is scanned in 
	 *  from file
	 */

	public final int SIZE;
	public final int SIZE_SQUARED;

	public final int SIZE_FOURTH;
	public final int SIZE_SIXTH;
        
        private boolean[][][] printBoard;
	private int numberOfItemInBlock;
        
	public SudokuBoard()
	{
		SIZE = 2;
		SIZE_SQUARED = (int) Math.pow(SIZE, 2);
		SIZE_FOURTH = (int) Math.pow(SIZE, 4);
		SIZE_SIXTH = (int) Math.pow(SIZE, 6);
		
		numberOfItemInBlock = SIZE_SQUARED;

		printBoard = new boolean[numberOfItemInBlock][numberOfItemInBlock][numberOfItemInBlock];

		setZeroToCellofBoard();
	}

	public SudokuBoard(int SizeGiven)
	{
		SIZE = SizeGiven;
		SIZE_SQUARED = (int) Math.pow(SIZE, 2);
		SIZE_FOURTH = (int) Math.pow(SIZE, 4);
		SIZE_SIXTH = (int) Math.pow(SIZE, 6);
                
                numberOfItemInBlock = SIZE_SQUARED;

		printBoard = new boolean[numberOfItemInBlock][numberOfItemInBlock][numberOfItemInBlock];

		setZeroToCellofBoard();

	}
        
        public void setZeroToCellofBoard() 
        {

            for (int i = 0; i < this.numberOfItemInBlock; i++) 
            {
                for (int j = 0; j < this.numberOfItemInBlock; j++) 
                {
                    for (int k = 0; k < this.numberOfItemInBlock; k++) {
                        this.printBoard[i][j][k] = false;
                    }
                }
            }
        }
        
        public void convertSatModelToSudokuBoard(int[] model) 
	{
        for (int i = 0; i < model.length; i++) 
        {
            int index = model[i];
            int[] pos = this.getPositionForIndex(index > 0 ? index : -index);
            this.printBoard[pos[0]][pos[1]][pos[2]] = (index > 0) ? true : false;
        }//end for loop
    }//end convertSatModelToSudokuBoard
	
	public int[] getPositionForIndex(int index) 
	{
        index = index - 1;
        int[] pos = new int[3];
        
        int maxInCol = this.numberOfItemInBlock;
        int maxInRow = this.numberOfItemInBlock*maxInCol;
        
        pos[0] = index/maxInRow;
        index = index%maxInRow;
        pos[1] = index/maxInCol;
        pos[2] = index%maxInCol;
        
        return pos;
    }//end getPositionForIndex
	
	public boolean saveSudokuResultToFile(int[][][] solArray, String fileName) 
	{
        //Destination will be the current directory of the project i.e. C:\Users\Owner\Documents\NetBeansProjects\Sudoku-Solver
        String outputSudokuFile = "output_of " + fileName + "_.txt";

        try{
            PrintWriter writer = new PrintWriter(outputSudokuFile);
            
            for (int i = 0; i < this.numberOfItemInBlock; i++) {
                for (int j = 0; j < this.numberOfItemInBlock; j++) {
                    for (int k = 0; k < this.numberOfItemInBlock; k++) {
                        if (solArray[i][j][k] > 0) {
                            writer.format("%4s", solArray[i][j][k] + "");
                        }
                    }
                    
                    if (j % this.SIZE == this.SIZE - 1) {
                        writer.print("  ");
                    }
                }
                if(i % this.SIZE == this.SIZE - 1) {
                    writer.println();
                }
                writer.println();
            }
        
            writer.close();
            return true;
        } catch(IOException ex) {
            System.out.println(ex.getMessage());
        } 
        
        return false;
    }
	
	public int[][][] printSudokuBoard() {
        int[][][] solArray = new int[this.numberOfItemInBlock][this.numberOfItemInBlock][this.numberOfItemInBlock];
        for (int i = 0; i < this.numberOfItemInBlock; i++) {
            for (int j = 0; j < this.numberOfItemInBlock; j++) {
                for (int k = 0; k < this.numberOfItemInBlock; k++) {
                    if (this.printBoard[k][j][i] == true) {
                        solArray[j][i][k] = k+1;

                    }
                }
            }
        }
                for (int a = 0; a < solArray.length; a++) {
                    for (int b = 0; b < solArray.length; b++) {
                        for (int c = 0; c < solArray.length; c++) {
                            if (solArray[a][b][c] > 0) {
                                System.out.format("%4s", solArray[a][b][c] + "");
                            }
                }
            }
            System.out.println();
        }
        return solArray;        
    }
   
        
	public void createBoard()
	{

		for(int i=1; i<=(SIZE_SIXTH); i++)
		{
			if(i<=(SIZE_FOURTH))
			{
				List<Literal> fetchedList;
				fetchedList = gatherCellLiterals(i);
				finalizeClause(fetchedList);
			}

			if(i%(SIZE_SQUARED)==1)
			{
				List<Literal> fetchedList;
				fetchedList = gatherRowLiterals(i);
				finalizeClause(fetchedList);
			}

			if((((i-1)/(SIZE_SQUARED)))%(SIZE_SQUARED)==0)//if (row%size^2==1)
			{
				List<Literal> fetchedList;
				fetchedList = gatherColumnLiterals(i);
				finalizeClause(fetchedList);
			}

			if(((i/(SIZE_SQUARED))+1)%(SIZE)==1 && i%SIZE==1)
			{

				List<Literal> fetchedList;
				fetchedList = gatherBlockLiterals(i);
				finalizeClause(fetchedList);

			}

		}
	}

	protected List<Literal> gatherCellLiterals(int startingCellNum)
	{
		ArrayList <Literal> workingList = new ArrayList <Literal>();

		//create Cell Literals
		for(int j=0;j<(SIZE_SQUARED);j++)
		{
			workingList.add(new Literal(startingCellNum+j*SIZE_FOURTH));
		}
		return workingList;	
	}

	protected List<Literal> gatherColumnLiterals(int startingCellNum)
	{
		ArrayList <Literal> workingList = new ArrayList <Literal>();

		//create column clauses
		for(int j=0;j<(SIZE_SQUARED);j++)
		{
			workingList.add(new Literal(startingCellNum+j*(SIZE_SQUARED)));
		}
		return workingList;	
	}

	protected List<Literal> gatherRowLiterals(int startingCellNum)
	{
		ArrayList <Literal> workingList = new ArrayList <Literal>();

		//create row Literals
		for(int j=0;j<(SIZE_SQUARED);j++)
		{
			workingList.add(new Literal(startingCellNum+j));
		}
		return workingList;	
	}

	protected List<Literal> gatherBlockLiterals(int startingCellNum)
	{
		ArrayList <Literal> workingList = new ArrayList <Literal>();

		//create block clauses
		for(int j=0;j< SIZE;j++)//j will increment rows
		{
			for(int k=0;k<SIZE;k++) //k will increment cells
				workingList.add(new Literal((startingCellNum+k)+j*(SIZE_SQUARED))); 
		}
		return workingList;	
	}

	public void finalizeClause(List<Literal> input)
	{
		Clause workingClause = new Clause();
		List <Literal> literalList = new ArrayList<Literal>();
		literalList.addAll(input);
		workingClause.addList(literalList);
		super.addClause(workingClause); //adds the full "or" statement
		for(int i=0;i<input.size()-1;i++)//starts outer loop to create individual clauses
		{
			for(int j=i+1;j<input.size();j++)//inner loop starting at i+1
			{
				Clause negateWorkingClause = new Clause ();
				negateWorkingClause.add(literalList.get(i).changeValue());
				//adds first Literal to clauseList
				negateWorkingClause.add(literalList.get(j).changeValue());
				//adds second Literal to clauseList
				super.addClause(negateWorkingClause);//add each mini clause to the SudokuBoard
			}
		}
	}
        
	public static SudokuBoard readFromSudokuFile (String filename) {
		BufferedReader br = null;
		String line = null;
		@SuppressWarnings("unused")
		SudokuBoard board = null;
		int numVars, numClauses, var = 0; int i =0;
		try {
			br = new BufferedReader(new FileReader(filename));
			while((line = br.readLine()) != null){          
				if(line.startsWith("c")) {
					continue;
				}
				if(line.length() == 0) {
					continue;
				}
				else {
					if (line!=null){
						Scanner sc = new Scanner(line);
						if (i == 0) {
							var = sc.nextInt();
							board = new SudokuBoard (var);
							board.setNumVariables(board.SIZE_SIXTH);
							i++;
						}
						else {
							while(sc.hasNextInt()){
								var = sc.nextInt();
								if (var != 0){
									Clause c = new Clause();
									c.add(new Literal(((var-1)*board.SIZE_FOURTH)+i));
									board.addClause(c);
								}
								i++;
							}
						}
					}
				}
			}
			br.close();
		} catch (FileNotFoundException ex) {
			System.out.println("file I/O error: " + ex);
		} catch (IOException ex) {
			System.out.println("file I/O error: " + ex);
		}
		return board;
	}
}
