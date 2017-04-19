package _client_Frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import Admin_viewHUD.Seat_pan;
import Admin_views.Seat_panAb;
import _client_dbDAO.LoginMemberDAO;

public class login extends JFrame implements ActionListener {

	public JLabel[] label = new JLabel[4];
	public boolean isChecked; // 좌석 눌러져있는지 체크
	public boolean isLogined; // 로그인이 되어있는지 체크
	public boolean isTurned; // 컴퓨터 on/off
	public int num;// 좌석번호를 혼자 가지고 있기 위한 넘버

	public Seat_panAb pan[] = new Seat_panAb[50]; // Seat_panAb라는 형식의 좌석을 50개
													// 배열로 선언(Seat_panAb=dto를
													// 담고있다)
	public JLabel seatNum;

	private JButton log_btn;
	JButton join_btn;
	JTextField id;
	JPasswordField pw;

	public login() {

		setBounds(100, 100, 1620, 900);
		setContentPane(new JLabel(new ImageIcon(getClass().getClassLoader().getResource("mainHud_back.png"))));	//exe파일 실행 시 이미지가 깨지지 않도록 하는 코드

		setBackground(Color.black);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// 윈도우창을 닫으면 프로세스까지 종료!(완전종료)

		// 첫번째 panel = 텍스트필드 tf, tf2 들어감.
		

		JPanel Leftpanel = new JPanel();	//좌석그림이 들어갈 판넬
		Leftpanel.setBounds(10, 100, 1200, 700);
		Leftpanel.setLayout(new GridLayout(5, 10));
		Leftpanel.setOpaque(false);
		pan = new Seat_pan[50];

		for (int a = 0; a < 50; a++) {
			pan[a] = new Seat_pan(a);

			Leftpanel.add(pan[a]);

		}

		loginCheck();	//서버에서 받아온 좌석별 로그인 정보를 가져와서 로그인 여부를 표시만 해주는 함수

		MyThread th = new MyThread(pan);	//다중체크를 막는 쓰레드
		th.start();	// 다중체크 막는 쓰레드 실행

		ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("login_main.png"));

		JPanel Rightpanel = new JPanel() {

			@Override
			protected void paintComponent(Graphics g) {
				g.drawImage(icon.getImage(), 0, 0, null);
				setOpaque(false);	// 판넬 초기모양을 보이지않도록하는 것
				super.paintComponent(g);
			}
		};

		Rightpanel.setBounds(1200, 100, 420, 700);
		Rightpanel.setLayout(null);

		Rightpanel.setOpaque(false);
		// Rightpanel = new MyPanel("img/login_main.png");
		// Rightpanel.setBackground(Color.bl

		seatNum = new JLabel("");
		Font f = new Font("HY울릉도M", Font.BOLD, 100);
		seatNum.setFont(f);
		seatNum.setForeground(Color.white);
		seatNum.setBounds(30, 10, 180, 180);
		Rightpanel.add(seatNum);

		Font f1 = new Font("HY울릉도M", Font.BOLD, 20);

		id = new JTextField(15);
		id.setBounds(97, 244, 260, 40);
		id.setOpaque(false);

		id.setForeground(Color.white);
		id.setFont(f1);
		id.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		Rightpanel.add(id);

		// 두번째 텍스트 731, 529
		pw = new JPasswordField(15);
		pw.setBounds(97, 330, 260, 40);

		pw.setOpaque(false);
		pw.setForeground(Color.white);
		pw.setBorder(javax.swing.BorderFactory.createEmptyBorder());

		Rightpanel.add(pw);

		log_btn = new JButton("");

		log_btn.setBounds(40, 480, 317, 60);
		log_btn.addActionListener(this);

		log_btn.setBorderPainted(false);
		log_btn.setFocusPainted(false);
		log_btn.setContentAreaFilled(false);
		/** 이게 레알이네: 버튼에 이미지만 보여주는 기능 */

		Rightpanel.add(log_btn);

		join_btn = new JButton("");
		join_btn.setBorderPainted(false);
		join_btn.setFocusPainted(false);
		join_btn.setContentAreaFilled(false);

		join_btn.setBounds(40, 600, 317, 60);
		join_btn.addActionListener(this);
		Rightpanel.add(join_btn);

		log_btn.addActionListener(new LoginProcess());
		join_btn.addActionListener(new memberadd());

		
		add(Leftpanel);
		add(Rightpanel);
		setVisible(true);

	}

	public void loginCheck() {
		List<Integer> loginSeat = LoginMemberDAO.seatSet();

		for (int i = 0; i < loginSeat.size(); i++) {
			pan[loginSeat.get(i) - 1].isLogined = true;
			// 좌석이 사용자가 보기엔 1부터 시작하지만 실제 프로그램내에서는 0부터 시작됩니다.
			pan[loginSeat.get(i) - 1].label[0].setText("사용중");
			pan[loginSeat.get(i) - 1].label[0].setForeground(Color.red);
			Font f = new Font("HY울릉도M", Font.PLAIN, 15);
			pan[loginSeat.get(i) - 1].label[0].setFont(f);
			pan[loginSeat.get(i) - 1].turnOn();
		}
	}

	class MyThread extends Thread { // 다중체크를 막는 역할!!
		public Seat_panAb pan[] = new Seat_panAb[50];
		int ch = 0;
		int chAll = 0;
		int pick = 0;

		MyThread(Seat_panAb[] pan) {
			this.pan = pan;
		}

		public void run() {
			while (true) {
				chAll = 0;
				for (int i = 0; i < pan.length; i++) {
					if (pan[i].isChecked) {
						chAll++;
						if (ch == 0) {
							ch++;
							pick = i;
						} else if (ch == 1) {
							if (!(pick == i)) {
								pan[pick].checkOff();
							}
							pick = i;
						} else {
							ch--;
							pan[pick].checkOff();
							pick = i;
						}
					}
				}
				if (chAll == 0) {
					if (ch == 1) {
						seatNum.setText("");
					}
				} else {
					seatNum.setText((pick + 1) + "");
				}

				// repaint();
			}
			// repaint();
		}
	}

	private class LoginProcess implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			ClientStart cs = ClientStart.getInstance();
			if (seatNum.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "좌석을 선택하세요.");
				return;
			} else {
				Integer.parseInt(seatNum.getText());

				int logIn = LoginMemberDAO.loginMember(id.getText(), pw.getText());

				if (logIn == 2) {
					JOptionPane.showMessageDialog(null, "아이디가 존재하지 않거나 잘 못 입력하셨습니다.");
				} else if (logIn == 0) {
					if (!LoginMemberDAO.isEmpty(seatNum.getText())) {
						JOptionPane.showMessageDialog(null, "선택한 " + seatNum.getText() + "좌석은 이미 선택되었습니다.");
						new login();
						dispose();
						return;
					}

					JOptionPane.showMessageDialog(null, "로그인 되었습니다.");
					cs.accountDao.log_id = id.getText();
					dispose();
					ClientPc2.doClient = true;
					ClientPc2 cl = new ClientPc2(id.getText(), seatNum.getText());

				} else if (logIn == 1) {
					JOptionPane.showMessageDialog(null, "로그인 하시려는 아이디는 이미 로그인 되어있습니다.");
				} else {
					JOptionPane.showMessageDialog(null, "로그인 오류!!");
				}
			}
		}

	}

	private class memberadd implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			new NewMember();

		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

	}

}
