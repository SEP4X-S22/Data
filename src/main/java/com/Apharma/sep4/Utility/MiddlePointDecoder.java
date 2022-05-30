package com.Apharma.sep4.Utility;

import com.Apharma.sep4.Model.DownlinkPayload;
import com.Apharma.sep4.Persistence.DatabaseDAO.iReadingDAO;
import com.Apharma.sep4.Persistence.Repos.SensorRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A class for logging and mutating communication data sent to and from the Data team's domain.
 *
 * @author 4X Data team
 * @author Ib Havn
 * @version 4.0 - 24.05.22
 */
@Component
public class MiddlePointDecoder
{
  private JSONObject receivedPayload = null;
  @Autowired
  private iReadingDAO readingDAO;
  private String telegram = null;
	private WebSocketClient client;
  @Autowired
  private SensorRepo sensorRepo;
  private String log = "";

  public MiddlePointDecoder(@Lazy WebSocketClient client)
  {
		this.client = client;
  }

  public JSONObject getReceivedPayload()
  {
    return receivedPayload;
  }
	
	/**
	 Setter for the receivedPayload field.
	 Called by custom method in WebSocketClient.
	 Has a try-catch block for a possible JSONException thrown during conversion of the String payload into a JSONObject.
	 
	 @param payload String of the data received from Loriot
	 */
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
  
  /**
   Method for mutating data received into variables needed for storing new Readings from a room.
   Filters the payload, only decoding and storing it if the 'cmd' field of the json is 'rx', meaning it is a new
   measurement from the IoT team. The 'data' field is extracted, sectioned and parsed into relevant variables. The
   temperature reading received from IoT is an integer they have multiplied by 10 before sending so that here it can
   be divided by 10 and converted to a double for a single decimal point precision.
   The Room ID is set to the 'EUI' part of the payload, the 'ts' long value is extracted and sent to a mutator method
   returning the time stamp in a specified format.
   Those variables are used as arguments for the ReadingDAO method for storing new entries in the database.
   
   Has a try-catch block for a possible JSONException thrown when extracting String fields from the JSONObject.
   
   @param receivedPayload JSONObject received from setReceivedPayload method.
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
        int light = Integer.parseInt(data, 11,14,16);
        String roomId = receivedPayload.getString("EUI");
  
        long ts = receivedPayload.getLong("ts");
        String formattedStringDate = tsToString(ts);

        readingDAO.storeNewEntry(hum, tempFinal, co2, light, formattedStringDate, roomId);
      }
    }
    catch (JSONException e)
    {
      e.printStackTrace();
    }
  }
  
  /**
   Method for converting a long into a String timestamp.
   Base code courtesy of Ib Havn, minor adjustments made to get the desired format.
   
   @param ts Long representing Epoch time when reading was taken
   @return String of the specified format timestamp
   */
  private String tsToString(long ts)
  {
    Date date = new Date(ts);
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy | HH:mm:ss");
    return dateFormat.format(date);
  }
  
  /**
   Method for creating a DownlinkPayload object, setting the payload instance variable and calling the private
   convertObjectToJson method to set the 'payload' field to the result of the data manipulation before calling the
   sendDownLink method.
   
   The ID of the Sensor is unique, therefore the SensorRepo is called to get the relevant Sensor and saving its Room
   ID as a String for later use. A new DownlinkPayload object is created and it's fields are set to appropriate
   values. The 'data' field is set to a concatenation of the upper and lower constraints when they've been converted
   to hexadecimal and filled out with zeroes if the values don't meet the byte length required.
   
   @param sensorId Integer ID of the Sensor the Readings are for
   @param min Integer of the lower constraint threshold for the Sensor
   @param max Integer of the upper constraint threshold for the Sensor
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
  
  private void convertObjectToJson(DownlinkPayload downLinkPayload){
    String json = null;
    try
    {
      json = new ObjectMapper().writeValueAsString(downLinkPayload);
    }
    catch (JsonProcessingException e)
    {
      e.printStackTrace();
    }
    setTelegram(json);

		System.out.println(json);
  }
  
  /**
   Method for calling the sendDownLink method on the WebSocketClient instance variable.
   
   @param json Json formatted String
   */
  private void sendDownLink(String json)
  {
    client.sendDownLink(json);
  }
  
  /**
   Method for logging the communication between Data and the other domains. Called from WebSocketClient class.
   
   Set a String prefix with whether the payload is an uplink or downlink message and labels them accordingly based on
   the presence of the value 'tx'. Subsequently, the 'log' instance variable is updated by concatenating the
   predefined header it is initialized with and the new addition of the payload contents, the relevant prefix and a
   timestamp. The timestamp is created from calling the tsToString method on the current time in milliseconds with
   the addition of two hours due to being hosted in a location using GMT to make it the local time.
   
   @param payload String containing the data in a raw Json format
   */
  public void setLog(String payload)
  {
    String good = formatter(payload);
    String date = tsToString(System.currentTimeMillis() + 2*3600*1000);
    String prefix;
    if(payload.contains("\"rx\"") || payload.contains("\"tx\"") || payload.contains("\"txd\""))
    {
      if (payload.contains("\"tx\""))
      {
        prefix = "DOWNLINK Message (From Android)";
      }
      else if (payload.contains("\"txd\""))
      {
        prefix = "UPLINK Message (Confirmation message for Android)";
      }
      else
      {
        prefix = "UPLINK Message (From IoT)";
      }
      log = log + "<br> <b style=\"color:#008F11;\">" + date + " - " + prefix + "</b><p style=\"color:#00FF41;\">" + good + "</p>  ";
    }
  }

  public String getLog()
  {
    return "<html> <head> <h3 style=\"color:#003B00;\"> PAYLOAD LOGGER </h3></head><body style=\"background-color:black;\"> "
            + log + "</body></html>";
  }
  
  /**
   Method for clearing the messages stored in the log.
   */
  public void clearLog()
  {
    log = "";
  }
  
  /**
   Method to prettify raw data in the log without browser extensions.
   
   @param payload String of Json data
   @return formatted String for the log
   */
  private String formatter(String payload)
  {
    payload = payload.replace("{", "{<br>&nbsp&nbsp&nbsp&nbsp");
    payload = payload.replace(",", ",<br>&nbsp&nbsp&nbsp&nbsp");
    payload = payload.replace("}","<br>}<br>");
    return payload;
  }
}

