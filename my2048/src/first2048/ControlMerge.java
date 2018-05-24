package first2048;

import java.util.Random;

public class ControlMerge {

	int ro, cl; // ro = 4,cl = 4,代表4行4列
	int[][] number; // 记录棋盘上数字
	int[][] ran;// 记录是否是新产生的，每一次random都标记新元素位置并且消除原位置标记
	int max;
	int grade; // 游戏得分
	int dir; // 初始为-1，0为上，1为下，2为左，3为右

	ControlMerge() {

		dir = -1;
		cl = ro = 4;
		grade = 0;
		max = 2048;
		ran = new int[ro][cl];
		number = new int[ro][cl];
		for (int i = 0; i < ro; i++) {
			number[i] = new int[ro];
			ran[i] = new int[ro];
			for (int j = 0; j < cl; j++) {
				number[i][j] = 0;
				ran[i][j] = 0;
			}
		}
	}

	// 合并某方向上某一行
	public void mergeRow(int r) {

		if (dir == 2) { // 向左合并

			int i = 0;
			while (i < cl - 1) { // 单纯合并
				if (number[r][i] > 0) {
					int j = i + 1;
					while (j <= cl - 1) {
						if (number[r][j] > 0)
							break;
						j++;
					}
					if (j <= cl - 1 && number[r][j] > 0) {
						if (number[r][j] == number[r][i]) { // 相等合并
							number[r][i] += number[r][j];
							grade += number[r][i];
							number[r][j] = 0;
							i = j + 1;
							continue;
						} else {
							i = j;
							continue;
						}
					}
				}
				i++;
			}

			for (int k = 0; k < cl - 1; k++) { // 移动
				if (number[r][k] == 0) {
					int j = k + 1;
					while (j <= cl - 1) {
						if (number[r][j] > 0)
							break;
						j++;
					}
					if (j <= cl - 1) {
						number[r][k] = number[r][j];
						number[r][j] = 0;
					}
				}
			}

		} else if (dir == 3) { // 向右合并

			int i = cl - 1;
			while (i > 0) { // 单纯合并
				if (number[r][i] > 0) {
					int j = i - 1;
					while (j >= 0) {
						if (number[r][j] > 0)
							break;
						j--;
					}
					if (j >= 0 && number[r][j] > 0) {
						if (number[r][j] == number[r][i]) { // 相等合并
							number[r][i] += number[r][j];
							grade += number[r][i];
							number[r][j] = 0;
							i = j - 1;
							continue;
						} else {
							i = j;
							continue;
						}
					}
				}
				i--;
			}

			for (int k = cl - 1; k > 0; k--) { // 移动
				if (number[r][k] == 0) {
					int j = k - 1;
					while (j >= 0) {
						if (number[r][j] > 0)
							break;
						j--;
					}
					if (j >= 0) {
						number[r][k] = number[r][j];
						number[r][j] = 0;
					}
				}
			}
		}
	}

	// 合并某方向上一列
	public void mergeCol(int c) {

		if (dir == 0) { // 向上合并

			int i = 0;
			while (i < ro - 1) {
				if (number[i][c] > 0) {
					int j = i + 1;
					while (j <= ro - 1) {
						if (number[j][c] > 0)
							break;
						j++;
					}
					if (j <= ro - 1 && number[j][c] > 0) {
						if (number[j][c] == number[i][c]) { // 相等合并
							number[i][c] += number[j][c];
							grade += number[i][c];
							number[j][c] = 0;
							i = j + 1;
							continue;
						} else {
							i = j;
							continue;
						}
					}
				}
				i++;
			}

			for (int k = 0; k < ro - 1; k++) { // 移动
				if (number[k][c] == 0) {
					int j = k + 1;
					while (j <= ro - 1) {
						if (number[j][c] > 0)
							break;
						j++;
					}
					if (j <= ro - 1 && number[j][c] > 0) {
						number[k][c] = number[j][c];
						number[j][c] = 0;
					}
				}
			}

		} else if (dir == 1) { // 向下合并

			int i = ro - 1;
			while (i > 0) { // 单纯合并
				if (number[i][c] > 0) {
					int j = i - 1;
					while (j >= 0) {
						if (number[j][c] > 0)
							break;
						j--;
					}
					if (j >= 0 && number[j][c] > 0) {
						if (number[i][c] == number[j][c]) { // 相等合并
							number[i][c] += number[j][c];
							grade += number[i][c];
							number[j][c] = 0;
							i = j - 1;
							continue;
						} else {
							i = j;
							continue;
						}
					}
				}
				i--;
			}

			for (int k = cl - 1; k > 0; k--) { // 移动
				if (number[k][c] == 0) {
					int j = k - 1;
					while (j >= 0) {
						if (number[j][c] > 0)
							break;
						j--;
					}
					if (j >= 0 && number[j][c] > 0) {
						number[k][c] = number[j][c];
						number[j][c] = 0;
					}
				}
			}
		}
	}

	// 总合并
	public void mergeAll() {

		if (dir == 0 || dir == 1) { // 上下

			for (int i = 0; i < cl; i++) {
				mergeCol(i);
			}
		} else if (dir == 2 || dir == 3) { // 左右

			for (int i = 0; i < ro; i++) {
				mergeRow(i);
			}
		}

	}

	// 在空格处随机产生方格2或4
	public int createRandom() {

		for (int i = 0; i < ro; i++) {
			for (int j = 0; j < cl; j++) {
				ran[i][j] = 0;
			}
		}

		int[] ns = new int[16];
		int ntop = 0;

		for (int i = 0; i < ro; i++) {
			for (int j = 0; j < cl; j++) {
				if (number[i][j] == 0) {
					int tot = i * 100 + j;
					if (ntop == 16)
						break;
					ns[ntop] = tot;
					ntop++;
				}
			}
		}

		if (ntop == 0) {
			return 1;
		}

		Random random = new Random();
		int index = random.nextInt(ntop);
		int r1 = ns[index] / 100;
		int c1 = ns[index] % 100;
		int indexi = random.nextInt(ntop);
		int r2 = ns[indexi] / 100;
		int c2 = ns[indexi] % 100;

		int tn1 = 0, tn2 = 0;
		double g = Math.random();
		if (g - 0.5 < 1e-6) {// 1个数

			double g1 = Math.random();
			if (g1 - 0.5 < 1e-6) {
				tn1 = 2;
			} else {
				tn1 = 4;
			}
			number[r1][c1] = tn1;
			ran[r1][c1] = 1;

		} else {// 2个数

			double g1 = Math.random();
			if (g1 - 0.5 < 1e-6) {
				tn1 = 2;
			} else {
				tn1 = 4;
			}
			double g2 = Math.random();
			if (g2 - 0.5 < 1e-6) {
				tn2 = 2;
			} else {
				tn2 = 4;
			}
			number[r1][c1] = tn1;
			ran[r1][c1] = 1;
			number[r2][c2] = tn2;
			ran[r2][c2] = 1;
		}
		return 0;

	}

	// 输赢判断
	public int judge() {	// 输出1赢，0输，-1继续游戏
		
		int flag = 0;

		for (int i = 0; i < ro; i++) {
			for (int j = 0; j < cl - 1; j++) {
				if (number[i][j] == max) {
					return 1;
				}
				int k = j + 1;
				if(number[i][j]==0||number[i][k]==0) {
					flag=1;
					continue;
				}
				if (number[i][j] == number[i][k]) {
					flag = 1;
					break;
				}
			}
			if (number[i][cl - 1] == max) {
				return 1;
			}
			if (flag == 1)
				break;
		}
		
		if (flag == 0) {
			for (int i = 0; i < ro - 1; i++) {
				for (int j = 0; j < cl; j++) {
					int k = i + 1;
					if(number[i][j]==0||number[k][j]==0) {
						flag=1;
						continue;
					}
					if (number[i][j] == number[k][j]) {
						flag = 1;
						break;
					}
				}
				if (flag == 1)
					break;
			}
		}

		if (flag == 1) {
			return -1;
		}

		return 0;
	}

}
