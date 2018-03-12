package com.pb.demo;

import java.util.List;

public class BaseModel implements JsonEncapCondition {

	private String id;
	private String upId;
	private List<BaseModel> subModels;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUpId() {
		return upId;
	}

	public List<BaseModel> getSubModels() {
		return subModels;
	}

	public void setSubModels(List<BaseModel> subModels) {
		this.subModels = subModels;
	}

	public BaseModel() {
		super();
	}

	public BaseModel(String id) {
		super();
		this.id = id;
	}

	public BaseModel(String id, String upId) {
		super();
		this.id = id;
		this.upId = upId;
	}

	public void setUpIndexId(String upId) {
		this.upId = upId;
	}

	@Override
	public boolean condition() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return this.id.equals(((BaseModel) obj).getId());
	}
}
