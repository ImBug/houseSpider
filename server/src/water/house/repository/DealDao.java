package water.house.repository;

import org.springframework.data.repository.CrudRepository;

import water.house.entity.DealInfo;

public interface DealDao extends CrudRepository<DealInfo, Long> {

}
