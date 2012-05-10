package dst2.server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class LogData implements Serializable{

		/**
	 * 
	 */
	private static final long serialVersionUID = 5189332447196469892L;
		@Id
	    @GeneratedValue
	    private long id;
		@Temporal(TemporalType.TIMESTAMP)
	    private Date invocationTime;
	    private String method;
	    private String result;

	    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	    private List<LogDataParam> parameters = new ArrayList<LogDataParam>();

	    public void setId(long id) {
	        this.id = id;
	    }

	    public long getId() {
	        return id;
	    }

	    public void setInvocationTime(Date invocationTime) {
	        this.invocationTime = invocationTime;
	    }

	    public Date getInvocationTime() {
	        return invocationTime;
	    }

	    public void setMethod(String method) {
	        this.method = method;
	    }

	    public String getMethod() {
	        return method;
	    }

	    public void setResult(String result) {
	        this.result = result;
	    }

	    public String getResult() {
	        return result;
	    }

	    public void setParameters(List<LogDataParam> parameters) {
	        this.parameters = parameters;
	    }

	    public List<LogDataParam> getParameters() {
	        return parameters;
	    }

	    public void addParameter(LogDataParam param) {
	        this.parameters.add(param);
	    }

	    public String toString() {
	        String output;
	        output = "Method " + this.method + " invoked: " + this.invocationTime.toString();
	        output += " with parameters: ";
	        for (LogDataParam param : this.parameters) {
	        	output += " "+param.toString();
	           
	        }
	        output += " Reult: "+ this.result;
	        return output;
	    }
	
	
}
