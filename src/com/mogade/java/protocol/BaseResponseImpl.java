package com.mogade.java.protocol;

import com.mogade.java.helpers.Utility;

public abstract class BaseResponseImpl implements Response
{
   private String info;
   private String maintenance;
   private String error;

   protected BaseResponseImpl()
   {
   }
   public BaseResponseImpl(String info, String maintenance, String error)
   {
      this.info = info;
      this.maintenance = maintenance;
      this.error = error;
   }

   public String getInfo()
   {
      return info;
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