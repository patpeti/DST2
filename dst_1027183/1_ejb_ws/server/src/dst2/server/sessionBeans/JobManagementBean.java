package dst2.server.sessionBeans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import dst2.server.interfaces.IJobManagementBean;
import dst2.server.model.Environment;
import dst2.server.model.Job;
import dst2.server.model.User;
import dst2.server.utils.exceptions.jobmanagement.NoGridFoundException;
import dst2.server.utils.exceptions.jobmanagement.NotEnoughCPUException;
import dst2.server.utils.exceptions.jobmanagement.WrongUsernameOrPasswordException;

@Stateful(name = "JobManagementBean")
@Remote(IJobManagementBean.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class JobManagementBean implements IJobManagementBean {

	
	
	@PersistenceContext(type = PersistenceContextType.EXTENDED, name="grid")
	private EntityManager em;
	
	private User loggedInUser;
	
//	private List<Job> tempList; 
	private Map<Long, List<Job>> cache;
//	private Map<Long, Map<Integer,List<Job>>> cache;
	  
	@Override
	public void doLogin(String username, String password)
			throws WrongUsernameOrPasswordException {
		Query q = em.createQuery("select u from User u where u.username like :username and u.password like :password");
		q.setParameter("username", username);
		q.setParameter("password", password);
		List<User> foundUser = (List<User>) q.getResultList();
		
		if(foundUser == null || foundUser.isEmpty()){
			throw new WrongUsernameOrPasswordException("Invalid Login Data");
		}else if(foundUser.size() > 1 ){
			throw new WrongUsernameOrPasswordException("Unique Constrain violated, better programmer needed");
		}else{
			this.loggedInUser = foundUser.get(0);
		}
		
	}

	@Override
	public void addJob(long gridId, int numCpus, String workflow,
			List<String> params) throws NotEnoughCPUException,
			NoGridFoundException {
		/**
		 * 1.)check if enough CPU capacity exists in the grid
		 * 2.)add jobs to tempList
		 * 
		 * TODO check these queries
		 */
		//select all computer
		Query q = em.createQuery("select c from Computer c JOIN c.cluster cl JOIN cl.grid g where g.id = :gridid");
		//join with parent and childclusters
		Query q2 = em.createQuery("select c from Computer c JOIN c.cluster cl JOIN cl.childClusters child JOIN cl.parentCluster parent JOIN cl.grid g where g.id = :gridid");
		
		//just the sum of CPUs
		Query q3 = em.createQuery("select SUM(c.cpus) from Computer c JOIN c.cluster cl JOIN cl.childClusters child JOIN cl.parentCluster parent JOIN cl.grid g where g.id = :gridid");
		q3.setParameter("gridid", ""+gridId);
		List<Integer> result = (List<Integer>) q3.getResultList();

		/**do checks and add job to cache*/
		if(result.get(0) < numCpus || result == null || result.isEmpty()) {
			throw new NotEnoughCPUException("Not enough cpu capacity free");
		}else{
			//can be added to cache
			Environment e = new Environment();
			e.setWorkflow(workflow);
			e.setParameters(params);
			Job j = new Job();
			j.setEnvironment(e);
			
			//check if jobs already exists to the grid in the cache
			List<Job> jobList = null;
			boolean gridCached = false;
			for(Map.Entry<Long, List<Job>> entry : cache.entrySet()){
				if(entry.getKey() == gridId) {
					gridCached = true;
					jobList = entry.getValue();
					break;
				}
			}
			if(jobList == null) jobList = new ArrayList<Job>();
			
			boolean jobChached = false;
			if(!jobList.isEmpty()){
				//we have to check whether the same job is already in the list
				for(Job iJob : jobList){
					if(iJob.equals(j)) jobChached = false;
				}
			}
			if(!jobChached){
				cache.put(gridId, jobList);
			}
			
		}
		
		
	}

	@Override
	public void submit() throws NotEnoughCPUException {
		//todo check
		//todo save
		
		//iterate over cahche
		//get computers of grid
		//create execution
		//start assign jobs to computers
		//(iterate over computers while numcpus-coomputer.cpu > 0)
		
	}

	@Override
	public void removeJobsFromGrid(long gridID) throws NoGridFoundException {
		
//		for(Map.Entry<Long, List<Job>> entry : cache.entrySet()){
//			if(entry.getKey() == gridID) entry.setValue(null);
//		}
		
		if(cache.containsKey(gridID)) cache.remove(gridID);
		else throw new NoGridFoundException("Grid is Not in cache");
		
	}

	@Override
	public BigDecimal getAllJobs(long gridID) {
		int sum = 0;
		for(Map.Entry<Long, List<Job>> entry: this.cache.entrySet() ){
			sum += entry.getValue().size();
		}
		return new BigDecimal(sum);
	}
	
	@PostConstruct
	private void init(){
		cache = new HashMap<Long, List<Job>>();
	}

	@Remove
	@Override
	public void destroy() {
		this.cache = null;
	}

	
}
