package dst2.server.dto;

import java.io.Serializable;
import java.util.List;

import dst2.server.model.Job;

public class JobCache implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3968685204569855084L;
	
	private long gridId;
	private int numOfCpus;
	private List<Job> jobList;
	
	public long getGridId() {
		return gridId;
	}
	public void setGridId(long gridId) {
		this.gridId = gridId;
	}
	public int getNumOfCpus() {
		return numOfCpus;
	}
	public void setNumOfCpus(int numOfCpus) {
		this.numOfCpus = numOfCpus;
	}
	public List<Job> getJobList() {
		return jobList;
	}
	public void setJobList(List<Job> jobList) {
		this.jobList = jobList;
	}
	
	
	
}
