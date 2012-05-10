package dst2.client;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import dst2.server.dto.LogDataDto;
import dst2.server.interfaces.IGeneralManagementBean;
import dst2.server.interfaces.IJobManagementBean;
import dst2.server.interfaces.ILogBean;
import dst2.server.interfaces.TestingBeanInterface;
import dst2.server.utils.exceptions.jobmanagement.CacheEmptyException;
import dst2.server.utils.exceptions.jobmanagement.NoGridFoundException;
import dst2.server.utils.exceptions.jobmanagement.NotEnoughCPUException;
import dst2.server.utils.exceptions.jobmanagement.WrongUsernameOrPasswordException;





public class BeanCaller{
	private static final Logger LOG = Logger.getLogger(BeanCaller.class.getName());
	private IGeneralManagementBean g;

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

	public void callJobManagement() {
		IJobManagementBean j;
		try {
			j = (IJobManagementBean) InitialContext.doLookup("java:global/dst2_1/JobManagementBean");
			//invalid credentials
		try{	
			j.doLogin("Gandalf", "YouShallNotPass");
			//valid
		} catch (WrongUsernameOrPasswordException e) {
			LOG.info(e.getMessage());
		}try{	
			j.doLogin("patonaipeter", "pass");
		} catch (WrongUsernameOrPasswordException e) {
			LOG.info(e.getMessage());
		}
			LOG.info("Login Successful");
			List<String> params = new ArrayList<String>();
			params.add("blabla");
			params.add("parameter");
			
			j.addJob(9, 4, "testworkflow",params );
			LOG.info("Job Added Successfully to Cache");
			
			List<String> params2 = new ArrayList<String>();
			params2.add("param1");
			params2.add("parameter2");
			
			j.addJob(9, 50, "testworkflow2",params );
			LOG.info("Job Added Successfully to Cache");
			
			BigDecimal b = j.getAllJobs(9);
			LOG.info("All jobs in cache:" + b);
			
//			j.removeJobsFromGrid(9);
//			LOG.info("Jobs deleted for grid 9 from cache");
//			
//			BigDecimal b2 = j.getAllJobs(9);
//			LOG.info("All jobs in cache:" + b2);
//			
			j.submit();
			LOG.info("Cache submitted");
			
		} catch (NamingException e) {
			e.printStackTrace();
		
		} catch (NotEnoughCPUException e) {
			LOG.info(e.getMessage());
		} catch (NoGridFoundException e) {
			LOG.info(e.getMessage());
		} catch (CacheEmptyException e) {
			LOG.info(e.getMessage());
		}
		
	}
	
	public void callJobManagement2() {
		IJobManagementBean j;
		try {
			j = (IJobManagementBean) InitialContext.doLookup("java:global/dst2_1/JobManagementBean");
			//invalid credentials
		try{	
			j.doLogin("Gandalf", "YouShallNotPass");
			//valid
		} catch (WrongUsernameOrPasswordException e) {
			LOG.info(e.getMessage());
		}try{	
			j.doLogin("patonaiagnes", "pass");
		} catch (WrongUsernameOrPasswordException e) {
			LOG.info(e.getMessage());
		}
			LOG.info("Login Successful");
			List<String> params = new ArrayList<String>();
			params.add("blabla");
			params.add("parameter");
			
			j.addJob(9, 4, "job2_1",params );
			LOG.info("Job Added Successfully to Cache");
			
			List<String> params2 = new ArrayList<String>();
			params2.add("param1");
			params2.add("parameter2");
			
			j.addJob(9, 50, "job2_2",params );
			LOG.info("Job Added Successfully to Cache");
			
			BigDecimal b = j.getAllJobs(9);
			LOG.info("All jobs in cache:" + b);
			
			j.removeJobsFromGrid(9);
			LOG.info("Jobs deleted for grid 9 from cache");
			
			BigDecimal b2 = j.getAllJobs(9);
			LOG.info("All jobs in cache:" + b2);
			
			j.submit();
			LOG.info("Cache submitted");
			
		} catch (NamingException e) {
			e.printStackTrace();
		
		} catch (NotEnoughCPUException e) {
			LOG.info(e.getMessage());
		} catch (NoGridFoundException e) {
			LOG.info(e.getMessage());
		} catch (CacheEmptyException e) {
			LOG.info(e.getMessage());
		}
		
	}

	public void callGeneralManagerForPrices() {
	
		Future<BigDecimal> bill = this.invokeBill();
		
		try {
			LOG.info("BILL: "+ bill.get().intValue());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		System.out.println("Finished");
		
	}
	
	private Future<BigDecimal> invokeBill(){
		
			
			return g.getTotalBill("patonaipeter");
			
			
			
	
	}

	public void callForLogs() {
		ILogBean l;
	
			try {
				l = (ILogBean) InitialContext.doLookup("java:global/dst2_1/LogBean");
				List<LogDataDto> loglist = l.getLog();
				for(LogDataDto log : loglist){
					LOG.info(log.toString());
				}
				
			} catch (NamingException e) {
				e.printStackTrace();
			}
		
	}
	
}
