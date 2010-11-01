package com.mogade.java.protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mogade.java.helpers.Utility;

import java.util.Map;
import java.util.TreeSet;

public abstract class BaseRequestImpl implements Request
{
   private static final Gson gson = new GsonBuilder().create();
   private String key;
   private int v;
   private String sig;

   protected BaseRequestImpl(String key, int v)
   {
      this.key = key;
      this.v = v;
   }

   public void setSig(String sig)
   {
      this.sig = sig;
   }
   public String calculateSignature(String secret)
   {
      Map<String,String> elements = Utility.flattenJsonElement(gson.toJsonTree(this), null);
      StringBuilder sb = new StringBuilder(100);

      for (String k : new TreeSet<String>(elements.keySet()))
      {
         sb.append(k);
         sb.append("=");
         sb.append(elements.get(k));
         sb.append("&");
      }

      sb.append(secret);
      return Utility.md5(sb.toString());
   }
}