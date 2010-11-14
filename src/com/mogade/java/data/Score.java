package com.mogade.java.data;

import com.mogade.java.helpers.Validator;

public class Score
{
   public static final int USERNAME_MAXLEN = 20;
   public static final int DATA_MAXLEN = 25;
   private String username;
   private String unique;
   private long points;
   private String data;

   private Score()
   {
   }
   private Score(String username, String unique, long points, String data)
   {
      this.username = username;
      this.unique = unique;
      this.points = points;
      this.data = data;
   }

   public static Score create(String username, String unique, long points)
   {
      return create(username, unique, points, null);
   }
   public static Score create(String username, String unique, long points, String data)
   {
      Validator.assertNotNullOrEmpty(username, "Invalid username");
      Validator.assertNotNullOrEmpty(unique, "Invalid unique");
      Validator.assertMaxLength(username, USERNAME_MAXLEN, "Invalid username, can't be more than " + USERNAME_MAXLEN + " chars");
      Validator.assertMaxLength(data, DATA_MAXLEN, "Invalid data, can't be more than " + DATA_MAXLEN + " chars");
      return new Score(username, unique, points, data);
   }

   public String getUsername()
   {
      return username;
   }
   public String getUnique()
   {
      return unique;
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