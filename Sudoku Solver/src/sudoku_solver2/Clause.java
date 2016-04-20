package sudoku_solver2;

import java.util.ArrayList;
import java.util.List;

/**
 * A clause is a disjunction (or) of literals, or single literal and
 * a clause holds a collection of literal
 * 
 * @author Adam Tucker
 * @author Dennis Kluader
 * @author Umair Chaudhry
 * @version 03/30/2016
 *
 */
public class Clause
{	
	// Collections of literals that are either positive or negative
	// This allows a clause to be in charge of its own literals
	// in which a formula doesn't now about the literals just the clause itself
	List <Literal> clauseValues;

	/**
	 * Constructor for objects of class State.
	 * Array size set to a default of 100
	 */
	public Clause()
	{  
		clearClause();
	}

	/**
	 * Adds one literal into the existing Array of Literals.
	 * @param input The literal to add to the clause
	 */
	public void add (Literal input)
	{
		clauseValues.add(input);
	}


	/**
	 * Adds an entire list of Literals as a clause.
	 * @param clauseList The collection of literals to add to the clause
	 */
	public void addList(List<Literal> clauseList)
	{
		clauseValues.clear();
		clauseValues.addAll(clauseList);
	}

	/**
	 * Clears the clause
	 */
	public void clearClause()
	{
		clauseValues=  new ArrayList<Literal>(100);
	}

	/**
	 * This will evaluate the Clause against the Literal provided
	 * See comments in method for more explanation
	 * @param workingVar A Literal object 
	 * @return A List of Literals still contained in the clause, otherwise null if clause is satisfied.
	 */
	public Clause evaluateClause(Literal workingVar)
	{
		// Create a duplicate list to return with modification
		// Otherwise method will change original clause
		List <Literal> testList = new ArrayList<Literal>();
		testList.addAll(clauseValues);

		//Create blank clause to return
		Clause returnClause = new Clause();

		//Check list for Literal of opposite value & remove if present
		Literal removeLit = workingVar.changeValue();
		int index = testList.indexOf(removeLit);
		// If the opposite value is present in the testList than it will remove
		// it from the clause because the opposite value of the working literal
		// is unnecessary
		// If the index == -1 then the opposite literal is not present
		if(index>=0)
		{
			testList.remove(index);
		}

		//Check to see if Literal will satisfy Clause
		else if (testList.contains(workingVar))
		{
			return null;
		}

		//saves new modified list to returnClause
		returnClause.addList(testList);
		return returnClause;
	}

	/**
	 * Gets the collection of literals.
	 * @return The collection of literals
	 */
	public List<Literal> getValues()
	{
		return clauseValues;

	}

	/**
	 * Returns the size of the clause as an integer.
	 * @return The size of the clause
	 */
	public int size()
	{
		return clauseValues.size();		
	}

	/**
	 * Returns a string formatted as { 1 true or 2 true or -3 false }.
	 * @return The contents of the List in a String format
	 */
	public String toString()
	{
		String result = "";
		for (int i=0;i<clauseValues.size();i++)
		{
			if (clauseValues.get(i).isValue()) {
				result += clauseValues.get(i).getName() + " ";
			}
			else {
				result += "-" + clauseValues.get(i).getName() + " ";
			}
		}
		return result;
	}

	/**
	 * Two clauses are equal if they have the same collection of literals.
	 * @param o The object to check if this is equal
	 * @return The result of the check
	 */
	public boolean equals(Object o)
	{
		if(!(o instanceof Clause))
		{
			return false;
		}
		Clause test = (Clause)o;
		if(clauseValues.equals(test.getValues()))
		{
			return true;
		}
		return false;
	}
}
