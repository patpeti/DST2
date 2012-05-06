package dst2.server.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class Job implements Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = 6321436998096420048L;


	@Id
	@GeneratedValue
	private long id;

	
	private boolean isPaid;
	@OneToOne
	private Environment environment;
	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumns(@JoinColumn(name = "user_id"))
	private User user;
	@OneToOne(mappedBy = "job", fetch = FetchType.EAGER)
	public Execution execution;
	
	/*********************************************      GETTERS - SETTERS           *************************************************/
	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	@Transient
	public int getNumCPUs() {
		Execution ex = this.getExecution();
		if(ex != null){
			Collection<Computer> computers = ex.getComputers();
			int sum = 0;
			for(Computer c : computers){
				sum += c.getCpus();
			}
			return sum;
		}else
			return 0;
	}

	
	@Transient
	public int getExecutionTime() {
		Execution ex = this.getExecution();
		if(ex != null){
			long duration = ex.getEnd().getTime() - ex.getStart().getTime();
			int i = (int) duration;
			return  i;
		}else{
			return 0;
		}
	}


	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		if(user != null) user.getJobs().add(this);
		if(user == null) {
			if(this.user != null)
			this.user.getJobs().remove(this);
		}
		this.user = user;
	}

	public Execution getExecution() {
		return execution;
	}

	public void setExecution(Execution execution) {
		this.execution = execution;
	}
	
	
}
