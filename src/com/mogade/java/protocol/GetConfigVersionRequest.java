package com.mogade.java.protocol;

public class GetConfigVersionRequest extends BaseRequestImpl
{
   public GetConfigVersionRequest(String key, int v)
   {
      super(key, v);
   }
   public String getUrl()
   {
      return "conf/version";
   }
   public RequestMethod getRequestMethod()
   {
      return RequestMethod.POST;
   }
}