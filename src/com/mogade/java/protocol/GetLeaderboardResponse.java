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
   public GetLeaderboardResponse(String maintenance, String error)
   {
      super(maintenance, error);
   }

   public List<Score> getScores()
   {
      return scores;
   }
}