package dst2.server.sessionBeans;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dst2.server.interfaces.IPriceManagementBean;
import dst2.server.model.Job;
import dst2.server.model.PriceStep;
import dst2.server.model.User;
import dst2.server.utils.exceptions.NoJobsAssignedException;
import dst2.server.utils.exceptions.NoUserExists;
import dst2.server.utils.exceptions.UserNameNotFoundException;

@Stateless(mappedName = "PriceManagementBean")
@Remote(IPriceManagementBean.class)
public class PriceManagementBean implements IPriceManagementBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5084960710257952405L;
	
	@PersistenceContext(name="grid")
	private EntityManager em;
	
	/**
	 * update all user with PriceStep Entity
	 * @throws NoUserExists 
	 */
	@Override
	public void storePriceSteps() throws NoUserExists {
		
		//select users 
		
		Query query = em.createNamedQuery("select u from User");
		List<User> uList = (List<User>) query.getResultList();
		if(uList == null) throw new NoUserExists();
		if(uList.isEmpty())  throw new NoUserExists();
		//for every user count jobs that are paid and create a PriceStep 
		for(User u : uList){
			
			int sum = 0;
			for(Job j : u.getJobs()){
				if(j.isPaid()) sum++;
			}
			
			PriceStep p  = u.getPriceStep();
			if(p == null){
				//create PriceStep
				p = new PriceStep();
				p.setUser(u);
				u.setPriceStep(p);
			}
			p.setNumberofHistoricalJobs(sum);
			//Reziproke Proportionalität
			p.setPrice(new BigDecimal(100/(sum+1)));
			
		
			em.persist(p);
		}
	}
	
	/**
	 * update given user with priceStep
	 */
	public void storeStepToUser(String username) throws UserNameNotFoundException, NoJobsAssignedException{
	
		//get user by username
		//Query query = em.createNamedQuery("SelectUserByUsername");
		Query query = em.createQuery("select u from User u where u.username like :username");
		query.setParameter("username", username);
		List<User> uList = (List<User>) query.getResultList();
		
		if(uList == null) throw new UserNameNotFoundException();
		if(uList.isEmpty()) throw new UserNameNotFoundException();	
		
		User u = uList.get(0);
		
		int sum = 0;
		
		if(u.getJobs() == null) throw new NoJobsAssignedException();
		if(u.getJobs().isEmpty()) throw new NoJobsAssignedException();
		
		for(Job j : u.getJobs()){
			if(j.isPaid()) sum++;
		}
		
		PriceStep p  = u.getPriceStep();
		if(p == null){
			//create PriceStep
			p = new PriceStep();
			p.setUser(u);
			u.setPriceStep(p);
		}
		p.setNumberofHistoricalJobs(sum);
		//Reziproke Proportionalität
		p.setPrice(new BigDecimal(100/(sum+1)));
		
		
		em.persist(p);
		
		
		
	}

	
}
