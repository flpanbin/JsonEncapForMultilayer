package com.pb.demo;

import java.util.ArrayList;
import java.util.List;

public class Test {
	public static void main(String[] args) {
		testJson();
	}

	public static void testJson() {
		Organize orgInfo;

		// ���ɲ�������
		List<Organize> lists = new ArrayList<Organize>();
		// ���һ��Model
		for (int i = 1; i < 4; i++) {
			orgInfo = new Organize("00" + i, "����A00" + i, "00" + i);
			lists.add(orgInfo);
		}
		// ��Ӷ���Model
		for (int i = 1; i < 5; i++) {
			orgInfo = new Organize("00100" + i, "����A00100" + i, "001");
			lists.add(orgInfo);
			orgInfo = new Organize("00200" + i, "����A00200" + i, "002");
			lists.add(orgInfo);
			orgInfo = new Organize("00300" + i, "����A00300" + i, "003");
			lists.add(orgInfo);
		}
		// �������Model
		for (int i = 1; i < 4; i++) {
			orgInfo = new Organize("00100200" + i, "����A00100200" + i, "001002");
			lists.add(orgInfo);
			orgInfo = new Organize("00100300" + i, "����A00100300" + i, "001003");
			lists.add(orgInfo);
			orgInfo = new Organize("00200100" + i, "����A00200100" + i, "002001");
			lists.add(orgInfo);
		}

		JsonEncap jsonEncap = new JsonEncap(lists);

		String json = jsonEncap.toJson();
		System.out.println("json--->" + json);
	}
}
