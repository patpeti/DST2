package dst2.server.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;


@Entity
public class Environment implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7687121463335283417L;
	@Id
	@GeneratedValue
	private long id;
	private String workflow;
	//to preserve order: 
	//Set  --> SortedSet 
	//List --> LinkedList
	//Map  --> SortedMap
	@ElementCollection
	@CollectionTable(name="environment_parameters", joinColumns=@JoinColumn(name="environment_id"))
	@Column(name="parameters")
	private List<String> parameters;
	
	/*********************************************      GETTERS - SETTERS           *************************************************/
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getWorkflow() {
		return workflow;
	}
	public void setWorkflow(String workflow) {
		this.workflow = workflow;
	}
	public List<String> getParameters() {
		return parameters;
	}
	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}
	
	
	
	
}
