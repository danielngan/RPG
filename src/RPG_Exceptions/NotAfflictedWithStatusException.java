package RPG_Exceptions;

public class NotAfflictedWithStatusException extends Exception{

	/**
	 * Added so the compiler doesn't complain.
	 */
	private static final long serialVersionUID = 8929233347896854450L;

	/**
	 * Default exception constructor
	 */
	public NotAfflictedWithStatusException()
	{
		super();
	}
	
	/**
	 * Message based exception constructor.
	 * @param msg
	 */
	public NotAfflictedWithStatusException(String msg)
	{
		super(msg);
	}
	
}
