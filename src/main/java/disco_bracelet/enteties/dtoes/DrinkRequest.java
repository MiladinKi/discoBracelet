package disco_bracelet.enteties.dtoes;



public class DrinkRequest {
	private Integer drinkId;
	private Integer quantity;

	public DrinkRequest() {
		super();
	}

	public DrinkRequest(Integer drinkId, Integer quantity) {
		super();
		this.drinkId = drinkId;
		this.quantity = quantity;
	}

	public Integer getDrinkId() {
		return drinkId;
	}

	public void setDrinkId(Integer drinkId) {
		this.drinkId = drinkId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
