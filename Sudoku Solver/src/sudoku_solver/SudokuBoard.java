package sudoku_solver;

import java.util.ArrayList;
import java.util.List;

public class SudokuBoard extends Formula {
	/**
	 * The SudokuBoard uses the structure of the SAT solver formula class to 
	 * store and create all the necessary clauses to represent an empty Sudoku board.
	 * 
	 *  Additional clauses will be added as the Sudoku puzzle information is scanned in 
	 *  from file
	 */

	public static final int SIZE=2;
	public static final int SIZE_SQUARED = (int) Math.pow(SIZE, 2);
	public static final int SIZE_CUBED = (int) Math.pow(SIZE, 3);
	public static final int SIZE_FOURTH = (int) Math.pow(SIZE, 4);

	public SudokuBoard()
	{

	}

	public void createBoard()
	{
		Clause workingList = new Clause();
		for(int i=1; i<=(SIZE_CUBED)*(SIZE_CUBED); i++)
		{
			if(i<=(SIZE_SQUARED)*(SIZE_SQUARED))
			{
				//create Cell clauses
				workingList.clearClause();
				for(int j=0;j<(SIZE_SQUARED);j++)
				{
					workingList.add(new Literal(j*SIZE_FOURTH+i));
				}
				finalizeClause(workingList);
			}
			if(i%(SIZE_SQUARED)==1)
			{
				//create row clauses
				workingList.clearClause();
				for(int j=0;j<(SIZE_SQUARED);j++)
				{
					workingList.add(new Literal(i+j));
				}
				finalizeClause(workingList);
			}
			if(((i/(SIZE_SQUARED))+1)%(SIZE_SQUARED)==1)//if (row%size^2==1)
			{
				//create column clauses
				workingList.clearClause();
				for(int j=0;j<(SIZE_SQUARED);j++)
				{
					workingList.add(new Literal(i+j*(SIZE_SQUARED)));
				}
				finalizeClause(workingList);
			}
			if(((i/(SIZE_SQUARED))+1)%(SIZE)==1 && i%SIZE==1)
			{
				//create block clauses
				workingList.clearClause();
				for(int j=0;j< SIZE;j++)//j will increment rows
				{
					for(int k=0;k<SIZE;k++) //k will increment cells
						workingList.add(new Literal((i+k)+j*(SIZE_SQUARED))); 
				}
				finalizeClause(workingList);
			}
		}
	}

	public void finalizeClause(Clause input)
	{
		Clause workingClause = new Clause();
		List <Literal> workingList = input.getValues();
		workingClause.addList(workingList);
		super.addClause(workingClause); //adds the full or statement
		for(int i=0;i<input.size()-1;i++)
		{
			for(int j=i+1;j<input.size();j++)
			{
				Clause negateWorkingClause = new Clause ();
				negateWorkingClause.add(workingList.get(i).changeValue());
				//adds first Literal to clauseList
				negateWorkingClause.add(workingList.get(j).changeValue());
				//adds second Literal to clauseList
				super.addClause(negateWorkingClause);
				}
		}

	}
}
