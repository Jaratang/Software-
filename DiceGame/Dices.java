package Game;

import java.util.Random;

public class Dices {
	int dice_1;
	int dice_2;
	
	Random random = new Random();
	
	void faceValue() {
		this.dice_1 = random.nextInt(6) + 1;
		this.dice_2 = random.nextInt(6) + 1;
	}
	
	int getfaceValue1() {
		return dice_1;
	}
	
	int getfaceValue2() {
		return dice_2;
	}
}
