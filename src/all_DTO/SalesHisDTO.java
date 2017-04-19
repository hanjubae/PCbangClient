package all_DTO;

import java.io.Serializable;

public class SalesHisDTO implements Serializable {
	private String pro_type;
	private String pro_name;
	private int sale_quantity;
	private int price;
	private int Total_income;
	
	public SalesHisDTO() {}

	public SalesHisDTO(String pro_type, String pro_name, int sale_quantity, int price, int total_income) {
		super();
		this.pro_type = pro_type;
		this.pro_name = pro_name;
		this.sale_quantity = sale_quantity;
		this.price = price;
		Total_income = total_income;
	}

	public String getPro_type() {
		return pro_type;
	}

	public void setPro_type(String pro_type) {
		this.pro_type = pro_type;
	}

	public String getPro_name() {
		return pro_name;
	}

	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}

	public int getSale_quantity() {
		return sale_quantity;
	}

	public void setSale_quantity(int sale_quantity) {
		this.sale_quantity = sale_quantity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getTotal_income() {
		return Total_income;
	}

	public void setTotal_income(int total_income) {
		Total_income = total_income;
	}

	@Override
	public String toString() {
		return "SalesHIsDTO [pro_type=" + pro_type + ", pro_name=" + pro_name + ", sale_quantity=" + sale_quantity
				+ ", price=" + price + ", Total_income=" + Total_income + "]";
	}
	
	
}
