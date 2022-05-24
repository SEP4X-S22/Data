package com.Apharma.sep4.Model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class DownlinkPayload
{
	private String cmd;
	@JsonProperty("EUI")
	private String EUI;
	private int port;
	private boolean confirmed;
	private String data;
	
	public DownlinkPayload()
	{
	
	}
	
	public DownlinkPayload(String cmd, String EUI, int port, boolean confirmed, String data)
	{
		this.cmd = cmd;
		this.EUI = EUI;
		this.port = port;
		this.confirmed = confirmed;
		this.data = data;
	}
	
	public String getCmd()
	{
		return cmd;
	}
	
	public void setCmd(String cmd)
	{
		this.cmd = cmd;
	}
	
	public String getEUI()
	{
		return EUI;
	}
	
	public void setEUI(String EUI)
	{
		this.EUI = EUI;
	}
	
	public int getPort()
	{
		return port;
	}
	
	public void setPort(int port)
	{
		this.port = port;
	}
	
	public boolean isConfirmed()
	{
		return confirmed;
	}
	
	public void setConfirmed(boolean confirmed)
	{
		this.confirmed = confirmed;
	}
	
	public String getData()
	{
		return data;
	}
	
	public void setData(String data)
	{
		this.data = data;
	}
}
