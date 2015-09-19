package bridgempp.service;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import bridgempp.Message;
import bridgempp.data.Endpoint;

@Entity(name = "MultiBridgeServiceHandle")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "HANDLE_TYPE", discriminatorType=DiscriminatorType.STRING, length = 50)
public abstract class MultiBridgeServiceHandle<S extends SingleToMultiBridgeService<S, H>, H extends MultiBridgeServiceHandle<S, H>>
{
	@Id
	@Column(name = "HANDLE_IDENTIFIER", nullable = false, length = 255)
	protected String identifier;
	
	@ManyToOne(optional = false, targetEntity = SingleToMultiBridgeService.class)
	@JoinColumn(name = "MULTI_BRIDGE_SERVICE_IDENTIFIER", referencedColumnName = "SERVICE_IDENTIFIER")
	protected S service;

	@OneToOne(optional = false)
	protected Endpoint endpoint;
	
	
	public abstract void sendMessage(Message message);

	@SuppressWarnings("unchecked")
	protected MultiBridgeServiceHandle(Endpoint endpoint, S service)
	{
		this.endpoint = endpoint;
		this.identifier = endpoint.getIdentifier();
		this.service = service;
		service.addHandle((H) this);
	}
	
	/**
	 * JPA Constructor
	 */
	protected MultiBridgeServiceHandle()
	{
	}
	
	@SuppressWarnings("unchecked")
	protected void removeHandle()
	{
		service.removeHandle((H) this);
	}
	
}