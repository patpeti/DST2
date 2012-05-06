package dst2.server.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class PriceStep implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -431556218314241466L;
	@Id
	@GeneratedValue
	private long id;
	private int numberofHistoricalJobs;
	private BigDecimal price;
	@OneToOne(mappedBy = "priceStep")
	private User user;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	public int getNumberofHistoricalJobs() {
		return numberofHistoricalJobs;
	}
	public void setNumberofHistoricalJobs(int numberofHistoricalJobs) {
		this.numberofHistoricalJobs = numberofHistoricalJobs;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
}
