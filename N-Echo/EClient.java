import java.io.*;
import java.net.*;
import java.util.*;
 
public class EClient {
 
	public static void main(String[] args) throws IOException {
        int c = 0;
        Scanner scanner = new Scanner(System.in);   //키보드에서 읽을 scanner 객체 생성
        Scanner scanner2 = new Scanner(System.in);
        Scanner scanner3 = new Scanner(System.in);
        Socket socket = null;
        try{
            socket = new Socket("localhost", 9999); // 클라이언트 소켓 생성. 서버에 연결
            System.out.println("Connect");	
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            DataInputStream dis = new DataInputStream(is);
            DataOutputStream dos = new DataOutputStream(os);
            while(true){
                do{
                    System.out.print("반복>>>");
                    String time = scanner2.nextLine();
                    System.out.print("보내기>>");
                    String outputMessage = scanner.nextLine();   // 키보드에서 한 행 읽기
                    dos.writeUTF(time + " " + outputMessage + "\n");    // 키보드에서 읽은 문자열 전송
                    dos.flush();    // out의 스트림 버퍼에 있는 모든 문자열 전송
                    String inputMessage = dis.readUTF();   // 서버로부터 한 행 수신
                    System.out.println("서버: " + inputMessage);
                    c=1;
                }while(c==0);
            System.out.print("다시 실행하려면 re, 종료하려면 bye>>");
            String check = scanner3.nextLine();
            if(check.equals("re")) c=0;
            else if(check.equals("bye")){
                dos.writeUTF(check + "\n"); // "bye" 문자열 전송
                dos.flush();
                break;  // 사용자가 "bye"를 입력한 경우 서버로 전송 후 실행 종료
            }
            }
        } catch(IOException e){
            System.out.println(e.getMessage());
        } finally{
            try{
                scanner.close();
                if(socket != null) socket.close();   // 클라이언트 소켓 닫기
            } catch(IOException e){
                System.out.println("서버와 채팅 중 오류가 발생했습니다.");
            }
        }
	}
}