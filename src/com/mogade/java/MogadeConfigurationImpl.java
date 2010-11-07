package com.mogade.java;

import com.mogade.java.helpers.Validator;

public class MogadeConfigurationImpl implements MogadeConfiguration
{
   private static final MogadeConfiguration instance = new MogadeConfigurationImpl();
   private static final String APIURL_DEFAULT = "http://api.mogade.com/api/";
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
      Validator.assertNotNullOrEmpty(apiUrl, "Invalid apiUrl");
      this.apiUrl = apiUrl;
   }
   public int getConnectTimeout()
   {
      return connectTimeout;
   }
   public void setConnectTimeout(int connectTimeout)
   {
      Validator.assertPositive(connectTimeout, "Invalid connectTimeout");
      this.connectTimeout = connectTimeout;
   }
   public int getReadTimeout()
   {
      return readTimeout;
   }
   public void setReadTimeout(int readTimeout)
   {
      Validator.assertPositive(readTimeout, "Invalid readTimeout");
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