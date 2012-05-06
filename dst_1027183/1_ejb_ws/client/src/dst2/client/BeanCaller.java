package dst2.client;

import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;

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
	
}
