package com.mogade.java.protocol;

import com.google.gson.annotations.SerializedName;

public class SaveAchievementRequest extends BaseRequestImpl
{
   @SerializedName("achievement_id")
   private String achievementId;
   private String username;
   private String unique;

   public SaveAchievementRequest(String key, int v, String achievementId, String username, String unique)
   {
      super(key, v);
      this.achievementId = achievementId;
      this.username = username;
      this.unique = unique;
   }

   public String getUrl()
   {
      return "achievements";
   }
   public RequestMethod getRequestMethod()
   {
      return RequestMethod.PUT;
   }
}