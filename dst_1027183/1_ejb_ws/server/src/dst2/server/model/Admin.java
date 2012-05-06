package dst2.server.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="person_id")
public class Admin extends Person implements Serializable{

/**
	 * 
	 */
	private static final long serialVersionUID = 3355390864606012474L;
//	@Id
//	@GeneratedValue
//	private Long id;
//	private String firstName;
//	private String lastName;
//	@Embedded
//	private Address address;
	@OneToMany
	private List<Cluster> clusters;
	
	/*********************************************      GETTERS - SETTERS           *************************************************/
	
	
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//	public String getFirstName() {
//		return firstName;
//	}
//	public void setFirstName(String firstName) {
//		this.firstName = firstName;
//	}
//	public String getLastName() {
//		return lastName;
//	}
//	public void setLastName(String lastName) {
//		this.lastName = lastName;
//	}
//	public Address getAddress() {
//		return address;
//	}
//	public void setAddress(Address address) {
//		this.address = address;
//	}
	public List<Cluster> getClusters() {
		return clusters;
	}
	public void setClusters(List<Cluster> clusters) {
		for(Cluster c : clusters){
			c.setAdmin(this);
		}
		this.clusters = clusters;
	}
	

	
}
