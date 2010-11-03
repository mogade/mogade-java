package com.mogade.java.helpers;

import com.mogade.java.MogadeException;

public class Validator
{
   public static void assertNotNullOrEmpty(String s, String msg)
   {
      if (Utility.isNullOrEmpty(s))
      {
         throw new MogadeException(msg);
      }
   }
   public static void assertNotNull(Object o, String msg)
   {
      if (o == null)
      {
         throw new MogadeException(msg);
      }
   }
   public static void assertMaxLength(String s, int length, String msg)
   {
      if (Utility.isNullOrEmpty(s))
      {
         return;
      }
      if (s.length() > length)
      {
         throw new MogadeException(msg);
      }
   }
   public static void assertGreaterThanZero(int i, String msg)
   {
      if (i < 1)
      {
         throw new MogadeException(msg);
      }
   }
   public static void assertPositive(int i, String msg)
   {
      if (i < 0)
      {
         throw new MogadeException(msg);
      }
   }
}