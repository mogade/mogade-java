package com.mogade.java.protocol;

import com.mogade.java.data.Leaderboard;

public class GetLeaderboardRequest extends BaseRequestImpl
{
   private Leaderboard leaderboard;

   public GetLeaderboardRequest(String key, int v, Leaderboard leaderboard)
   {
      super(key, v);
      this.leaderboard = leaderboard;
   }

   public String getUrl()
   {
      return "scores";
   }
   public RequestMethod getRequestMethod()
   {
      return RequestMethod.POST;
   }
}