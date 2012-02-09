package org.gemsjax.shared.communication.message.request;

/**
 * pushed from server to client
 * @author Hannes Dorfmann
 *
 */
public class LiveAdminExperimentRequestMessage extends LiveRequestMessage{

	public static final String TAG="experiment";
	public static final String ATTRIBUTE_EXPERIMENT_ID="id";
	public static final String ATTRIBUTE_EXPERIMENT_NAME="name";
	
	private AdminExperimentRequest req;
	
	public LiveAdminExperimentRequestMessage(AdminExperimentRequest request) {
		super(request);
		this.req = request;
	}

	@Override
	public String toXml() {
		return super.openingXml()+"<"+TAG+" "+ATTRIBUTE_EXPERIMENT_ID+"=\""+req.getExperimentId()+"\" "+ATTRIBUTE_EXPERIMENT_NAME+"=\""+req.getExperimentName()+"\" />"+super.closingXml();
	}
	
	
	public AdminExperimentRequest getRequest()
	{
		return req;
	}

}
