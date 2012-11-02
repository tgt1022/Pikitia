package de.htw.fb4.bilderplattform.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/************************************************
 * <p>user database object</p>
 * 
 * <p>
 * @author Josch Rossa
 * </p>
 * <p>
 * 02.11.2012
 * </p>
 ************************************************/
@Entity
@Table(name="User", uniqueConstraints = {@UniqueConstraint(columnNames={"username"})})
public class User implements Serializable, UserDetails {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idUser")
	private Integer idUser;

	@Column(name="username", nullable=false)
	private String username;
	
	@Column(name="password", nullable=false)
	private String password;
	
	@Column(name="isNormalUser", nullable=false)
	private boolean isNormalUser;
	
	@Column(name="isAdmin", nullable=false)
	private boolean isAdmin;
	
	@Column(name="lastUpdateDate", nullable=false)
	private Date lastUpdateDate;
	
	@Column(name="isDeleted", nullable=false, columnDefinition ="tinyint(1) default 0")
	private boolean isDeleted;
	
	
	public User(){
		super();
	}
	
	public User(String username, String password,
			boolean isNormalUser, boolean isAdmin, Date lastUpdateDate,
			boolean isDeleted) {
		super();
		this.username = username;
		this.password = password;
		this.isNormalUser = isNormalUser;
		this.isAdmin = isAdmin;
		this.lastUpdateDate = lastUpdateDate;
		this.isDeleted = isDeleted;
	}

	/* 
	 * Methods
	 * 
	 */
	
	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
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
		this.password = password;
	}

	public boolean isNormalUser() {
		return isNormalUser;
	}

	public void setNormalUser(boolean isNormalUser) {
		this.isNormalUser = isNormalUser;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		if(isAdmin)
			authorities.add(AppAuthorities.getAdminAuthority());
		if(isNormalUser)
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
	
}
