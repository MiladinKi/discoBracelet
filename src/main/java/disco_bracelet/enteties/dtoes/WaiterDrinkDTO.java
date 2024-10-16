package disco_bracelet.enteties.dtoes;

import java.time.LocalDateTime;

public class WaiterDrinkDTO {

    private Long id;
    private String waiterName;
    private String drinkName;
    private int quantity;
    private LocalDateTime soldAt;
    private double totalPrice;

    // Konstruktor sa svim parametrima
    public WaiterDrinkDTO(Long id, String waiterName, String drinkName, int quantity, LocalDateTime soldAt, double totalPrice) {
        this.id = id;
        this.waiterName = waiterName;
        this.drinkName = drinkName;
        this.quantity = quantity;
        this.soldAt = soldAt;
        this.totalPrice = totalPrice;
    }

    // Getters i Setters (ili koristite Lombok za automatsko generisanje)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getWaiterName() { return waiterName; }
    public void setWaiterName(String waiterName) { this.waiterName = waiterName; }
    
    public String getDrinkName() { return drinkName; }
    public void setDrinkName(String drinkName) { this.drinkName = drinkName; }
    
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    
    public LocalDateTime getSoldAt() { return soldAt; }
    public void setSoldAt(LocalDateTime soldAt) { this.soldAt = soldAt; }
    
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
}

