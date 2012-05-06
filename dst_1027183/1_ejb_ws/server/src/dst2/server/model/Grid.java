package dst2.server.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Grid implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6980967075156563373L;
	@Id
	@GeneratedValue
	private long id;
	@Column(unique = true)
	private String name;
	private String location;
	private BigInteger costsPerCPUMinute;
	@OneToMany
	private List<Cluster> clusters;
	@OneToMany
	private List<Membership> membership = new ArrayList<Membership>();
	
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public BigInteger getCostsPerCPUMinute() {
		return costsPerCPUMinute;
	}
	public void setCostsPerCPUMinute(BigInteger costsPerCPUMinute) {
		this.costsPerCPUMinute = costsPerCPUMinute;
	}
	public List<Cluster> getClusters() {
		return clusters;
	}
	public void setClusters(List<Cluster> clusters) {
		for(Cluster c : clusters){
			c.setGrid(this);
		}
		this.clusters = clusters;
	}
	public List<Membership> getMembership() {
		return membership;
	}
	public void setMembership(List<Membership> membership) {
		this.membership = membership;
	}
	
	
	
	
}
