package dst2.server.interceptor;

import java.util.Date;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import dst2.server.interfaces.IInvocationPersistiererBean;
import dst2.server.model.LogData;
import dst2.server.model.LogDataParam;

//bypass rollback
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AuditInterceptor {

	
	@EJB
	private IInvocationPersistiererBean ip;

	private static final Logger logger = Logger
			.getLogger(AuditInterceptor.class.getName());
	
	
	//persist invocation time , method name ,params (index, class, value) , result value (or exception)
	
	@AroundInvoke
	public Object audit(InvocationContext ctx) throws Exception {
		try {
			Object result = ctx.proceed();
			String resultString;
				
			if (result == null)	resultString = "null";
			else	resultString = result.toString();
			
			logger.info("resultString: " + resultString);		
					
			saveLog(ctx, resultString);
			return result;
		} catch (Exception e) {
			String resultString = e.getMessage();
			saveLog(ctx, resultString);
			throw e;
		}

	}


	private void saveLog(InvocationContext ctx, String resultString) {
		
		  Date date = new Date();
	      LogData log = new LogData();
	      log.setInvocationTime(date);
	      log.setMethod(ctx.getMethod().getName());
	      log.setResult(resultString); 
	      logger.info("Method Intercepted!: " + log.getMethod());

	        final Object[] params = ctx.getParameters();
	        if (params != null) {
	        	
	        	//iterate over InvocationContext params
	            for (int i = 0; i < params.length; i++) { 
	            	
	            	//create new LogDataPram -->index,classname,value
	            	LogDataParam param = new LogDataParam();
	            
	                Object p = params[i];
	                //save index as string
	                param.setIndex("" + i);
	                if (p != null) {
	                   
	                    param.setValue(p.toString()); 
	                    param.setClassName((p.getClass().getName()));
	                } else {
	                    param.setClassName("null");
	                    param.setValue("null");
	                }

	                log.addParameter(param);
	            }
		
	          
		}
	         this.ip.persist(log);
	        
	}
	
	
}
