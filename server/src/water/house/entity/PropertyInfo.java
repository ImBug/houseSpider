package water.house.entity;

import java.util.HashMap;
import java.util.Map;

public class PropertyInfo {
	
	protected static final Map<String, String> PropertyMap = new HashMap<>(20);
	
	static{
		PropertyMap.put("房屋户型", "bedroom");
		PropertyMap.put("建筑面积", "squre");
		PropertyMap.put("套内面积", "insqure");
		PropertyMap.put("建筑类型", "style");
		PropertyMap.put("产权年限", "ownyear");
		PropertyMap.put("所在楼层", "floor");
		PropertyMap.put("梯户比例", "liftrate");
		PropertyMap.put("装修情况", "decorate");
		PropertyMap.put("房屋朝向", "headfor");
		PropertyMap.put("挂牌时间", "ontime");
		PropertyMap.put("抵押信息", "mortgage");
		PropertyMap.put("产权所属", "share");
		PropertyMap.put("交易权属", "ownertype");
		PropertyMap.put("户型介绍", "descr");
		PropertyMap.put("权属抵押", "cause");
	}

	public static String getProperty(String cnInfo){
		return PropertyMap.get(cnInfo);
	}
}
