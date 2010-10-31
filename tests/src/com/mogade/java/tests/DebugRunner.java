package com.mogade.java.tests;

import com.mogade.java.tests.unit.TestMogade;
import com.mogade.java.tests.unit.TestScore;
import com.mogade.java.tests.unit.TestUtility;

public class DebugRunner
{
   public static void main(String[] args)
   {
      if (args.length == 2)
      {
         System.setProperty("http.proxyHost", args[0]);
         System.setProperty("http.proxyPort", args[1]);
      }
      org.junit.runner.JUnitCore.runClasses(TestMogade.class);
      org.junit.runner.JUnitCore.runClasses(TestScore.class);
      org.junit.runner.JUnitCore.runClasses(TestUtility.class);
      org.junit.runner.JUnitCore.runClasses(com.mogade.java.tests.functional.TestMogade.class);
   }
}