package sudoku_solver;

/**
 * A literal is a Boolean variable or the negation of variable and
 * will store a boolean value and a name for uses of comparison
 * 
 * @author Adam Tucker
 * @author Dennis Kluader
 * @author Umair Chaudhry
 * @version 03/30/2016
 *
 */
public class Literal implements Comparable <Literal>
{
	// The value of the literal
	private boolean value;
	// The literal number or negation of that number
	private int name;

	/**
	 * Constructor for a Literal
	 * @param nameGiven The name of the literal
	 * @param valueGiven The value of the literal
	 */
	public Literal(int nameGiven, boolean valueGiven)
	{
		value = valueGiven;
		name = Math.abs(nameGiven);
	}

	/**
	 * Alternate constructor where the value is dependent
	 * on the number given from the formula
	 * @param input The name of the literal
	 */
	public Literal (int input)
	{
		if (input <0)
		{
			value = false;
			input *= -1;
		}
		else
		{
			value = true;
		}
		name = input;
	}

	/**
	 * The value of the Literal
	 * @return The boolean value of the Literal
	 */
	public boolean get()
	{
		return value;
	}
	
	/**
	 * The name of the literal
	 *	@return The name String assigned to the Literal
	 */
	public int getName()
	{
		return name;
	}

	/**
	 * Sets the boolean value of literal
	 *	@param newValue Allows the value of the Literal to be changed.
	 */
	public void set(boolean newValue)
	{
		value = newValue;
	}
	
	/**
	 * Changes the value of the literal to the opposite value
	 * Allows the program to find a opposite literal in
	 * any clause to be removed
	 * @return The literal with the opposite boolean value
	 */
	protected Literal changeValue()
	{
		boolean returnValue;
		if(value)
			returnValue=false;
		else
		{
			returnValue=true;
		}
		return new Literal (this.name,returnValue);
	}

	/**
	 * Returns String i.e. 1 true or -2 false
	 *	@return	The value and name of the Literal as a String
	 */
	public String toString()
	{
		String result=name+" ";
		if (value)
			result += "true";
		else 
			result += "false";
		return result;
	}

	/** 
	 * Two literals are equal if they have the same name and value
	 * @param other The object to compare with this literal
	 * @return True if both literals have the same value and name, false otherwise
	 */
	public boolean equals (Object other)
	{
		if(!(other instanceof Literal))
		{
			return false;
		}
		Literal test = (Literal)other;
		if(name == test.getName()&&value==test.get())
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Compares two literals names and returns the result
	 * @param other The literal to compare
	 */
	public int compareTo(Literal other)
	{
		return (Integer.valueOf(this.name).
				compareTo(Integer.valueOf(other.getName())));
	}
}
