package dst2.server.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Address implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8891416117707049367L;
	private String street;
	private String city;
	private String zipCode;
	
	/*********************************************      GETTERS - SETTERS           *************************************************/
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	
	
	
	
}
