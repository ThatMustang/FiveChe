package edu.csuft.hlei.fivechess;

/**
 * �������
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
 * �������Ĳ���
 */
public class MyPanel extends JPanel implements MouseListener, Runnable {
	
	/**
	 * ���л��İ汾��
	 */
	private static final long serialVersionUID = 1L;
	int[][] allChess = new int[15][15]; // ��������
	int[][] emptyChessGoals = new int[15][15];
	int x;// �������ӵĺ�����
	int y;// �������ӵ�������
	Boolean canPlay = false; // ��Ϸ�Ƿ����
	Boolean isBlack = true;// �Ƿ��Ǻ��ӣ�Ĭ��Ϊ����
	Boolean isVsMan = false; // �ж��Ƿ������˶�ս
	Thread t = new Thread(this);
	int theTime = 60;
	int blackTime = 60;
	int whiteTime = 60;
	static int gameDifficulty = 0;// ������Ϸ�Ѷȣ�0Ϊ����ģʽ��1Ϊ��ģʽ
	static int whiteWin = 0,blackWin = 0;// ��¼�ڰ����ʤ����
	JLabel jla = new JLabel("��Ϸδ��ʼ");// ��ʾ��ť
	
	// ��ȡisBlack��ֵ
	public boolean getIsBlack() {
		return this.isBlack;
	}
	// ����isBlack��ֵ
	public void setIsBlack(boolean isBlack) {
		this.isBlack = isBlack;
	}
	// ��ȡisManAgainst��ֵ
	public boolean getIsVsMan() {
		return this.isVsMan;
	}
	// ��ȡisManAgainst��ֵ
	public void setIsVsMan(boolean isManAgainst) {
		this.isVsMan = isManAgainst;
	}
	// ��ȡgameDifficulty��ֵ
	public int getGameDifficulty() {
		return MyPanel.gameDifficulty;
	}
	// ����gameDifficulty��ֵ
	public void setGameDifficulty(int gameDifficulty) {
		MyPanel.gameDifficulty = gameDifficulty;
	}	
	
	/**
	 * ���췽��
	 */
	@SuppressWarnings("deprecation")
	public MyPanel() {
		this.add(jla);// �����ʾ��Ϣ�ı���
		this.setLayout(null);
		this.setBackground(new Color(255,184,90));
		jla.setSize(100, 50);
		jla.setLocation(212, 455);
		jla.setBackground(new Color(255,184,90));
		this.repaint();
		addMouseListener((MouseListener)this);// �����������
		t.start();
		t.suspend();// �̹߳���
	}	
	
	/**
	 *  ����
	 */
	@Override
	public void paint(Graphics g) {

		super.paint(g);
		Graphics2D gr2 = (Graphics2D) g;
		Font fo = new Font("hl", 0 , 19);
		g.setFont(fo);
		g.drawString("�ڷ�ʱ�䣺" + blackTime, 29, 490);
		g.drawString("�׷�ʱ�䣺" + whiteTime, 335, 490);
		
		/**
		 *  ��������
		 */
		for (int i = 0; i < 15; i++) {
			g.drawLine(30, 30 + 30 * i, 450, 30 + 30 * i);
			g.drawLine(30 + 30 * i, 30, 30 + 30 * i, 450);
		}
		// ���� 2D ͼ����Ⱦ�Ŀ����ѡ��
		gr2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.fillOval(240 - 6, 240 - 6, 12, 12); // ���������ĵ�Բ��
		g.fillOval(360 - 6, 360 - 6, 12, 12); // �������µ�Բ��
		g.fillOval(360 - 6, 120 - 6, 12, 12); // �������ϵ�Բ��
		g.fillOval(120 - 6, 360 - 6, 12, 12); // �������µ�Բ��
		g.fillOval(120 - 6, 120 - 6, 12, 12); // �������ϵ�Բ��

		/**
		 * ����
		 */
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++)
				draw(g, i, j); 
		}
	}	
	
	/**
	 *  ���ƺڰ�����
	 *  
	 * @param g
	 * @param i
	 * @param j
	 */
	public void draw(Graphics g, int i, int j) {
		if (allChess[i][j] == 1) {
			Graphics2D gr2 = (Graphics2D) g;// ���� 2D ͼ����Ⱦ�Ŀ����ѡ��
			g.setColor(Color.black);// ��ɫ����
			g.fillOval(30 * i + 30 - 11, 30 * j + 30 - 11, 22, 22);
			gr2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		if (allChess[i][j] == 2) {
			Graphics2D gr2 = (Graphics2D) g;// ���� 2D ͼ����Ⱦ�Ŀ����ѡ��
			g.setColor(Color.white);// ��ɫ����
			g.fillOval(30 * i + 30 - 11, 30 * j + 30 - 11, 22, 22);
			gr2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
	}
	
	/**
	 *  �����ʱ�����ĺ���
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		if (canPlay == true) {// �ж��Ƿ���Կ�ʼ��Ϸ
			x = e.getX(); // ��ȡ���Ľ���
			y = e.getY();
			if (isVsMan == true) {// �ж��Ƿ������˶�ս
				manVSManChess();
				} 
			else { // �������˻���ս(Ĭ�ϣ����˻�����
				manVSRobot();
				}
			}
		}
	
	/**
	 *  �˻���ս���庯��
	 */
	@SuppressWarnings("deprecation")
	public void manVSRobot() {
		if (x >= 29 && x <= 451 && y >= 29 && y <= 451) {
			x = (x + 15) / 30 - 1; // Ϊ��ȡ�ý���������
			y = (y + 15) / 30 - 1;
			if (allChess[x][y] == 0) {
				// �жϵ�ǰҪ�µ���ʲô����
				if (isBlack == true) {
					allChess[x][y] = 1;
					this.repaint();
					isBlack = false;
					jla.setText("�û�����");
					boolean winFlag = this.checkWin(x, y);
					if (winFlag == true) {
						t.suspend();
						JOptionPane.showMessageDialog(this, "��Ϸ����," + "�ڷ���ʤ!");
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
	 *  ���˶�ս���庯��
	 */
	@SuppressWarnings("deprecation")
	public void manVSManChess() {
		if (x >= 29 && x <= 451 && y >= 29 && y <= 451) {
			x = (x + 15) / 30 - 1; // ȡ�ý���������
			y = (y + 15) / 30 - 1;
			if (allChess[x][y] == 0) {
				// �жϵ�ǰҪ�µ���ʲô����
				if (isBlack == true) {
					allChess[x][y] = 1;
					isBlack = false;
					blackTime = 60;
					jla.setText("�ֵ��׷�");
				} else {
					allChess[x][y] = 2;
					isBlack = true;
					whiteTime = 60;
					jla.setText("�ֵ��ڷ�");
				}
			}
			// �ж���������Ƿ��������������5��
			boolean winFlag = this.checkWin(x, y);
			this.repaint(); 
			if (winFlag == true) {
				JOptionPane.showMessageDialog(this, "��Ϸ����," + 
						(allChess[x][y] == 1 ? "�ڷ�" : "�׷�") + "��ʤ!");
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
	 *  �����ʼ��Ϸ�˵����Ϸ��ʼ
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
				"               ��Ϸ��ʼ��");
		jla.setText("��Ϸ�ѿ�ʼ");
		if (isBlack == false && isVsMan == false) {
			robotChess(gameDifficulty);
		}
	}
	
	/**
	 *  run() ����
	 */
	@Override
	public void run() {
		while (true) {
			if (isVsMan) {
				if (isBlack) {
					if(blackTime>0)
						blackTime--;
					if (blackTime == 0) {
						JOptionPane.showMessageDialog(this, "�ڷ���ʱ����Ϸ����! �׷�ʤ��");
						whiteWin = whiteWin + 1;
						canPlay = false;
						blackTime = -1;
					}
				} else {
					if(whiteTime>0)
						whiteTime--;
					if (whiteTime == 0) {
						JOptionPane.showMessageDialog(this, "�׷���ʱ����Ϸ����! �ڷ�ʤ��");
						blackWin = blackWin + 1; 
						canPlay = false;
						whiteTime = -1;
					}
				}
			} else {
				// ��غ��������ʱ��
				if(blackTime>0)
					blackTime--;
				if (blackTime == 0) {
					JOptionPane.showMessageDialog(this, "�û���ʱ����Ϸ����! �׷�ʤ��");
					whiteWin = whiteWin + 1;
					canPlay = false;
					blackTime = -1;
				}
				// ����ص��԰�������
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
	 *  �ж��Ƿ���Ӯ�ĺ���
	 *  
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean checkWin(int x, int y) {
		boolean flag = false;
		int count = 1;// ������ͬ��ɫ����������Ŀ
		int color = allChess[x][y];// ȷ����ɫ
		// �жϺ��� �ص㣺allChess[x][y]��yֵ��ͬ
		count = this.checkCount(x, y, 1, 0, color);
		if (count >= 5) {
			flag = true;
		} 
		else {
			// �ж�����
			count = this.checkCount(x, y, 0, 1, color);
			if (count >= 5) {
				flag = true;
			} 
			else {
				// �ж���������
				count = this.checkCount(x, y, 1, -1, color);
				if (count >= 5) {
					flag = true;
				} 
				else {
					// �ж���������
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
	 *  ���˶�ս���ж���ͬ�������ӵĸ���
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
	 *  �������жϺ�������������
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
	 *  �����������㷨
	 * @param gameDifficulty
	 */
	@SuppressWarnings("deprecation")
	public void robotChess(int gameDifficulty) {
		if (gameDifficulty == 0) {
			int i, j;
			boolean chessSucceed = true;// ����ɹ��ı�־
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
						JOptionPane.showMessageDialog(this, "��Ϸ����," + "�׷���ʤ!");
						whiteWin = whiteWin + 1;
						canPlay = false;
						t.suspend();
					}
				}
			}
		}else if(gameDifficulty == 1) {
			int max = 0;
			int m = 0, n = 0;
			int fourDirection[] = new int[4];// �Կ�����ĸ�������д��
			for (int i = 0; i < 15; i++) {
				for (int j = 0; j < 15; j++) {
					if (allChess[i][j] == 0) {
						fourDirection[0] = robotCheckCount(i, j, 0, 1, 1);
						fourDirection[1] = robotCheckCount(i, j, 1, 0, 1);
						fourDirection[2] = robotCheckCount(i, j, 1, -1, 1);
						fourDirection[3] = robotCheckCount(i, j, 1, 1, 1);
						orderFour(fourDirection);
						emptyChessGoals[i][j] = fourDirection[0] * 100 + fourDirection[1] * 200 + 
								fourDirection[2] * 400 + fourDirection[3] * 600 ;// ��������ӵķ���
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
				JOptionPane.showMessageDialog(this, "��Ϸ����," + "�׷���ʤ!");
				whiteWin = whiteWin + 1;
				canPlay = false;
				t.suspend();
			}
		}
		this.repaint();
	}
	/**
	 *  ����������
	 *  
	 * @param n
	 */
	public void orderFour(int n[]) {
		Arrays.sort(n);// ��������(��С����
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
