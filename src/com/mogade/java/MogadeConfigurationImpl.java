package com.mogade.java;

import com.mogade.java.helpers.Utility;

import java.net.MalformedURLException;
import java.net.URL;

public class MogadeConfigurationImpl implements MogadeConfiguration
{
   private static final MogadeConfiguration instance = new MogadeConfigurationImpl();
   private static final String APIURL_DEFAULT = "http://api.mogade.com/";
   private static final int CONNECTTIMEOUT_DEFAULT = 5000;
   private static final int READTIMEOUT_DEFAULT = 10000;
   private static final boolean KEEPALIVE_DEFAULT = false;

   private String apiUrl;
   private int connectTimeout;
   private int readTimeout;
   private boolean keepAlive;

   private MogadeConfigurationImpl()
   {
      resetDefaults();
   }
   public static MogadeConfiguration instance()
   {
      return instance;
   }

   public void resetDefaults()
   {
      apiUrl = APIURL_DEFAULT;
      connectTimeout = CONNECTTIMEOUT_DEFAULT;
      readTimeout = READTIMEOUT_DEFAULT;
      keepAlive = KEEPALIVE_DEFAULT;
   }
   public String getApiUrl()
   {
      return apiUrl;
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
      return connectTimeout;
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
      return readTimeout;
   }
   public void setReadTimeout(int readTimeout)
   {
      if (readTimeout < 0)
      {
         throw new IllegalArgumentException("Invalid readTimeout");
      }
      this.readTimeout = readTimeout;
   }
   public boolean getKeepAlive()
   {
      return keepAlive;
   }
   public void setKeepAlive(boolean keepAlive)
   {
      this.keepAlive = keepAlive;
   }
}