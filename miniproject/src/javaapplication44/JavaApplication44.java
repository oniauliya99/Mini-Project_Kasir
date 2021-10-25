/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication44;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Auliya-Oni
 */
public class JavaApplication44 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
      list.add("Raja");
      list.add("Jai");
      list.add("Adithya");
      JSONArray array = new JSONArray();
      for(int i = 0; i < list.size(); i++) {
         array.put(list.get(i));
      }
      JSONObject obj = new JSONObject();
      try {
         obj.put("Employee Names:", array);
      } catch(JSONException e) {
         e.printStackTrace();
      }
      System.out.println(obj.toString());
    }
    
}
