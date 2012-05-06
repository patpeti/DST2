package dst2.client;

import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import dst2.server.interfaces.IPriceManagementBean;
import dst2.server.interfaces.TestingBeanInterface;
import dst2.server.utils.exceptions.NoJobsAssignedException;
import dst2.server.utils.exceptions.NoUserExists;
import dst2.server.utils.exceptions.UserNameNotFoundException;





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
		
		IPriceManagementBean p;
		
		p = (IPriceManagementBean) InitialContext.doLookup("java:global/dst2_1/PriceManagementBean");
		try {
			
			p.storeStepToUser("patonaipeter");
			
			//testing exception
			p.storeStepToUser("nonExistinguSer");
			
			p.storePriceSteps();
			
		} catch (UserNameNotFoundException e) {
			LOG.info(e.getMessage());
		} catch (NoJobsAssignedException e) {
			LOG.info(e.getMessage());
		} catch (NoUserExists e) {
			LOG.info(e.getMessage());
		}	
		LOG.info("PriceSteps Added");
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}
	
}
