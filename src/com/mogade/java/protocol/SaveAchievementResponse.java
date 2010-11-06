package com.mogade.java.protocol;

public class SaveAchievementResponse extends BaseResponseImpl
{
   private long points;

   private SaveAchievementResponse()
   {
   }
   public SaveAchievementResponse(String info, String maintenance, String error)
   {
      super(info, maintenance, error);
   }

   public long getPoints()
   {
      return points;
   }
}