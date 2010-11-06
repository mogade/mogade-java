package com.mogade.java.protocol;

public class GetConfigVersionResponse extends BaseResponseImpl
{
   private int version;

   protected GetConfigVersionResponse()
   {
   }
   public GetConfigVersionResponse(String info, String maintenance, String error)
   {
      super(info, maintenance, error);
   }

   public int getVersion()
   {
      return version;
   }
}