package water.house.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import water.house.entity.HouseInfo;
import water.house.logic.HouseLogic;
import water.tool.json.CustomObjectMapper;

@RestController
@RequestMapping("/v2/house")
public class HouseService {

	@Autowired
	private HouseLogic houseLogic;
	
	@RequestMapping("/say")
	public String sayGood(){
		return "xxxx";
	}
	
	@RequestMapping(value = "/queryHouse",produces = { "application/json" },  method = RequestMethod.GET)
	public String getAllHouse(){
		Page<HouseInfo> houses = houseLogic.findAllHouse(null);
		return CustomObjectMapper.encodeJson(houses);
	}
}
