package org.gemsjax.shared.communication.message.experiment;

import java.util.Set;

import org.gemsjax.shared.experiment.ExperimentGroup;
import org.gemsjax.shared.experiment.ExperimentInvitation;

/**
 * 
 * 
 * <exp ref-id="string">
 * 		<new>
 * 			<name>String</name>
 * 			<desc>String</desc>
 * 
 * 			<admins>
 * 				<user id="integer" />
 * 				<user id="integer" />
 * 				...
 * 			</admins>
 * 			
 * 			<gr name="String" start="timeInSecond(Type=Long)" end="TimeInSeconds(Type=Long)">
 * 				<email>email@mail.com</email>
 * 				<email>email@mail.com</email>
 * 				...
 * 			</gr>
 * 
 * 			<gr name="String" start="timeInSecond(Type=Long)" end="TimeInSeconds(Type=Long)">
 * 				<email>email@mail.com</email>
 * 				<email>email@mail.com</email>
 * 				...
 * 			</gr>
 * 
 * 			...
 * 			
 * 		</new>
 * </exp>
 * 
 * There can be arbitaray <gr> and arbitary <email> in a <gr>. There is exactly one <name>, <desc> and <admins> tag.
 * The <admins> tag contains arbitary <user> tags (including zero),
 * 
 * @author Hannes Dorfmann
 *
 */
public class CreateExperimentMessage extends ReferenceableExperimentMessage {

	public static final String TAG ="new";
	
	public static final String SUBTAG_NAME ="name";
	public static final String SUBTAG_DESCRIPTION = "desc";
	public static final String SUBTAG_GROUP ="gr";
	public static final String SUBSUBTAG_GROUP_EMAIL="email";
	public static final String SUBTAG_ADMINS="admins";
	public static final String SUBSUBTAG_USER = "user";
	
	public static final String ATTRIBUTE_GROUP_STARTDATE="start";
	public static final String ATTRIBUTE_GROUP_ENDDATE="end";
	public static final String ATTRIBUTE_GROUP_NAME="name";
	public static final String ATTRIBUTE_USER_ID = "id";
	
	
	private Set<ExperimentGroup> groups;
	private Set<Integer> userIds;
	private String name;
	private String description;
	
	public CreateExperimentMessage(String referenceId, String name, String description, Set<ExperimentGroup> groups, Set<Integer> userIds) {
		super(referenceId);
		this.name = name;
		this.description = description;
		this.groups = groups;
		this.userIds = userIds;
	}

	@Override
	public String toXml() {
		
		String ret = super.openingXml()+"<"+TAG+"><"+SUBTAG_NAME+">"+name+"</"+SUBTAG_NAME+"><"+SUBTAG_DESCRIPTION+">"+description+"</"+SUBTAG_DESCRIPTION+">";
		
		
		ret+="<"+SUBTAG_ADMINS+">";
		
		for (int id : userIds)
			ret+="<"+SUBSUBTAG_USER+" "+ATTRIBUTE_USER_ID+"=\""+id+"\" />";
		
		ret+="</"+SUBTAG_ADMINS+">";
		
		
		for (ExperimentGroup e : groups){
			ret+="<"+SUBTAG_GROUP+" "+ATTRIBUTE_GROUP_NAME+"=\""+name+"\" "+ATTRIBUTE_GROUP_STARTDATE+"=\""+e.getStartDate().getTime()+"\" "+ATTRIBUTE_GROUP_ENDDATE+"=\""+e.getEndDate().getTime()+"\">";
			for (ExperimentInvitation i : e.getExperimentInvitations())
				ret+="<"+SUBSUBTAG_GROUP_EMAIL+">"+i.getEmail()+"</"+SUBSUBTAG_GROUP_EMAIL+">";
			
			ret+="</"+SUBTAG_GROUP+">";
		}
		
		
		return ret+"</"+TAG+">"+closingXml();
	}

	public Set<ExperimentGroup> getGroups() {
		return groups;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	
	
	public Set<Integer> getUserIds(){
		return userIds;
	}
	
	
/*
	public static void main(String args[]){
		
		Set<ExperimentGroup> groups = new LinkedHashSet<ExperimentGroup>();
		
		for (int j =0; j<10; j++){
		ExperimentGroup g = new ExperimentGroupImpl();
		g.setStartDate(new Date());
		g.setEndDate(new Date());
		
		ExperimentInvitationImpl i = new ExperimentInvitationImpl();
		i.setEmail("test@email.com");
		
		g.getExperimentInvitations().add(i);
		
		groups.add(g);
		}
		
		CreateExperimentMessage m = new CreateExperimentMessage("referenceId", "name", "description", groups);
		
		System.out.println(m.toXml());
		
	}
*/
	
}
