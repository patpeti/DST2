package dst2.server.utils.exceptions;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = false)
public class NoUserExists extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8239866725309663779L;

	@Override
	public String getMessage() {
		return "No User exists in the DB";
	}
}
