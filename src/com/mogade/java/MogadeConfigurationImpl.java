package com.mogade.java;

import com.mogade.java.helpers.Utility;

import java.net.MalformedURLException;
import java.net.URL;

public class MogadeConfigurationImpl implements MogadeConfiguration
{
   private static final MogadeConfiguration instance = new MogadeConfigurationImpl();
   private static final String APIURL = "http://api.mogade.com/";
   private static final int CONNECTTIMEOUT = 5000;
   private static final int READTIMEOUT = 10000;

   private String apiUrl = null;
   private int connectTimeout = -1;
   private int readTimeout = -1;

   private MogadeConfigurationImpl()
   {
   }
   public static MogadeConfiguration instance()
   {
      return instance;
   }

   public void resetDefaults()
   {
      apiUrl = null;
      connectTimeout = -1;
      readTimeout = -1;
   }
   public String getApiUrl()
   {
      return Utility.isNullOrEmpty(apiUrl) ? APIURL : apiUrl;
   }
   public void setApiUrl(String apiUrl)
   {
      if (Utility.isNullOrEmpty(apiUrl))
      {
         throw new IllegalArgumentException("Invalid apiUrl");
      }
      this.apiUrl = apiUrl;
   }
   public int getConnectTimeout()
   {
      return (connectTimeout == -1) ? CONNECTTIMEOUT : connectTimeout;
   }
   public void setConnectTimeout(int connectTimeout)
   {
      if (connectTimeout < 0)
      {
         throw new IllegalArgumentException("Invalid connectTimeout");
      }
      this.connectTimeout = connectTimeout;
   }
   public int getReadTimeout()
   {
      return (readTimeout == -1) ? READTIMEOUT : readTimeout;
   }
   public void setReadTimeout(int readTimeout)
   {
      if (readTimeout < 0)
      {
         throw new IllegalArgumentException("Invalid readTimeout");
      }
      this.readTimeout = readTimeout;
   }
}