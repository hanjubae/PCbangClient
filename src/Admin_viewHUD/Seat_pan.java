package Admin_viewHUD;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


import Admin_views.Seat_panAb;

public class Seat_pan extends Seat_panAb implements ActionListener,
		MouseListener {
	private static final long serialVersionUID = 1L;
	
	ImageIcon img = null;
	JLayeredPane lpane;
	JPanel panel3;

	public Seat_pan(int i) {
		num = i;
		isChecked = false;
		img("gameOff");
		setLayout(null);

		// 제이레이어드 패널
		lpane = new JLayeredPane();
		lpane.setBounds(0, 0, 1600, 900);
		lpane.setLayout(null);
		lpane.setOpaque(false);
		// 이미지 패널
		JPanel panel = new InnerPanel();
		panel.setBounds(0, 0, 112, 133);
		panel.setOpaque(false);
		// 안에 들어갈 내용물들
		JPanel panel2 = new JPanel();
		panel2.setLayout(null);
		panel2.setBounds(0, 0, 112, 133);

		int y = 15;
		for (int a = 0; a < 4; a++) {
			if (a == 0)
				label[a] = new JLabel((i + 1) + ". 빈자리");
			else
				label[a] = new JLabel("");

			label[a].setBounds(20, y, 80, 15);
			y += 16;
			label[a].setForeground(Color.black);
			label[a].setFont(new Font("HY울릉도L", 1, 12));
			panel2.add(label[a]);
		}
		panel2.setOpaque(false);

		// 체크패널
		panel3 = new CheckPanel();
		panel3.setLayout(null);
		panel3.setBounds(0, 0, 99, 99);
		panel3.setOpaque(false);
		// 마지막 붙이기
		lpane.add(panel, new Integer(0), 0);
		lpane.add(panel2, new Integer(1), 0);

		add(lpane);
		setVisible(true);

		setOpaque(false);
		setFocusable(true);
		addMouseListener(this);
		/** 여기서부터는 오른쪽 버튼 구현~ */
//		pMenu = new JPopupMenu();
//		miEnd = new JMenuItem("정산");
//		miEnd.addActionListener(this);
//		miChat = new JMenuItem("메세지보내기");
//		miChat.addActionListener(this);
//		pMenu.add(miEnd);
//		pMenu.add(miChat);
//		// 패널에 마우스 리스너를 붙인다. JPopupMenu는 이런식으로 구현을 해야 한다..
//		addMouseListener(new MousePopupListener());
	}

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setTitle("시트 패널");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(99, 144);

		JPanel panel = new Seat_pan(1);
		f.add(panel);

		f.setVisible(true);
	}

	public void img(String s) {
		// 이미지 받아오기 - turnOn, turnOff, gameOn, gameOff
		
			img = new ImageIcon(getClass().getClassLoader().getResource(s + ".png"));
			
		
		repaint();
	}

	/** 이부분이 상태 체크 */
	public void turnOn() {
		img("gameOn");
		isTurned = true;
	}

	public void turnOff() {
		img("gameOff");
		isTurned = false;
	}

	public void checkOn() {
		lpane.add(panel3, new Integer(2), 0);
		this.isChecked = true;
		repaint();
	}

	@Override
	public void checkOff() {
		lpane.remove(panel3);
		this.isChecked = false;
		repaint();
	}

	// 이미지불러오는패널
	class InnerPanel extends JPanel {
		private static final long serialVersionUID = 1547128190348749556L;

		public void paint(Graphics g) {
			super.paint(g);
			g.drawImage(img.getImage(), 0, 0, null);
		}
	}

	// 체크패널
	@SuppressWarnings("serial")
	class CheckPanel extends JPanel {
		ImageIcon img_c;

		CheckPanel() {
		
				img_c = new ImageIcon(getClass().getClassLoader().getResource("check.png"));
			
		
		}

		public void paint(Graphics g) {
			super.paint(g);
			g.drawImage(img_c.getImage(), 0, 0, null);
		}
	}

	/** 여기서부터 액션처리 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(this.isLogined == true){
	         JOptionPane.showMessageDialog(null, "이미 사용중인 좌석입니다. 다른 좌석을 선택해주세요");
	         return;
	      }
		
		if (this.isChecked == false) {
			checkOn();

		} else if (this.isChecked == true) {
			checkOff();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@SuppressWarnings("static-access")
	

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}



	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
