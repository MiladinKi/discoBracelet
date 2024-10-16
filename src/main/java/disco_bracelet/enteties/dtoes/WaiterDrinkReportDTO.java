package disco_bracelet.enteties.dtoes;

public class WaiterDrinkReportDTO {
	private Integer drinkId;
	private String drinkName;
	private Long quantity;
	private Double totalValue;

	public WaiterDrinkReportDTO() {
		super();
	}

	public WaiterDrinkReportDTO(Integer drinkId, String drinkName, Long quantity, Double totalValue) {
		super();
		this.drinkId = drinkId;
		this.drinkName = drinkName;
		this.quantity = quantity;
		this.totalValue = totalValue;
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

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(Double totalValue) {
		this.totalValue = totalValue;
	}

}
