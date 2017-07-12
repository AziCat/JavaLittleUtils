package yan.study.jsonutil;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class ParseElementDemo {
	/*
	 * {
  "code": 999999,
  "time": 12312312312,
  "data": {
    "total": 12334,
    "data": [
      {
        "key1": "aaa",
        "key2": ""
      },
      {
        "key1": "asaa",
        "key2": "bdbb"
      }
    ]
  }
}
	 */
	private static String JSONSTR = "{\"code\":999999,\"time\":12312312312,\"data\":{\"total\":12334,\"data\":[{\"key1\":\"aaa\",\"key2\":\"\"},{\"key1\":\"asaa\",\"key2\":\"bdbb\"}]}}";
	public static void main(String[] args) {
		//解析器
		JsonParser parser = new JsonParser();
		//根内容
		JsonElement root = parser.parse(JSONSTR);
		//JsonArray element = root.getAsJsonArray();
		JsonObject jsonObj = root.getAsJsonObject();
		if(null != jsonObj){
			//code内容获取
			JsonPrimitive code_jp = jsonObj.getAsJsonPrimitive("code");
			if(null != code_jp){
				String code  = code_jp.getAsString();
				System.out.println(code);
			}
			
			//data\\data内容获取
			JsonObject data_jo = jsonObj.getAsJsonObject("data");
			if(null != data_jo){
				JsonPrimitive total_jp = data_jo.getAsJsonPrimitive("total");
				if(null != total_jp){
					int total = total_jp.getAsInt();
					System.out.println(total);
				}
				JsonArray data_ja = data_jo.getAsJsonArray("data");
				if(null != data_ja && data_ja.size() > 0){
					for(JsonElement je : data_ja){
						if(je.isJsonObject()){
							JsonObject jo = je.getAsJsonObject();
							if(jo.getAsJsonPrimitive("key1").isJsonPrimitive()){
								System.out.println(jo.getAsJsonPrimitive("key1").getAsString());
							}
						}
					}
				}
			}
			
			
		}
	}
}
