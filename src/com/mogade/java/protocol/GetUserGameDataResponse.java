package com.mogade.java.protocol;

import com.mogade.java.data.HighScore;

import java.util.List;

public class GetUserGameDataResponse extends BaseResponseImpl
{
   private List<String> achievements;
   private List<HighScore> leaderboards;

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
   public boolean hasHighScores()
   {
      return (leaderboards != null && leaderboards.size() > 0);
   }
   public List<HighScore> getHighScores()
   {
      return leaderboards;
   }
}