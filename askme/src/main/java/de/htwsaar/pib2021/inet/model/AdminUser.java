package de.htwsaar.pib2021.inet.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "admin_user")
public class AdminUser {

	@EmbeddedId
	private AdminUserId adminUserId;

	public AdminUserId getAdminUserId() {
		return adminUserId;
	}

	public void setAdminUserId(AdminUserId adminUserId) {
		this.adminUserId = adminUserId;
	}

}
