package dst2.server.interfaces;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;

import dst2.server.dto.LogDataDto;

@Remote
public interface ILogBean extends Serializable{

	public List<LogDataDto> getLog();
	
}
