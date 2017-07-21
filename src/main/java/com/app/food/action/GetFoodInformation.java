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

public class GetFoodInformation extends ActionSupport {

	private String name;

	// private String jsonString;

	private List<Object> myList;

	private StringBuilder builder;

	private JSONObject json = new JSONObject();

	// all struts logic here
	public String execute() {

		HashMap<Integer,String> map = new HashMap<Integer,String>();
		map.put(0,
				"https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Good_Food_Display_-_NCI_Visuals_Online.jpg/1200px-Good_Food_Display_-_NCI_Visuals_Online.jpg");
		map.put(1,
				"http://trichilofoods.com/site/wp-content/uploads/2015/06/veggies.jpg");

		map.put(2,
				"http://static.wixstatic.com/media/d0f16d_2a74bb0f4f9d438b887101d51113d046~mv2_d_2500_1563_s_2.jpg");

		map.put(3,
				"http://www.centerforfoodsafety.org/thumbs/1900x1150/files/zcr/istock-91757065_36826_mirror.png");

		map.put(4,
				"http://foodlawstudentnetwork.org/wp-content/uploads/2016/03/photo-1437750769465-301382cdf094.jpeg");

		map.put(5,
				"https://foodtank.com/wp-content/uploads/2017/03/Food-Tank-Keri-Glassman-interview.jpg");

		map.put(6,
				"https://az616578.vo.msecnd.net/files/responsive/cover/main/desktop/2016/07/06/636034360643459128-507613226_indian.jpg");

		map.put(7, "http://dreamatico.com/data_images/food/food-6.jpg");

		map.put(8,
				"http://media3.s-nbcnews.com/i/newscms/2017_22/1218158/healthiest-food-choices-for-summer-today-170531-1_82d1d624c2c929c856d7318ac7269e7e.jpg");

		map.put(9,
				"http://www.slate.com/content/dam/slate/articles/health_and_science/science/2015/11/151119_SCI_salmon-gmo.jpg.CROP.promo-xlarge2.jpg");

		builder = new StringBuilder();

		// builder.append(str)
		try {

			Connection connection = NewDBConnection.getConenction();

			System.out.println(" This is the product Name = " + name);

			name = name.trim();

			String query = "select code,product_name,serving_size,sugars_100g,salt_100g,fat_100g,ingredients_list from food where product_name like '%"
					+ name + "%' limit 1";

			System.out.println(query);
			Statement stmt = connection.createStatement();

			ResultSet result = stmt.executeQuery(query);

			String code;
			String product_name;
			String serving_size;
			String sugars_100g;
			String salt_100g;
			String fat_100g;
			String ingredients_list;

			String similar;

			// jsonString = getJSONFromResultSet(result, "x1foods");
			while (result.next()) {

				code = result.getString("code");
				product_name = result.getString("product_name");
				serving_size = result.getString("serving_size");
				sugars_100g = result.getString("sugars_100g");
				salt_100g = result.getString("salt_100g");
				fat_100g = result.getString("fat_100g");
				ingredients_list = result.getString("ingredients_list");
				similar = ingredients_list;
				// System.out.println(code);
				JSONArray ingreList = new JSONArray();
				String[] ingrre_split = ingredients_list.split(" ");

				for (int i = 0; i < ingrre_split.length; i++) {

					ingreList.add(i, ingrre_split[i]);
				}

				json.put("ingredients", ingreList);

				JSONArray ingredientsList = new JSONArray();
				ingredientsList.add(ingreList);

				JSONArray nutrientsList = new JSONArray();
				JSONObject sugarObj = new JSONObject();

				sugarObj.put("name", "sugar");
				sugarObj.put("amount_per_serving", sugars_100g);
				nutrientsList.add(sugarObj);

				JSONObject fatObj = new JSONObject();

				fatObj.put("name", "fat");
				fatObj.put("amount_per_serving", fat_100g);
				nutrientsList.add(fatObj);

				JSONObject saltObj = new JSONObject();

				saltObj.put("name", "salt");
				saltObj.put("amount_per_serving", salt_100g);
				nutrientsList.add(saltObj);


				json.put("nutrients", nutrientsList);

				json.put("id", code);
				// json.put("image",
				// "https://images-na.ssl-images-amazon.com/images/I/91fewjUnnuL._SL1500_.jpg");

				String val = code.substring(code.length() - 1);

				String imageBack = map.get(Integer.parseInt(val));

				String urlQuery = "select url from urltable where code ='" + code + "'";

				Statement urlStatement = connection.createStatement();
				
				ResultSet resultSet = urlStatement.executeQuery(urlQuery);

				String imageName = imageBack;


				if (!resultSet.next()) {
					// System.out.println("no data");
				}
				else {

					imageName = resultSet.getString("url");

					if (imageName.contains("Not_available.jpg")) {

						imageName = imageBack;
					}
					do {
						// statement(s)
					}
					while (resultSet.next());
				}

				if (urlStatement != null)
				urlStatement.close();

				json.put("image", imageName);

				// json.put("backdropImage",
				// "http://www.quillafoods.com/wp-content/uploads/2014/10/Organic-Quinoa-Red-Seeds-Scoop.jpg");

				json.put("backdropImage", imageName);

				json.put("name", product_name);
				json.put("serving_size", serving_size);
				// jsonString

				String[] productnameSplit = similar.split(" ");

				String similarQuery = "SELECT code,product_name FROM food WHERE code not in('"
						+ code + "') and ";
				
				boolean first = true;
				for (int i = 0; i < productnameSplit.length; i++) {
					
					if (first) {

						similarQuery = similarQuery + "ingredients_list like '%"
								+ productnameSplit[i] + "%' ";

						first = false;

					}
					else {

						similarQuery = similarQuery + "OR ingredients_list like '%"
								+ productnameSplit[i] + "%' ";

					}

				}

				similarQuery = similarQuery + "limit 10";

				System.out.println(similarQuery);

				Statement stmtSimilar = connection.createStatement();

				ResultSet resultSimilar = stmtSimilar.executeQuery(similarQuery);

				JSONArray similarList = new JSONArray();

				while (resultSimilar.next()) {

					code = resultSimilar.getString("code");
					product_name = resultSimilar.getString("product_name");

					JSONObject simiObj = new JSONObject();

					simiObj.put("id", code);
					simiObj.put("name", product_name);

					String valSim = code.substring(code.length() - 1);

					String imageBackSim = map.get(Integer.parseInt(valSim));

					String urlQuerySim = "select url from urltable where code ='" + code
							+ "'";

					Statement urlStatementSim = connection.createStatement();

					ResultSet resultSetSim = urlStatementSim.executeQuery(urlQuerySim);

					String imageNameSim = imageBackSim;

					if (!resultSetSim.next()) {
						System.out.println("no data");
					}
					else {

						imageNameSim = resultSetSim.getString("url");

						if (imageNameSim.contains("Not_available.jpg")) {

							imageNameSim = imageBackSim;
						}
						do {
							// statement(s)
						}
						while (resultSetSim.next());
					}

					if (urlStatementSim != null)
						urlStatementSim.close();

					// simiObj.put("image",
					// "https://superlife.co/wp-content/uploads/2015/04/2015-11-06-19.12.20.png");

					simiObj.put("image",
							imageNameSim);

					similarList.add(simiObj);
					// simiObj = null;

				}

				json.put("similar_products", similarList);

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

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}