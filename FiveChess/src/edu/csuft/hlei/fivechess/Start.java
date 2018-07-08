package edu.csuft.hlei.fivechess;

/**
 * 五子棋游戏
 * 
 * @author hlei
 *
 * @version 1.0
 */

import javax.swing.JFrame;

public class Start {
	public static void main(String[] args) {	
		MyFrame myFrame = new MyFrame();// 窗口实例化
		myFrame.setVisible(true); // 设置窗口为可见
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置窗口关闭操作属性
		
	}

}