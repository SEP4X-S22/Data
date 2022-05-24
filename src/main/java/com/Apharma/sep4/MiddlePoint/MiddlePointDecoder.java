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

/*
* Date formatting method "tsToString" courtesy of Ib Havn
*/

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
	
	/*
	* Taking JSONObject argument, sectioning it and filtering by the 'cmd' field
	* Only decoding if the 'cmd' field equals 'rx'
	* 	Then the string from the 'data' field is extracted, sectioned down and converted from hex to integer
	* 		To get a single decimal point precision double the IoT team multiplied the temperature sensor reading by 10
	* 		Reversing that with division gives us a double for the temperature reading
	* 	The room ID is extracted and used unaltered, the ID corresponds to the device EUI code
	* 	The timestamp is taken from the 'ts' field of the payload and sent for conversion & formatting
	* Then the ReadingDAO is called to store a new Reading entry using the extracted and transformed data
	*/
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
	
	/*
	* Method for converting a long argument into a java.util.Date object and returning a timestamp in a given format
	* Courtesy of Ib Havn
	*/
	private String tsToString(long ts)
	{
		//TODO add reference to Ib for date changing code
		Date date = new Date(ts);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy | HH:mm:ss");
		return dateFormat.format(date);
	}
	
	/*
	* Creating a DownlinkPayload object adhering to the LoRaWAN downlink format constraints
	* Taking a constraint for the minimum allowed value and the maximum allowed value for the sensor with the ID
	* The integer arguments are converted to a string and formatted to hex
	* 	If the hex value of the constraint does not meet the byte length requirement the rest is filled out with zeroes
	* 	The two strings of the min and max constraints are then concatenated and inserted into the 'data' field
	* The 'EUI' field is set from calling the SensorRepo with the sensor ID to get the ID of the room the sensor is in
	* The port number is set to the agreed port number between Data and IoT
	* When the DownlinkPayload instance variables have been set it is sent to convertObjectToJson method
	* Lastly the sendDownLink method is called
	*/
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
		
		convertObjectToJson(downlinkPayload);
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
	
	/*
	* This method takes a DownlinkPayload object and converts it into a JSONObject
	* It calls a method to set the string telegram instance variable to the string JSONObject
	*/
	public void convertObjectToJson(DownlinkPayload downlinkPayload)
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
	
	/*
	* Calling the WebSocketClient instance variable and calling the sendDownLink method with the string argument
	*/
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

