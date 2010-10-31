package com.mogade.java.data;

import com.mogade.java.helpers.Utility;

public class Score
{
   public static final int DATA_MAXLEN = 20;
   private String username;
   private long points;
   private String data;

   private Score(String username, long points, String data)
   {
      this.username = username;
      this.points = points;
      this.data = data;
   }

   public static Score create(String username, long points)
   {
      return create(username, points, null);
   }
   public static Score create(String username, long points, String data)
   {
      if (Utility.isNullOrEmpty(username))
      {
         throw new IllegalArgumentException("Invalid username");
      }
      if (!Utility.isNullOrEmpty(data) && data.length() > DATA_MAXLEN)
      {
         throw new IllegalArgumentException("Invalid data, can't be more than " + DATA_MAXLEN + " chars");
      }
      return new Score(username, points, data);
   }

   public String getUsername()
   {
      return username;
   }
   public long getPoints()
   {
      return points;
   }
   public String getData()
   {
      return data;
   }
}