package dst2.server.sessionBeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dst2.server.dto.LogDataDto;
import dst2.server.dto.LogDataParamDto;
import dst2.server.interfaces.ILogBean;
import dst2.server.model.LogData;
import dst2.server.model.LogDataParam;

@Stateless(mappedName = "LogBean")
@Remote(ILogBean.class)
public class LogBean implements ILogBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4350031809285453782L;

	@PersistenceContext(name="grid")
	private EntityManager em;
	
	@Override
	public List<LogDataDto> getLog() {
		Query q = em.createQuery("select log from LogData log");
		List<LogData> logs = (List<LogData>) q.getResultList();
		List<LogDataDto> dtos = mapLogs(logs);
		return dtos;
	}

	private List<LogDataDto> mapLogs(List<LogData> logs) {
		List<LogDataDto> data = new ArrayList<LogDataDto>();
		for(LogData l : logs){
			LogDataDto dto = new LogDataDto();
			dto.setInvocationTime(l.getInvocationTime());
			dto.setId(l.getId());
			dto.setMethod(l.getMethod());
			dto.setResult(l.getResult());
			dto.setParameters(this.mapLogParams(l.getParameters()));
			data.add(dto);
		}
		return data;
	}

	private List<LogDataParamDto> mapLogParams(List<LogDataParam> parameters) {
		List<LogDataParamDto> dtoparams = new ArrayList<LogDataParamDto>();
		for(LogDataParam p : parameters){
			LogDataParamDto dtoparam = new LogDataParamDto();
			dtoparam.setClassName(p.getClassName());
			dtoparam.setId(p.getId());
			dtoparam.setIndex(p.getIndex());
			dtoparam.setValue(p.getValue());
			dtoparams.add(dtoparam);
		}
		return dtoparams;
	}

	
}
