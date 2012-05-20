package org.gemsjax.client.admin.view.grids;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.gemsjax.client.admin.notification.Notification.NotificationPosition;
import org.gemsjax.client.admin.notification.NotificationManager;
import org.gemsjax.client.admin.notification.TipNotification;
import org.gemsjax.client.admin.view.MetaModelView;
import org.gemsjax.client.admin.view.MetaModelView.MetaAttributeManipulationListener;
import org.gemsjax.client.admin.view.MetaModelView.MetaAttributeManipulationListener.MetaAttributeManipulationEvent;
import org.gemsjax.client.admin.view.MetaModelView.MetaAttributeManipulationListener.MetaAttributeManipulationEvent.ManipulationType;
import org.gemsjax.client.admin.view.MetaModelView.MetaConnectionPropertiesListener;
import org.gemsjax.client.admin.view.MetaModelView.MetaConnectionPropertiesListener.MetaConnectionPropertyEvent;
import org.gemsjax.client.admin.view.MetaModelView.MetaConnectionPropertiesListener.MetaConnectionPropertyEvent.ConnectionPropertyChangedType;
import org.gemsjax.client.admin.widgets.UploadDiaolog;
import org.gemsjax.client.util.Console;
import org.gemsjax.shared.FieldVerifier;
import org.gemsjax.shared.collaboration.CollaborateableElementPropertiesListener;
import org.gemsjax.shared.metamodel.MetaAttribute;
import org.gemsjax.shared.metamodel.MetaBaseType;
import org.gemsjax.shared.metamodel.MetaConnection;

import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyUpEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyUpHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellSavedEvent;
import com.smartgwt.client.widgets.grid.events.CellSavedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;




public class MetaConnectionDetailView extends VLayout implements ClickHandler, CollaborateableElementPropertiesListener{
	

	private MetaConnection metaConnection;
	private ListGrid attributesGrid;
	private MetaConnectionPropertiesGrid propertiesGrid;
	private Button addAttributeButton, removeAttributeButton;
	private AddMetaAttributeDialog addDialog;
	private List<MetaBaseType> metaBaseTypes; 
	
	private Set<MetaConnectionPropertiesListener> propertiesListeners;
	private Set<MetaModelView.MetaAttributeManipulationListener> attributeModifyListeners;
	
	public MetaConnectionDetailView(List<MetaBaseType> types ){
		this.metaBaseTypes = types;
		this.setWidth100();
		this.setHeight100();
		attributeModifyListeners = new LinkedHashSet<MetaModelView.MetaAttributeManipulationListener>();
		propertiesListeners = new LinkedHashSet<MetaModelView.MetaConnectionPropertiesListener>();
	
		
		SectionStack stack  = new SectionStack();
		stack.setWidth100();
		stack.setHeight("*");
		
		propertiesGrid = new MetaConnectionPropertiesGrid(this);
		
		
		SectionStackSection properties = new SectionStackSection("Properies");
		
		SectionStackSection attributes = new SectionStackSection("Attributes");
		
		
		attributesGrid = new ListGrid();
		ListGridField attributeName = new ListGridField("name","Name");
		ListGridField attributeType = new ListGridField("type","Type");
		SelectItem typesItem = new SelectItem();
		LinkedHashMap<String, String> typesMap = new LinkedHashMap<String, String>();
		for (MetaBaseType t: types)
			typesMap.put(t.getName(), t.getName());
		typesItem.setValueMap(typesMap);
		typesItem.setAddUnknownValues(false);
		attributeType.setEditorType(typesItem);
		
		attributesGrid.setCanEdit(true);  
		attributesGrid.setEditEvent(ListGridEditEvent.DOUBLECLICK);  
		attributesGrid.setModalEditing(true);  
		attributesGrid.setFields(attributeName, attributeType);
		//attributesGrid.setCanRemoveRecords(true);
		
				
		attributesGrid.addCellSavedHandler(new CellSavedHandler() {
			
			@Override
			public void onCellSaved(CellSavedEvent event) {
				onAttributeEdited((AttributeRecord) event.getRecord());
			}
		});
		
		addAttributeButton = new Button("Add Attribute");
		addAttributeButton.addClickHandler(this);
		addAttributeButton.setWidth100();
		addAttributeButton.setHeight(25);
		
		removeAttributeButton = new Button("Remove Attribute");
		removeAttributeButton.addClickHandler(this);
		removeAttributeButton.setWidth100();
		removeAttributeButton.setHeight(25);
		
		
		properties.addItem(propertiesGrid);
		attributes.addItem(attributesGrid);
		attributes.addItem(addAttributeButton);
		attributes.addItem(removeAttributeButton);
		stack.addSection(properties);
		stack.addSection(attributes);
	
		this.addMember(stack);
		
	}
	
	
	public void addMetaAttributeManipulationListeners(Set<MetaAttributeManipulationListener> listeners){
		attributeModifyListeners.addAll(listeners);
	}
	
	public void addMetaAttributeManipulationListener(MetaModelView.MetaAttributeManipulationListener l){
		attributeModifyListeners.add(l);
	}
	
	public void removeMetaAttributeManipulationListener(MetaModelView.MetaAttributeManipulationListener l){
		attributeModifyListeners.remove(l);
	}
	
	public void addMetaConnectionPropertiesListener(MetaConnectionPropertiesListener l){
		propertiesListeners.add(l);
	}
	
	public void removeMetaConnectionPropertiesListener(MetaConnectionPropertiesListener l){
		propertiesListeners.remove(l);
	}
	
	private void onAttributeEdited(AttributeRecord r){
		
		MetaAttribute att = r.getMetaAttribute();
		String name = r.getName();
		MetaBaseType t = stringToMetaBaseType(r.getTypeName());
		
		if (!FieldVerifier.isValidAttributeName(name)){
			
			NotificationManager.getInstance().showTipNotification(new TipNotification("Invalid name", "\""+name+"\" is not a valid attribute name", 2000, NotificationPosition.CENTER));
			
			r.setName(att.getName());
			r.setTypeName(att.getType().getName());
			
			return;
		}
		
		
		if (t == null){
			
			NotificationManager.getInstance().showTipNotification(new TipNotification("Invalid MetaBaseType",null, 2000, NotificationPosition.CENTER));
			
			r.setName(att.getName());
			r.setTypeName(att.getType().getName());
			
			return;
		}
		
		MetaAttributeManipulationEvent e = new MetaAttributeManipulationEvent(ManipulationType.MODIFY, metaConnection);
		e.setAttribute(att);
		e.setName(name);
		e.setBaseType(t);
		
		fireAttributeModifiedEvent(e);
	}
	
	
	public MetaConnection getMetaConnection(){
		return metaConnection;
	}
	
	
	public void setMetaConnection(MetaConnection m){
		if (metaConnection!=null)
			metaConnection.removePropertiesListener(this);
		
		this.metaConnection = m;
		this.metaConnection.addPropertiesListener(this);
		this.metaConnection.addPropertiesListener(this);
		propertiesGrid.setMetaConnection(m);
		
		
		onChanged();
	}


	private void fireAttributeModifiedEvent(MetaModelView.MetaAttributeManipulationListener.MetaAttributeManipulationEvent e)
	{
		for (MetaModelView.MetaAttributeManipulationListener l : attributeModifyListeners)
			l.onMetaAttributeManipulated(e);
	}
	
	
	public void fireConnectionPropertyChanged(MetaConnectionPropertyEvent e)
	{
		for (MetaConnectionPropertiesListener l : propertiesListeners )
			l.onMetaConnectionPropertyChanged(e);
	}
	

	@Override
	public void onClick(ClickEvent event) {
		
		if (event.getSource() == removeAttributeButton)
		{
			
			ListGridRecord [] records = attributesGrid.getSelectedRecords();
			
			if(records.length == 0){
				NotificationManager.getInstance().showTipNotification(new TipNotification("No Attribute selected", null, 2000, NotificationPosition.CENTER));
			}
			else
			{
				for (ListGridRecord r : records){
					
					MetaAttributeManipulationEvent e = new MetaAttributeManipulationEvent(ManipulationType.DELETE, metaConnection);
					e.setAttribute(((AttributeRecord)r).getMetaAttribute());
					fireAttributeModifiedEvent(e);
				}
			}
			
		}
		else
		if (event.getSource() == addAttributeButton){
			this.addDialog = new AddMetaAttributeDialog(metaBaseTypes);
			this.addDialog.getSaveButton().addClickHandler(this);
			addDialog.animateShow(AnimationEffect.FADE);
		}
		else
		if (addDialog!=null && addDialog.getSaveButton() == event.getSource()){
			if(FieldVerifier.isValidAttributeName(addDialog.getName()) && FieldVerifier.isNotEmpty(addDialog.getType())){
				
				MetaBaseType type = stringToMetaBaseType(addDialog.getType());
				
				if (type==null)
				{
					NotificationManager.getInstance().showTipNotification(new TipNotification("MetaBaseType was null", null, 2000, NotificationPosition.CENTER));
					return;
				}
				
				if (!metaConnection.isAttributeNameAvailable(addDialog.getName()))
				{
					NotificationManager.getInstance().showTipNotification(new TipNotification("Attribute with this name already exists", null, 2000, NotificationPosition.CENTER));
					return;
				}
				
				
				MetaAttributeManipulationEvent e = new MetaAttributeManipulationEvent(ManipulationType.NEW, metaConnection);
				e.setBaseType(type);
				e.setName(addDialog.getName());
				
				addDialog.destroy();
				fireAttributeModifiedEvent(e);
				
			}
			else
				NotificationManager.getInstance().showTipNotification(new TipNotification("No valid name set", null, 2000, NotificationPosition.CENTER));
		}
		
	}
	
	
	private MetaBaseType stringToMetaBaseType(String name){
		for (MetaBaseType type : metaBaseTypes)
			if(type.getName().equals(name))
				return type;
		
		return null;
	}


	@Override
	public void onChanged() {
		// Attributes
		AttributeRecord attributes[] = new AttributeRecord[metaConnection.getAttributes().size()];
		for (int i =0; i<metaConnection.getAttributes().size(); i++){
			MetaAttribute a = metaConnection.getAttributes().get(i);
			attributes[i]=new AttributeRecord(a);
		}
		attributesGrid.setRecords(attributes);
		
	}
	

}





class MetaConnectionPropertiesGrid extends VStack implements ClickHandler, CollaborateableElementPropertiesListener{
	
	private TextItem nameField;
	private TextItem boundsField;
	private Button sourceIconButton;
	private Button targetIconButton;
	private int cretedItems = 0;
	private MetaConnection metaConnection;
	private MetaConnectionDetailView parent;
	private Label sourceClassName;
	private Label targetClassName;
	
	public MetaConnectionPropertiesGrid(MetaConnectionDetailView p){
		
		this.parent = p;
		setWidth100();
		this.setMembersMargin(0);
		
		DynamicForm nameForm = new DynamicForm();
		nameField = new TextItem();
		nameField.setWidth("100%");
		nameField.setHeight("100%");
		nameField.setShowTitle(false);
		nameField.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getKeyName().equals("Enter") && !metaConnection.getName().equals(nameField.getValue())){
					
					MetaConnectionPropertyEvent e = new MetaConnectionPropertyEvent(ConnectionPropertyChangedType.RENAME, metaConnection);
					e.setName(nameField.getValueAsString());
					
					parent.fireConnectionPropertyChanged(e);
					
				}
					
			}
		});
		
		nameForm.setFields(nameField);
		
		
		
		DynamicForm boundsForm = new DynamicForm();
		boundsField = new TextItem();
		boundsField.setWidth("100%");
		boundsField.setHeight("100%");
		boundsField.setShowTitle(false);
		boundsField.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (!event.getKeyName().equals("Enter"))
					return;
				
				String val = boundsField.getValueAsString();
				val = val.replaceAll(" ", "");
				String bounds[] = val.split("\\.\\.");
				
				Console.log(bounds.toString());
				
				if (val.equals("*"))
				{
					MetaConnectionPropertyEvent e = new MetaConnectionPropertyEvent(ConnectionPropertyChangedType.MULTIPLICITY,metaConnection);
					e.setLowerBound(MetaConnection.MULTIPLICITY_MANY);
					e.setUpperBound(MetaConnection.MULTIPLICITY_MANY);
					parent.fireConnectionPropertyChanged(e);
				}
				else
				if (bounds.length==2){
					int low, up;
					if(bounds[0].equals("*"))
						low = MetaConnection.MULTIPLICITY_MANY;
					else
						try{
							low = Integer.parseInt(bounds[0]);
							if (low<0)
							{	generateMultiplicityString();
								NotificationManager.getInstance().showTipNotification(new TipNotification("Parse Error", "Lower bound must be a positive integer or *", 2000, NotificationPosition.CENTER));
								return;
							}
								
						}catch(NumberFormatException e){
							NotificationManager.getInstance().showTipNotification(new TipNotification("Parse Error", "Lower bound must be a valid integer or *", 2000, NotificationPosition.CENTER));
							return;
						}
					
					if(bounds[1].equals("*"))
						up = MetaConnection.MULTIPLICITY_MANY;
					else
						try{
							up = Integer.parseInt(bounds[1]);
							if (up<0)
							{
								generateMultiplicityString();
								NotificationManager.getInstance().showTipNotification(new TipNotification("Parse Error", "Upper bound must be a positive integer or *", 2000, NotificationPosition.CENTER));
								return;
							}
							
						}catch(NumberFormatException e){
							NotificationManager.getInstance().showTipNotification(new TipNotification("Parse Error", "Upper bound must be a valid integer or *", 2000, NotificationPosition.CENTER));
							generateMultiplicityString();
							return;
						}
					
					
					if ( low!=MetaConnection.MULTIPLICITY_MANY && low>up && up!=MetaConnection.MULTIPLICITY_MANY)
					{
						NotificationManager.getInstance().showTipNotification(new TipNotification("Error", "Upper bound can not be less than the lower bound", 2000, NotificationPosition.CENTER));
						generateMultiplicityString();
						return;
					}
					
					if ( low == 0 && up == 0)
					{
						NotificationManager.getInstance().showTipNotification(new TipNotification("Error", "Its not allowed to have zero Multiplicity", 2000, NotificationPosition.CENTER));
						generateMultiplicityString();
						return;
					}
					
					
					MetaConnectionPropertyEvent e = new MetaConnectionPropertyEvent(ConnectionPropertyChangedType.MULTIPLICITY,metaConnection);
					e.setLowerBound(low);
					e.setUpperBound(up);
					parent.fireConnectionPropertyChanged(e);
				}
				else
					NotificationManager.getInstance().showTipNotification(new TipNotification("Incorrect Multiplicity format", "The correct format is: lower .. upper and you can use * as symbol for arbitary many", 2000, NotificationPosition.CENTER));
				
					
			}
		});
		
		boundsForm.setFields(boundsField);
		
		
		sourceIconButton = new Button("Set source icon");
		sourceIconButton.addClickHandler(this);
		sourceIconButton.setWidth100();
		sourceIconButton.setHeight(25);
		
		
		targetIconButton = new Button("Set target icon");
		targetIconButton.addClickHandler(this);
		targetIconButton.setWidth100();
		targetIconButton.setHeight(25);
		
		
		sourceClassName = new Label();
		targetClassName = new Label();
		
		
		
		this.addMember(createItem("Name:", nameForm));
		this.addMember(createItem("Multiplicity:", boundsForm));
		this.addMember(createItem("Source", sourceClassName));
		this.addMember(createItem("Target", targetClassName));
		
		this.addMember(sourceIconButton);
		this.addMember(targetIconButton);
		
		
	}
	
	private void generateMultiplicityString(){
		if (metaConnection.getTargetLowerBound() == MetaConnection.MULTIPLICITY_MANY &&
			metaConnection.getTargetUpperBound() == MetaConnection.MULTIPLICITY_MANY)
			boundsField.setValue( "*");
		
		else{
			String up = metaConnection.getTargetUpperBound()==MetaConnection.MULTIPLICITY_MANY ?"*":""+metaConnection.getTargetUpperBound();
			String low = metaConnection.getTargetLowerBound()==MetaConnection.MULTIPLICITY_MANY ?"*":""+metaConnection.getTargetLowerBound();
			
			boundsField.setValue( low+" .. "+up );
		}
	}
	
	
	private HLayout createItem(String name, Canvas component){
		
		Label l = new Label(name);
		l.setWidth("40%");
		l.setHeight("25px");
		l.setStyleName("DetailGridLabel");
		
		
		
		HLayout layout = new HLayout();
		layout.setWidth100();
		layout.setHeight(25);
		layout.setMembersMargin(0);
		
		component.setWidth("60%");
		component.setHeight(25);
		layout.setBackgroundColor(cretedItems%2==0?"#f1f1f1":"#f8f8f8");
		layout.addMember(l);
		layout.addMember(component);
		cretedItems++;
		return layout;
		
	}


	@Override
	public void onClick(ClickEvent event) {
		
		final Object clickSource = event.getSource();
		
		
		UploadDiaolog ud = new UploadDiaolog("Upload icon", new UploadDiaolog.SuccessfulHandler() {
			
			@Override
			public void onUploadSuccessful(String pathToUploadedFile) {
				
				if (clickSource == sourceIconButton){
					MetaConnectionPropertyEvent e = new MetaConnectionPropertyEvent(ConnectionPropertyChangedType.SOURCE_ICON, metaConnection);
					e.setSourceIcon(pathToUploadedFile);
					parent.fireConnectionPropertyChanged(e);
				}
				else
				if (clickSource == targetIconButton){
					MetaConnectionPropertyEvent e = new MetaConnectionPropertyEvent(ConnectionPropertyChangedType.TARGET_ICON, metaConnection);
					e.setTargetIcon(pathToUploadedFile);
					parent.fireConnectionPropertyChanged(e);
				}	
				
			}
		});
		
		ud.show();
		
	}
	
	
	public void setMetaConnection(MetaConnection mc){
		if (this.metaConnection!=null)
			this.metaConnection.removePropertiesListener(this);
		
		this.metaConnection = mc;
		this.metaConnection.addPropertiesListener(this);
		
		onChanged();
	}


	@Override
	public void onChanged() {
		nameField.setValue(metaConnection.getName());
		sourceClassName.setContents(metaConnection.getSource().getName());
		targetClassName.setContents(metaConnection.getTarget().getName());
		generateMultiplicityString();
	}
	
	
}

