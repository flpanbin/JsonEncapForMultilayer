# JsonEncapForMultilayer
## 一个用于将多层结构的数据封装成Json字符串的Demo.借助Gson实现。
### 使用场景：<br>
需要多个层级展示，类似于树形结构。<br>
比如菜单展示，第一层是一级菜单，一级菜单下面有二级菜单，二级下有三级菜单...<br>
比如机构展示，第一层是省级机构，省级机构下有市级机构，市级下有区县...<br>

### 类说明：
#### 1.BaseModel
该类是所有Model的基类，需实现JsonEncapCondition接口。其中属性包括
    
    
    private String id;    //当前Model的id
    private String upId;  //上级id
    private List<BaseModel> subModels;   //该Model下的子Model集合
重写了equals方法：
    
    	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return this.id.equals(((BaseModel) obj).getId());
	}
  
#### 2.JsonEncapCondition
该接口类只有一个方法：
    
    public boolean condition();//用于设置判断顶级Model的条件
#### 3.JsonEncap
该类封装了将List集合转换为Json字符串的方法。
    其中关键代码是是toJson()方法
        
    private List<? extends BaseModel> modelList;
    public JsonEncap(List<? extends BaseModel> modelList) {
		super();
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
  ### 使用方法：
  1. 创建的Model继承BaseModel.
  2. 实现JsonEncapCondition接口，实现condition方法。
  3. 创建JsonEncap，调用toJson()方法生成Json 字符串
  ### 测试用例：
      
      Organize orgInfo;
		// 生成测试数据
		List<Organize> lists = new ArrayList<Organize>();
		// 添加一级Model
		for (int i = 1; i < 4; i++) {
			orgInfo = new Organize("00" + i, "机构A00" + i, "00" + i);
			lists.add(orgInfo);
		}
		// 添加二级Model
		for (int i = 1; i < 4; i++) {
			orgInfo = new Organize("00100" + i, "机构A00100" + i, "001");
			lists.add(orgInfo);
			orgInfo = new Organize("00200" + i, "机构A00200" + i, "002");
			lists.add(orgInfo);
			orgInfo = new Organize("00300" + i, "机构A00300" + i, "003");
			lists.add(orgInfo);
		}
		// 添加三级Model
		for (int i = 1; i < 4; i++) {
			orgInfo = new Organize("00100200" + i, "机构A00100200" + i, "001002");
			lists.add(orgInfo);
			orgInfo = new Organize("00100300" + i, "机构A00100300" + i, "001003");
			lists.add(orgInfo);
			orgInfo = new Organize("00200100" + i, "机构A00200100" + i, "002001");
			lists.add(orgInfo);
		}

		JsonEncap jsonEncap = new JsonEncap(lists);

		String json = jsonEncap.toJson();
		System.out.println("json--->" + json);
  #### json输出结果：
      
      [{"orgName":"机构A001","id":"001","upId":"001","subModels":[{"orgName":"机构A001001","id":"001001","upId":"001"},{"orgName":"机构A001002","id":"001002","upId":"001","subModels":[{"orgName":"机构A001002001","id":"001002001","upId":"001002"},{"orgName":"机构A001002002","id":"001002002","upId":"001002"},{"orgName":"机构A001002003","id":"001002003","upId":"001002"}]},{"orgName":"机构A001003","id":"001003","upId":"001","subModels":[{"orgName":"机构A001003001","id":"001003001","upId":"001003"},{"orgName":"机构A001003002","id":"001003002","upId":"001003"},{"orgName":"机构A001003003","id":"001003003","upId":"001003"}]}]},{"orgName":"机构A002","id":"002","upId":"002","subModels":[{"orgName":"机构A002001","id":"002001","upId":"002","subModels":[{"orgName":"机构A002001001","id":"002001001","upId":"002001"},{"orgName":"机构A002001002","id":"002001002","upId":"002001"},{"orgName":"机构A002001003","id":"002001003","upId":"002001"}]},{"orgName":"机构A002002","id":"002002","upId":"002"},{"orgName":"机构A002003","id":"002003","upId":"002"}]},{"orgName":"机构A003","id":"003","upId":"003","subModels":[{"orgName":"机构A003001","id":"003001","upId":"003"},{"orgName":"机构A003002","id":"003002","upId":"003"},{"orgName":"机构A003003","id":"003003","upId":"003"}]}]
