package disco_bracelet.mappers;

import java.util.List;
import java.util.stream.Collectors;

import disco_bracelet.enteties.BraceletEntity;
import disco_bracelet.enteties.dtoes.BraceletDTO;
import disco_bracelet.enteties.dtoes.DrinkDTO;

public class BraceletMapper {
	
	 public static BraceletDTO toDTO(BraceletEntity bracelet) {
	        if (bracelet == null) {
	            return null;
	        }

	        List<DrinkDTO> drinkDTOs = bracelet.getDrinks().stream()
	            .map(DrinkMapper::toDTO)
	            .collect(Collectors.toList());

	        return new BraceletDTO(
	            bracelet.getId(),
	            bracelet.getManufacturer(),
	            bracelet.getYearOfProduction(),
	            drinkDTOs
	        );
	    }

}
