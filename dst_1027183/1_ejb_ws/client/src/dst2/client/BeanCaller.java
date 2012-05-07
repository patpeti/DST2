package dst2.client;

import java.math.BigDecimal;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import dst2.server.interfaces.IGeneralManagementBean;
import dst2.server.interfaces.TestingBeanInterface;





public class BeanCaller{
	private static final Logger LOG = Logger.getLogger(BeanCaller.class.getName());

	public void callFillTestData(){
		try {
		
		TestingBeanInterface t;
		
		t = (TestingBeanInterface) InitialContext.doLookup("java:global/dst2_1/TestingBean");
		t.fillTestData();	
		LOG.info("TestData added");
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}
	
	public void callStorePriceSteps(){
		try {
		
		IGeneralManagementBean g;
		
		g = (IGeneralManagementBean) InitialContext.doLookup("java:global/dst2_1/GeneralManagementBean");
		//int numJob = 600;
		g.storePriceStep(100,new BigDecimal(30));
		g.storePriceStep(1000,new BigDecimal(15));
		g.storePriceStep(5000,new BigDecimal(5));
	
		LOG.info("Prices Added");
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}
	
}
