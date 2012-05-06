package dst2.server.interfaces;

import java.io.Serializable;

import javax.ejb.Remote;

import dst2.server.utils.exceptions.NoJobsAssignedException;
import dst2.server.utils.exceptions.NoUserExists;
import dst2.server.utils.exceptions.UserNameNotFoundException;

@Remote
public interface IPriceManagementBean extends Serializable{

	public void storePriceSteps() throws NoUserExists;
	public void storeStepToUser(String username) throws UserNameNotFoundException, NoJobsAssignedException;
}
