package disco_bracelet.mappers;

import disco_bracelet.enteties.DrinkEntity;
import disco_bracelet.enteties.dtoes.DrinkDTO;

public class DrinkMapper {

	public static DrinkDTO toDTO(DrinkEntity drink) {
		if (drink == null) {
			return null;
		}

		return new DrinkDTO(drink.getId(), drink.getName(), drink.getPrice(), null);

	}

}
