package ci6206.model;

import java.sql.Date;

public class Stock 
{
	private String symbol,name,exchange;
	private Date lastupdate;
	private double price, mktPrice;
	
	public double getMktPrice() {
		return mktPrice;
	}
	public void setMktPrice(double mktPrice) {
		this.mktPrice = mktPrice;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExchange() {
		return exchange;
	}
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Date getLastupdate() {
		return lastupdate;
	}
	public void setLastupdate(Date lastupdate) {
		this.lastupdate = lastupdate;
	}
	
	
	
	
	

}
