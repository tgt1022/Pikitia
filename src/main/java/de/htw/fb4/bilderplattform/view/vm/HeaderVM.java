package de.htw.fb4.bilderplattform.view.vm;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.business.ISearchService;
import de.htw.fb4.bilderplattform.dao.Image;
import de.htw.fb4.bilderplattform.spring.SpringPropertiesUtil;

/************************************************
 * <p>Header functionality trigger: logout, search</p>
 * <p>
 * @author Josch Rossa
 * </p>
 * <p>
 * 28.11.2012
 * </p>
 ************************************************/
public class HeaderVM {
	
	private String search;
	
	public void setSearch(String search) {
		this.search = search;
	}
	
	public String getLogLabel() {
		if(BusinessCtx.getInstance().getUserService().isAUserAuthenticated()) {
			return SpringPropertiesUtil.getProperty("lbl.logout");
		}
		return SpringPropertiesUtil.getProperty("lbl.login");
	}
	
	public String getLogPath() {
		if(BusinessCtx.getInstance().getUserService().isAUserAuthenticated()) {
			return "/j_spring_security_logout";
		}
		return "./login.zul";
	}
	
	public String getSearch() {
		return search;
	}
	
	public boolean isAUserAuthenticated(){
		return BusinessCtx.getInstance().getUserService().isAUserAuthenticated();
	}
	
	@Command
	public void search() {
		ISearchService searchService = BusinessCtx.getInstance().getSearchService();
		for(Image img : searchService.searchImages(search)) {
			System.out.println("FOUND: " + img.getFile());
		}

	}
	
	/* show cart*/
	
	@Command
	public void showCart() {
		Executions.getCurrent().sendRedirect("/cart.zul");
	}
	
	/* Logo click */
	
	@Command
	public void returnHome() {
		Executions.getCurrent().sendRedirect("/index.zul");
	}
	
	/* show gallery for buying an image*/
	
	@Command
	public void showGallery() {
		Executions.getCurrent().sendRedirect("/gallery.zul");
	}
	
	/* User Menu*/
	
	@Command
	public void editUser() {
		Executions.getCurrent().sendRedirect("/user/userUpdate2.zul");
	}
	
	@Command
	public void addImage() {
		Executions.getCurrent().sendRedirect("/user/addImage.zul");
	}
	
	@Command
	public void showMessageList() {
		Executions.createComponents("/user/messageList.zul", null, null);
	}
	
	/* Admin Menu */
	
	@Command
	public void redirectToUserList() {
		Executions.getCurrent().sendRedirect("/admin/userList.zul");
	}
	
	/* for testing, should be deleted when not needed anymore*/
	
	@Command
	public void purchase() {
		Executions.getCurrent().sendRedirect("/bilderplattform/purchase.zul");
	}
	
	@Command
	public void contact() {
		Sessions.getCurrent().setAttribute("receiver_idUser", "2");
		Executions.getCurrent().sendRedirect("/contactForm.zul");
	}
}




