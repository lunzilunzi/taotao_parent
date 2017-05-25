package myTest;

import org.junit.Test;

public class my {

	@Test
	public void aa(){
//		int a = getNumber(10, 1);
//		System.out.println("共摘了"+a+"个桃");
		System.out.println("有"+calculateCount(8, 2)+"种方法");
	}

	int calculateCount(int ladder, int maxJump) {
		int jump = 0;
		if (ladder == 0) {
			return 1;
		}
		if (ladder >= maxJump) {
			// 剩下的楼梯大于最大可跳跃数
			for (int i = 1; i <= maxJump; i++) {
				jump += calculateCount(ladder - i, maxJump);
			}
		} else {
			// 剩下的楼梯不足最大可跳跃数
			jump = calculateCount(ladder, ladder);
		}
		return jump;
	}

	int getNumber(int day, int sum){
		if (day==1)
			return sum;
		sum = getNumber(day-1, (sum+1)*2);
		return sum;
	}
}
