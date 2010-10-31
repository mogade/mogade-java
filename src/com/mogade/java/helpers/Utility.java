package com.mogade.java.helpers;

import com.google.gson.JsonElement;
import org.apache.commons.codec.digest.DigestUtils;

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
      return DigestUtils.md5Hex(s);
   }
}