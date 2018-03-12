package com.pb.demo;

public class Organize extends BaseModel {

	private String orgName;

	public String getOrgName() {
		return orgName;
	}

	public void setExamName(String examName) {
		this.orgName = examName;
	}

	public Organize(String examName) {
		super();
		this.orgName = examName;
	}

	public Organize() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Organize(String orgId, String upOrgId) {
		super(orgId, upOrgId);
		// TODO Auto-generated constructor stub
	}

	public Organize(String orgId, String name, String upOrgId) {
		super(orgId, upOrgId);
		// TODO Auto-generated constructor stub
		this.orgName = name;
	}

	@Override
	public boolean condition() {
		// TODO Auto-generated method stub
		return (getId().equals(getUpId()));
	}

}
