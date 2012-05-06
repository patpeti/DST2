package dst2.server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class Computer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9026145281954301856L;
	@Id
	@GeneratedValue
	private long id;
	@Size(min= 5, max=25, message = "Computer name is invalid")
	private String name;

	private int cpus;
	@Pattern(regexp = "[A-Z]{3}-[A-Z]{3}@[0-9]{4,}", message="Computer location is invalid")
	private String location;
	@Temporal(TemporalType.DATE) 
	@Past(message="Computer creation must be in the past")
	private Date creation;
	@Temporal(TemporalType.DATE) 
	@Past(message="Computer lastUpdate must be in the past")
	private Date lastUpdate;
	@ManyToOne
	private Cluster cluster;
	@ManyToMany
	@JoinTable(name="computer_execution", joinColumns = @JoinColumn(name = "computer_id"),
	  inverseJoinColumns = @JoinColumn(name = "execution_id"))
	private List<Execution> executions = new ArrayList<Execution>();
	
	/*********************************************      GETTERS - SETTERS           *************************************************/
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCpus() {
		return cpus;
	}
	public void setCpus(int cpus) {
		this.cpus = cpus;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Date getCreation() {
		return creation;
	}
	public void setCreation(Date creation) {
		this.creation = creation;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public Cluster getCluster() {
		return cluster;
	}
	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}
	public List<Execution> getExecutions() {
		return executions;
	}
	public void setExecutions(List<Execution> executions1) {
//		if(executions1 == null){
//			this.executions = new ArrayList<Execution>();
//		}else{
//			for(Execution e : executions1){
//				e.getComputers().add(this);
//			}
			this.executions = executions1;
//		}
	}
	
	
	
}
