package de.htw.fb4.bilderplattform.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/************************************************
 * <p>
 * user database object
 * </p>
 * 
 * <p>
 * 
 * @author Josch Rossa
 * @author Wojciech Konitzer
 *         </p>
 *         <p>
 *         02.11.2012
 *         </p>
 ************************************************/
@Entity
@Table(name = "User", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }) })
public class User implements Serializable, UserDetails {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idUser")
	private Integer idUser;

	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "isNormalUser")
	private boolean isNormalUser = false;

	@Column(name = "isAdmin")
	private boolean isAdmin = false;

	@Column(name = "lastUpdateDate")
	private Date lastUpdateDate = new Date();

	@Column(name = "isDeleted", nullable = false, columnDefinition = "tinyint(1) default 0")
	private boolean isDeleted = false;
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.REMOVE})
	private List<Image> images = new ArrayList<Image>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy="user", cascade = {CascadeType.REMOVE})
	private List<UserPurchase> userPurchase = new ArrayList<UserPurchase>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="receiver", cascade = {CascadeType.REMOVE})
	private List<Message> messages = new ArrayList<Message>();
/*
	@OneToOne(cascade = {CascadeType.REMOVE})
	private Bankaccount bankaccount;
*/
	@OneToOne(fetch = FetchType.LAZY, mappedBy="bankaccountOwner", cascade = {CascadeType.ALL})
	private Bankaccount bankaccount;
	
	public User() {
		super();
	}

	public User(String username, String password, String email) {
		super();
		this.username = username;
//		this.password = encryptPassword(password);
		this.password = password;
		this.email = email;
	}

	public User(String username, String password, String email,
			boolean isNormalUser, boolean isAdmin, boolean isDeleted) {
		super();
		this.username = username;
//		this.password = encryptPassword(password);
		this.password = password;
		this.email = email;
		this.isNormalUser = isNormalUser;
		this.isAdmin = isAdmin;
	}

	/*
	 * Methods
	 */
	public Integer getIdUser() {
		return idUser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
//		this.password = encryptPassword(password);
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getIsNormalUser() {
		return isNormalUser;
	}

	public void setIsNormalUser(boolean isNormalUser) {
		this.isNormalUser = isNormalUser;
	}

	public boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate=lastUpdateDate;
	}
	
	public boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}
	
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
	public List<Message> getMessages() {
		return messages;
	}
	
	public List<UserPurchase> getUserPurchase() {
		return userPurchase;
	}
	
	public void setUserPurchase(List<UserPurchase> userPurchase) {
		this.userPurchase = userPurchase;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		if (isAdmin)
			authorities.add(AppAuthorities.getAdminAuthority());
		if (isNormalUser)
			authorities.add(AppAuthorities.getUserAuthority());
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	private static class AppAuthorities {

		public static GrantedAuthority getAdminAuthority() {
			return new GrantedAuthority() {
				private static final long serialVersionUID = 1L;

				@Override
				public String getAuthority() {
					return "ROLE_ADMIN";
				}
			};
		}

		public static GrantedAuthority getUserAuthority() {
			return new GrantedAuthority() {
				private static final long serialVersionUID = 1L;

				@Override
				public String getAuthority() {
					return "ROLE_USER";
				}
			};
		}
	}
	
	private String encryptPassword(String password) {
		MessageDigest md5;
		String hashValue = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.update(password.getBytes());
			BigInteger hash = new BigInteger(1, md5.digest());
			hashValue = hash.toString(16);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hashValue;
	}
}
