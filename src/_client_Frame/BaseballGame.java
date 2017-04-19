package _client_Frame;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import _client_dbDAO.Loop;

public class BaseballGame extends JFrame implements ActionListener, ItemListener {

	Choice ch1, ch2, ch3;
	TextArea ta;
	Loop lp = new Loop();
	int cnt = 0;
	
	public BaseballGame() {
		Font font = new Font("HY울릉도M", Font.BOLD, 30);
		Label title = new Label("Baseball Game", Label.CENTER);
		title.setFont(font);
		
		lp.shuffle();
		int resNum[] = lp.getResNum();
		ta = new TextArea();
		
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		setBounds(width/2, height/3, 300, 400);
		setVisible(true);
		
		ch1 = new Choice();
		ch2 = new Choice();
		ch3 = new Choice();
		for(int i=0; i<10; ++i){
			ch1.add(String.valueOf(i));
			ch3.add(String.valueOf(i));
			ch2.add(String.valueOf(i));
		}
		
		
		Panel panel = new Panel();
		panel.setLayout(new GridLayout(1, 3, 0, 10));
		panel.add(ch1);
		panel.add(ch2);
		panel.add(ch3);

		
		Button inputNum = new Button("throw ball!!");
		Button reset = new Button("RESET");
		
		setLayout(new GridLayout(6, 1, 20, 0));
		add(title);
		add(panel);
		add(inputNum);
		add(ta);
		add(reset);
		
		
		ch1.addItemListener(this);
		ch2.addItemListener(this);
		ch3.addItemListener(this);
		
		inputNum.addActionListener(this);
		reset.addActionListener(this);
		
	}
	
	
	@Override
	public void itemStateChanged(ItemEvent arg0) {


	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Button btn = (Button)e.getSource();
		String btnStr = btn.getLabel();
		int userNum[] = new int[3];
		String result = "";
		
		if(btnStr.equals("throw ball!!")){
			
			if(!ch1.getSelectedItem().isEmpty() &&
					!ch2.getSelectedItem().isEmpty() && !ch3.getSelectedItem().isEmpty()){
				
				userNum[0] = Integer.parseInt(ch1.getSelectedItem());
				userNum[1] = Integer.parseInt(ch2.getSelectedItem());
				userNum[2] = Integer.parseInt(ch3.getSelectedItem());
				
				if(cnt < 10){
					if(userNum[0] == userNum[1]){
						JOptionPane.showMessageDialog(null, "숫자가 겹치면 안됩니다.");				
					}else if(userNum[0] == userNum[2]){
						JOptionPane.showMessageDialog(null, "숫자가 겹치면 안됩니다.");					
					}else if(userNum[1] == userNum[2]){
						JOptionPane.showMessageDialog(null, "숫자가 겹치면 안됩니다.");
					}else{
						result = lp.loop(userNum[0], userNum[1], userNum[2]);
						ta.append("\n" + result);
						++cnt;
						System.out.println(cnt +"회 시도하였습니다.");
						
					}
					
					
					
				}else{
					ta.append("\nFailure because you already try 10count!");
				}
			}else{
				JOptionPane.showMessageDialog(null, "숫자를 전부 채우십시오!!");
			}
			
		}else if(btnStr.equals("RESET")){
			lp.shuffle();
			ta.setText("RESET");
			cnt = 0;
		}
		
		if(result.equals("성공!!")){
			int what = JOptionPane.showConfirmDialog(null, "계속 하시겠습니까?");
			if(what == 0){
				lp.shuffle();
				ta.setText("RESET");
				cnt = 0;
			}else if(what == 1){
				this.dispose();
			}
		}
		

	}

}
