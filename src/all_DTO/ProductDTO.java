package all_DTO;

public class ProductDTO {
	private String productType;	//상품 분류
	private String productName;	//품명
	private String id;			//주문자 id
	private int seat;			//주문 좌석
	private int price;			//상품 1개 가격
	private int quantity;		//주문 수량
	
	public ProductDTO() {
	}
	
	public ProductDTO(String productType, String productName, String id, int seat, int price, int quantity) {
		super();
		this.productType = productType;
		this.productName = productName;
		this.id = id;
		this.seat = seat;
		this.price = price;
		this.quantity = quantity;
	}

	public String getProductType() {
		return productType;
	}
	
	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public int getSeat() {
		return seat;
	}
	
	public void setSeat(int seat) {
		this.seat = seat;
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
	
	//주문 개수에 따른 총 가격
	public int totalPrice(){
		return this.price*this.quantity;
	}

	@Override
	public String toString() {
		return "ProductDTO [productType=" + productType + ", productName=" + productName + ", id=" + id + ", seat="
				+ seat + ", price=" + price + ", quantity=" + quantity + "]";
	}
}
