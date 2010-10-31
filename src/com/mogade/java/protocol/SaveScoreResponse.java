package com.mogade.java.protocol;

public class SaveScoreResponse extends BaseResponseImpl
{
   private long daily;
   private long weekly;
   private long overall;

   public SaveScoreResponse()
   {
   }
   public SaveScoreResponse(String error)
   {
      super(error);
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