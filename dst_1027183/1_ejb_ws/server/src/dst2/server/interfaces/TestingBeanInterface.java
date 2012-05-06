package dst2.server.interfaces;

import java.io.Serializable;

import javax.ejb.Remote;

@Remote
public interface TestingBeanInterface extends Serializable{

	public void fillTestData();
}
