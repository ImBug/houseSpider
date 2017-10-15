package water.house.logic.imp;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import water.house.entity.DealInfo;
import water.house.entity.HouseInfo;
import water.house.logic.HouseLogic;
import water.house.repository.DealDao;
import water.house.repository.HouseDao;

@Component
public class HouseLogicImp implements HouseLogic {
	
	@Autowired
	private HouseDao housedao;
	@Autowired
	private DealDao dealDao;
	
	@Override
	public void saveHouse(HouseInfo house) {
		housedao.save(house);
		DealInfo dealInfo = house.getDealInfo();
		if(dealInfo != null){
			List list=dealDao.findByHourseIdAndPrice(dealInfo.getHourseId(), dealInfo.getPrice());
			if(list.isEmpty()){
				dealInfo.setRecordTime(new Date());
				dealDao.save(dealInfo);
			}
		}
	}

	@Override
	public HouseInfo loadHouse(String id) {
		return housedao.findById(id);
	}

	@Override
	public Page<HouseInfo> findAllHouse(Pageable page) {
		return housedao.findAll(page);
	}

	@Override
	public List<DealInfo> findDealByHouseId(String houseId) {
		return dealDao.findByHourseId(houseId);
	}

	@Override
	public Page<DealInfo> findAllDeal(Pageable page) {
		return dealDao.findAll(page);
	}

	@Override
	public Iterable<DealInfo> findAllDeal() {
		return dealDao.findAll();
	}

}
