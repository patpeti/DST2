package dst2.server.interfaces;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.ejb.Remote;

@Remote
public interface IGeneralManagementBean extends Serializable{

	public void storePriceStep(int numberOfJobs, BigDecimal price) ;
	
}
