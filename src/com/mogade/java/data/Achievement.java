package com.mogade.java.data;

public class Achievement
{
   private String id;
   private String name;
   private String desc;
   private long points;

   private Achievement()
   {
   }
   public Achievement(String id, String name, String desc, long points)
   {
      this.id = id;
      this.name = name;
      this.desc = desc;
      this.points = points;
   }

   public String getId()
   {
      return id;
   }
   public String getName()
   {
      return name;
   }
   public String getDescription()
   {
      return desc;
   }
   public long getPoints()
   {
      return points;
   }
}