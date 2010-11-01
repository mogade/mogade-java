package com.mogade.java.tests;

import com.mogade.java.tests.functional.TestLive;

public class LiveRunner
{
   public static void main(String[] args) throws ClassNotFoundException
   {
      if (args.length == 2)
      {
         System.setProperty("http.proxyHost", args[0]);
         System.setProperty("http.proxyPort", args[1]);
      }

      Class clazz = TestLive.class;
      System.out.println("Testsuite: " + clazz.getName());
      int failed = DebugRunner.outputTestResult(org.junit.runner.JUnitCore.runClasses(clazz));

      if (failed == 0)
      {
         System.out.println("----> ALL TESTS PASSED <----");
      }
      else
      {
         System.out.println("----> FAILURES: " + failed + " <----");
      }
   }
}
