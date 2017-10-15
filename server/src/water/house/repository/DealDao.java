package water.house.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import water.house.entity.DealInfo;

public interface DealDao extends PagingAndSortingRepository<DealInfo, Long> {
	
	List<DealInfo> findByHourseId(String houseId);
	
	List<DealInfo> findByHourseIdAndPrice(String houseId,String price);
	
}
