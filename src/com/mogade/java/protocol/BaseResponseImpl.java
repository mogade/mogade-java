package com.mogade.java.protocol;

import com.mogade.java.helpers.Utility;

public abstract class BaseResponseImpl implements Response
{
   private String maintenance;
   private String error;
   public BaseResponseImpl()
   {
   }
   public BaseResponseImpl(String maintenance, String error)
   {
      this.maintenance = maintenance;
      this.error = error;
   }

   public boolean isOk()
   {
      return (!isUnavailable() && !isError());
   }
   public boolean isUnavailable()
   {
      return !Utility.isNullOrEmpty(maintenance);
   }
   public boolean isError()
   {
      return !Utility.isNullOrEmpty(error);
   }
   public String getStatus()
   {
      if (isError())
      {
         return error;
      }
      if (isUnavailable())
      {
         return maintenance;
      }
      return null;
   }
}