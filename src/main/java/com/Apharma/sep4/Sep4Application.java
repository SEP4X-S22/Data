package com.Apharma.sep4;

import com.Apharma.sep4.DAO.DatabaseHandler;
import com.Apharma.sep4.MiddlePoint.MiddlePointDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.Apharma.sep4.WebSocketClient.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.net.http.WebSocket;
import java.util.Scanner;

@SpringBootApplication
@ComponentScan(basePackages = {"com/Apharma/sep4/DAO",
		"com/Apharma/sep4/MiddlePoint",
		"com/Apharma/sep4/WebAPI/Repos"})
public class Sep4Application
{
	public static void main(String[] args)
	{
		WebSocketClient client = new WebSocketClient("wss://iotnet.teracom.dk/app?token=vnoUcQAAABFpb3RuZXQudGVyYWNvbS5ka-iuwG5H1SHPkGogk2YUH3Y=");
		MiddlePointDecoder middlePointDecoder;

		ApplicationContext context = SpringApplication.
				run(Sep4Application.class,args);
		middlePointDecoder = context.getBean(MiddlePointDecoder.class);

		System.out.println("Contains dao  "+context.
				containsBeanDefinition("com.Apharma.sep4.DAO.ReadingDAO"));
		System.out.println("Contains B2  " + context.
				containsBeanDefinition("demoBeanB2"));
		System.out.println("Contains C   " + context.
				containsBeanDefinition("demoBeanC"));
		System.out.println("Contains D   " + context.
				containsBeanDefinition("demoBeanD"));


		Scanner keyboard = new Scanner(System.in);
		while(true){
			System.out.println("Try again? Y/Y?");
			if(keyboard.nextLine().equals("y")){
				middlePointDecoder.doIt();
			}
		}
	}
}
