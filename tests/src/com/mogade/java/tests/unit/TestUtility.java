package com.mogade.java.tests.unit;

import static junit.framework.Assert.*;

import com.google.gson.Gson;
import com.mogade.java.helpers.Utility;
import com.mogade.java.tests.Obj;
import com.mogade.java.tests.ObjEmbedded;
import org.junit.Test;

import java.util.Map;

public class TestUtility
{
   @Test
   public void testisNullOrEmpty()
   {
      assertTrue(Utility.isNullOrEmpty(null));
      assertTrue(Utility.isNullOrEmpty(""));
      assertFalse(Utility.isNullOrEmpty("brian"));
   }
   @Test
   public void testMD5()
   {
      assertEquals("d41d8cd98f00b204e9800998ecf8427e", Utility.md5(""));
      assertEquals("9e107d9d372bb6826bd81d3542a419d6", Utility.md5("The quick brown fox jumps over the lazy dog"));
      assertEquals("e4d909c290d0fb1ca068ffaddf22cbd0", Utility.md5("The quick brown fox jumps over the lazy dog."));
   }
   @Test
   public void testFlattenJsonElement()
   {
      Obj obj = new Obj();
      obj.key = "keyvalue";
      obj.v = "1";
      obj.leaderboard_id = "myleaderboard";
      obj.score = new ObjEmbedded();
      obj.score.username = "brian";
      obj.score.points = 2000;
      obj.score.data = "nothing";

      Map<String,String> flat = Utility.flattenJsonElement(new Gson().toJsonTree(obj), null);

      assertEquals(flat.size(), 6);

      assertEquals(flat.get("key"), "keyvalue");
      assertEquals(flat.get("v"), "1");
      assertEquals(flat.get("leaderboard_id"), "myleaderboard");
      assertEquals(flat.get("username"), "brian");
      assertEquals(flat.get("points"), "2000");
      assertEquals(flat.get("data"), "nothing");
   }
   @Test
   public void testReplace()
   {
      assertEquals("white fox jump", Utility.replace("brown fox jump", "brown", "white"));
      assertEquals("brown fox swim", Utility.replace("brown fox jump", "jump", "swim"));
      assertEquals("brown rabbit jump", Utility.replace("brown fox jump", "fox", "rabbit"));
   }
   @Test
   public void testFormat()
   {
      assertEquals("http://127.0.0.1:9000/", Utility.format("http://{0}:{1}/", "127.0.0.1", 9000));
   }
}