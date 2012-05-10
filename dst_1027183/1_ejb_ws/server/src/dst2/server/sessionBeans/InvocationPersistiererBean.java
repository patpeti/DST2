package dst2.server.sessionBeans;

import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dst2.server.interfaces.IInvocationPersistiererBean;
import dst2.server.model.LogData;

@Singleton
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class InvocationPersistiererBean implements IInvocationPersistiererBean {

    @PersistenceContext
    private EntityManager em;
    
    public void persist(LogData l) {
        this.em.persist(l);
    }
}
