package _client_Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import _client_Frame.login;
import all_DTO.MembershipDTO;


public class UpdateMember extends JFrame implements ActionListener {
	
	JButton deletemember;
	// JLabel
		JLabel idLabel;
		JLabel passLabel;
		JLabel nameLabel;
		JLabel emailLabel;
		JLabel titleLabel;
		JLabel phoneLabel;

		// JTextField
		JTextField txtID;
		JTextField txtPass;
		JTextField txtName;
		JTextField txtEmail;
		JTextField txtphone;

		// JButton
		JButton checkBtn;
		JButton finishBtn;

		

		public UpdateMember() {
			super("회원정보수정");
			setBounds(300, 300, 400, 330);
			setLayout(null);
			this.setResizable(false);
			ClientStart cs = ClientStart.getInstance();
			
			titleLabel = new JLabel("회원정보수정");
			titleLabel.setBounds(165, 25, 115, 20);
			add(titleLabel);

			idLabel = new JLabel("아이디");
			idLabel.setBounds(70, 60, 50, 30);
			add(idLabel);
			txtID = new JTextField(cs.accountDao.log_id);
			txtID.setBounds(130, 60, 150, 30);
			txtID.setEditable(false);
			add(txtID);
			try{
			MembershipDTO dto = cs.accountDao.search(cs.accountDao.log_id);
			
			passLabel = new JLabel("비밀번호");
			passLabel.setBounds(70, 100, 70, 30);
			add(passLabel);
			txtPass = new JTextField(dto.getPassword());
			txtPass.setBounds(130, 100, 150, 30);
			add(txtPass);

			nameLabel = new JLabel("이름");
			nameLabel.setBounds(70, 140, 70, 30);
			add(nameLabel);
			txtName = new JTextField(dto.getName());
			txtName.setBounds(130, 140, 150, 30);
			txtName.setEditable(false);
			add(txtName);

			phoneLabel = new JLabel("이메일");
			phoneLabel.setBounds(70, 180, 70, 30);
			add(phoneLabel);
			txtphone = new JTextField(dto.getPhone_number());
			txtphone.setBounds(130, 180, 150, 30);
			add(txtphone);
			}catch(SQLException eeq){
				
			}
			finishBtn = new JButton("수정완료");
			finishBtn.setBounds(30, 230, 200, 30);
			add(finishBtn);
			finishBtn.addActionListener(this);
			
			deletemember = new JButton("회원탈퇴");
			deletemember.setBounds(250, 230, 100, 30);
			add(deletemember);
			deletemember.addActionListener(this);
			
			setVisible(true);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource();
		String btnstr = btn.getLabel();
		
		if(btnstr.equals("수정완료")){
			
			try{
				ClientStart cs = ClientStart.getInstance();
			if(cs.accountDao.phoneSearch(txtphone.getText())){
				cs.accountDao.update(txtEmail.getText(), txtPass.getText());
				new login();
				dispose();
			}else{
				
				JOptionPane.showMessageDialog(null, "이메일 중복입니다.");
				
			}
		
			
			
			
			
			}catch(SQLException se){
				
			}
		}else if(btnstr.equals("회원탈퇴")){
			try{
			ClientStart cs = ClientStart.getInstance();
			cs.accountDao.delete();
			new login();
			dispose();
			}catch(SQLException eed1){
				
			}
		}
		

	}

}
