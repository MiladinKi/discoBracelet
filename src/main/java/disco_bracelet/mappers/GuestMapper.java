package disco_bracelet.mappers;

import disco_bracelet.enteties.BraceletEntity;
import disco_bracelet.enteties.GuestEntity;
import disco_bracelet.enteties.dtoes.BraceletDTO;
import disco_bracelet.enteties.dtoes.GuestDTO;

public class GuestMapper {

	public static GuestDTO toDTO(GuestEntity guest) {
		GuestDTO guestdto = new GuestDTO();
		guestdto.setId(guest.getId()); // Dodaj ovo mapiranje
		guestdto.setName(guest.getName());
		guestdto.setLastname(guest.getLastname());
		guestdto.setIdDocument(guest.getIdDocument());
		guestdto.setPhoneNumber(guest.getPhoneNumber());

		if (guest.getBracelet() != null) {
			BraceletDTO braceletDTO = new BraceletDTO();
			braceletDTO.setId(guest.getBracelet().getId());
			braceletDTO.setManufacturer(guest.getBracelet().getManufacturer());
			braceletDTO.setYearOfProduction(guest.getBracelet().getYearOfProduction());
			guestdto.setBracelet(braceletDTO);
		}

		return guestdto;
	}

	public static GuestEntity toEntity(GuestDTO guestDTO) {
		GuestEntity guestEntity = new GuestEntity();
		guestEntity.setId(guestDTO.getId()); // Dodaj ovo mapiranje
		guestEntity.setName(guestDTO.getName());
		guestEntity.setLastname(guestDTO.getLastname());
		guestEntity.setIdDocument(guestDTO.getIdDocument());
		guestEntity.setPhoneNumber(guestDTO.getPhoneNumber());

		if (guestDTO.getBracelet() != null) {
			BraceletEntity braceletEntity = new BraceletEntity();
			braceletEntity.setId(guestDTO.getBracelet().getId());
			braceletEntity.setManufacturer(guestDTO.getBracelet().getManufacturer());
			braceletEntity.setYearOfProduction(guestDTO.getBracelet().getYearOfProduction());
			guestEntity.setBracelet(braceletEntity);
		}

		return guestEntity;
	}
}
