package com.mogade.java.protocol;

public interface Response
{
   public String getInfo();
   public boolean isOk();
   public boolean isUnavailable();
   public boolean isError();
   public String getStatus();
}