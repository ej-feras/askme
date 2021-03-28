package de.htwsaar.pib2021.inet.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "test_access")
public class TestAccess {

	@EmbeddedId
	private TestAccessId testAccessId;
	@Column(columnDefinition = "boolean default false")
	private boolean access;

	public TestAccessId getTestAccessId() {
		return testAccessId;
	}

	public void setTestAccessId(TestAccessId testAccessId) {
		this.testAccessId = testAccessId;
	}

	public boolean isAccess() {
		return access;
	}

	public void setAccess(boolean access) {
		this.access = access;
	}

}
