package _client_Boards;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import _client_Frame.ClientStart;
import all_DTO.boardDTO;

public class Boardsel extends JFrame implements ActionListener {

	// Jbutton , 메뉴가기, 로그아웃
	// private JButton menuBtn;
	//private JButton logoutBtn;
	private JButton buWrite;
	private JButton selbtn;
	// JLabel
	//private JLabel label;
	//private JLabel labelsel;

	// tf
	//private JTextField tfsel;

	// table

	private JTable jTable;
	private JScrollPane jscrPane;
	private DefaultTableModel model = null;

	private String columnNames[] = { "번호", "제목", "작성자"

	};

	private Object rowData[][];

	ClientStart cs = ClientStart.getInstance();

	List<boardDTO> list = null;

	public Boardsel(boardDTO dto) {
		super(" 검색한 게시판 목록");
		setBounds(200, 300, 640, 480);
		setLayout(null);
		this.setResizable(false);

		// 로그아웃 필요 없음
		/*
		 * logoutBtn = new JButton("로그아웃"); logoutBtn.setBounds(500, 10, 100,
		 * 30); add(logoutBtn); logoutBtn.addActionListener(this);
		 * 
		 * 
		 */

		buWrite = new JButton("글쓰기");
		buWrite.setBounds(500, 410, 100, 30);
		add(buWrite);
		buWrite.addActionListener(this);

		// labelsel = new JLabel("검색");
		// labelsel.setBounds(20, 410, 100, 30);
		// add(labelsel);
		// tfsel = new JTextField();
		// tfsel.setBounds(60, 410, 120, 30);
		// add(tfsel);
		selbtn = new JButton("전체목록");
		selbtn.setBounds(390, 410, 110, 30);
		add(selbtn);
		selbtn.addActionListener(this);

		try {
	         list = cs.boardDao.getListsel(dto.getStr(),dto.getSelect());

			rowData = new Object[list.size()][3];
			int num = 1;
			for (int i = 0; i < list.size(); i++) {

				rowData[i][0] = list.get(i).getSeq();
				rowData[i][1] = list.get(i).getTitle();
				rowData[i][2] = list.get(i).getId();
				num++;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model = new DefaultTableModel(columnNames, 0);
		model.setDataVector(rowData, columnNames);
		jTable = new JTable(model);
		// table 크기 조절
		jTable.getColumnModel().getColumn(0).setMaxWidth(75);
		jTable.getColumnModel().getColumn(1).setMaxWidth(450);
		jTable.getColumnModel().getColumn(2).setMaxWidth(150);
		// 가운데 정렬
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		// 가운데 정렬
		jTable.getColumnModel().getColumn(0).setCellRenderer(dtcr);
		jTable.getColumnModel().getColumn(1).setCellRenderer(dtcr);
		jTable.getColumnModel().getColumn(2).setCellRenderer(dtcr);
		jTable.setRowHeight(25);
		jscrPane = new JScrollPane(jTable);
		jscrPane.setBounds(15, 50, 600, 350);
		add(jscrPane);

		jTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int rowNum = jTable.getSelectedRow();
				cs.boardDao.selBoard = list.get(rowNum);

				dispose();
				new BoardReading();

			}

		});

		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		String btnstr = btn.getLabel();

		if (btnstr.equals("전체목록")) {

			new Board();
			dispose();

		} else if (btnstr.equals("글쓰기")) {
			new BoardWrite();
			dispose();

		}

	}
}