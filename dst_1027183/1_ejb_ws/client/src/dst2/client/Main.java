package dst2.client;



public class Main {

	
	
	public static void main(String[] args){
		
		
		
		BeanCaller b = new BeanCaller();
		b.callFillTestData();
		b.callStorePriceSteps();
		b.callJobManagement();
		b.callJobManagement2();
		
		//waiting as wished in assignment, but commented out 'cause it's more expressive to just run the client twice...
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		b.callGeneralManagerForPrices();
	}
	
}
