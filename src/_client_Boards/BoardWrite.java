package _client_Boards;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import _client_Frame.ClientStart;
import all_DTO.boardDTO;

public class BoardWrite extends JFrame implements ActionListener {

	// Label
	private JLabel labelID;
	private JLabel labelTitle;
	private JLabel labeltext;

	// TextField
	private JTextField tfID;
	private JTextField tfTitle;
	private JTextField pwText;
	private JTextField dateText;
	// TextArea
	private JTextArea taText;
	private JScrollPane jscr;

	// Button
	private JButton bu;
	private JButton bu1;

	//

	public BoardWrite() {
		super("게시글 작성");
		setBounds(200, 300, 640, 480);
		setLayout(null);
		this.setResizable(false);
		ClientStart cs = ClientStart.getInstance();
		labelID = new JLabel("작성자");
		labelID.setBounds(20, 20, 50, 30);
		add(labelID);
		tfID = new JTextField(cs.accountDao.log_id);
		tfID.setBounds(80, 20, 400, 30);
		tfID.setEditable(false);
		add(tfID);

		JLabel pwLabel = new JLabel("비밀번호:");
		pwLabel.setBounds(20, 50, 50, 30);
		add(pwLabel);

		pwText = new JTextField();
		pwText.setBounds(80, 50, 400, 30);

		JLabel dateLabel = new JLabel("작성일:");
		dateLabel.setBounds(20, 80, 50, 30);
		add(dateLabel);
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		dateText = new JTextField(sdf.format(d));
		dateText.setBounds(80, 80, 400, 30);
		dateText.setEditable(false);

		add(dateText);
		add(pwText);
		labelTitle = new JLabel("제목");
		labelTitle.setBounds(20, 110, 50, 30);
		add(labelTitle);
		tfTitle = new JTextField();
		tfTitle.setBounds(80, 110, 400, 30);
		add(tfTitle);

		labeltext = new JLabel("내용");
		labeltext.setBounds(20, 140, 50, 30);
		add(labeltext);
		taText = new JTextArea();
		jscr = new JScrollPane(taText);
		jscr.setBounds(20, 170, 550, 220);
		add(jscr);

		bu = new JButton("글올리기");
		bu.setBounds(20, 400, 100, 30);
		add(bu);
		bu.addActionListener(this);

		bu1 = new JButton("취소");
		bu1.setBounds(130, 400, 80, 30);
		add(bu1);
		bu1.addActionListener(this);

		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		String btnstr = btn.getLabel();
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		if (btnstr.equals("글올리기")) {
			ClientStart cs = ClientStart.getInstance();
			boardDTO dto = new boardDTO();
			dto.setId(cs.accountDao.log_id);
			dto.setTitle(tfTitle.getText());
			dto.setContent(taText.getText());
			// dto.setWdate(sdf.format(d));
			dto.setPword(pwText.getText());

			if (cs.boardDao.addBoard(dto)) {
				new Board();
				dispose();
			} else {
				JOptionPane.showMessageDialog(null, "추가실패!");
				new Board();
				dispose();
			}
		} else if (btnstr.equals("취소")) {
			new Board();
			dispose();
		}

	}

}
