package _client_Frame;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

import _client_Boards.Board;
import _client_Frame.login;
import _client_dbDAO.AccountDAO;
import all_DTO.MembershipDTO;

import java.awt.event.*;
import java.net.*;
import java.sql.SQLException;
import java.io.*;

//현재 사용중인 피시방 이용자의 이용시간과 이용요금창을 보여준다
public class ClientPc2 {// 클라이언트 클래스 시작
	
	private String id; // 현재 사용중인 아이디 저장
	private String pc; // 현재 사용중인 피시 번호 저장
	private JFrame clFrame;
	private JLabel userId;
	private JLabel userPc;
	private JLabel userTime;
	private JLabel userPrice;
	private Socket socket;
	private DataOutputStream out;
	private DataInputStream in;
	private ClientChat chat;
	public static boolean doClient=true;	//false면 서버와 연결이 끊어진것 

	public ClientPc2(String id, String pc) {// 클라이언트 생성자시작

		this.id = id;
		this.pc = pc;
		clFrame = new JFrame("이용중");

		// 현재 스크린사이즈를 받아온다
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;

		// 표시라벨
		userId = new JLabel(id);
		userPc = new JLabel(pc);
		userTime = new JLabel("임시");
		userPrice = new JLabel("임시");
		JLabel border1 = new JLabel("");
		JLabel border2 = new JLabel("");
		JLabel border3 = new JLabel("");
		
		EtchedBorder eborder=new EtchedBorder(EtchedBorder.RAISED);
		
		border1.setBounds(1, 1, 210, 100);
		border1.setBorder(eborder);
		
		border2.setBounds(210, 1, 232, 50);
		border2.setBorder(eborder);
		
		border3.setBounds(210, 50, 232, 50);
		border3.setBorder(eborder);

		JLabel pc_label = new JLabel("좌 석 번 호");
		pc_label.setFont(myfont(30));
		
		
		
		userPc.setFont(myfont(50));
		JLabel id_label = new JLabel("아이디 :");
		Font f = new Font("HY울릉도M", Font.PLAIN, 20);
		id_label.setFont(f);
		JLabel time_label = new JLabel("이용시간 :");
		Font f2 = new Font("HY울릉도M", Font.PLAIN, 13);
		time_label.setFont(f2);
		JLabel price_label = new JLabel("이용요금 :");
		
		price_label.setFont(f2);
		
		// 버튼
		JButton chatBtn = new JButton("채팅");
		chatBtn.setFont(f2);
		JButton orderBtn = new JButton("주문");
		JButton boardBtn = new JButton("게시판");
		JButton gameBtn = new JButton("게임");
		JButton logoutBtn = new JButton("로그아웃");
		
		
		// 컴포넌트가 붙을 패널 생성
		JPanel panel = new JPanel();
		
		panel.add(border1);
		panel.add(border2);
		panel.add(border3);

		// 컴포넌트 배치부
		pc_label.setBounds(20, 5, 200, 50);
		id_label.setBounds(218, 10, 100, 27);
		time_label.setBounds(218, 51, 100, 30);
		price_label.setBounds(218, 70, 95, 30);
		userId.setBounds(330, 8, 95, 27);
		userId.setForeground(Color.blue);
		userId.setFont(myfont(25));
		if(Integer.parseInt(userPc.getText())>=10){
			userPc.setBounds(77, 55, 95, 40);
		}else{
			userPc.setBounds(83, 55, 95, 40);
		}
		
		userTime.setBounds(310, 49, 95, 30);
		userPrice.setBounds(310, 68, 95, 30);
		orderBtn.setBounds(1, 100, 90, 80);
		chatBtn.setBounds(91, 100, 90, 80);
		boardBtn.setBounds(181, 100, 90, 80);
		gameBtn.setBounds(271, 100, 90, 80);
		logoutBtn.setBounds(361, 100, 90, 80);

		// 컴포넌트 결합부
		panel.add(pc_label);
		panel.add(id_label);
		panel.add(time_label);
		panel.add(userId);
		panel.add(userTime);
		panel.add(userPc);
		panel.add(userPrice);
		
		panel.add(orderBtn);
		panel.add(chatBtn);
		panel.add(boardBtn);
		panel.add(gameBtn);
		panel.add(logoutBtn);
		
		panel.add(price_label);
		panel.setBorder(panel.getBorder());
		panel.setLayout(null);
		clFrame.add(panel);

		// 버튼 이벤트 처리부
		chatBtn.addActionListener(new ChatEvent());
		orderBtn.addActionListener(new MenuEvent());
		boardBtn.addActionListener(new boardEvent());
		gameBtn.addActionListener(new GameEvent());
		logoutBtn.addActionListener(new logoutEvent());
		// 현재 프레임 위치 및 크기
		clFrame.setBounds(width - 480, height / 5 - 100, 450, 200);
		clFrame.setResizable(false);

		// 유저가 창을 강제 종료시키면 안되므로
		clFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		clFrame.setVisible(true);
		// 소켓 쓰레드시작
		new Thread(new ClientConnector()).start();

	}// 클라이언트 생성자종료
	
	public Font myfont(int size){
		Font f = new Font("HY울릉도M", Font.BOLD, size);
		return f;
	}

	// 챗이벤트클래스 시작(채팅창을 불러온다)
	private class ChatEvent implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {

			if (chat == null) {
				chat = new ClientChat(out, pc);
				return;
			}
			chat.chatFrameVisible();

		}

	}
	// 챗이벤트 클래스 종료
	// 메뉴표를 불러오기 위한 이벤트 클래스 시작
	
	
	
	private class logoutEvent implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, "카운터에서 요금정산 해주세요");
			return;
			
		}
		
		
		
	}
	private class MenuEvent implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			ClientOrder of = new ClientOrder(out, id, pc);
		}
	}
	
	private class boardEvent implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			 new Board();
		}
	}
	
	private class GameEvent implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			new BaseballGame();
		}
		
	}
	

	// 메뉴표를 불러오기 위한 이벤트 클래스 종료
	// 서버와 연결을 위한 클라이언트 커넥터
	private class ClientConnector implements Runnable {

		@Override
		public void run() {
			try {
				String serverIp = "211.238.142.152";// "172.168.0.80";
				socket = new Socket(InetAddress.getByName(serverIp), 7777);
				in = new DataInputStream(new BufferedInputStream(
						socket.getInputStream()));
				out = new DataOutputStream(new BufferedOutputStream(
						socket.getOutputStream()));
				AccountDAO ac = new AccountDAO();
				MembershipDTO dto;
				
					dto = ac.search(id);
				
				
				int pcNum = Integer.parseInt(pc)-1;
				out.writeInt(pcNum);
				out.writeUTF(id);
				out.writeInt(dto.getAge());
				out.writeUTF(dto.getName());
				out.writeUTF("로그인");
				out.flush();

				while (true) {
					String str = in.readUTF();
					// 이용요금 처리부
					if (str.equals("요금정보")) {
						Integer money = in.readInt();
						userPrice.setText(money.toString());
						userTime.setText(in.readUTF());
					}
					// 채팅메시지 처리부
					if (str.equals("메시지")) {
						String msg = in.readUTF();
						if (chat == null) {
							chat = new ClientChat(out, pc);
						}
						chat.chatFrameVisible();
						chat.addChat(msg);
						
					}
					if(str.equals("주문")){
		                  String item = in.readUTF();
		                  int itemCount = in.readInt();
		                  
		                  JOptionPane.showMessageDialog(null, item + " " + itemCount + "개 주문 확인했습니다");
					
					}
					// 로그아웃 처리부
					if (str.equals("로그아웃")) {
						
						socket.close();
					}
				}

			} catch (IOException e) {// 서버와 연결이 끊어질시 창이 변함

				if (chat != null) {
					chat.closeFrame();
				}
				doClient=false;
				ClientStart cs = ClientStart.getInstance();
				cs.accountDao.setSeat(0, id); // 로그아웃시 좌석 null로.. 0을 null로 간주
				clFrame.dispose();
				login cl = new login();

			} catch (SQLException e) {

				e.printStackTrace();
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}

	}// 클라이언트 커넥터종료

}// 클라이언트 클래스 종료
