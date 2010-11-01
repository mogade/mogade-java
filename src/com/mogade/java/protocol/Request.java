package com.mogade.java.protocol;

public interface Request
{
   public enum RequestMethod {GET, POST, PUT}
   public abstract String getUrl();
   public abstract RequestMethod getRequestMethod();
   public void setSig(String sig);
   public String calculateSignature(String secret);
}