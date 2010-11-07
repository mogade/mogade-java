package com.mogade.java.protocol;

public class GetConfigRequest extends BaseRequestImpl
{
   public GetConfigRequest(String key, int v)
   {
      super(key, v);
   }

   public RequestMethod getRequestMethod()
   {
      return RequestMethod.POST;
   }

   public String getUrl()
   {
      return "conf";
   }
}