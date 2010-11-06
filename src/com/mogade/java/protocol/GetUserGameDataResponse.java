package com.mogade.java.protocol;

import java.util.List;

public class GetUserGameDataResponse extends BaseResponseImpl
{
   private List<String> achievements;

   private GetUserGameDataResponse()
   {
   }
   public GetUserGameDataResponse(String info, String maintenance, String error)
   {
      super(info, maintenance, error);
   }

   public boolean hasAchievements()
   {
      return (achievements != null && achievements.size() > 0);
   }
   public List<String> getAchievements()
   {
      return achievements;
   }
}