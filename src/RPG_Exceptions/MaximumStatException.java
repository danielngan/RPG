package RPG_Exceptions;

/**
 * MaximumStatException should be thrown when a hero attempts to use an ability that will make no difference to their statistics.
 * @author Kevin
 *
 */
public class MaximumStatException extends Exception{
	
	/**
	 * Added so the compiler doesn't complain.
	 */
	private static final long serialVersionUID = 3567746945824316434L;

	/**
	 * Default exception constructor
	 */
	public MaximumStatException()
	{
		super();
	}
	
	/**
	 * Message based exception constructor.
	 * @param msg
	 */
	public MaximumStatException(String msg)
	{
		super(msg);
	}
	
}
