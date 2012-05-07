package dst2.server.interfaces;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.ejb.Local;

@Local
public interface IPriceManagementBean extends Serializable{


	public void storePriceStep(int numberOfJobs, BigDecimal price) ;

	public BigDecimal getPriceForGivenNumberOfJobs(int i);

}
