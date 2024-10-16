package disco_bracelet.enteties.dtoes;

import java.time.LocalDate;
import java.util.List;

public class BraceletDTO {
	private Integer id;
	private String manufacturer;
	private LocalDate yearOfProduction;
	private List<DrinkDTO> drinks;

	public BraceletDTO() {
		super();
	}

	public BraceletDTO(Integer id, String manufacturer, LocalDate yearOfProduction, List<DrinkDTO> drinks) {
		super();
		this.id = id;
		this.manufacturer = manufacturer;
		this.yearOfProduction = yearOfProduction;
		this.drinks = drinks;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public LocalDate getYearOfProduction() {
		return yearOfProduction;
	}

	public void setYearOfProduction(LocalDate yearOfProduction) {
		this.yearOfProduction = yearOfProduction;
	}

	public List<DrinkDTO> getDrinks() {
		return drinks;
	}

	public void setDrinks(List<DrinkDTO> drinks) {
		this.drinks = drinks;
	}

}
