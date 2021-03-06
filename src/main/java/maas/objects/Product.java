package maas.objects;

import org.json.JSONObject;

public class Product {
	
	private String id;
	private int doughPrepTime;
	private int doughRestingTime;
	private int itemPrepTime;
	private int breadsPerOven;
	private int bakingTime;
	private int bakingTemp;
	private int coolingRate;
	private int boxingTemp;
	private int breadsPerBox;
	private int productionCost;
	private int salesPrice;
	
	public Product(String jsonProduct) {
		// Parse the jsonProduct
		JSONObject product = new JSONObject(jsonProduct);
		this.id = product.getString("guid");
		this.doughPrepTime = product.getInt("dough_prep_time");
		this.doughRestingTime = product.getInt("resting_time");
		this.itemPrepTime = product.getInt("item_prep_time");
		this.breadsPerOven = product.getInt("breads_per_oven");
		this.bakingTime = product.getInt("baking_time");
		this.bakingTemp = product.getInt("baking_temp");
		this.coolingRate = product.getInt("cooling_rate");
		this.boxingTemp = product.getInt("boxing_temp");
		this.breadsPerBox = product.getInt("breads_per_box");
		this.productionCost = product.getInt("production_cost");
		this.salesPrice = product.getInt("sales_price");
	}
	public String getId() {
		return id;
	}
	public int getDoughPrepTime() {
		return doughPrepTime;
	}
	public int getDoughRestingTime() {
		return doughRestingTime;
	}
	public int getItemPrepTime() {
		return itemPrepTime;
	}
	public int getBreadsPerOven() {
		return breadsPerOven;
	}
	public int getBakingTime() {
		return bakingTime;
	}
	public int getBakingTemp() {
		return bakingTemp;
	}
	public int getCoolingRate() {
		return coolingRate;
	}
	public int getBoxingTemp() {
		return boxingTemp;
	}
	public int getBreadsPerBox() {
		return breadsPerBox;
	}
	public int getProductionCost() {
		return productionCost;
	}
	public int getSalesPrice() {
		return salesPrice;
	}
	
	/**
	 * Create a String representation of the product
	 */
	public String toString() {
		String s = "Product "+this.id;
		s += "\nDough preparation time: " + this.doughPrepTime;
		s += "\nDough resting time: " + this.doughRestingTime;
		s += "\nItem preparation time: " + this.itemPrepTime;
		s += "\nBreads per oven: " + this.breadsPerOven;
		s += "\nBaking time: " + this.bakingTime;
		s += "\nBaking temp: " + this.bakingTemp;
		s += "\nCooling rate: " + this.coolingRate;
		s += "\nBoxing temperature: " + this.boxingTemp;
		s += "\nBreads per box: " + this.breadsPerBox;
		s += "\nProduction cost: " + this.productionCost;
		s += "\nSales price: " + this.salesPrice;
		return s;
	}

}
