package dst2.server.sessionBeans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dst2.server.interfaces.IPriceManagementBean;
import dst2.server.model.PriceStep;

@Startup
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class PriceManagementBean implements IPriceManagementBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5084960710257952405L;
	
	private List<PriceStep> steps = new ArrayList<PriceStep>();
	
	@PersistenceContext(name="grid")
	private EntityManager em;
	

	
	@Override
	public BigDecimal getPriceForGivenNumberOfJobs(int sum) {
		
		PriceStep step = null;	
		for(PriceStep s : steps){
			/*
			 * since pricesteps are ordered
			 * the fist pricestep wohse number of jobs is bigger than the input
			 * is the one we need
			 */
			if(sum < s.getNumberofHistoricalJobs()){
				step = s;
				break;
			}
		}
		
		
		if(step == null) System.out.println("NO PRICESTEP");
			
			
		return step.getPrice();
		
	}


	
	@Lock(LockType.WRITE)
	@Override
	public void storePriceStep(int numberOfJobs, BigDecimal price){
		PriceStep step = new PriceStep();
		step.setNumberofHistoricalJobs(numberOfJobs);
		step.setPrice(price);
		
		//try retrieve PriceStep from DB
		Query q = this.em.createQuery("select p from PriceStep p where p.numberofHistoricalJobs = :numJobs");
		q.setParameter("numJobs", step.getNumberofHistoricalJobs());
		List<PriceStep> result = (List<PriceStep>) q.getResultList();
		
		if(result == null || result.isEmpty()){
			//step not exists in the db, persist it and add it to cache
			em.persist(step);
			steps.add(step);
			Collections.sort(steps);
		}else{
			//step already exists update it with the new price, update cache
			
			result.get(0).setPrice(price);
			for(PriceStep s : steps){
				if(s.getNumberofHistoricalJobs() == result.get(0).getNumberofHistoricalJobs()) s.setPrice(price);
			}
			
		}
		

	}


	@PostConstruct
	private void fillCache() {
		Query q = em.createQuery("select p from PriceStep p");
		
		
		steps = (List<PriceStep>) q.getResultList();
		Collections.sort(steps);
		
	}
	
	

	
}
