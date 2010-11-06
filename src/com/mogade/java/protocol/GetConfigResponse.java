package com.mogade.java.protocol;

import com.mogade.java.data.Achievement;

import java.util.List;

public class GetConfigResponse extends GetConfigVersionResponse
{
   private List<Achievement> achievements;

   private GetConfigResponse()
   {
   }
   public GetConfigResponse(String info, String maintenance, String error)
   {
      super(info, maintenance, error);
   }

   public boolean hasAchievements()
   {
      return (achievements != null && achievements.size() > 0);
   }
   public List<Achievement> getAchievements()
   {
      return achievements;
   }
}