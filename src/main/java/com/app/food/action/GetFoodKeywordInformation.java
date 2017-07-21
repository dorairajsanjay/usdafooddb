package com.app.food.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.json.simple.JSONValue;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class GetFoodKeywordInformation extends ActionSupport {

	private String name;

	// private String jsonString;

	private List<Object> myList;

	private StringBuilder builder;

	private JSONArray jsonArray = new JSONArray();

	// all struts logic here
	public String execute() {

		builder = new StringBuilder();

		// builder.append(str)
		try {

			Connection connection = NewDBConnection.getConenction();

			// System.out.println(" This is the product Name = " + name);

			name = name.trim();

			String query = "select code,product_name,serving_size,sugars_100g,salt_100g,fat_100g,ingredients_list from food where product_name like '%"
					+ name + "%' limit 10;";

			// System.out.println(query);
			Statement stmt = connection.createStatement();

			ResultSet result = stmt.executeQuery(query);

			String code;
			String product_name;
			String serving_size;
			String sugars_100g;
			String salt_100g;
			String fat_100g;
			String ingredients_list;

			// jsonString = getJSONFromResultSet(result, "x1foods");
			while (result.next()) {

				JSONObject json = new JSONObject();
				code = result.getString("code");
				product_name = result.getString("product_name");
				serving_size = result.getString("serving_size");
				sugars_100g = result.getString("sugars_100g");
				salt_100g = result.getString("salt_100g");
				fat_100g = result.getString("fat_100g");
				ingredients_list = result.getString("ingredients_list");

				// System.out.println(code);
				// JSONArray ingreList = new JSONArray();
				// String[] ingrre_split = ingredients_list.split(" ");
				//
				// for (int i = 0; i < ingrre_split.length; i++) {
				//
				// ingreList.add(i, ingrre_split[i]);
				// }
				//
				// json.put("ingredients", ingreList);
				//
				// JSONArray ingredientsList = new JSONArray();
				// ingredientsList.add(ingreList);
				//
				// JSONArray nutrientsList = new JSONArray();
				// JSONObject sugarObj = new JSONObject();
				//
				// sugarObj.put("name", "sugar");
				// sugarObj.put("amount_per_serving", sugars_100g);
				// nutrientsList.add(sugarObj);
				//
				// JSONObject fatObj = new JSONObject();
				//
				// fatObj.put("name", "fat");
				// fatObj.put("amount_per_serving", fat_100g);
				// nutrientsList.add(fatObj);
				//
				// JSONObject saltObj = new JSONObject();
				//
				// saltObj.put("name", "salt");
				// saltObj.put("amount_per_serving", salt_100g);
				// nutrientsList.add(saltObj);
				//
				//
				// json.put("nutrients", nutrientsList);

				json.put("id", code);
				// json.put("image",
				// "https://images-na.ssl-images-amazon.com/images/I/91fewjUnnuL._SL1500_.jpg");
				// json.put("backdropImage",
				// "http://www.quillafoods.com/wp-content/uploads/2014/10/Organic-Quinoa-Red-Seeds-Scoop.jpg");

				json.put("name", product_name);
				// json.put("serving_size", serving_size);
				// jsonString
				
				jsonArray.add(json);


			}


		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Action.SUCCESS;

	}


	//
	// public String getProductname() {
	// return productname;
	// }
	//
	// public void setProductname(String productname) {
	// this.productname = productname;
	// }

	// public String getJsonString() {
	// return jsonString;
	// }
	//
	// public void setJsonString(String jsonString) {
	// this.jsonString = jsonString;
	// }

	public List<Object> getMyList() {
		return myList;
	}

	public void setMyList(List<Object> myList) {
		this.myList = myList;
	}

	public JSONArray getJsonArray() {
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}