package org.gemsjax.client.admin.view.grids;

import java.util.LinkedHashMap;
import java.util.List;

import org.gemsjax.client.admin.widgets.ModalDialog;
import org.gemsjax.client.admin.widgets.OptionButton;
import org.gemsjax.client.admin.widgets.Title;
import org.gemsjax.shared.metamodel.MetaBaseType;

import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyUpEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyUpHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VStack;

public class AddMetaAttributeDialog extends ModalDialog{
	
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
		name.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getKeyName().equals("Enter"))
						saveButton.fireEvent(new ClickEvent(saveButton.getJsObj()));
			}
		});
		
		type = new SelectItem();
		type.setTitle("Type");
		type.setAddUnknownValues(false);
		type.setDefaultValue((baseTypes.get(0).getName()));
		type.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getKeyName().equals("Enter"))
						saveButton.fireEvent(new ClickEvent(saveButton.getJsObj()));
			}
		});
		
		LinkedHashMap<String, String> typesMap = new LinkedHashMap<String, String>();
		for (MetaBaseType typ: baseTypes)
			typesMap.put(typ.getName(), typ.getName());
		type.setValueMap(typesMap);
		
		inputContainer.setFields(name, type);
		inputContainer.setWidth100();
		inputContainer.setHeight(50);
		inputContainer.focus();
		
		
		
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
