package dst2.server.utils.exceptions;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = false)
public class NoJobsAssignedException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2833444965190289618L;

	@Override
	public String getMessage() {
		return "No Jobs are assigned to this user";
	}
}
