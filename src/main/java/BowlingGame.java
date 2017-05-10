package main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BowlingGame {
	
	private String[] turnScore;
	private char[] extra;
	/**
	 * socre rules
	 * @param bowlingCode
	 * @return
	 */
	public int getBowlingScore(String bowlingCode) {
		int score = 0;
		//|是特殊字符，需要转义
		turnScore = bowlingCode.split("\\|");
		if(turnScore.length > 10)
			extra = turnScore[11].toCharArray();
		else
			extra = null;
		//计算每一局分数
		int i = 0;
		while(i != 10) {
			int curScore = 0;
		    if (turnScore[i].equals("X")){ //如果是strike，则分数为10且加上下两个球的分数
				if (i < 9) //如果是第9局之前
					curScore = 10 + getNextScore(i+1, 2);
				else if(i == 9)
					curScore = 10 + charToInt(extra[0]) + charToInt(extra[1]);
		    } else if (matchRegex("[1-9]/", turnScore[i])) {//如果是spare
		    	if(i < 9)
		    		curScore = 10 + getNextScore(i+1, 1);
		    	if(i == 9)
		    		curScore = 10 + charToInt(extra[0]);
		    } else {
				char[] chScore = turnScore[i].toCharArray();
				for (int j = 0; j < chScore.length; j++) {
					if(chScore[j] != '-') {
						curScore = charToInt(chScore[j]);
						break;
					}
				}
			}
			score += curScore;
			i++;
		}
		return score;
	}
	
	/**
	 *  get next ball score
	 *  note if i = 9 for it is turn 10 then we need count extra balls
	 * @param i for which turn
	 * @param next for next 1 ball or next 2 ball
	 * @return
	 */
	private int getNextScore(int i, int next) {
		int curScore = 0;
		if(turnScore[i].equals("X")) {
			if(next == 1)
				curScore = 10;
			else if(i < 9)
				curScore = 10 + getNextScore(i+1, 1);
			else if(i == 9)
				curScore = 10 + charToInt(extra[0]);
		} else if(matchRegex("[1-9]/", turnScore[i])) {
			if(next == 1) {
				char s = turnScore[i].toCharArray()[0];
				curScore = charToInt(s);
			} else
				curScore = 10;
		} else if(matchRegex("-[1-9]", turnScore[i])) {
			if(next == 1)
				curScore = 0;
			else
				curScore = charToInt(turnScore[i].toCharArray()[1]);
		} else if(matchRegex("[1-9]-", turnScore[i])) {
			curScore = charToInt(turnScore[i].toCharArray()[0]);
		}
		return curScore;
	}
	
	private int charToInt(char ch) {
		if(ch == 'X') return 10;
		return Integer.valueOf(String.valueOf(ch));
	}

	private boolean matchRegex(String regex, String str) {
		if(regex != null && str != null) {
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(str);
			if(matcher.find()) return true;
		}
		return false;
	}

}
