package com.mogade.java.protocol;

public class GetUserGameDataRequest extends BaseRequestImpl
{
   private String username;
   private String unique;

   public GetUserGameDataRequest(String key, int v, String username, String unique)
   {
      super(key, v);
      this.username = username;
      this.unique = unique;
   }

   public RequestMethod getRequestMethod()
   {
      return RequestMethod.POST;
   }
   public String getUrl()
   {
      return "conf/my";
   }
}