package com.mogade.java.protocol;

public class SaveScoreResponse extends BaseResponseImpl
{
   private long daily;
   private long weekly;
   private long overall;

   private SaveScoreResponse()
   {
   }
   public SaveScoreResponse(String info, String maintenance, String error)
   {
      super(info, maintenance, error);
   }
   public long getDaily()
   {
      return daily;
   }
   public long getWeekly()
   {
      return weekly;
   }
   public long getOverall()
   {
      return overall;
   }
}