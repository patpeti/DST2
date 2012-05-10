package dst2.server.dto;

import java.io.Serializable;


public class LogDataParamDto implements Serializable{



	  /**
	 * 
	 */
	private static final long serialVersionUID = -6646121745059265782L;

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
