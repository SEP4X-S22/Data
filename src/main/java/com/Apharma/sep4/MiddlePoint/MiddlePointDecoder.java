package com.Apharma.sep4.MiddlePoint;

import com.Apharma.sep4.Persistence.DAO.ReadingDAO;
import com.Apharma.sep4.Model.DownlinkPayload;
import com.Apharma.sep4.Run.WebSocketClient;
import com.Apharma.sep4.WebAPI.Repos.SensorRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

//Date formatting method "tsToString" courtesy of Ib Havn

@Component
public class MiddlePointDecoder
{
	private JSONObject receivedPayload = null;
	@Autowired
	private ReadingDAO readingDAO;
	private String telegram = null;
	private WebSocketClient client;
	@Autowired
	private SensorRepo sensorRepo;
	private String log = "<html> <head> <h3> PAYLOAD LOGGER </h3></head><body> ";
	
	public MiddlePointDecoder(@Lazy WebSocketClient client)
	{
		this.client = client;
	}
	
	public JSONObject getReceivedPayload()
	{
		return receivedPayload;
	}
	
	public void setReceivedPayload(String payload)
	{
		try
		{
			receivedPayload = new JSONObject(payload);
			decode(receivedPayload);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}
	
	public void decode(JSONObject receivedPayload)
	{
		try
		{
			if (receivedPayload.getString("cmd").equals("rx"))
			{
				String data = receivedPayload.getString("data");
				int hum = Integer.parseInt(data, 0, 2, 16); // radix describes the base we want our number in. 16 - hex, so on
				int temp = Integer.parseInt(data, 3, 6, 16);
				double tempFinal = temp / 10d;
				int co2 = Integer.parseInt(data, 7, 10, 16);
				String roomId = receivedPayload.getString("EUI");
				
				long ts = receivedPayload.getLong("ts");
				String formattedStringDate = tsToString(ts);
				
				readingDAO.storeNewEntry(hum, tempFinal, co2, formattedStringDate, roomId);
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}
	
	private String tsToString(long ts)
	{
		//TODO add reference to Ib for date changing code
		Date date = new Date(ts);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy | HH:mm:ss");
		return dateFormat.format(date);
	}
	
	public void createTelegram(int sensorId, int min, int max)
	{
		String roomId = sensorRepo.getRoomIdBySensorId(sensorId);
		DownlinkPayload downlinkPayload = new DownlinkPayload();
		
		String minConstraint = String.format("%04X", min & 0x0FFFFF);
		String maxConstraint = String.format("%04X", max & 0x0FFFFF);
		String data = minConstraint + maxConstraint;
		
		downlinkPayload.setEUI(roomId);
		downlinkPayload.setPort(1);
		downlinkPayload.setCmd("tx");
		downlinkPayload.setConfirmed(false);
		downlinkPayload.setData(data);
		
		convertToObjectToJson(downlinkPayload);
		sendDownLink(getTelegram());
	}
	
	public String getTelegram()
	{
		return telegram;
	}
	
	public void setTelegram(String telegram)
	{
		this.telegram = telegram;
	}
	
	public void convertToObjectToJson(DownlinkPayload downlinkPayload)
	{
		String json = null;
		try
		{
			json = new ObjectMapper().writeValueAsString(downlinkPayload);
		}
		catch (JsonProcessingException e)
		{
			e.printStackTrace();
		}
		setTelegram(json);
		
		System.out.println(json);
	}
	
	private void sendDownLink(String json)
	{
		client.sendDownLink(json);
	}
 
	public void setLog(String payload)
	{
		String date = tsToString(System.currentTimeMillis() + 2 * 3600 * 1000);
		log = log + "<br> <i>" + date + "</i> " + payload + "</body> </html>  ";
	}
	
	public String getLog()
	{
		return log;
	}
	
	public void clearLog()
	{
		log = "<html> <head> <h3> PAYLOAD LOGGER </h3> </head><body> ";
	}
}

