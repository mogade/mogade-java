package com.mogade.java.tests;

import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

public class DebugRunner
{
   public static Class[] getClasses(String pckgname) throws ClassNotFoundException
   {
      ArrayList<Class> classes = new ArrayList<Class>();
      File directory = null;
      try
      {
         ClassLoader cld = Thread.currentThread().getContextClassLoader();
         if (cld == null)
         {
            throw new ClassNotFoundException("Can't get class loader.");
         }
         String path = pckgname.replace('.', '/');
         URL resource = cld.getResource(path);
         if (resource == null)
         {
            throw new ClassNotFoundException("No resource for " + path);
         }
         directory = new File(resource.getFile());
      }
      catch (NullPointerException x)
      {
         throw new ClassNotFoundException(pckgname + " (" + directory + ") does not appear to be a valid package");
      }
      if (directory.exists())
      {
         // Get the list of the files contained in the package
         String[] files = directory.list();
         for (int i = 0; i < files.length; i++)
         {
            // we are only interested in .class files
            if (files[i].endsWith(".class"))
            {
               // removes the .class extension
               classes.add(Class.forName(pckgname + '.' + files[i].substring(0, files[i].length() - 6)));
            }
         }
      }
      else
      {
         throw new ClassNotFoundException(pckgname + " does not appear to be a valid package");
      }
      return classes.toArray(new Class[classes.size()]);
   }
   public static int outputTestResult(Result result)
   {
      StringBuilder sb = new StringBuilder();
      if (result.wasSuccessful())
      {
         sb.append("[SUCCESS] ");
      }
      else
      {
         sb.append("[FAIED] ");
      }

      sb.append("Tests run: " + result.getRunCount() + ", ");
      sb.append("Failures: " + result.getFailureCount() + ", ");
      sb.append("Igorned: " + result.getIgnoreCount() + ", ");
      sb.append("Time elapsed: " + result.getRunTime() + " sec");

      if (result.getFailureCount() > 0)
      {
         for(Failure failure : result.getFailures())
         {
            System.out.println(failure.getDescription());
            System.out.println(failure.getMessage());
            System.out.println(failure.getTrace());
         }
      }

      System.out.println(sb.append("\n").toString());
      return result.getFailureCount();
   }

   public static void main(String[] args) throws ClassNotFoundException
   {
      if (args.length == 2)
      {
         System.setProperty("http.proxyHost", args[0]);
         System.setProperty("http.proxyPort", args[1]);
      }

      String[] packages = new String[] {"com.mogade.java.tests.unit", "com.mogade.java.tests.functional"};

      int failed = 0;
      
      for (String p : packages)
      {
         for(Class clazz : getClasses(p))
         {
            System.out.println("Testsuite: " + clazz.getName());
            failed += outputTestResult(org.junit.runner.JUnitCore.runClasses(clazz));
         }
      }

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