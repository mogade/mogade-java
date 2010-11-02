package com.mogade.java.protocol;

import com.mogade.java.data.Score;

import java.util.ArrayList;
import java.util.List;

public class GetLeaderboardResponse extends BaseResponseImpl
{
   private List<Score> scores;
   private GetLeaderboardResponse()
   {
   }
   public GetLeaderboardResponse(String info, String maintenance, String error)
   {
      super(info, maintenance, error);
   }

   public List<Score> getScores()
   {
      return scores;
   }
}