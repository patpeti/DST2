package dst2.server.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogDataDto implements Serializable{

	
	    /**
	 * 
	 */
	private static final long serialVersionUID = 4767881666771769561L;

		private long id;
	    private Date invocationTime;
	    private String method;
	    private String result;
	    private List<LogDataParamDto> parameters = new ArrayList<LogDataParamDto>();

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

	    public void setParameters(List<LogDataParamDto> parameters) {
	        this.parameters = parameters;
	    }

	    public List<LogDataParamDto> getParameters() {
	        return parameters;
	    }

	    public void addParameter(LogDataParamDto param) {
	        this.parameters.add(param);
	    }

	    public String toString() {
	        String output;
	        output = "Method " + this.method + " invoked: " + this.invocationTime.toString();
	        output += " with parameters: ";
	        for (LogDataParamDto param : this.parameters) {
	        	output += " "+param.toString();
	           
	        }
	        output += " Reult: "+ this.result;
	        return output;
	    }
	
	
}
