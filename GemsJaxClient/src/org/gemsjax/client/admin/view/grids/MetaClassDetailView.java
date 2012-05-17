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
import org.gemsjax.client.admin.widgets.ModalDialog;
import org.gemsjax.client.admin.widgets.OptionButton;
import org.gemsjax.client.admin.widgets.Title;
import org.gemsjax.client.admin.widgets.UploadDiaolog;
import org.gemsjax.shared.FieldVerifier;
import org.gemsjax.shared.collaboration.CollaborateableElementPropertiesListener;
import org.gemsjax.shared.metamodel.MetaAttribute;
import org.gemsjax.shared.metamodel.MetaBaseType;
import org.gemsjax.shared.metamodel.MetaClass;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellSavedEvent;
import com.smartgwt.client.widgets.grid.events.CellSavedHandler;
import com.smartgwt.client.widgets.grid.events.EditCompleteEvent;
import com.smartgwt.client.widgets.grid.events.EditCompleteHandler;
import com.smartgwt.client.widgets.grid.events.RowEditorExitEvent;
import com.smartgwt.client.widgets.grid.events.RowEditorExitHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;


class AttributeRecord extends ListGridRecord{
	
	private MetaAttribute metaAttribute;

	
	public AttributeRecord(MetaAttribute ma){
		this.metaAttribute = ma;
		
		setName(ma.getName());
		setTypeName(ma.getType().getName());
		
	}
	
	
	public MetaAttribute getMetaAttribute(){
		return metaAttribute;
	}


	public String getName(){
		return getAttribute("name");
	}
	
	public String getTypeName(){
		return getAttribute("type");
	}

	
	public void setName(String name){
		this.setAttribute("name", name);
	}
	
	public void setTypeName(String name){
		this.setAttribute("type", name);
	}
	
	
}


class AddMetaAttributeDialog extends ModalDialog{
	
	private TextItem name;
	private SelectItem type;
	private OptionButton saveButton;
	private DynamicForm inputContainer;
	
	
	public AddMetaAttributeDialog(List<MetaBaseType> baseTypes){
		OptionButton close = new OptionButton("close");
		close.setWidth(55);
		close.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				destroy();
			}
		});
		
		Title t = new Title("New Attribute");
		t.setWidth("*");
		
		HLayout header = new HLayout();
		header.setMembersMargin(20);
		header.addMember(t);
		header.addMember(close);
		header.setWidth100();
		header.setHeight(30);
		
		inputContainer = new DynamicForm();
		name = new TextItem();
		name.setTitle("Name");
		name.setRequired(true);
		
		type = new SelectItem();
		type.setTitle("Type");
		type.setAddUnknownValues(false);
		type.setDefaultValue((baseTypes.get(0).getName()));
		
		LinkedHashMap<String, String> typesMap = new LinkedHashMap<String, String>();
		for (MetaBaseType typ: baseTypes)
			typesMap.put(typ.getName(), typ.getName());
		type.setValueMap(typesMap);
		
		inputContainer.setFields(name, type);
		inputContainer.setWidth100();
		inputContainer.setHeight(50);
		
		
		saveButton = new OptionButton("save");
		saveButton.setWidth(50);
		
		VStack content = new VStack();
		content.addMember(header);
		content.addMember(inputContainer);
		content.addMember(saveButton);
		
		content.setWidth100();
		content.setHeight100();
		
		this.addItem(content);
		
		this.setWidth(250);
		this.setHeight(200);
		this.centerInPage();
		inputContainer.focusInItem(0);
	}
	
	@Override
	public void show(){
		inputContainer.focusInItem(0);
		super.show();
	}
	
	public String getName(){
		return name.getValueAsString();
	}
	
	public String getType(){
		return type.getValueAsString();
	}
	
	public HasClickHandlers getSaveButton(){
		return saveButton;
	}
	
}












class MetaClassPropertiesGrid extends VStack implements ClickHandler, CollaborateableElementPropertiesListener{
	
	private TextItem nameField;
	private Button iconButton;
	private CheckboxItem abstractCheckBox;
	private int cretedItems = 0;
	private MetaClass metaClass;
//	private Set<MetaModelView> handlers;
	
	public MetaClassPropertiesGrid(){
		
		setWidth100();
		this.setMembersMargin(0);
		
		DynamicForm nameForm = new DynamicForm();
		nameField = new TextItem();
		nameField.setWidth("100%");
		nameField.setHeight("100%");
		nameField.setShowTitle(false);
		nameForm.setFields(nameField);
		
		
		DynamicForm abstractForm = new DynamicForm();
		abstractCheckBox = new CheckboxItem();
		abstractCheckBox.setWidth("100%");
		abstractCheckBox.setHeight("100%");
		abstractCheckBox.setTitle("");
		abstractCheckBox.setName("");
		abstractCheckBox.setShowTitle(false);
		abstractForm.setFields(abstractCheckBox);
		
		iconButton = new Button("Set icon");
		iconButton.addClickHandler(this);
		
		
		
		this.addMember(createItem("Name:", nameForm));
		this.addMember(createItem("Abstract:", abstractForm));
		this.addMember(createItem("Icon:", iconButton));
		
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
		UploadDiaolog ud = new UploadDiaolog("Upload icon", new UploadDiaolog.SuccessfulHandler() {
			
			@Override
			public void onUploadSuccessful(String pathToUploadedFile) {
				Window.alert(pathToUploadedFile);
			}
		});
		
		ud.show();
		
	}
	
	
	public void setMetaModel(MetaClass mc){
		if (this.metaClass!=null)
			metaClass.removePropertiesListener(this);
		
		this.metaClass = mc;
		this.metaClass.addPropertiesListener(this);
		
		onChanged();
	}


	@Override
	public void onChanged() {
		nameField.setValue(metaClass.getName());
		abstractCheckBox.setValue(metaClass.isAbstract());	
	}
	
	
}





public class MetaClassDetailView extends VLayout implements ClickHandler, CollaborateableElementPropertiesListener{
	
	private MetaClass metaClass;
	private ListGrid attributesGrid;
	private MetaClassPropertiesGrid propertiesGrid;
	private Button addAttributeButton;
	private AddMetaAttributeDialog addDialog;
	private List<MetaBaseType> metaBaseTypes; 
	
	private Set<MetaModelView.MetaAttributeManipulationListener> attributeModifyListeners;
	
	public MetaClassDetailView(List<MetaBaseType> types ){
		this.metaBaseTypes = types;
		this.setWidth100();
		this.setHeight100();
		attributeModifyListeners = new LinkedHashSet<MetaModelView.MetaAttributeManipulationListener>();
	
		
		SectionStack stack  = new SectionStack();
		stack.setWidth100();
		stack.setHeight("*");
		
		propertiesGrid = new MetaClassPropertiesGrid();
		
		
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
		attributesGrid.setCanRemoveRecords(true);
		
		
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
		
		
		properties.addItem(propertiesGrid);
		attributes.addItem(attributesGrid);
		attributes.addItem(addAttributeButton);
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
		
		MetaAttributeManipulationEvent e = new MetaAttributeManipulationEvent(ManipulationType.MODIFY, metaClass);
		e.setAttribute(att);
		e.setName(name);
		e.setBaseType(t);
		
		fireAttributeModifiedEvent(e);
	}
	
	
	public MetaClass getMetaClass(){
		return metaClass;
	}
	
	
	public void setMetaClass(MetaClass m){
		if (metaClass!=null)
			metaClass.removePropertiesListener(this);
		
		this.metaClass = m;
		this.metaClass.addPropertiesListener(this);
		propertiesGrid.setMetaModel(m);
		
		onChanged();
	}


	private void fireAttributeModifiedEvent(MetaModelView.MetaAttributeManipulationListener.MetaAttributeManipulationEvent e)
	{
		for (MetaModelView.MetaAttributeManipulationListener l : attributeModifyListeners)
			l.onMetaAttributeManipulated(e);
	}
	
	

	@Override
	public void onClick(ClickEvent event) {
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
				
				if (!metaClass.isAttributeNameAvailable(addDialog.getName()))
				{
					NotificationManager.getInstance().showTipNotification(new TipNotification("Attribute with this name already exists", null, 2000, NotificationPosition.CENTER));
					return;
				}
				
				
				MetaAttributeManipulationEvent e = new MetaAttributeManipulationEvent(ManipulationType.NEW, metaClass);
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
		AttributeRecord attributes[] = new AttributeRecord[metaClass.getAttributes().size()];
		for (int i =0; i<metaClass.getAttributes().size(); i++){
			MetaAttribute a = metaClass.getAttributes().get(i);
			attributes[i]=new AttributeRecord(a);
		}
		attributesGrid.setRecords(attributes);
		
	}
	
	
	
}