package dst2.server.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Membership implements Serializable  {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5394427366387926459L;
	@Id
	@GeneratedValue
	private long id;
	@Temporal(TemporalType.DATE) 
	private Date registration;
	private double discount;
	@ManyToOne(cascade = CascadeType.ALL)
	private User user;
	@ManyToOne
	private Grid grid;
	/*********************************************      GETTERS - SETTERS           *************************************************/
	
	

	public Date getRegistration() {
		return registration;
	}
	public void setRegistration(Date registration) {
		this.registration = registration;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		if(user != null) user.getMembership().add(this);
		this.user = user;
	}
	public Grid getGrid() {
		return grid;
	}
	public void setGrid(Grid grid) {
		if(grid != null) grid.getMembership().add(this);
		this.grid = grid;
	}
	
	

	
}
