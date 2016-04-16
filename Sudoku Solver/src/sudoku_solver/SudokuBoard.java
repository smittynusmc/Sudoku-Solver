package sudoku_solver;

import java.util.List;

public class SudokuBoard extends Formula {
	/**
	 * The SudokuBoard uses the structure of the SAT solver formula class to 
	 * store and create all the necessary clauses to represent an empty Sudoku board.
	 * 
	 *  Additional clauses will be added as the Sudoku puzzle information is scanned in 
	 *  from file
	 */

	public static final int SIZE=3;


	public SudokuBoard()
	{

	}

	public void createBoard()
	{
		Clause workingList = new Clause();
		for(int i=1; i<=(SIZE*SIZE*SIZE)*(SIZE*SIZE*SIZE); i++)
		{

			if(i<=(SIZE*SIZE)*(SIZE*SIZE))
			{
				//create Cell clauses
				workingList.clearClause();
				for(int j=1;j<=(SIZE*SIZE);j++)
				{
					workingList.add(new Literal(i*j*SIZE*SIZE*SIZE*SIZE));
				}
				finalizeClause(workingList);
			}
			if(i%(SIZE*SIZE)==1)
			{
				//create row clauses
				workingList.clearClause();
				for(int j=0;j<(SIZE*SIZE);j++)
				{
					workingList.add(new Literal(i+j));
				}
				finalizeClause(workingList);
			}
			if(((i/(SIZE*SIZE))+1)%(SIZE*SIZE)==1)//if (row%size^2==1)
			{
				//create column clauses
				workingList.clearClause();
				for(int j=0;j<(SIZE*SIZE);j++)
				{
					workingList.add(new Literal(i+j*(SIZE*SIZE)));
				}
				finalizeClause(workingList);
			}
			if(((i/(SIZE*SIZE))+1)%(SIZE)==1 && i%SIZE==1)
			{
				//create block clauses
				workingList.clearClause();
				for(int j=0;j<(SIZE*SIZE);j++)//j will increment rows
				{
					for(int k=0;k<SIZE;k++) //k will increment cells
						workingList.add(new Literal((i+k)+j*(SIZE*SIZE))); 
				}
				finalizeClause(workingList);
			}
		}
	}

	public void finalizeClause(Clause input)
	{
		Clause workingClause = new Clause();
		List <Literal> workingList=input.getValues();
		workingClause.addList(workingList);
		super.addClause(workingClause); //adds the full or statement
		for(int i=0;i<input.size()-1;i++)
		{
			for(int j=i+1;j<input.size();j++)
			{
				workingClause.clearClause();//clears clauselist
				workingClause.add(workingList.get(i).changeValue());
				//adds first Literal to clauseList
				workingClause.add(workingList.get(j).changeValue());
				//adds second Literal to clauseList
				super.addClause(workingClause);
				}
		}

	}
}
