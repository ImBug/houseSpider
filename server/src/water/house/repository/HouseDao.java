package water.house.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import water.house.entity.HouseInfo;

public interface HouseDao extends PagingAndSortingRepository<HouseInfo, String> {
	
	HouseInfo findById(String id);

}
