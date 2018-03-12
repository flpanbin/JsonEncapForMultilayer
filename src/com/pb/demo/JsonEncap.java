package com.pb.demo;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class JsonEncap {

	private List<? extends BaseModel> modelList;

	public List<? extends BaseModel> getModelList() {
		return modelList;
	}

	public JsonEncap(List<? extends BaseModel> modelList) {
		super();
		this.modelList = modelList;
	}

	public JsonEncap() {
		super();
	}

	public void setModelList(List<? extends BaseModel> modelList) {
		this.modelList = modelList;
	}

	public String toJson() {
		List<BaseModel> removeModels = new ArrayList<BaseModel>();
		for (int i = 0; i < modelList.size(); i++) {
			BaseModel tempModel = modelList.get(i);

			// 如果是一级Model，则不处理
			if (tempModel.condition())
				continue;
			// 找到上级Model
			String upIndexId = tempModel.getUpId();
			BaseModel upTempBaseMode = new BaseModel(upIndexId);
			BaseModel upModel = modelList
					.get(modelList.indexOf(upTempBaseMode));

			// 获取上级Model下的下级Model集合
			List<BaseModel> subBaseModels = upModel.getSubModels();
			if (subBaseModels == null) {
				subBaseModels = new ArrayList<BaseModel>();
			}
			// 将当前Model添加到下级Model中
			subBaseModels.add(tempModel);
			upModel.setSubModels(subBaseModels);
			// 处理完的Model添加到需要移除的Model集合中
			removeModels.add(tempModel);
		}
		// 移除之前保存的Model
		for (BaseModel i : removeModels) {
			modelList.remove(i);
		}

		// 使用Gson 将List转换为json String
		Gson gson = new Gson();
		String jsonString = gson.toJson(modelList);
		return jsonString;
	}

}
