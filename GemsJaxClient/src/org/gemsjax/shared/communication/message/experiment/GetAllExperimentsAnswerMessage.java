 package org.gemsjax.shared.communication.message.experiment;

import java.util.Set;
import org.gemsjax.shared.experiment.Experiment;

/**
 * Sent from Server to Client as response on a {@link GetAllExperimentsMessages}
 * 
 * <exp ref-id="blabla">
 * 		<all>
 * 			<e id="integer" name="String" />
 * 			<e id="integer" name="String" />
 * 			...
 * 		</all>
 * </exp>
 * 
 * Note: that there can also be no <e> tag. However the passed constructor parameter "experiments" must be not null, that means
 * an empty Set<Experiment>
 * 
 * @author Hannes Dorfmann
 *
 */
public class GetAllExperimentsAnswerMessage extends ReferenceableExperimentMessage{

	public static final String TAG = "all";
	public static final String SUBTAG_EXPERIMENT = "e";
	public static final String ATTRIBUTE_ID="id";
	public static final String ATTRIBUTE_NAME="name";
	
	private Set<ExperimentDTO> experiments;
	
	
	public GetAllExperimentsAnswerMessage(String referenceId, Set<ExperimentDTO> experiments) {
		super(referenceId);
		this.experiments = experiments;
		
	}
	
	public Set<ExperimentDTO> getExperiments(){
		return experiments;
	}

	@Override
	public String toXml() {
		/*
		String ret = super.openingXml()+"<"+TAG+">";
		
		for (Experiment e: experiments)
			ret+="<"+SUBTAG_EXPERIMENT+" "+ATTRIBUTE_ID+"=\""+e.getId()+"\" "+ATTRIBUTE_NAME+"=\""+e.getName()+"\" />";
		
		return ret+"</"+TAG+">"+super.closingXml();
		*/
		return null;
	}

}
