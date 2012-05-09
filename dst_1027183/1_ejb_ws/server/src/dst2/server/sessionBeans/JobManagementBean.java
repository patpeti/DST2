package dst2.server.sessionBeans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import dst2.server.dto.JobCache;
import dst2.server.interfaces.IJobManagementBean;
import dst2.server.model.Computer;
import dst2.server.model.Environment;
import dst2.server.model.Execution;
import dst2.server.model.Job;
import dst2.server.model.User;
import dst2.server.model.enums.JobStatus;
import dst2.server.utils.exceptions.jobmanagement.CacheEmptyException;
import dst2.server.utils.exceptions.jobmanagement.NoGridFoundException;
import dst2.server.utils.exceptions.jobmanagement.NotEnoughCPUException;
import dst2.server.utils.exceptions.jobmanagement.WrongUsernameOrPasswordException;

@Stateful(name = "JobManagementBean")
@Remote(IJobManagementBean.class)
/**
NOT_SUPPORTED
A NOTSUPPORTED_ method is guaranteed to never be executed in a transaction. 
If the caller attempts to invoke the method inside of a transaction, the container will suspend the caller's 
transaction, execute the method, then resume the caller's transaction.
**/
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class JobManagementBean implements IJobManagementBean {

	
	
	@PersistenceContext(type = PersistenceContextType.EXTENDED, name="grid")
	private EntityManager em;
	
	private User loggedInUser;
	
//	private List<Job> tempList; 
//	private Map<Long, List<Job>> cache;
//	private Map<Long, Map<Integer,List<Job>>> cache;
	
	private List<JobCache> cache;
	
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
			throw new WrongUsernameOrPasswordException("Unique Constraint violated, better programmer needed");
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
		 * 
		 */
//gets sum of all cpu-s...
//		Query q3 = em.createQuery("select SUM(c.cpus) from Computer c JOIN c.cluster cl LEFT JOIN cl.childClusters child " +
//				"JOIN cl.grid g LEFT JOIN c.executions e where g.id = :gridid");
//		q3.setParameter("gridid", gridId);
//		//q3.setParameter("jobstatus", JobStatus.RUNNING);
//		List<Long> result = (List<Long>) q3.getResultList();
//		System.out.println(result.size());
//		System.out.println(result.get(0));
//		
//		Long sumCpus = (Long) result.get(0);
		
		Query q2 = em.createQuery("select c from Computer c JOIN c.cluster cl LEFT JOIN cl.childClusters childs JOIN cl.grid g where g.id = :gridid");
		q2.setParameter("gridid", gridId);
		List<Computer> cList = (List<Computer>) q2.getResultList();
		System.out.println("liste "+ cList.size());
		int sumCpus = 0;
		for(Computer c : cList){
			if(c.getExecutions() == null || c.getExecutions().isEmpty()) sumCpus += c.getCpus();
		}
		
		System.out.println("SUMCPUS: "+ sumCpus);
		
		/**do checks and add job to cache*/
		if(sumCpus < numCpus ) {
			throw new NotEnoughCPUException("Not enough cpu capacity free");
		}else{
			//can be added to cache
			Environment e = new Environment();
			e.setWorkflow(workflow);
			e.setParameters(params);
			Job j = new Job();
			j.setEnvironment(e);
			
			List<Job> jobs = null;
			boolean gridCached = false;
			JobCache jobcache = null;
			for(JobCache jc : cache){
				if(jc.getGridId() == gridId){
					//grid already in the cache
					gridCached = true;
					jobcache = jc;
					jobs = jc.getJobList();
					break; //just one instance must be exist pro grid
				}
			}
			
			if(jobs == null) jobs = new ArrayList<Job>();
			
			boolean jobCached = false;
			//we have to check whether the same job is already in the list
			if(!jobs.isEmpty()){
				for(Job iJob : jobs){
					if(iJob.equals(j)) jobCached = true;
				}
			}
			//if job is new
			if(!jobCached){
				//add new job to the list
				jobs.add(j);
				//add list to the cache
				if(jobcache == null){
					//create new cache
					jobcache = new JobCache();
					jobcache.setGridId(gridId);
					jobcache.setNumOfCpus(0);
				}
				jobcache.setJobList(jobs);
				//set num of cpus
				jobcache.setNumOfCpus(jobcache.getNumOfCpus()+numCpus);
				if(!gridCached)	cache.add(jobcache);
				
			}
			
			//DEBUG:
			for(JobCache jc : cache){
				System.out.println("GridID: " +jc.getGridId() + " " + "Number of jobs: " + jc.getJobList().size() + " CPU NEEDED: "+ jc.getNumOfCpus());
			}

		}
		
		
		
	}
	/**
	A REQUIRED method is guaranteed to always be executed in a transaction.
	If the caller attempts to invoke the method outside of a transaction, 
	the container will start a transaction, execute the method, then commit the transaction.
	 * @throws CacheEmptyException 
	**/
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void submit() throws NotEnoughCPUException, CacheEmptyException {
		//todo check
		
		//todo save
		
		//iterate over cahche
		if(loggedInUser == null) new WrongUsernameOrPasswordException("Not Authenticated");
		else if(cache == null || cache.isEmpty())  throw new CacheEmptyException("Nothing to submit");
		else{	
		for(JobCache jc : cache){
			List<Job> jobList = null;
			jobList = jc.getJobList();
			//check if enough capacity availaible
			Query q2 = em.createQuery("select c from Computer c JOIN c.cluster cl LEFT JOIN cl.childClusters childs JOIN cl.grid g where g.id = :gridid");
			q2.setParameter("gridid", jc.getGridId());
			List<Computer> cList = (List<Computer>) q2.getResultList();
			System.out.println("liste "+ cList.size());
			int sumCpus = 0;
			for(Computer c : cList){
				if(c.getExecutions() == null || c.getExecutions().isEmpty()) sumCpus += c.getCpus();
			}
			
			System.out.println("SUMCPUS: "+ sumCpus);
			if(sumCpus < jc.getNumOfCpus()) throw new NotEnoughCPUException("Not Enough Cpu capacity in the grid --> joblist cant be submitted");
			else{
//				//get available computers of grid
//				Query q = em.createQuery("select c from Computer c JOIN c.cluster cl JOIN cl.childClusters child JOIN c.parentCluster parent" +
//						"JOIN cl.grid g where g.id = :gridid AND c.executions IS NULL");
//				
//	/**			Query q2 = em.createQuery("select c from Computer c JOIN c.cluster cl JOIN cl.childClusters child JOIN c.parentCluster parent" +
//						"JOIN cl.grid g OUTER JOIN c.executions e where g.id = :gridid");*/
				
//				q.setParameter("gridid", jc.getGridId());
				
				List<Computer> compList = new ArrayList<Computer>();
				for(Computer c : cList){
					if(c.getExecutions() == null || c.getExecutions().isEmpty()) compList.add(c);
					else{
						boolean allExecutionFinished = true;
						for(Execution e : c.getExecutions()){
							if(!e.getStatus().equals(JobStatus.FINISHED)) allExecutionFinished = false; 
						}
						if(allExecutionFinished) compList.add(c);
					}
				}
				
				if(compList == null || compList.isEmpty()) throw new NotEnoughCPUException("No Available Computers Found");  
				else{
					//create Execution to Jobs
					
					List<Execution> exList = new ArrayList<Execution>();
					for(Job j : jobList){
						Execution e = new Execution();
						e.setStatus(JobStatus.SCHEDULED);
						e.setStart(new Date());
						e.setJob(j);
						j.setUser(loggedInUser);
						exList.add(e);
						em.persist(j.getEnvironment());
						em.persist(j);
						em.persist(e);
						List<Job> userJobs = loggedInUser.getJobs();
						userJobs.add(j);
						loggedInUser.setJobs(userJobs);
						
					}
					//select as many computer is needed
					List<Computer> neededComputers = new ArrayList<Computer>();
					int i = jc.getNumOfCpus();
					int j = 0;
					while(i>0){
						neededComputers.add(compList.get(j));
						i = i - compList.get(j).getCpus();
						j++;
					}
					
					//assign execution to computer
					for(Computer c : neededComputers ){
						c.setExecutions(exList);
					}
					//clear cache
					this.destroy();
					em.flush();
				}
			}
		}
		}
		
		//create execution
		//start assign jobs to computers
		//(iterate over computers while numcpus-coomputer.cpu > 0)
		
	}

	@Override
	public void removeJobsFromGrid(long gridID) throws NoGridFoundException {
		
//		for(Map.Entry<Long, List<Job>> entry : cache.entrySet()){
//			if(entry.getKey() == gridID) entry.setValue(null);
//		}
		
//		if(cache.containsKey(gridID)) cache.remove(gridID);
//		else throw new NoGridFoundException("Grid is Not in cache");
		
		for(JobCache j : cache){
			if(j.getGridId() == gridID){
				j.setJobList(null);
				j.setJobList(new ArrayList<Job>());
				j.setNumOfCpus(0);
			}
		}
		//DEBUG:
		for(JobCache jc : cache){
			System.out.println("GridID: " +jc.getGridId() + " " + "Number of jobs: " + jc.getJobList().size() + " CPU NEEDED: "+ jc.getNumOfCpus());
		}
		
	}

	@Override
	public BigDecimal getAllJobs(long gridID) {
		int sum = 0;
//		for(Map.Entry<Long, Map<Integer,List<Job>>> entry: this.cache.entrySet() ){
//			//sum += entry.getValue().size();
//			for(Map.Entry<Integer, List<Job>> entryinside : entry.getValue().entrySet()){
//				sum += entryinside.getValue().size();
//			}
//		}
		for(JobCache j : cache){
			if(j.getGridId() == gridID) sum += j.getJobList().size();
		}
		return new BigDecimal(sum);
	}
	
	@PostConstruct
	private void init(){
		//cache = new HashMap<Long, Map<Integer,List<Job>>>();
		cache = new ArrayList<JobCache>();
	}

	@Remove
	@Override
	public void destroy() {
		this.cache = null;
	}

	
}
