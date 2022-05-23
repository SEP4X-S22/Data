package com.Apharma.sep4.MiddlePoint;

import com.Apharma.sep4.DAO.ReadingDAO;
import com.Apharma.sep4.Model.DownLinkPayload;
import com.Apharma.sep4.Model.Sensor;
import com.Apharma.sep4.Run.WebSocketClient;
import com.Apharma.sep4.WebAPI.Repos.SensorRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
    Date date = new Date(ts); // convert seconds to milliseconds
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy | HH:mm:ss"); // the format of your date
    return dateFormat.format(date);
  }
  
  public void createTelegram(int sensorId, int min, int max)
  {
    String roomId = sensorRepo.getRoomIdBySensorId(sensorId);
    DownLinkPayload downLinkPayload = new DownLinkPayload();

    String minConstraint = String.format("%04X", min & 0x0FFFFF);
    String maxConstraint = String.format("%04X", max & 0x0FFFFF);
    String data = minConstraint + maxConstraint;

		downLinkPayload.setEUI(roomId);
		downLinkPayload.setPort(1);
		downLinkPayload.setCmd("tx");
		downLinkPayload.setConfirmed(false);
		downLinkPayload.setData(data);

		convertToObjectToJson(downLinkPayload);
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
  
  public void convertToObjectToJson(DownLinkPayload downLinkPayload){
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

  private void sendDownLink(String json){
    client.sendDownLink(json);
  }

}

