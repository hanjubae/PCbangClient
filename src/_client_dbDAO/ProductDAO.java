package _client_dbDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import _client_dbFile.DBConnectionMgr;
import all_DTO.ProductDTO;



public class ProductDAO {
	public List<ProductDTO> orderList;
	public List<ProductDTO> menuList;
	
	public ProductDAO() {
		orderList = new ArrayList<>();
		menuList = new ArrayList<>();
	}
	
	public void addList(ProductDTO item){	//선택한 상품을 orderList에 추가
		orderList.add(item);
	}
	
	public void removeList(String name){	//취소한 상품을 orderList에서 지움
		for(int i = 0; i < orderList.size(); i++){
			if(name.equals(orderList.get(i).getProductName())){
				orderList.remove(i);
			}
		}
	}
	
	public int orderContain(String name){	//name과 동일한 상품이름을 orderList에서 가지고 있는지 확인
		for(int i = 0; i < orderList.size(); i++){
			if(name.equals(orderList.get(i).getProductName())){
				return i;
			}
		}
		
		return -1;
	}
	
	public void getMenuList(){
		//디비 연결
		//상품 리스트 가져와서 저장하기
		ProductDTO productDTO = null;
		
		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;
		DBConnectionMgr pool = null;
		
		
		
		String sql = "select * from product order by pro_type";
		
		try{
			pool = DBConnectionMgr.getInstance();
			conn = pool.getConnection();
			statement = conn.createStatement();
			resultSet = statement.executeQuery(sql);
			
			while(resultSet.next()){
				productDTO = new ProductDTO();
				productDTO.setProductType(resultSet.getString("pro_type"));
				productDTO.setProductName(resultSet.getString("pro_name"));
				productDTO.setPrice(resultSet.getInt("price"));
				
				menuList.add(productDTO);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			pool.freeConnection(conn, statement, resultSet);
		}
	}
	
	public void saleStock(ProductDTO sold){
		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;
		DBConnectionMgr pool = null;
		
		boolean itemExist = false;
		int newQuan = 0;
		
		
		
		
		String selectSql = "select sale_quantity from sales_history "
				+ "where pro_name = " + sold.getProductName();

		selectSql = "select pro_name, sale_quantity from sales_history";
		
		try{
			pool = DBConnectionMgr.getInstance();
			conn = pool.getConnection();
			statement = conn.createStatement();
			resultSet = statement.executeQuery(selectSql);
			
			for(;resultSet.next();){
				
				if(resultSet.getString("pro_name").equals(sold.getProductName())){
					itemExist = true;
					newQuan = resultSet.getInt("sale_quantity");
					break;
				}
			}
			
			if(itemExist){
				newQuan += sold.getQuantity();
				int newTotal = newQuan * sold.getPrice();
				
				String upSql = "update sales_history"
						+ " set sale_quantity = " + newQuan + ", total_income = " + newTotal
						+ " where pro_name = '" + sold.getProductName() + "'";
				
				resultSet = statement.executeQuery(upSql);
			}else{
				int totalIncome = sold.getPrice()*sold.getQuantity();
				String insSql = " insert into sales_history (pro_name, price, sale_quantity, total_income, pro_type)"
						+ " values('" + sold.getProductName() + "', " + sold.getPrice() + ", " + sold.getQuantity() + ", " + totalIncome + ", '" + sold.getProductType() + "')";
				
				resultSet = statement.executeQuery(insSql);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			pool.freeConnection(conn, statement, resultSet);
		}
		
	}
	
	
}
