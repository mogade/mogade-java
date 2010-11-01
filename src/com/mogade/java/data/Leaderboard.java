package com.mogade.java.data;

import com.google.gson.annotations.SerializedName;
import com.mogade.java.helpers.Utility;

public class Leaderboard
{
   public static enum Scope
   {
      DAILY(1), WEEKLY(2), OVERALL(3);

      private int value;
      Scope(int value)
      {
         this.value = value;
      }
      public int getValue()
      {
         return value;
      }
   }

   private String id;
   private int page;
   private int scope;

   private Leaderboard(String id, int page, Scope scope)
   {
      this.id = id;
      this.page = page;
      this.scope = scope.getValue();
   }
   public static Leaderboard create(String id, int page, Scope scope)
   {
      if (Utility.isNullOrEmpty(id))
      {
         throw new IllegalArgumentException("Invalid id");
      }
      if (page <=0 )
      {
         throw new IllegalArgumentException("Invalid page");
      }
      if (scope == null)
      {
         throw new IllegalArgumentException("Invalid scope");
      }
      return new Leaderboard(id, page, scope);
   }
}