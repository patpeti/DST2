package dst2.server.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;



/*
 * 
 * select u from User u JOIN u.membership mem where username like :username AND mem.grid.name like :gridname
 * 
 * number of assigned jobs in a grid:
 * 
 * query = "select Count(j) from Job j " +
			"JOIN j.execution ex " +
			"JOIN ex.computers comps " +
			"JOIN comps.cluster cl " +
			"JOIN cl.parentCluster pcl " +
			"JOIN pcl.grid g " +
			"where g.name like :gridname"
*/



//
//@NamedQueries({
//	@NamedQuery(
//	name = "UserWithMembership"	,
//	query = "select u from Job j " +
//			"JOIN j.execution ex " +
//			"JOIN ex.computers comps " +
//			"JOIN comps.cluster cl " +
//			"JOIN cl.grid g " +
//			"JOIN j.user u " +
//			"where g.name like :gridname AND EXISTS (select u from User u JOIN u.membership memb where memb.grid.name like :gridname) " +
//			"GROUP BY u.id " +
//			"HAVING Count(DISTINCT j) > :numberofjobs"
//
//	),
//	@NamedQuery(
//			name = "MostActiveUser"	,
//			query = "select u from User u " +
//					"JOIN u.jobs j " +
//					"GROUP BY u.id " +
//					"ORDER BY Count(distinct j)"
//					
//					
//
//			)
//})
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames={"accountNo","bankCode"})})
@PrimaryKeyJoinColumn(name="person_id")
public class User extends Person{

/**
	 * 
	 */
	private static final long serialVersionUID = -6679228508241460679L;
//	@Id
//	@GeneratedValue
//	private long id;
//
//	private String firstName;
//	private String lastName;
	@Column(nullable = false, unique = true)
	private String username;
	@Column(columnDefinition="CHAR(32)")
//	@Index(name="passwordIndex")
	private byte[] password;
//	@Embedded
//	private Address address;

	
	@OneToMany
	private List<Job> jobs = new ArrayList<Job>();
	@Column(name = "accountNo")
	private String accountNo;
	@Column(name = "bankCode")
	private String bankCode;
	
	@OneToMany
	private List<Membership> membership = new ArrayList<Membership>();
	
	/*********************************************      GETTERS - SETTERS           *************************************************/
	
	
//	public long getId() {
//		return id;
//	}
//
//	public void setId(long id) {
//		this.id = id;
//	}
//
//	public String getFirstName() {
//		return firstName;
//	}
//
//	public void setFirstName(String firstName) {
//		this.firstName = firstName;
//	}
//
//	public String getLastName() {
//		return lastName;
//	}
//
//	public void setLastName(String lastName) {
//		this.lastName = lastName;
//	}
//
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public byte[] getPassword() {
		return password;
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}

//	public Address getAddress() {
//		return address;
//	}
//
//	public void setAddress(Address address) {
//		this.address = address;
//	}

	public List<Job> getJobs() {
		return jobs;
	}

	public void setJobs(List<Job> jobs) {
		if(jobs != null){
			for(Job j : jobs){
				j.setUser(this);
			}
			this.jobs = jobs;
		}else{
			this.jobs = new ArrayList<Job>();
		}
		
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public List<Membership> getMembership() {
		return membership;
	}

	public void setMembership(List<Membership> membership) {
		this.membership = membership;
	}
	
	
	
}
