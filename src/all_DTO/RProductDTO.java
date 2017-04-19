package all_DTO;

import java.io.Serializable;

public class RProductDTO implements Serializable{
	
	private String pro_type;
	private String pro_name;
	private int price;
	private int quantity;
	
	
	public RProductDTO() {}


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


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public RProductDTO(String pro_type, String pro_name, int price, int quantity) {
		super();
		this.pro_type = pro_type;
		this.pro_name = pro_name;
		this.price = price;
		this.quantity = quantity;
	}





	@Override
	public String toString() {
		return "ProductDTO [pro_type=" + pro_type + ", pro_name=" + pro_name + ", price=" + price + ", quantity="
				+ quantity + "]";
	}
	
	


	
	
	
}
