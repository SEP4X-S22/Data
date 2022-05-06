package com.Apharma.sep4;

import com.Apharma.sep4.DAO.DatabaseHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class WebSocketClient implements WebSocket.Listener
{
	private WebSocket server = null;
	private DatabaseHandler db;
	
	// Send down-link message to device
	// Must be in Json format according to https://github.com/ihavn/IoT_Semester_project/blob/master/LORA_NETWORK_SERVER.md
	public void sendDownLink(String jsonTelegram)
	{
		server.sendText(jsonTelegram, true);
	}
	
	
	// E.g. url: "wss://iotnet.teracom.dk/app?token=??????????????????????????????????????????????="
	// Substitute ????????????????? with the token you have been given
	public WebSocketClient(String url)
	{
		HttpClient client = HttpClient.newHttpClient();
		CompletableFuture<WebSocket> socket = client.newWebSocketBuilder().buildAsync(URI.create(url), this);

		server = socket.join();
	}
	
	public void onOpen(WebSocket webSocket)
	{
		// This WebSocket will invoke onText, onBinary, onPing, onPong or onClose methods on the associated listener
		// (i.e. receive methods) up to n more times
		
		webSocket.request(1);
		System.out.println("WebSocket Listener is open for requests.");
	}
	
	public void onError(WebSocket webSocket, Throwable error)
	{
		System.out.println("A " + error.getCause() + " exception was thrown.");
		System.out.println("Message: " + error.getLocalizedMessage());
		webSocket.abort();
	}
	
	public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason)
	{
		System.out.println("WebSocket is closed.");
		System.out.println("Status: " + statusCode + " Reason: " + reason);
		return CompletableFuture.completedFuture("onClose() completed.").thenAccept(System.out::println);
	}
	
	public CompletionStage<?> onPing(WebSocket webSocket, ByteBuffer message)
	{
		webSocket.request(1);
		System.out.println("Ping: Client --> Server.");
		System.out.println(message.asCharBuffer());
		return CompletableFuture.completedFuture("Ping complete.").thenAccept(System.out::println);
	}
	
	public CompletionStage<?> onPong(WebSocket webSocket, ByteBuffer message)
	{
		webSocket.request(1);
		System.out.println("Pong: Client --> Server.");
		System.out.println(message.asCharBuffer().toString());
		return CompletableFuture.completedFuture("Pong completed.").thenAccept(System.out::println);
	}
	
	public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last)
	{
		String indented = null;
		try
		{
			indented = (new JSONObject(data.toString())).toString(4);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		System.out.println(indented);
		webSocket.request(1);
		
		return CompletableFuture.completedFuture("onText() completed.").thenAccept(System.out::println);
	}
	
	//TODO decide where to decode payload, create class for decoding & calling db
}
