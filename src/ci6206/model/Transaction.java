package ci6206.model;

public class Transaction 
{
	private User user;
	private Stock buyStock, sellStock;
	public Stock getBuyStock() {
		return buyStock;
	}
	public void setBuyStock(Stock buyStock) {
		this.buyStock = buyStock;
	}
	public Stock getSellStock() {
		return sellStock;
	}
	public void setSellStock(Stock sellStock) {
		this.sellStock = sellStock;
	}
	private int sellQuantity,buyQuantity;
	
	public int getSellQuantity() {
		return sellQuantity;
	}
	public void setSellQuantity(int sellQuantity) {
		this.sellQuantity = sellQuantity;
	}
	public int getBuyQuantity() {
		return buyQuantity;
	}
	public void setBuyQuantity(int buyQuantity) {
		this.buyQuantity = buyQuantity;
	}
	private double buyAmount,sellAmount,profit;
	private String action;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public double getBuyAmount() {
		return buyAmount;
	}
	public void setBuyAmount(double buyAmount) {
		this.buyAmount = buyAmount;
	}
	public double getSellAmount() {
		return sellAmount;
	}
	public void setSellAmount(double sellAmount) {
		this.sellAmount = sellAmount;
	}
	public double getProfit() {
		return profit;
	}
	public void setProfit(double profit) {
		this.profit = profit;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	

}
