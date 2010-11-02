package com.mogade.java.helpers;

import com.google.gson.JsonElement;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Utility
{
   public static boolean isNullOrEmpty(String s)
   {
      return (s == null || s.length() == 0);
   }
   public static Map<String,String> flattenJsonElement(JsonElement root, Map<String,String> elements)
   {
      if (elements == null)
      {
         elements = new HashMap<String,String>();
      }

      for (Map.Entry<String,JsonElement> element : root.getAsJsonObject().entrySet())
      {
         if (element.getValue().isJsonArray() || element.getValue().isJsonObject())
         {
            flattenJsonElement(element.getValue(), elements);
         }
         else
         {
            elements.put(element.getKey(), element.getValue().getAsString());
         }
      }

      return elements;
   }
   public static String md5(String s)
   {
      try
      {
         return encodeHexString(MessageDigest.getInstance("MD5").digest(s.getBytes("UTF-8")));
      }
      catch (UnsupportedEncodingException e)
      {
         throw new RuntimeException(e.getMessage());
      }
      catch (NoSuchAlgorithmException e)
      {
         throw new RuntimeException(e.getMessage());
      }
   }
   public static String encodeHexString(byte[] bytes)
   {
      final char[] toDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

      int l = bytes.length;
      char[] out = new char[l << 1];

      for (int i = 0, j = 0; i < l; i++)
      {
         out[j++] = toDigits[(0xF0 & bytes[i]) >>> 4];
         out[j++] = toDigits[0x0F & bytes[i]];
      }
      return new String(out);
   }
   public static String replace(String orig, String from, String to)
   {
      if (Utility.isNullOrEmpty(orig) || Utility.isNullOrEmpty(from))
      {
         return orig;
      }

      if (to == null)
      {
         to  = "";
      }

      int fromLength = from.length();
      int start = orig.indexOf(from);

      if (start == -1)
      {
         return orig;
      }

      StringBuffer buffer;

      if (to.length() >= fromLength)
      {
         if (from.equals(to))
         {
             return orig;
         }
         buffer = new StringBuffer(orig.length());
      }
      else
      {
         buffer = new StringBuffer();
      }

      char[] origChars = orig.toCharArray();

      int copyFrom = 0;
      while (start != -1)
      {
         buffer.append(origChars, copyFrom, start-copyFrom);
         buffer.append(to);
         copyFrom = start + fromLength;
         start = orig.indexOf (from, copyFrom);
      }

      buffer.append(origChars, copyFrom, origChars.length-copyFrom);
      return buffer.toString();
   }
   public static String format(String format, Object...o)
   {
      int cnt = o.length;

      if (cnt == 0)
      {
         return format;
      }

      for(int i = 0 ; i < cnt ; i++)
      {
         format = replace(format, "{"+i+"}", o[i].toString());
      }
      return format;
   }
}