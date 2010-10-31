package com.mogade.java.protocol;

public abstract class BaseRequestImpl implements Request
{
   private String key;
   private String v;
   private String sig;

   protected BaseRequestImpl(String key, String v)
   {
      this.key = key;
      this.v = v;
   }

   public void setSig(String sig)
   {
      this.sig = sig;
   }
}