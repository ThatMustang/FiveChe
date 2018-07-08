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
	 * 序列化的版本号
	 */
	private static final long serialVersionUID = 1L;
	MyPanel panel = new MyPanel();// 游戏面板
	
	/**
	 * 构造方法
	 */
	public MyFrame() {
		
		// 设置窗体的大小并居中
		this.setSize(500, 580); // 设置窗体大小
		this.setTitle("五子棋游戏"); // 设置窗体标题
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;// 获取屏幕的宽度
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;// 获取屏幕的高度
		this.setLocation((width - 500) / 2, (height - 580) / 2); // 设置窗体的位置（居中）
		this.setResizable(false); // 设置窗体不可以改变大小
		
		/**
		 *  菜单栏的目录设置  
		 */
		// 设置菜单栏
		JMenuBar bar = new JMenuBar();
		this.setJMenuBar(bar);
		
		JMenu menu1 = new JMenu("游戏菜单"); // 实例化菜单栏目录		
		JMenu menu2 = new JMenu("游戏设置");		
		JMenu menu3 = new JMenu("查看战绩");
		JMenu menu4 = new JMenu("帮助");
		
		bar.add(menu1); // 将目录添加到菜单栏
		bar.add(menu2);
		bar.add(menu3);
		bar.add(menu4);
		
		/**
		 * “游戏菜单”菜单项设置
		 */
		JMenuItem menu5 = new JMenuItem("开始游戏");
		menu1.add(menu5);
		JMenuItem menu6 = new JMenuItem("重新开始");
		menu1.add(menu6);
		JMenuItem menu7 = new JMenuItem("退出游戏");
		menu1.add(menu7);
		
		/**
		 * “开始游戏”监听
		 */
		menu5.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				panel.Start();
			}
		});
		
		/**
		 * “重新开始”监听
		 */
		menu6.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Object[] options = { "重新开始", "取消" };
				int n = JOptionPane.showOptionDialog(null,
				"           是否重新开始？", "", 0, 1, null, options, "重新开始");
				if (n == 0) {
					panel.Start();
				}
			}
		});
		
		/**
		 * “退出游戏”监听
		 */
		menu7.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Object[] options = { "退出游戏", "取消" };
				int n = JOptionPane.showOptionDialog(null, 
				"           是否退出游戏？", "", 0, 1, null, options, "重新开始");
				if (n == 0) {
					System.exit(0);// 退出程序
				}
			}
		});
		
		JMenu menu8 = new JMenu("博弈模式"); // 将“模式”菜单项添加到“游戏设置”里面
		menu2.add(menu8);
		
		/**
		 * “博弈模式”中的选项设置
		 */
		JRadioButtonMenuItem item1 = new JRadioButtonMenuItem("人人博弈");
		JRadioButtonMenuItem item2 = new JRadioButtonMenuItem("人机博弈");
		
		/**
		 *  item1按钮添加事件并且为匿名类
		 */
		item1.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Object[] options = { "重新开始", "取消" };
				int n = JOptionPane.showOptionDialog(null,
				"           是否重新开始？", "", 0, 1, null, options, "重新开始");
				if (n == 0) {
					panel.setIsVsMan(true);
					panel.Start();
					item1.setSelected(true);
				}
			}
		});
		
		/**
		 *  设置item2按钮的事件监听事件，设置人机博弈
		 */
		item2.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Object[] options = { "重新开始", "取消" };
				int n = JOptionPane.showOptionDialog(null, 
				"           是否重新开始?", "", 0, 1, null, options, "重新开始");
				if (n == 0) {
					panel.setIsVsMan(false);
					panel.Start();
					item2.setSelected(true);
				}
			}
		});
		// 设置按钮组并把人机博弈与人人博弈添加到组里面
		ButtonGroup bg = new ButtonGroup();
		bg.add(item1);
		bg.add(item2);
		// 将按钮组添加到菜单里面
		menu8.add(item1);
		menu8.add(item2);
		item2.setSelected(true);
		
		/**
		 *  先行设置
		 */
		JMenu menu9 = new JMenu("先行设置"); // 将“先行设置”菜单项添加到“设置”里面
		menu2.add(menu9);
		// 设置黑子先行还是白子先行的按钮
		JRadioButtonMenuItem item3 = new JRadioButtonMenuItem("黑方先行");
		JRadioButtonMenuItem item4 = new JRadioButtonMenuItem("白方先行");
		/**
		 *  设置item3的鼠标点击事件，设置黑方先行
		 */
		item3.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Object[] options = { "重新开始", "取消" };
				int n = JOptionPane.showOptionDialog(null,
				"           是否重新开始？", "", 0, 1, null, options, "重新开始");
				if (n == 0) {
					panel.setIsBlack(true);
					panel.Start();
					item3.setSelected(true);
				}
			}
		});
		
		/**
		 *  设置item4的鼠标点击事件
		 */
		item4.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Object[] options = { "重新开始", "取消" };
				int n = JOptionPane.showOptionDialog(null, 
				"           是否重新开始？", "", 0, 1, null, options, "重新开始");
				if (n == 0) {
					panel.setIsBlack(false);
					panel.Start();
					item4.setSelected(true);
				}
			}
		});
		// 设置按钮组并把人机博弈与人人博弈添加到一个按钮组里面
		ButtonGroup bg1 = new ButtonGroup();
		bg1.add(item3);
		bg1.add(item4);
		// 将按钮组添加到菜单里面
		menu9.add(item3);
		menu9.add(item4);
		item3.setSelected(true);
		
		/**
		 *  游戏难度设置
		 */
		JMenu menu10 = new JMenu("游戏难度设置");// 添加菜单到主菜单
		menu2.add(menu10);
		JRadioButtonMenuItem item5 = new JRadioButtonMenuItem("菜鸟");// 添加按钮
		JRadioButtonMenuItem item6 = new JRadioButtonMenuItem("普通");
		ButtonGroup bg3 = new ButtonGroup();// 设置按钮组
		bg3.add(item5);
		bg3.add(item6);;
		
		menu10.add(item5);// 添加选项到难度设置菜单
		menu10.add(item6);
		item5.setSelected(true);// 默认选项按钮
		/**
		 *  新手难度设置的鼠标点击事件
		 */
		item5.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Object[] options = { "重新开始", "取消" };
				int n = JOptionPane.showOptionDialog(null, 
				"           是否重新开始？", "", 0, 1, null, options, "重新开始");
				if (n == 0) {
					panel.setGameDifficulty(0);
					panel.Start();
					item5.setSelected(true);
				}
			}
		});
		/**
		 * 普通难度设置模式
		 */
		item6.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Object[] options = { "重新开始", "取消" };
				int n = JOptionPane.showOptionDialog(null, 
				"           是否重新开始？", "", 0, 1, null, options, "重新开始");
				if (n == 0) {
					panel.setGameDifficulty(1);
					panel.Start();
					item6.setSelected(true);
				}
			}
		});
		
		/**
		 *  设置“查看战绩”下的子目录
		 */
		JMenuItem menu11 = new JMenuItem("查看战绩");
		menu3.add(menu11);
		menu11.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "黑方胜局：" +
						MyPanel.blackWin +"   白方胜局：" + MyPanel.whiteWin);
			}
		});
		/**
		 *  设置“帮助”下面的子目录
		 */
		JMenuItem menu12 = new JMenuItem("帮助");
		menu4.add(menu12);
		/**
		 * 游戏帮助提示信息
		 */
		menu12.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				JOptionPane.showMessageDialog(null, 
					">>>>>>>>>>>任意方向五子相连则获胜>>>>>>>>>>>>>>"+"\n"+
					"抵制不良游戏 拒绝盗版游戏 注意自我保护 谨防受骗上当\r\n" +  
					"适度游戏益脑 沉迷游戏伤身 合理安排时间 享受健康生活.\r\n" + 
					"                                   制作者：hlei");
			}
		});
		
		/**
		 * 窗体添加容器
		 */
		this.add(panel);
	}
}
