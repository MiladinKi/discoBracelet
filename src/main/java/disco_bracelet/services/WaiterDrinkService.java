package disco_bracelet.services;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import disco_bracelet.enteties.dtoes.WaiterDrinkReportDTO;
import disco_bracelet.repositories.WaiterDrinkRepository;
import disco_bracelet.enteties.WaiterDrinkEntity;

@Service
public class WaiterDrinkService {

	@Autowired
	private WaiterDrinkRepository waiterDrinkRepository;

	public List<WaiterDrinkEntity> getDrinksByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
		return waiterDrinkRepository.findDrinksByDateRange(startDate, endDate);
	}


	
	public List<WaiterDrinkEntity> getDrinksByWaiterAndDateRange(Integer waiterId, LocalDateTime startDate, LocalDateTime endDate) {
	    return waiterDrinkRepository.findByWaiterIdAndCreatedAtBetween(waiterId, startDate, endDate);
	}

}
