package dst2.server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import dst2.server.model.enums.JobStatus;

//@NamedQueries({
//	@NamedQuery(
//			name = "TotalUsage"	,
//			query = "select c.id from Computer c " +
//					"where c.location like 'AUT-VIE' "
//
//			)
//})
@Entity
public class Execution implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8621343438973412129L;
	@Id
	@GeneratedValue
	private long id;
	@Temporal(TemporalType.DATE) 
	private Date start;
	@Temporal(TemporalType.DATE) 
	private Date end;
	@Enumerated(EnumType.STRING)
	@Basic
	private JobStatus status;
	

	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, optional = true)
	public Job job;
	
	@ManyToMany(mappedBy = "executions")
//	@JoinTable(name="computer_execution", joinColumns = @JoinColumn(name = "execution_id"),
//										  inverseJoinColumns = @JoinColumn(name = "computer_id"))
	private List<Computer> computers = new ArrayList<Computer>();
	
	/*********************************************      GETTERS - SETTERS           *************************************************/
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public JobStatus getStatus() {
		return status;
	}
	public void setStatus(JobStatus status) {
		this.status = status;
	}
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		//setting backreference
		if(job != null)	job.setExecution(this);
		this.job = job;
	}
	public List<Computer> getComputers() {
		return computers;
	}
	public void setComputers(List<Computer> computers1) {
		if(computers1 == null){
			this.computers = new ArrayList<Computer>();
		}else{
			
		
			for(Computer c : computers1){
				c.getExecutions().add(this);
			}
		
		this.computers = computers1;
		}
	}
	
	
	
}
