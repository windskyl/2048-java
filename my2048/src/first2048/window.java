package first2048;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

import org.w3c.dom.css.RGBColor;

public class window implements ActionListener {

	JFrame jf;
	JLabel[][] blocks; // 棋盘块
	JLabel mark; // 赢或输
	JButton breset;
	JPanel pnorth, pcenter;

	ControlMerge my_cm;

	static int ro, cl;

	// 响应
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == breset) {
			my_cm = new ControlMerge();
		}
		else {
			my_cm.mergeAll();
		}
		jf.requestFocus();
		my_cm.createRandom();
		setWindow();
	}

	// 更新视图
	public void setWindow() {

		for (int i = 0; i < ro; i++) {
			for (int j = 0; j < cl; j++) {
				if (my_cm.number[i][j] > 0) {
					if(my_cm.number[i][j]==2)
						blocks[i][j].setForeground(new Color(120,50,50));
					else if(my_cm.number[i][j]==4)
						blocks[i][j].setForeground(new Color(200,50,2));
					else if(my_cm.number[i][j]==8)
						blocks[i][j].setForeground(new Color(170,5,250));
					else if(my_cm.number[i][j]==16)
						blocks[i][j].setForeground(new Color(133,5,50));
					else if(my_cm.number[i][j]==32)
						blocks[i][j].setForeground(new Color(33,225,50));
					else if(my_cm.number[i][j]==64)
						blocks[i][j].setForeground(new Color(17,35,150));
					else if(my_cm.number[i][j]==128)
						blocks[i][j].setForeground(new Color(17,150,50));
					else if(my_cm.number[i][j]==256)
						blocks[i][j].setForeground(new Color(199,5,50));
					else if(my_cm.number[i][j]==512)
						blocks[i][j].setForeground(new Color(17,54,75));
					else if(my_cm.number[i][j]==1024)
						blocks[i][j].setForeground(new Color(1,15,50));
					else if(my_cm.number[i][j]==2048)
						blocks[i][j].setForeground(new Color(0,0,0));
//					if (my_cm.ran[i][j] == 1) {
//						blocks[i][j].setForeground(Color.BLUE);
//					} else {
//						blocks[i][j].setForeground(Color.RED);
//					}
					blocks[i][j].setText("" + my_cm.number[i][j]);
				} else {
					blocks[i][j].setForeground(Color.BLACK);
					blocks[i][j].setText("" + 0);
				}
			}
		}

		if (my_cm.judge() == 0) {// 输
			mark.setText("you lose!your grade is" + my_cm.grade);
			return;
		} else if (my_cm.judge() == 1) {// 赢
			mark.setText("you win！your grade is" + my_cm.grade);
			return;
		} else if (my_cm.judge() == -1) {// 继续游戏
			mark.setText("your grade is" + my_cm.grade);
		}
	}

	window() {

		jf = new JFrame("my_2048");
		jf.getContentPane().setLayout(new BorderLayout(5, 5));

		my_cm = new ControlMerge();

		ro = cl = 4;

		mark = new JLabel("your grade is " + my_cm.grade);
		mark.setFont(new java.awt.Font("Dialog", 6, 40));
		jf.add(BorderLayout.SOUTH, mark);

		breset = new JButton("新游戏");
		breset.setFont(new java.awt.Font("Dialog", 6, 60));
		breset.addActionListener(this);
		pnorth = new JPanel();
		pnorth.add(breset);
		jf.add(BorderLayout.NORTH, pnorth);

		pcenter = new JPanel();
		pcenter.setLayout(new GridLayout(4, 4));

		blocks = new JLabel[ro][cl];
		for (int i = 0; i < ro; i++) {
			blocks[i] = new JLabel[cl];
		}

		for (int i = 0; i < ro; i++) {
			for (int j = 0; j < cl; j++) {
				blocks[i][j] = new JLabel("");
				blocks[i][j].setFont(new java.awt.Font("Dialog", 6, 60));
				pcenter.add(blocks[i][j]);
			}
		}
		pcenter.setBackground(Color.WHITE);
		jf.add(BorderLayout.CENTER, pcenter);

		jf.setFocusable(true);
		jf.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {

				super.keyPressed(e);
				mark.setText("" + e.getKeyCode());
				if (e.getKeyCode() == 38) {// 上
					my_cm.dir = 0;
				} else if (e.getKeyCode() == 40) {// 下
					my_cm.dir = 1;
				} else if (e.getKeyCode() == 37) {// 左
					my_cm.dir = 2;
				} else if (e.getKeyCode() == 39) {// 右
					my_cm.dir = 3;
				}

				my_cm.mergeAll();
				my_cm.createRandom();
				setWindow();
			}

		});

		jf.setLocationRelativeTo(null);
		jf.setSize(700, 700);
		jf.setVisible(true);
		jf.setFocusable(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
