package _client_Frame;



import _client_Frame.login;
import _client_dbDAO.AccountDAO;
import _client_dbDAO.BoardDAO;
import _client_dbDAO.ProductDAO;

public class ClientStart {
	private static ClientStart instance = new ClientStart();
	
	public static void main(String[] args) {
		/*login login = */new login();	//클라이언트 시작프로그램
	}
	
	private ClientStart() {}	//클라이언트 싱글턴
	
	
	public BoardDAO boardDao = new BoardDAO();
	public AccountDAO accountDao = new AccountDAO();
	public ProductDAO productDao = new ProductDAO();
	
	public static ClientStart getInstance(){
		return instance;
	}
}
