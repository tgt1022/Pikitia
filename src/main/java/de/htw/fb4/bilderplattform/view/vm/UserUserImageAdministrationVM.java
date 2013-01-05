package de.htw.fb4.bilderplattform.view.vm;

import java.util.HashMap;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;

import de.htw.fb4.bilderplattform.dao.Image;

/************************************************
 * <p>VM to the userImageList.zul in folder user</p>
 * <p>
 * @author Josch Rossa
 * </p>
 * <p>
 * 26.12.2012
 * </p>
 ************************************************/
public class UserUserImageAdministrationVM extends AbstractUserImageAdministrationVM {

	@Init
	public void init() {
		super.init();
	}
	
	@Command
	public void editImage(@BindingParam("image") final Image image) {
		final HashMap<String, Object> sessionMap = new HashMap<String, Object>();
		sessionMap.put("image", image);
		Executions.createComponents("/user/editImage.zul", null, sessionMap);
	}
	
	@Command
	public void imageComments(@BindingParam("image") final Image image) {
		final HashMap<String, Object> sessionMap = new HashMap<String, Object>();
		sessionMap.put("idImage", image.getIdImage());
		//TODO: realize edit comments
//		Executions.createComponents("/user/comments.zul", null, sessionMap);
	}
	
	
}
