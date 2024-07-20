package disco_bracelet.enteties.dtoes;

public class BraceletDrinkDTO {
	private Integer drinkId;
	private String drinkName;
	private Double drinkPrice;
	private Integer quantity = 1;
	private Integer braceletId;

	public BraceletDrinkDTO() {
	}

	public BraceletDrinkDTO(Integer drinkId, String drinkName, Double drinkPrice, Integer quantity,
			Integer braceletId) {
		super();
		this.drinkId = drinkId;
		this.drinkName = drinkName;
		this.drinkPrice = drinkPrice;
		this.quantity = quantity;
		this.braceletId = braceletId;
	}

	public Integer getDrinkId() {
		return drinkId;
	}

	public void setDrinkId(Integer drinkId) {
		this.drinkId = drinkId;
	}

	public String getDrinkName() {
		return drinkName;
	}

	public void setDrinkName(String drinkName) {
		this.drinkName = drinkName;
	}

	public Double getDrinkPrice() {
		return drinkPrice;
	}

	public void setDrinkPrice(Double drinkPrice) {
		this.drinkPrice = drinkPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getBraceletId() {
		return braceletId;
	}

	public void setBraceletId(Integer braceletId) {
		this.braceletId = braceletId;
	}

}
