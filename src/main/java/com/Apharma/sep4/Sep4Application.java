package com.Apharma.sep4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.Apharma.sep4.WebSocketClient.*;

import java.net.http.WebSocket;

@SpringBootApplication
public class Sep4Application
{
	public static void main(String[] args)
	{
		WebSocketClient client = new WebSocketClient("wss://iotnet.teracom.dk/app?token=vnoUcQAAABFpb3RuZXQudGVyYWNvbS5ka-iuwG5H1SHPkGogk2YUH3Y=");
		SpringApplication.run(Sep4Application.class, args);
	}

}
