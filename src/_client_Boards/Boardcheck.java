package _client_Boards;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import _client_Frame.ClientStart;

public class Boardcheck extends JFrame implements ActionListener {

	JLabel passLabel;

	// textfield
	JPasswordField txtPass;

	// Button
	JButton checkBtn;

	public Boardcheck() {
		super("확인");
		setLayout(null);
		setBounds(300, 300, 300, 250);
		this.setResizable(false);

		passLabel = new JLabel("비밀번호");
		passLabel.setBounds(10, 60, 60, 30);
		add(passLabel);
		txtPass = new JPasswordField();
		txtPass.setBounds(75, 60, 160, 30);
		add(txtPass);

		checkBtn = new JButton("확인");
		checkBtn.setBounds(100, 160, 80, 30);
		add(checkBtn);
		checkBtn.addActionListener(this);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		String btnstr = btn.getLabel();

		ClientStart cs = ClientStart.getInstance();



		if (btnstr.equals("확인")) {

			String pass = txtPass.getText();
			
			int seq = cs.boardDao.selBoard.getSeq();
			
			
			
			//boardDTO dto = dao.searchpass(seq);
			
			if (cs.boardDao.selBoard.getPword().equals(pass)) {
				JOptionPane.showMessageDialog(null, "확인되었습니다..");
				
				new BoardUpdate();
				
				dispose();
			} else {
				JOptionPane.showMessageDialog(null, "비밀 번호가 틀렸습니다.");
				new Board();
				dispose();
			}

		}

	}

}
