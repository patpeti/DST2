package dst2.server.sessionBeans;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import dst2.server.interfaces.IGeneralManagementBean;
import dst2.server.interfaces.IPriceManagementBean;

@Stateless(name = "GeneralManagementBean")
@Remote(IGeneralManagementBean.class)
public class GeneralManagementBean implements IGeneralManagementBean, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5335992770730878412L;
	@EJB
	private IPriceManagementBean priceManager;
	
	@Override
	public void storePriceStep(int numberOfJobs, BigDecimal price) {
		this.priceManager.storePriceStep(numberOfJobs, price);
	}

	public void setPriceManager(IPriceManagementBean priceManager) {
		this.priceManager = priceManager;
	}
	
	

}
