package com.mogade.java;

public interface MogadeConfiguration
{
   public void resetDefaults();

   public String getApiUrl();
   public void setApiUrl(String apiUrl);
   public int getConnectTimeout();
   public void setConnectTimeout(int connectTimeout);
   public int getReadTimeout();
   public void setReadTimeout(int readTimeout);
   public boolean getKeepAlive();
   public void setKeepAlive(boolean keepAlive);
}