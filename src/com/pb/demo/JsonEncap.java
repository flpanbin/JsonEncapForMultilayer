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

			// �����һ��Model���򲻴���
			if (tempModel.condition())
				continue;
			// �ҵ��ϼ�Model
			String upIndexId = tempModel.getUpId();
			BaseModel upTempBaseMode = new BaseModel(upIndexId);
			BaseModel upModel = modelList
					.get(modelList.indexOf(upTempBaseMode));

			// ��ȡ�ϼ�Model�µ��¼�Model����
			List<BaseModel> subBaseModels = upModel.getSubModels();
			if (subBaseModels == null) {
				subBaseModels = new ArrayList<BaseModel>();
			}
			// ����ǰModel��ӵ��¼�Model��
			subBaseModels.add(tempModel);
			upModel.setSubModels(subBaseModels);
			// �������Model��ӵ���Ҫ�Ƴ���Model������
			removeModels.add(tempModel);
		}
		// �Ƴ�֮ǰ�����Model
		for (BaseModel i : removeModels) {
			modelList.remove(i);
		}

		// ʹ��Gson ��Listת��Ϊjson String
		Gson gson = new Gson();
		String jsonString = gson.toJson(modelList);
		return jsonString;
	}

}
