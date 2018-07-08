package edu.csuft.hlei.fivechess;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

public class MyFrame extends JFrame{
	
	/**
	 * ���л��İ汾��
	 */
	private static final long serialVersionUID = 1L;
	MyPanel panel = new MyPanel();// ��Ϸ���
	
	/**
	 * ���췽��
	 */
	public MyFrame() {
		
		// ���ô���Ĵ�С������
		this.setSize(500, 580); // ���ô����С
		this.setTitle("��������Ϸ"); // ���ô������
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;// ��ȡ��Ļ�Ŀ��
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;// ��ȡ��Ļ�ĸ߶�
		this.setLocation((width - 500) / 2, (height - 580) / 2); // ���ô����λ�ã����У�
		this.setResizable(false); // ���ô��岻���Ըı��С
		
		/**
		 *  �˵�����Ŀ¼����  
		 */
		// ���ò˵���
		JMenuBar bar = new JMenuBar();
		this.setJMenuBar(bar);
		
		JMenu menu1 = new JMenu("��Ϸ�˵�"); // ʵ�����˵���Ŀ¼		
		JMenu menu2 = new JMenu("��Ϸ����");		
		JMenu menu3 = new JMenu("�鿴ս��");
		JMenu menu4 = new JMenu("����");
		
		bar.add(menu1); // ��Ŀ¼��ӵ��˵���
		bar.add(menu2);
		bar.add(menu3);
		bar.add(menu4);
		
		/**
		 * ����Ϸ�˵����˵�������
		 */
		JMenuItem menu5 = new JMenuItem("��ʼ��Ϸ");
		menu1.add(menu5);
		JMenuItem menu6 = new JMenuItem("���¿�ʼ");
		menu1.add(menu6);
		JMenuItem menu7 = new JMenuItem("�˳���Ϸ");
		menu1.add(menu7);
		
		/**
		 * ����ʼ��Ϸ������
		 */
		menu5.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				panel.Start();
			}
		});
		
		/**
		 * �����¿�ʼ������
		 */
		menu6.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Object[] options = { "���¿�ʼ", "ȡ��" };
				int n = JOptionPane.showOptionDialog(null,
				"           �Ƿ����¿�ʼ��", "", 0, 1, null, options, "���¿�ʼ");
				if (n == 0) {
					panel.Start();
				}
			}
		});
		
		/**
		 * ���˳���Ϸ������
		 */
		menu7.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Object[] options = { "�˳���Ϸ", "ȡ��" };
				int n = JOptionPane.showOptionDialog(null, 
				"           �Ƿ��˳���Ϸ��", "", 0, 1, null, options, "���¿�ʼ");
				if (n == 0) {
					System.exit(0);// �˳�����
				}
			}
		});
		
		JMenu menu8 = new JMenu("����ģʽ"); // ����ģʽ���˵�����ӵ�����Ϸ���á�����
		menu2.add(menu8);
		
		/**
		 * ������ģʽ���е�ѡ������
		 */
		JRadioButtonMenuItem item1 = new JRadioButtonMenuItem("���˲���");
		JRadioButtonMenuItem item2 = new JRadioButtonMenuItem("�˻�����");
		
		/**
		 *  item1��ť����¼�����Ϊ������
		 */
		item1.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Object[] options = { "���¿�ʼ", "ȡ��" };
				int n = JOptionPane.showOptionDialog(null,
				"           �Ƿ����¿�ʼ��", "", 0, 1, null, options, "���¿�ʼ");
				if (n == 0) {
					panel.setIsVsMan(true);
					panel.Start();
					item1.setSelected(true);
				}
			}
		});
		
		/**
		 *  ����item2��ť���¼������¼��������˻�����
		 */
		item2.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Object[] options = { "���¿�ʼ", "ȡ��" };
				int n = JOptionPane.showOptionDialog(null, 
				"           �Ƿ����¿�ʼ?", "", 0, 1, null, options, "���¿�ʼ");
				if (n == 0) {
					panel.setIsVsMan(false);
					panel.Start();
					item2.setSelected(true);
				}
			}
		});
		// ���ð�ť�鲢���˻����������˲�����ӵ�������
		ButtonGroup bg = new ButtonGroup();
		bg.add(item1);
		bg.add(item2);
		// ����ť����ӵ��˵�����
		menu8.add(item1);
		menu8.add(item2);
		item2.setSelected(true);
		
		/**
		 *  ��������
		 */
		JMenu menu9 = new JMenu("��������"); // �����������á��˵�����ӵ������á�����
		menu2.add(menu9);
		// ���ú������л��ǰ������еİ�ť
		JRadioButtonMenuItem item3 = new JRadioButtonMenuItem("�ڷ�����");
		JRadioButtonMenuItem item4 = new JRadioButtonMenuItem("�׷�����");
		/**
		 *  ����item3��������¼������úڷ�����
		 */
		item3.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Object[] options = { "���¿�ʼ", "ȡ��" };
				int n = JOptionPane.showOptionDialog(null,
				"           �Ƿ����¿�ʼ��", "", 0, 1, null, options, "���¿�ʼ");
				if (n == 0) {
					panel.setIsBlack(true);
					panel.Start();
					item3.setSelected(true);
				}
			}
		});
		
		/**
		 *  ����item4��������¼�
		 */
		item4.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Object[] options = { "���¿�ʼ", "ȡ��" };
				int n = JOptionPane.showOptionDialog(null, 
				"           �Ƿ����¿�ʼ��", "", 0, 1, null, options, "���¿�ʼ");
				if (n == 0) {
					panel.setIsBlack(false);
					panel.Start();
					item4.setSelected(true);
				}
			}
		});
		// ���ð�ť�鲢���˻����������˲�����ӵ�һ����ť������
		ButtonGroup bg1 = new ButtonGroup();
		bg1.add(item3);
		bg1.add(item4);
		// ����ť����ӵ��˵�����
		menu9.add(item3);
		menu9.add(item4);
		item3.setSelected(true);
		
		/**
		 *  ��Ϸ�Ѷ�����
		 */
		JMenu menu10 = new JMenu("��Ϸ�Ѷ�����");// ��Ӳ˵������˵�
		menu2.add(menu10);
		JRadioButtonMenuItem item5 = new JRadioButtonMenuItem("����");// ��Ӱ�ť
		JRadioButtonMenuItem item6 = new JRadioButtonMenuItem("��ͨ");
		ButtonGroup bg3 = new ButtonGroup();// ���ð�ť��
		bg3.add(item5);
		bg3.add(item6);;
		
		menu10.add(item5);// ���ѡ��Ѷ����ò˵�
		menu10.add(item6);
		item5.setSelected(true);// Ĭ��ѡ�ť
		/**
		 *  �����Ѷ����õ�������¼�
		 */
		item5.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Object[] options = { "���¿�ʼ", "ȡ��" };
				int n = JOptionPane.showOptionDialog(null, 
				"           �Ƿ����¿�ʼ��", "", 0, 1, null, options, "���¿�ʼ");
				if (n == 0) {
					panel.setGameDifficulty(0);
					panel.Start();
					item5.setSelected(true);
				}
			}
		});
		/**
		 * ��ͨ�Ѷ�����ģʽ
		 */
		item6.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Object[] options = { "���¿�ʼ", "ȡ��" };
				int n = JOptionPane.showOptionDialog(null, 
				"           �Ƿ����¿�ʼ��", "", 0, 1, null, options, "���¿�ʼ");
				if (n == 0) {
					panel.setGameDifficulty(1);
					panel.Start();
					item6.setSelected(true);
				}
			}
		});
		
		/**
		 *  ���á��鿴ս�����µ���Ŀ¼
		 */
		JMenuItem menu11 = new JMenuItem("�鿴ս��");
		menu3.add(menu11);
		menu11.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "�ڷ�ʤ�֣�" +
						MyPanel.blackWin +"   �׷�ʤ�֣�" + MyPanel.whiteWin);
			}
		});
		/**
		 *  ���á��������������Ŀ¼
		 */
		JMenuItem menu12 = new JMenuItem("����");
		menu4.add(menu12);
		/**
		 * ��Ϸ������ʾ��Ϣ
		 */
		menu12.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				JOptionPane.showMessageDialog(null, 
					">>>>>>>>>>>���ⷽ�������������ʤ>>>>>>>>>>>>>>"+"\n"+
					"���Ʋ�����Ϸ �ܾ�������Ϸ ע�����ұ��� ������ƭ�ϵ�\r\n" +  
					"�ʶ���Ϸ���� ������Ϸ���� ������ʱ�� ���ܽ�������.\r\n" + 
					"                                   �����ߣ�hlei");
			}
		});
		
		/**
		 * �����������
		 */
		this.add(panel);
	}
}
