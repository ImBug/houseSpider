package water.house.repository;

import org.springframework.data.repository.CrudRepository;

import water.house.entity.HouseInfo;

public interface HouseDao extends CrudRepository<HouseInfo, String> {

}
