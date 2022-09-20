package Game;

import java.util.Scanner;

public class DiceGame {
	public static void main(String[] args) {
		int Player1 = 0;
		int Player2 = 0;
		
		Dices Dice = new Dices();
		
		
		while(true){
			System.out.print("게임을 시작하시겠습니까? y/n >> ");
			Scanner scanner = new Scanner(System.in);	//키보드 입력 받기
			
			String check = scanner.next();
			
			if (check.equals("y")) {
				Dice.getfaceValue1();
				Dice.getfaceValue2();
				Dice.faceValue();
				Player1 = Dice.getfaceValue1() + Dice.getfaceValue2();
				Dice.faceValue();
				Player2 = Dice.getfaceValue1() + Dice.getfaceValue2();
				
				System.out.println("Player1의 주사위 총 합 >> " + Player1);
				System.out.println("Player2의 주사위 총 합 >> " + Player2);
				
				if(Player1 > Player2) {
					System.out.println("Player1의 승리");
				}
				else if(Player1 == Player2) {
					System.out.println("무승부");
				}
				else {
					System.out.println("Player2의 승리");
				}
			}
			else if(check.equals("n")) {
				System.out.print("게임종료");
				break;
			}
		}	
	}
}
