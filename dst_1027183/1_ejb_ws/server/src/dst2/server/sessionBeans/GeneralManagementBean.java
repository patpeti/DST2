package dst2.server.sessionBeans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dst2.server.interfaces.IGeneralManagementBean;
import dst2.server.interfaces.IPriceManagementBean;
import dst2.server.model.Computer;
import dst2.server.model.Execution;
import dst2.server.model.Grid;
import dst2.server.model.Job;
import dst2.server.model.Membership;
import dst2.server.model.User;
import dst2.server.model.enums.JobStatus;

@Stateless(name = "GeneralManagementBean")
@Remote(IGeneralManagementBean.class)
public class GeneralManagementBean implements IGeneralManagementBean, Serializable{


	private static final long serialVersionUID = -5335992770730878412L;
	
	@PersistenceContext(name="grid")
	private EntityManager em;
	
	@EJB
	private IPriceManagementBean priceManager;
	
	@Override
	public void storePriceStep(int numberOfJobs, BigDecimal price) {
		this.priceManager.storePriceStep(numberOfJobs, price);
	}

	public void setPriceManager(IPriceManagementBean priceManager) {
		this.priceManager = priceManager;
	}

	@Override
	@Asynchronous
	public Future<BigDecimal> getTotalBill(String username) {
		//select user
		//select jobs
		//get user discount
		//per job calculate PriceStep
		//calculate execution time for each grid
		
		//get user
		BigDecimal bill ;
		double doublebill;
		
		Query q = em.createQuery("select u from User u where u.username like :username");
		q.setParameter("username", username);
		User user = (User) q.getSingleResult();
		
		List<Job> jobs = user.getJobs();
		int finishedJobs = 0;
		for(Job j: jobs){
			if(j.getExecution().getStatus().equals(JobStatus.FINISHED)) finishedJobs++;
		}
		
		System.out.println("Finished JObs: "+finishedJobs);
		
		BigDecimal priceStep = priceStep = priceManager.getPriceForGivenNumberOfJobs(finishedJobs);
		
		List<Job> scheduledJobList = new ArrayList<Job>();
		for(Job j : jobs){
			if(j.getExecution().getStatus().equals(JobStatus.SCHEDULED)) scheduledJobList.add(j);
		}
		
		System.out.println("Scheduled JObs: "+scheduledJobList.size());
		
		//calculate pricestepbill
		doublebill = priceStep.intValue() * scheduledJobList.size() ;
		bill = new BigDecimal(doublebill);
		
		//debug:
		System.out.println("PriceStep bill: " + bill);
		
		//for each job taht is finished and not paid
		
		/**
		 * !!! I assume that one Job is executed only in one grid
		 */
		for(Job job: jobs){
			if(job.isPaid() == false){
			if(job.getExecution().getStatus().equals(JobStatus.FINISHED)){
				Execution execution = job.getExecution();
				
				//calculate execution time
				//assume that if a job is finished endDate and Startdate are set and enddate > startdate
				//minutes
				double executionTime =  ((execution.getEnd().getTime() - execution.getStart().getTime())/1000)/60;
				
				//DEBUG:
				System.out.println("Execution Time: "+ executionTime);
				
				List<Computer> computers = execution.getComputers();
				
				//get number of CPUs
				int sumCPUs = 0;
				for(Computer c : computers){
					sumCPUs += c.getCpus();
				}
							
				//find out what is the costPoMinite in the grid in that it is running
				//assumtioon that execution happens in maximal one grid and to that grid is every computer associated on what the job running
				Grid grid = computers.get(0).getCluster().getGrid();
				
				//DEBUG:
				System.out.println("Execution Time:" + executionTime);
				System.out.println("Grid costperminites: "+ grid.getCostsPerCPUMinute());
				
				doublebill += executionTime * sumCPUs;
				bill = new BigDecimal(doublebill);
				
				//DEBUG:
				System.out.println("bill: " + doublebill);
				//get the membership to this grid for the user and check if user has discount
				
				Membership memberhsip = null;
				for(Membership m : user.getMembership()){
					if(m.getGrid().getId() == grid.getId()) memberhsip = m;
				}
				
				double discount = 0;
				if(memberhsip != null){
					discount = memberhsip.getDiscount();
				}else{
					//assume thit is never the case
					System.out.println("ERROR, user is not a member in the Grid where he is executing Jobs");
				}
				doublebill = doublebill * (1-discount);
				
				
			}
			}
		}
		
		
		
		
		
		return new AsyncResult<BigDecimal>(bill);
	}
	
	

}
