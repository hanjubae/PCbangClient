package _client_Boards;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import _client_Frame.ClientStart;

import all_DTO.boardDTO;



public class BoardReading extends JFrame implements ActionListener {
	// Label
	JLabel la_ID;
	JLabel la_Title;
	JLabel la_Wdate;
	JLabel la_readCount;
	JLabel la_content;

	// Textfield
	JTextField tf_ID;
	JTextField tf_Title;
	JTextField tf_Wdate;
	JTextField tf_readCount;
	JTextArea ta_content;
	JTextField pwText;
	JTextField dateText;

	// button
	JButton bu_list;
	JButton bu_update;
	JButton bu_delete;

	//
	
	ClientStart cs = ClientStart.getInstance();
	List<boardDTO> list = null;

	public BoardReading() {
		setLayout(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		list = cs.boardDao.getList();

		la_ID = new JLabel("작성자");
		la_ID.setBounds(20, 20, 50, 28);
		add(la_ID);
		tf_ID = new JTextField(cs.boardDao.selBoard.getId());
		tf_ID.setBounds(80, 20, 400, 28);
		tf_ID.setEditable(false);
		add(tf_ID);
		/*
		 * JLabel pwLabel = new JLabel("비번:"); pwLabel.setBounds(20, 50, 50,
		 * 28); add(pwLabel); pwText = new
		 * JTextField(list.get(bdao.rowNum).getPword()); pwText.setBounds(80,
		 * 50, 200, 28); add(pwText);
		 */
		la_Title = new JLabel("제목");
		la_Title.setBounds(20, 50, 50, 30);
		add(la_Title);
		tf_Title = new JTextField(cs.boardDao.selBoard.getTitle());
		tf_Title.setBounds(80, 50, 400, 30);
		tf_Title.setEditable(false);
		add(tf_Title);

		la_Wdate = new JLabel("작성일");
		la_Wdate.setBounds(20, 80, 50, 28);
		add(la_Wdate);
		tf_Wdate = new JTextField(cs.boardDao.selBoard.getWdate());
		tf_Wdate.setBounds(80, 80, 400, 28);
		tf_Wdate.setEditable(false);
		add(tf_Wdate);

		la_content = new JLabel("내용");
		la_content.setBounds(20, 110, 50, 30);
		add(la_content);
		ta_content = new JTextArea(cs.boardDao.selBoard.getContent());
		// ta_content.setBounds(10, 220, 450, 240);
		ta_content.setEditable(false);
		JScrollPane jsc = new JScrollPane(ta_content);
		jsc.setBounds(20, 150,550, 220);
		add(jsc);

		bu_list = new JButton("목록");
		bu_list.setBounds(20, 400, 100, 30);
		add(bu_list);

		bu_update = new JButton("수정");
		bu_update.setBounds(130, 400, 80, 30);
		add(bu_update);

		//bu_delete = new JButton("삭제");
		//bu_delete.setBounds(300, 480, 70, 30);
		//add(bu_delete);

		bu_list.addActionListener(this);
		//bu_delete.addActionListener(this);
		bu_update.addActionListener(this);

		getContentPane().setBackground(Color.LIGHT_GRAY);
		setBounds(200, 300, 640, 480);
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		String btnstr = btn.getLabel();

		if (btnstr.equals("목록")) {
			new Board();
			dispose();
			
		} else if (btnstr.equals("수정")) {
			new Boardcheck();
			dispose();
			

		}

	}
}
