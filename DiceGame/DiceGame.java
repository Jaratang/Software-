package Game;

import java.util.Scanner;

public class DiceGame {
	public static void main(String[] args) {
		int Player1 = 0;
		int Player2 = 0;
		
		Dices Dice = new Dices();
		
		
		while(true){
			System.out.print("������ �����Ͻðڽ��ϱ�? y/n >> ");
			Scanner scanner = new Scanner(System.in);	//Ű���� �Է� �ޱ�
			
			String check = scanner.next();
			
			if (check.equals("y")) {
				Dice.getfaceValue1();
				Dice.getfaceValue2();
				Dice.faceValue();
				Player1 = Dice.getfaceValue1() + Dice.getfaceValue2();
				Dice.faceValue();
				Player2 = Dice.getfaceValue1() + Dice.getfaceValue2();
				
				System.out.println("Player1�� �ֻ��� �� �� >> " + Player1);
				System.out.println("Player2�� �ֻ��� �� �� >> " + Player2);
				
				if(Player1 > Player2) {
					System.out.println("Player1�� �¸�");
				}
				else if(Player1 == Player2) {
					System.out.println("���º�");
				}
				else {
					System.out.println("Player2�� �¸�");
				}
			}
			else if(check.equals("n")) {
				System.out.print("��������");
				break;
			}
		}	
	}
}
