package _client_Frame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import _client_dbDAO.ProductDAO;
import all_DTO.ProductDTO;


public class ClientOrder extends JFrame implements ActionListener {

	private JButton selectButton;	//장바구니에 넣기 버튼
	private JButton buyButton;		//결제하기 버튼
	private JTable menuTable;		//상품목록 테이블
	private JTable listTable;		//선택상품 목록 테이블
	private JScrollPane menuScrollPane;	//상품목록 스크롤판
	private JScrollPane listScrollPane;	//선택상품 목록 스크롤판
	private DefaultTableModel defaultTableModel;
	
	int selectedNumber = -1;
	
	String[][] columeName = {		//columeName을 객체 두개로 안잡고 그냥 2차원 배열로 썼습니다
			{"상품분류", "상품이름", "가격"},
			{"주문내용", "개수", "취소"}
	};
	
	Object[][] menuRowData;	//메뉴 행 데이터
	Object[][] listRowData = new Object[1][3];	//선택상품 목록 데이터
	
	ProductDAO productManager = new ProductDAO();
	
	DataOutputStream out;
	String pc;
	String id;
	
	public ClientOrder(DataOutputStream out, String id, String pc) {
		super("주문하기");
		
		this.out = out;
		this.pc = pc;
		this.id = id;
		
		setLayout(null);
		
		int xPos = Toolkit.getDefaultToolkit().getScreenSize().width - 600;
		int yPos = Toolkit.getDefaultToolkit().getScreenSize().height / 5 + 250;
		
		setBounds(xPos, yPos, 550, 350);
		
		//db에서 상품 데이터받아오기
		productManager.getMenuList();
		
		menuRowData = new Object[productManager.menuList.size()][3];
		
		for(int i = 0; i < menuRowData.length; i++){
			menuRowData[i][0] = productManager.menuList.get(i).getProductType();
			menuRowData[i][1] = productManager.menuList.get(i).getProductName();
			menuRowData[i][2] = String.valueOf(productManager.menuList.get(i).getPrice());
		}
		
		menuTable = new JTable(menuRowData, columeName[0]);
		
		DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer();
		defaultTableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		
		menuTable.getColumnModel().getColumn(0).setMaxWidth(100);
		menuTable.getColumnModel().getColumn(1).setMaxWidth(100);
		menuTable.getColumnModel().getColumn(2).setMaxWidth(100);
		
		menuTable.getColumnModel().getColumn(0).setCellRenderer(defaultTableCellRenderer);
		menuTable.getColumnModel().getColumn(1).setCellRenderer(defaultTableCellRenderer);
		menuTable.getColumnModel().getColumn(2).setCellRenderer(defaultTableCellRenderer);
		
		menuTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				//JOptionPane.showMessageDialog(null, "선택한 상품 : " + menuRowData[menuTable.getSelectedRow()][1]);
				selectedNumber = menuTable.getSelectedRow();
			}
			
		});
		
		defaultTableModel = new DefaultTableModel(columeName[1], 0) {
			// 셀편집을 못하게 하는 필드
			public boolean isCellEditable(int row, int column) {
				if(column == 2 && row != this.getRowCount() - 1)
					return true;
				return false;
			}
		};

		listTable = new JTable(defaultTableModel);
		
		listTable.getColumnModel().getColumn(0).setMaxWidth(100);
		listTable.getColumnModel().getColumn(1).setMaxWidth(100);
		listTable.getColumnModel().getColumn(2).setMaxWidth(120);
		
		listTable.getColumnModel().getColumn(0).setCellRenderer(defaultTableCellRenderer);
		listTable.getColumnModel().getColumn(1).setCellRenderer(defaultTableCellRenderer);
		listTable.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());
		listTable.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor(new JCheckBox()));
		
		menuScrollPane = new JScrollPane(menuTable);
		menuScrollPane.setBounds(20, 20, 280, 200);
		menuScrollPane.setOpaque(false);
		menuScrollPane.getViewport().setOpaque(false);
		
		listScrollPane = new JScrollPane(listTable);
		listScrollPane.setBounds(310, 20, 200, 200);
		listScrollPane.setOpaque(false);
		listScrollPane.getViewport().setOpaque(false);
		

		
		selectButton = new JButton(new ImageIcon(getClass().getClassLoader().getResource("orderbag_v1.png")));
		selectButton.setBorderPainted(false);
		selectButton.setFocusPainted(false);
		selectButton.setContentAreaFilled(false);
		selectButton.addActionListener(this);
		buyButton = new JButton(new ImageIcon(getClass().getClassLoader().getResource("payment.png")));
		buyButton.setBorderPainted(false);
		buyButton.setFocusPainted(false);
		buyButton.setContentAreaFilled(false);
		buyButton.addActionListener(this);
		
		selectButton.setBounds(20, 225, 280, 80);
		buyButton.setBounds(310, 250, 200, 50);
		
		add(menuScrollPane);
		add(listScrollPane);
		add(selectButton);
		add(buyButton);
		
		getContentPane().setBackground(new Color(20, 20, 20));
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == selectButton){
			if(selectedNumber >= 0){
				int number = productManager.orderContain((String)menuRowData[selectedNumber][1]);
				
				if(number > -1){
					int beforeQuantity = productManager.orderList.get(number).getQuantity();
					productManager.orderList.get(number).setQuantity(beforeQuantity + 1);
				}
				
				else{
					ProductDTO newItem = new ProductDTO();
					
					newItem.setProductType((String)menuRowData[selectedNumber][0]);	//상품 분류
					newItem.setProductName((String)menuRowData[selectedNumber][1]);	//상품이름
					newItem.setPrice(Integer.parseInt((String)menuRowData[selectedNumber][2]));	//상품가격
					newItem.setQuantity(1);
					/*주문자 아이디, 좌석 추가*/
					newItem.setSeat(Integer.parseInt(pc));
					
					productManager.addList(newItem);
				}
				
				listTableRepaint();
				
				selectedNumber = -1;
			}else{
				JOptionPane.showMessageDialog(null, "상품을 먼저 선택해주세요");
			}
		}else if(e.getSource() == buyButton){
			try {
				for(int i = 0; i <productManager.orderList.size(); i++){
					out.writeUTF("주문");
					out.writeInt(Integer.parseInt(pc));
					out.writeUTF(productManager.orderList.get(i).getProductType());
					out.writeUTF(productManager.orderList.get(i).getProductName());
					out.writeInt(productManager.orderList.get(i).getQuantity());
					out.writeInt(productManager.orderList.get(i).getPrice());
					out.writeUTF(id);
					out.flush();
				}
				
				productManager.orderList.clear();
				
				listTableRepaint();
				
				JOptionPane.showMessageDialog(null, "주문 완료 잠시만 기다려주세요");

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void listTableRepaint(){
		int sum = 0;
		
		removeAllRow();
		
		for(int i = 0; i < productManager.orderList.size(); i++){
			Object[] selected = {
					productManager.orderList.get(i).getProductName(),
					productManager.orderList.get(i).getQuantity(),
					"취소"
			};
			
			sum += productManager.orderList.get(i).totalPrice();
			
			defaultTableModel.addRow(selected);
		}
		
		if(productManager.orderList.size() == 0)
			return;
		
		Object[] selected = {
				"총계",
				String.valueOf(sum)
		};
		
		defaultTableModel.addRow(selected);
	}

	public void removeAllRow(){	//선택 상품 테이블의 row를 전부 지움
		if(defaultTableModel.getRowCount() > 0){
			for(int i = defaultTableModel.getRowCount() - 1; i >= 0; i--){
				defaultTableModel.removeRow(i);
			}
		}
	}
	
	public boolean listContain(String name){	//선택한 상품 리스트에 이미 있는 상품인지 검수
		boolean exist = false;
		
		for(int i = 0; i < listRowData.length; i++){	//for문을 통해 list전체 확인 
			if(name.equals((String)listRowData[i][0])){
				selectedNumber = i;	//같은게 있으면 menuRowData에서 가져오지 않고 listRowData에서 가져올것임
				exist = true;
				return exist;
			}
		}
		
		return exist;	//선택 상품 리스트에 없으면 false반환
	}
	
	//버튼 renderer
	class ButtonRenderer extends JButton implements TableCellRenderer{

		public ButtonRenderer(){
			setOpaque(true);
		}
		
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if(isSelected){
				setForeground(table.getSelectionForeground());
				setBackground(table.getSelectionBackground());
			}else{
				setForeground(table.getForeground());
				setBackground(UIManager.getColor("Button.background"));
			}
			setText((value == null)?"":value.toString());
			return this;
		}
		
	}
	
	class ButtonEditor extends DefaultCellEditor{
		protected JButton button;
		private String label;
		private boolean isPushed;
		private int selectRow;
		
		public ButtonEditor(JCheckBox checkBox) {
			super(checkBox);
			button = new JButton();
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try{					
						productManager.orderList.remove(selectRow);
						
						listTableRepaint();
						
						isPushed = false;
						
						//fireEditingStopped();
					}catch(Exception e1){
						e1.printStackTrace();
					}
					
				}
			});
		}

		//이게 버튼처럼 눌리게 하는건가
		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			if(isSelected){
				button.setForeground(table.getSelectionForeground());
				button.setBackground(table.getSelectionBackground());
			}else{
				button.setForeground(table.getForeground());
				button.setBackground(table.getBackground());
			}
			
			selectRow = row;
			label = (value== null)?"":value.toString();
			button.setText(label);
			isPushed = true;
			return button;
		}

		//이 메소드 안씀
		@Override
		public Object getCellEditorValue() {
			if(isPushed){
				//선택 상품 취소
				String removeName = (String)listRowData[selectRow + 1][0];
				
				
				//list로 할걸 
				for(int i = 0; i < listRowData.length; i++){
					Object[][] tempArr = listRowData;
					listRowData = new Object[tempArr.length - 1][3];
					
					String nowName = (String)tempArr[i][0];
					
					if(nowName.equals(removeName)){
						continue;
					}else{
						listRowData[i] = tempArr[i];
					}
				}
				
				listTableRepaint();
			}
			
			isPushed = false;
			return label;
		}

		@Override
		public boolean stopCellEditing() {
			isPushed = false;
			return super.stopCellEditing();
		}
	}
}
