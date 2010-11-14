package com.mogade.java.data;

public class HighScore
{
   private String id;
   private long points;

   private HighScore()
   {
   }
   public HighScore(String id, long points)
   {
      this.id = id;
      this.points = points;
   }

   public String getLeaderboardId()
   {
      return id;
   }
   public long getPoints()
   {
      return points;
   }
}