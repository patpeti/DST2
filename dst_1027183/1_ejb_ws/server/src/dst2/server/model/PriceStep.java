package dst2.server.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PriceStep implements Serializable, Comparable<PriceStep>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -431556218314241466L;
	@Id
	@GeneratedValue
	private long id;
	private int numberofHistoricalJobs;
	private BigDecimal price;
	
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

	@Override
	public int compareTo(PriceStep o) {
	        if (this.numberofHistoricalJobs == o.getNumberofHistoricalJobs())
	            return 0;
	        else if ((this.numberofHistoricalJobs) > (o.getNumberofHistoricalJobs()))
	            return 1;
	        else
	            return -1;
	}

	
}
