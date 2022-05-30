package com.Apharma.sep4.RawData;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 Class for handling communication with Loriot server courtesy of Ib Havn with minor alterations to fit this project.
 Alterations include the addition of an instance variable of a MiddlePointDecoder object, a method to pass the
 payload to the decoder and a method call to add the payload to the logger in the MiddlePointDecoder class.
 
 @author Ib Havn
 @author 4X Data team
 @version 2.1 - 25.05.2022
 */
public class WebSocketClient implements WebSocket.Listener
{
	private WebSocket server = null;
	@Autowired
	private MiddlePointDecoder decoder;
	
	public WebSocketClient(String url)
	{
		HttpClient client = HttpClient.newHttpClient();
		CompletableFuture<WebSocket> socket = client.newWebSocketBuilder().buildAsync(URI.create(url), this);
		server = socket.join();
	}
	
	public void onOpen(WebSocket webSocket)
	{
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
	
	/**
	 Only alteration made to this method is to call the private handleReceivedPayload method and call the setLog method
	 on the decoder field.
	 */
	public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last)
	{
		String indented = null;
		try
		{
			indented = (new JSONObject(data.toString())).toString(4);
			handleReceivedPayload(indented);
			decoder.setLog(indented);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		System.out.println(indented);
		webSocket.request(1);
		
		return CompletableFuture.completedFuture("onText() completed").thenAccept(System.out::println);
	}
	
	public void sendDownLink(String jsonTelegram)
	{
		server.sendText(jsonTelegram, true);
	}
	
	/**
	 Method for setting the receivedPayload instance variable in the decoder.
	 
	 @param indentedPayload Json formatted String
	 */
	private void handleReceivedPayload(String indentedPayload)
	{
		decoder.setReceivedPayload(indentedPayload);
	}
}
