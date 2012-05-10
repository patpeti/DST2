package dst2.server.interfaces;

import javax.ejb.Local;

import dst2.server.model.LogData;

@Local
public interface IInvocationPersistiererBean {
	void persist(LogData l);
}
