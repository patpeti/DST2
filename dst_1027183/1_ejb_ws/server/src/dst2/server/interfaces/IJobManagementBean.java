package dst2.server.interfaces;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Remote;

import dst2.server.utils.exceptions.jobmanagement.NoGridFoundException;
import dst2.server.utils.exceptions.jobmanagement.NotEnoughCPUException;
import dst2.server.utils.exceptions.jobmanagement.WrongUsernameOrPasswordException;

@Remote
public interface IJobManagementBean {

	public void doLogin(String username, String password) throws WrongUsernameOrPasswordException;
	public void addJob(long gridId, int numCpus, String workflow, List<String> params) throws NotEnoughCPUException, NoGridFoundException;
	public void submit() throws NotEnoughCPUException;
	
	public void removeJobsFromGrid(long gridID) throws NoGridFoundException;
	public BigDecimal getAllJobs(long gridID);
	public void destroy();
}
