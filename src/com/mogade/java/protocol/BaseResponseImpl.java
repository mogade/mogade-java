package com.mogade.java.protocol;

import com.mogade.java.helpers.Utility;

public abstract class BaseResponseImpl implements Response
{
   private String error;
   public BaseResponseImpl()
   {
   }
   public BaseResponseImpl(String error)
   {
      this.error = error;
   }
   public boolean hasError()
   {
      return !Utility.isNullOrEmpty(error);
   }
   public String getError()
   {
      return error;
   }
}