package edu.kh.network.model.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
	
	
	// Server : 서비스를 제공하는 프로그램 or 컴퓨터
	// Client : 서버의 서비스를 요청하거나, 사용하는 프로그램 or 컴퓨터
	
	// TCP Socket Programming
	
	// Transmission Control Protocol
	// --> 데이터 전달의 신뢰성을 최대한 보장하기 위한 방식
	// --> 순차적으로 데이터를 전달, 확인, 오류가 발생 시, 재전송해줌
	
	// Socket : 프로세스 통신에 사용되는 양 끝단
	// --> 서버와 클라이언트가 통신을 하기 위한 매개체 역할
	
	// ServerSocket : 서버용 소켓으로, 포트와 연결돼서 클라이언트의 요청을 기다리는 것
					// 클라이언트의 요청이 들어오면 클라가 사용할 수 있게 accpet한다.
					// 클라이언트가 사용할 수 있는 소켓을 생성한다.
					// --> 서버 소켓과 클라이언트 소켓이 연결돼서 데이터 송신이 가능해짐
	
	
	public void serverStart() {
		
		
//		1.서버의 포트번호 정함
		
		int port = 8500; // port 번호는 0 ~ 65535 사이 지정 가능
						 // 단 1023번 이하는 이미 사용중인 경우가 많으니 제외
		
		/* 사용할 변수 미리 선언 */
		ServerSocket serverSocket = null; // 서버 소켓 저장 변수
		Socket clientSocket = null; // 클라이언트 소켓 저장 변수
		
		
		InputStream is = null; // 클라이언트에서 서버로 입력하는 입력용 스트림 변수
		BufferedReader br = null; // 입력용 보조 스트림 변수
		
		
		OutputStream os = null;	// 서버 -> 클라이언트로 출력용 스트림 변수
		PrintWriter pw = null; // 출력용 보조 스트림 변수
		// PrintWriter에는 println print pritnf 출력 함수를 제공함으로써, 파일 출력을 편하게 해준다.
		
		
		
		// BufferedWriter bw = null; 
		
		
		
		
		
		try {
			
			//		2.서버용 소켓 객체 생성
			serverSocket = new ServerSocket(port); // 서버용 소켓을 생성하여, 포트와 결합한 상태
			
			//		3.클라이언트 쪽에서 접속 요청이 오길 기다림
			System.out.println("[Server]");
			System.out.println("클라이언트 요청을 기다리고 있습니다...");
			// 		서버용 소켓은 생성되면 클라이언트 요청이 오기 전까지 다음 코드를 수행하지 않고 기다리고 있는다.
			
			
			//		4.접속 요청이 오면 요청 수락 후 해당 클라이언트에 대한 소켓 객체 생성
			// 		요청을 수락하면 자동으로 Socket 객체가 얻어와진다.
			clientSocket = serverSocket.accept();
			
			// 접속한 클라이언트의 IP를 얻어와 출력하는 것
			String clientIP = clientSocket.getInetAddress().getHostAddress(); // IP주소의 형태
			
			System.out.println(clientIP + "가 연결을 요청함...");
			
			
			//		5.연결된 클라이언트와 입출력 스트림 생성
			is = clientSocket.getInputStream(); // clientSocket에서 제공하는 스트림
			os = clientSocket.getOutputStream(); // clientSocket에서 제공하는 스트림
			
			//		6.보조 스트림을 통해 성능 개선
			br = new BufferedReader(new InputStreamReader(is));
			// InputStreamReader는 바이트 기반 스트림과 문자 기반 스트림 연결에 사용하는 스트림
			pw = new PrintWriter(os);

			
			//		7.스트림을 통해 읽고 쓰기
			pw.println("[서버 접속 성공!]");
			pw.println("[배고프다!]");
			pw.flush(); // 스트림에 있는 내용을 모두 밀어냄
						// close()도 모두 밀어내는 데, 하지만 close는 밀어내는 게 부가적인 기능이지,
						// 궁극적으로는 연결을 끊어내는 역할
			//		7-2) 클라이언트가 서버에게 입력한 메세지를 전송 받아보자
			String message = br.readLine(); // 클라이언트 메세지를 한 줄 읽어옴
			System.out.println(clientIP + "가 보낸 메세지 : " + message);
			
			
			
		}catch(IOException e) {
			
			e.printStackTrace(); 
			
		}finally {
			
			//		8.통신 종료
			//		사용한 스트림, 소켓 자원을 모두 반환(close)
			try {
				if(pw != null) pw.close(); // 둘다 보조 스트림 --> 보조 스트림 close 시
										   // 연결된 기반 스트림(is,os)도 같이 close됨
				if(br != null) br.close();
				
				if(serverSocket != null) serverSocket.close(); 
				if(clientSocket != null) clientSocket.close();
			
				
				}catch(IOException e) {
				e.printStackTrace();
				}
		}
	}
}
