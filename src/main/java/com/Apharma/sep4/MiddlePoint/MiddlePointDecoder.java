package com.Apharma.sep4.MiddlePoint;

import com.Apharma.sep4.DAO.ReadingDAO;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
public class MiddlePointDecoder
{
  private JSONObject receivedPayload; // JSONObject so we can extract the data more easily?

  @Autowired
  private ReadingDAO readingDAO;

  //private DatabaseHandler db; // something about Configuration and Bean is fishy. Research if we have to use it like this

  public MiddlePointDecoder(){

  }

  public JSONObject getReceivedPayload()
  {
    return receivedPayload;
  }

  public void setReceivedPayload(String receivedPayload)
  {
    try
    {
      this.receivedPayload = new JSONObject(receivedPayload);
      doIt();
    }
    catch (JSONException e)
    {
      e.printStackTrace();
    }
  }

  public void doIt(){
    decode(receivedPayload);
  }


  public void decode(JSONObject receivedPayload)
  {
    try
    {
      String data = receivedPayload.getString("data");
      int hum = Integer.parseInt(data,0,2,16); // radix describes the base we want our number in. 16 - hex, so on
      int temp = Integer.parseInt(data,3,6,16);
      float tempFinal = temp/100f;
      int co2 = Integer.parseInt(data,7,10,16);

      long ts = receivedPayload.getLong("ts");

//      DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//      String formattedDate = dateFormat.format(new Date(ts));
      //Date timestamp = new Date(ts);
      Date timestamp = new Date((ts + (3600*2*1000)));

      String roomId = receivedPayload.getString("EUI");

      readingDAO.storeNewEntry(hum, temp,co2,timestamp,roomId);


      System.out.println(hum + "\n" +
          tempFinal + "\n" +
          co2 + "\n" +
          timestamp + "\n" +
          roomId + "\n");
    }
    catch (JSONException e)
    {
      e.printStackTrace();
    }
  }
}
