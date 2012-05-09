package dst2.server.sessionBeans;

import java.util.Date;
import java.util.List;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dst2.server.interfaces.ITimerBean;
import dst2.server.model.Job;
import dst2.server.model.enums.JobStatus;

@Startup
@Singleton(name = "TimerBean")
public class TimerBean implements ITimerBean {

	@PersistenceContext(name="grid")
	private EntityManager em;
	
	@Schedule(persistent = false, minute = "*/1", hour = "*")
	public void simulate(){
		
		System.out.println("############# TIMER SIMULATON CALLED ");
		
		Query q = em.createQuery("select j from Job j where j.execution.end IS NULL AND j.execution.status = :status");
		q.setParameter("status", JobStatus.RUNNING);
		
		List<Job> jobList = (List<Job>) q.getResultList();
		
	
		//DEBUG:
//		System.out.println("Size of list: " + jobList.size());
//		
		for(Job j : jobList){
			j.getExecution().setStatus(JobStatus.FINISHED);
			j.getExecution().setEnd(new Date());
		}
		
	}
}
