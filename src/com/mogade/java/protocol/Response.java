package com.mogade.java.protocol;

public interface Response
{
   public boolean isOk();
   public boolean isUnavailable();
   public boolean isError();
   public String getStatus();
}