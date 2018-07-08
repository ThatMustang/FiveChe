package edu.csuft.hlei.fivechess;

/**
 * 棋盘面板
 * 
 * @author hlei
 *
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * 设置面板的操作
 */
public class MyPanel extends JPanel implements MouseListener, Runnable {
	
	/**
	 * 序列化的版本号
	 */
	private static final long serialVersionUID = 1L;
	int[][] allChess = new int[15][15]; // 棋盘数组
	int[][] emptyChessGoals = new int[15][15];
	int x;// 保存棋子的横坐标
	int y;// 保存棋子的纵坐标
	Boolean canPlay = false; // 游戏是否继续
	Boolean isBlack = true;// 是否是黑子，默认为黑子
	Boolean isVsMan = false; // 判断是否是人人对战
	Thread t = new Thread(this);
	int theTime = 60;
	int blackTime = 60;
	int whiteTime = 60;
	static int gameDifficulty = 0;// 设置游戏难度，0为菜鸟模式，1为简单模式
	static int whiteWin = 0,blackWin = 0;// 记录黑白棋获胜次数
	JLabel jla = new JLabel("游戏未开始");// 提示按钮
	
	// 获取isBlack的值
	public boolean getIsBlack() {
		return this.isBlack;
	}
	// 设置isBlack的值
	public void setIsBlack(boolean isBlack) {
		this.isBlack = isBlack;
	}
	// 获取isManAgainst的值
	public boolean getIsVsMan() {
		return this.isVsMan;
	}
	// 获取isManAgainst的值
	public void setIsVsMan(boolean isManAgainst) {
		this.isVsMan = isManAgainst;
	}
	// 获取gameDifficulty的值
	public int getGameDifficulty() {
		return MyPanel.gameDifficulty;
	}
	// 设置gameDifficulty的值
	public void setGameDifficulty(int gameDifficulty) {
		MyPanel.gameDifficulty = gameDifficulty;
	}	
	
	/**
	 * 构造方法
	 */
	@SuppressWarnings("deprecation")
	public MyPanel() {
		this.add(jla);// 添加提示信息文本框
		this.setLayout(null);
		this.setBackground(new Color(255,184,90));
		jla.setSize(100, 50);
		jla.setLocation(212, 455);
		jla.setBackground(new Color(255,184,90));
		this.repaint();
		addMouseListener((MouseListener)this);// 添加鼠标监视器
		t.start();
		t.suspend();// 线程挂起
	}	
	
	/**
	 *  绘制
	 */
	@Override
	public void paint(Graphics g) {

		super.paint(g);
		Graphics2D gr2 = (Graphics2D) g;
		Font fo = new Font("hl", 0 , 19);
		g.setFont(fo);
		g.drawString("黑方时间：" + blackTime, 29, 490);
		g.drawString("白方时间：" + whiteTime, 335, 490);
		
		/**
		 *  绘制棋盘
		 */
		for (int i = 0; i < 15; i++) {
			g.drawLine(30, 30 + 30 * i, 450, 30 + 30 * i);
			g.drawLine(30 + 30 * i, 30, 30 + 30 * i, 450);
		}
		// 开启 2D 图形渲染的抗锯齿选项
		gr2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.fillOval(240 - 6, 240 - 6, 12, 12); // 绘制最中心的圆形
		g.fillOval(360 - 6, 360 - 6, 12, 12); // 绘制右下的圆形
		g.fillOval(360 - 6, 120 - 6, 12, 12); // 绘制右上的圆形
		g.fillOval(120 - 6, 360 - 6, 12, 12); // 绘制左下的圆形
		g.fillOval(120 - 6, 120 - 6, 12, 12); // 绘制左上的圆形

		/**
		 * 落子
		 */
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++)
				draw(g, i, j); 
		}
	}	
	
	/**
	 *  绘制黑白棋子
	 *  
	 * @param g
	 * @param i
	 * @param j
	 */
	public void draw(Graphics g, int i, int j) {
		if (allChess[i][j] == 1) {
			Graphics2D gr2 = (Graphics2D) g;// 开启 2D 图形渲染的抗锯齿选项
			g.setColor(Color.black);// 黑色棋子
			g.fillOval(30 * i + 30 - 11, 30 * j + 30 - 11, 22, 22);
			gr2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		if (allChess[i][j] == 2) {
			Graphics2D gr2 = (Graphics2D) g;// 开启 2D 图形渲染的抗锯齿选项
			g.setColor(Color.white);// 白色棋子
			g.fillOval(30 * i + 30 - 11, 30 * j + 30 - 11, 22, 22);
			gr2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
	}
	
	/**
	 *  鼠标点击时发生的函数
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		if (canPlay == true) {// 判断是否可以开始游戏
			x = e.getX(); // 获取鼠标的焦点
			y = e.getY();
			if (isVsMan == true) {// 判断是否是人人对战
				manVSManChess();
				} 
			else { // 否则是人机对战(默认），人机下棋
				manVSRobot();
				}
			}
		}
	
	/**
	 *  人机对战下棋函数
	 */
	@SuppressWarnings("deprecation")
	public void manVSRobot() {
		if (x >= 29 && x <= 451 && y >= 29 && y <= 451) {
			x = (x + 15) / 30 - 1; // 为了取得交叉点的坐标
			y = (y + 15) / 30 - 1;
			if (allChess[x][y] == 0) {
				// 判断当前要下的是什么棋子
				if (isBlack == true) {
					allChess[x][y] = 1;
					this.repaint();
					isBlack = false;
					jla.setText("用户下棋");
					boolean winFlag = this.checkWin(x, y);
					if (winFlag == true) {
						t.suspend();
						JOptionPane.showMessageDialog(this, "游戏结束," + "黑方获胜!");
						blackWin = blackWin + 1;
						canPlay = false;
						isBlack = true;
					}
				} if(isBlack == false){
					isBlack = true;
					robotChess(gameDifficulty);
					whiteTime = 60;
					blackTime = 60;
				}
			 }
		}
	}
	
	/**
	 *  人人对战下棋函数
	 */
	@SuppressWarnings("deprecation")
	public void manVSManChess() {
		if (x >= 29 && x <= 451 && y >= 29 && y <= 451) {
			x = (x + 15) / 30 - 1; // 取得交叉点的坐标
			y = (y + 15) / 30 - 1;
			if (allChess[x][y] == 0) {
				// 判断当前要下的是什么棋子
				if (isBlack == true) {
					allChess[x][y] = 1;
					isBlack = false;
					blackTime = 60;
					jla.setText("轮到白方");
				} else {
					allChess[x][y] = 2;
					isBlack = true;
					whiteTime = 60;
					jla.setText("轮到黑方");
				}
			}
			// 判断这个棋子是否和其他棋子连成5个
			boolean winFlag = this.checkWin(x, y);
			this.repaint(); 
			if (winFlag == true) {
				JOptionPane.showMessageDialog(this, "游戏结束," + 
						(allChess[x][y] == 1 ? "黑方" : "白方") + "获胜!");
				if(allChess[x][y] == 1) 
					blackWin = blackWin + 1;
				else
					whiteWin = whiteWin + 1;
				isBlack = true;
				canPlay = false;
				t.suspend();
			}
		} 
	}
	
	/**
	 *  点击开始游戏菜单项，游戏开始
	 */
	@SuppressWarnings("deprecation")
	public void Start() {
		this.canPlay = true;
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				allChess[i][j] = 0;
			}
		}
		t.resume();
		this.repaint();
		JOptionPane.showMessageDialog(this, 
				"               游戏开始！");
		jla.setText("游戏已开始");
		if (isBlack == false && isVsMan == false) {
			robotChess(gameDifficulty);
		}
	}
	
	/**
	 *  run() 方法
	 */
	@Override
	public void run() {
		while (true) {
			if (isVsMan) {
				if (isBlack) {
					if(blackTime>0)
						blackTime--;
					if (blackTime == 0) {
						JOptionPane.showMessageDialog(this, "黑方超时，游戏结束! 白方胜！");
						whiteWin = whiteWin + 1;
						canPlay = false;
						blackTime = -1;
					}
				} else {
					if(whiteTime>0)
						whiteTime--;
					if (whiteTime == 0) {
						JOptionPane.showMessageDialog(this, "白方超时，游戏结束! 黑方胜！");
						blackWin = blackWin + 1; 
						canPlay = false;
						whiteTime = -1;
					}
				}
			} else {
				// 监控黑子下棋的时间
				if(blackTime>0)
					blackTime--;
				if (blackTime == 0) {
					JOptionPane.showMessageDialog(this, "用户超时，游戏结束! 白方胜！");
					whiteWin = whiteWin + 1;
					canPlay = false;
					blackTime = -1;
				}
				// 不监控电脑白字下棋
			}
			this.repaint();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 *  判断是否输赢的函数
	 *  
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean checkWin(int x, int y) {
		boolean flag = false;
		int count = 1;// 保存相同颜色棋子相连数目
		int color = allChess[x][y];// 确定颜色
		// 判断横向 特点：allChess[x][y]中y值相同
		count = this.checkCount(x, y, 1, 0, color);
		if (count >= 5) {
			flag = true;
		} 
		else {
			// 判断纵向
			count = this.checkCount(x, y, 0, 1, color);
			if (count >= 5) {
				flag = true;
			} 
			else {
				// 判断右上左下
				count = this.checkCount(x, y, 1, -1, color);
				if (count >= 5) {
					flag = true;
				} 
				else {
					// 判断左下右上
					count = this.checkCount(x, y, 1, 1, color);
					if (count >= 5) {
						flag = true;
					}
				}
			}
		}
		return flag;
	}
	
	/**
	 *  人人对战中判断相同棋子连接的个数
	 *  
	 * @param x
	 * @param y
	 * @param xChange
	 * @param yChange
	 * @param color
	 * @return
	 */
	private int checkCount(int x, int y, int xChange, int yChange, int color) {
		int count = 1;
		int tempX = xChange;
		int tempY = yChange;
		while (x + xChange >= 0 && x + xChange <= 14 && y + yChange >= 0 && y + yChange <= 14
				&& color == allChess[x + xChange][y + yChange]) {
			count++;
			if (xChange != 0) {
				xChange++;
			}
			if (yChange != 0) {
				if (yChange > 0) {
					yChange++;
				} else {
					yChange--;
				}
			}
		}
		xChange = tempX;
		yChange = tempY;
		while (x - xChange >= 0 && x - xChange <= 14 && y - yChange >= 0 && y - yChange <= 14
				&& color == allChess[x - xChange][y - yChange]) {
			count++;
			if (xChange != 0) {
				xChange++;
			}
			if (yChange != 0) {
				if (yChange > 0) {
					yChange++;
				} else {
					yChange--;
				}
			}
		}
		return count;
	}
	
	/**
	 *  机器人判断黑棋相连的数量
	 *  
	 * @param x
	 * @param y
	 * @param xChange
	 * @param yChange
	 * @param color
	 * @return
	 */
	private int robotCheckCount(int x, int y, int xChange, int yChange, int color) {
		int count = 0;
		int tempX = xChange;
		int tempY = yChange;
		while (x + xChange >= 0 && x + xChange <= 14 && y + yChange >= 0 && y + yChange <= 14
				&& color == allChess[x + xChange][y + yChange]) {
			count++;
			if (xChange != 0) {
				xChange++;
			}
			if (yChange != 0) {
				if (yChange > 0) {
					yChange++;
				} else {
					yChange--;
				}
			}
		}
		xChange = tempX;
		yChange = tempY;
		while (x - xChange >= 0 && x - xChange <= 14 && y - yChange >= 0 && y - yChange <= 14
				&& color == allChess[x - xChange][y - yChange]) {
			count++;
			if (xChange != 0) {
				xChange++;
			}
			if (yChange != 0) {
				if (yChange > 0) {
					yChange++;
				} else {
					yChange--;
				}
			}
		}
		return count;
	}

	/**
	 *  机器人下棋算法
	 * @param gameDifficulty
	 */
	@SuppressWarnings("deprecation")
	public void robotChess(int gameDifficulty) {
		if (gameDifficulty == 0) {
			int i, j;
			boolean chessSucceed = true;// 下棋成功的标志
			while (chessSucceed) {
				i = (int) (Math.random() * 15);
				j = (int) (Math.random() * 15);
				if (allChess[i][j] == 0) {
					allChess[i][j] = 2;
					this.repaint();
					chessSucceed = false;
					boolean winFlag = this.checkWin(i, j);
					this.repaint(); 
					if (winFlag == true) {
						JOptionPane.showMessageDialog(this, "游戏结束," + "白方获胜!");
						whiteWin = whiteWin + 1;
						canPlay = false;
						t.suspend();
					}
				}
			}
		}else if(gameDifficulty == 1) {
			int max = 0;
			int m = 0, n = 0;
			int fourDirection[] = new int[4];// 对空棋的四个方向进行打分
			for (int i = 0; i < 15; i++) {
				for (int j = 0; j < 15; j++) {
					if (allChess[i][j] == 0) {
						fourDirection[0] = robotCheckCount(i, j, 0, 1, 1);
						fourDirection[1] = robotCheckCount(i, j, 1, 0, 1);
						fourDirection[2] = robotCheckCount(i, j, 1, -1, 1);
						fourDirection[3] = robotCheckCount(i, j, 1, 1, 1);
						orderFour(fourDirection);
						emptyChessGoals[i][j] = fourDirection[0] * 100 + fourDirection[1] * 200 + 
								fourDirection[2] * 400 + fourDirection[3] * 600 ;// 计算空棋子的分数
					}
				}
			}
			for (int i = 0; i < 15; i++) {
				for (int j = 0; j < 15; j++) {
					if (emptyChessGoals[i][j] > max && allChess[i][j] != 2 && allChess[i][j] != 1) {
						m = i;
						n = j;
						max = emptyChessGoals[i][j];
					}
				}
			}
			allChess[m][n] = 2;
			this.repaint(); 
			boolean winFlag = this.checkWin(m, n);		
			if (winFlag == true) {
				JOptionPane.showMessageDialog(this, "游戏结束," + "白方获胜!");
				whiteWin = whiteWin + 1;
				canPlay = false;
				t.suspend();
			}
		}
		this.repaint();
	}
	/**
	 *  对数组排序
	 *  
	 * @param n
	 */
	public void orderFour(int n[]) {
		Arrays.sort(n);// 正序排序(从小到大）
	}	
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e){
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
}
