package com.mogade.java.data;

import com.mogade.java.helpers.Validator;

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
      Validator.assertNotNullOrEmpty(id, "Invalid id");
      Validator.assertGreaterThanZero(page, "Invalid page");
      Validator.assertNotNull(scope, "Invalid scope");
      return new Leaderboard(id, page, scope);
   }
}