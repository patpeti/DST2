package dst2.server.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class LogDataParam implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7179996347931695238L;

	  @Id
	  @GeneratedValue
	  private long id;
	  private String indexxx;
	  private String className;
	  private String value;
	  
	  
	  
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getIndex() {
		return indexxx;
	}
	public void setIndex(String index) {
		this.indexxx = index;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	

	  
	
}
