package water.house.logic;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import water.house.entity.DealInfo;
import water.house.entity.HouseInfo;

public interface HouseLogic {
	
	void saveHouse(HouseInfo house);
	HouseInfo loadHouse(String id);
	Page<HouseInfo> findAllHouse(Pageable page);
	
	List<DealInfo> findDealByHouseId(String houseId);
	Page<DealInfo> findAllDeal(Pageable page);
	Iterable<DealInfo> findAllDeal();
}
