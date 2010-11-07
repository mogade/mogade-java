package com.mogade.java.protocol;

import com.google.gson.annotations.SerializedName;
import com.mogade.java.data.Score;

public class SaveScoreRequest extends BaseRequestImpl
{
   @SerializedName("leaderboard_id")
   private String leaderboardId;
   private Score score;

   public SaveScoreRequest(String key, int v, String leaderboardId, Score score)
   {
      super(key, v);
      this.leaderboardId = leaderboardId;
      this.score = score;
   }

   public String getUrl()
   {
      return "scores";
   }
   public RequestMethod getRequestMethod()
   {
      return RequestMethod.PUT;
   }
}