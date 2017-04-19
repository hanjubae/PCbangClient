package _client_Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import all_DTO.MembershipDTO;



public class NewMember extends JFrame implements ActionListener {

	// JLabel
	JLabel idLabel;
	JLabel passLabel;
	JLabel nameLabel;
	JLabel phoneLabel;
	JLabel titleLabel;

	JLabel ageLabel;
	//JLabel authLabel;
	//JLabel seatLabel;
	//JLabel minutesLabel;
	
	// JTextField
	JTextField txtID;
	JPasswordField txtPass;
	JTextField txtName;
	JTextField txtphone;
	
	JTextField txtage;
	//JTextField txtauth;
	//JTextField txtseat;
	//JTextField txtminutes;
	// JButton
	private boolean chId; // 아이디 중복체크 해야지만 로그인할수있도록
	String selId; // 아이디 중복체크 누르고 아이디 다시 바꾸면 다시 중복체크 해야만 로그인 할수 있도록
	JButton checkBtn;
	JButton finishBtn;

	

	public NewMember() {
		super("회원가입");
		chId = false;
		setBounds(300, 300, 400, 350);
		setLayout(null);
		this.setResizable(false);

		titleLabel = new JLabel("회원가입");
		titleLabel.setBounds(165, 25, 80, 20);
		add(titleLabel);

		idLabel = new JLabel("아이디");
		idLabel.setBounds(30, 60, 50, 30);
		add(idLabel);
		txtID = new JTextField();
		txtID.setBounds(90, 60, 150, 30);
		add(txtID);
		checkBtn = new JButton("중복확인");
		checkBtn.setBounds(255, 60, 90, 30);
		add(checkBtn);
		checkBtn.addActionListener(this);
		
		
		passLabel = new JLabel("비밀번호");
		passLabel.setBounds(30, 100, 70, 30);
		add(passLabel);
		txtPass = new JPasswordField();
		txtPass.setBounds(90, 100, 150, 30);
		add(txtPass);

		nameLabel = new JLabel("이름");
		nameLabel.setBounds(30, 140, 70, 30);
		add(nameLabel);
		txtName = new JTextField();
		txtName.setBounds(90, 140, 150, 30);
		add(txtName);

		phoneLabel = new JLabel("전화번호");
		phoneLabel.setBounds(30, 180, 70, 30);
		add(phoneLabel);
		txtphone = new JTextField();
		txtphone.setBounds(90, 180, 150, 30);
		add(txtphone);
	
		ageLabel = new JLabel("나이");
		ageLabel.setBounds(30, 220, 70, 30);
		add(ageLabel);
		txtage = new JTextField();
		txtage.setBounds(90, 220, 150, 30);
		add(txtage);
		
		finishBtn = new JButton("가입하기");
		finishBtn.setBounds(45, 280, 300, 30);
		add(finishBtn);
		finishBtn.addActionListener(this);

		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		JButton btn = (JButton) e.getSource();
		String btnstr = btn.getLabel();
		
		
		if (btnstr.equals("가입하기")) {

			if (txtID.getText().equals("") || txtPass.getText().equals("") || txtphone.getText().equals("")
					|| txtName.getText().equals("") || txtage.getText().equals("")) { 
				JOptionPane.showMessageDialog(null, "빈칸을 채워주세요");
				return;
			}
			
			
			String member_id = txtID.getText();
			if(!(selId.equals(member_id))) chId = false; // 중복체크 사용가능상태에서 다시 아이디 바꿀시
			if(chId){
				String password = txtPass.getText();
				String name = txtName.getText();
				int age = Integer.parseInt(txtage.getText());
				String phone_number = txtphone.getText();
		
				ClientStart cs = ClientStart.getInstance();
				
				
				MembershipDTO dto = new MembershipDTO(member_id, password, name, age,phone_number );
				try{
				MembershipDTO dto1 = cs.accountDao.search1(member_id, phone_number);
				if(dto1 != null){
					JOptionPane.showMessageDialog(null, "아이디 혹은 전화번호 중복입니다.");
				}else{
					if (cs.accountDao.addMember(dto)) {
						JOptionPane.showMessageDialog(null, "회원가입성공!!");
						dispose();
					}
				}
				
				}catch(SQLException ex1){
					
				}
			}else{
				JOptionPane.showMessageDialog(null, "Id 중복체크를 하셔야 합니다.");
			}
		} else if (btnstr.equals("중복확인")) {
			try {
				ClientStart cs = ClientStart.getInstance();
				MembershipDTO dto = cs.accountDao.search(txtID.getText());
				if (dto != null) {
					chId = false;
					JOptionPane.showMessageDialog(null, "중복된 아이디입니다.");
				}else{
					chId = true;
					selId = txtID.getText();
					JOptionPane.showMessageDialog(null, "사용 가능합니다.");
				}

			} catch (SQLException ex) {

			}

		}

	}

	}

