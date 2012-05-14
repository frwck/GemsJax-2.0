package org.gemsjax.client.admin.view.grids;

import java.util.LinkedHashMap;
import java.util.List;

import org.gemsjax.client.admin.widgets.ModalDialog;
import org.gemsjax.client.admin.widgets.OptionButton;
import org.gemsjax.client.admin.widgets.Title;
import org.gemsjax.shared.metamodel.MetaAttribute;
import org.gemsjax.shared.metamodel.MetaBaseType;
import org.gemsjax.shared.metamodel.MetaClass;

import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.EditCompleteEvent;
import com.smartgwt.client.widgets.grid.events.EditCompleteHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VStack;


class AttributeRecord extends ListGridRecord{
	
	private MetaAttribute metaAttribute;
	
	public AttributeRecord(MetaAttribute ma){
		this.metaAttribute = ma;
		
		this.setAttribute("name", ma.getName());
		this.setAttribute("type", ma.getType().getName());
	}
	
	
	public MetaAttribute getMetaAttribute(){
		return metaAttribute;
	}
	
}


class AddMetaAttributeDialog extends ModalDialog{
	
	private TextItem name;
	private SelectItem type;
	private OptionButton saveButton;
	
	
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
		
		DynamicForm inputContainer = new DynamicForm();
		name = new TextItem();
		name.setTitle("Name");
		name.setRequired(true);
		
		type = new SelectItem();
		type.setTitle("Type");
		type.setAddUnknownValues(false);
		
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
		
		this.setWidth(250);
		this.setHeight(200);
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



public class MetaClassDetailView extends HLayout implements ClickHandler{
	
	private MetaClass metaClass;
	private ListGrid attributesGrid;
	private ListGrid properiesGrid;
	private Button addAttributeButton;
	private AddMetaAttributeDialog addDialog;
	private List<MetaBaseType> metaBaseTypes; 
	
	public MetaClassDetailView(List<MetaBaseType> types ){
		this.metaBaseTypes = types;
		this.setWidth100();
		this.setHeight100();
	
		
		SectionStack stack  = new SectionStack();
		stack.setWidth100();
		stack.setHeight("*");
		
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
		attributesGrid.addEditCompleteHandler(new EditCompleteHandler() {
			
			@Override
			public void onEditComplete(EditCompleteEvent event) {
				
				onAttributeEdited(event);
			}
		});
		
		
		properties.addItem(properiesGrid);
		attributes.addItem(attributesGrid);
		stack.addSection(properties);
		stack.addSection(attributes);
		
		
		
		addAttributeButton = new Button("Add Attribute");
		addAttributeButton.addClickHandler(this);
		addAttributeButton.setWidth100();
		addAttributeButton.setHeight(25);
		
		
		this.addMember(stack);
		this.addMember(addAttributeButton);
		
		
	}
	
	
	private void onAttributeEdited(EditCompleteEvent event){
		
	}
	
	
	public MetaClass getMetaClass(){
		return metaClass;
	}
	
	
	public void setMetaClass(MetaClass m){
		this.metaClass = m;
		// TODO properties
		
		
		// Attributes
		AttributeRecord attributes[] = new AttributeRecord[m.getAttributes().size()];
		for (int i =0; i<m.getAttributes().size(); i++){
			MetaAttribute a = m.getAttributes().get(i);
			attributes[i]=new AttributeRecord(a);
		}
		attributesGrid.setRecords(attributes);
	}



	@Override
	public void onClick(ClickEvent event) {
		if (event.getSource() == addAttributeButton){
			this.addDialog = new AddMetaAttributeDialog(metaBaseTypes);
			addDialog.animateShow(AnimationEffect.FADE);
		}
		else
		if (addDialog!=null && addDialog.getSaveButton() == event.getSource()){
			// Add a new 
		}
		
	}
	
}