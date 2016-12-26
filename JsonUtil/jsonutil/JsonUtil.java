package yan.study.jsonutil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {
	/**
	 * 对象转json字符
	 * @param obj
	 * @return
	 */
	public static String bean2Json(Object obj){
		Gson gson = new Gson();
		String jsonStr = gson.toJson(obj);
		return jsonStr;
	}
	/**
	 * json字符转对象
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	public static <T> T json2Bean(String jsonStr,Class<T> clazz){
		Gson gson = new Gson();
		return gson.fromJson(jsonStr, clazz);
	}
	/**
	 * json字符转对象，条件过滤
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	public static <T> T json2Bean(String jsonStr,Class<T> clazz,final List<String> filterNames,final List<Class<?>> clazzs){
		GsonBuilder gsonBuilder = new GsonBuilder(); 
		gsonBuilder.setExclusionStrategies(new ExclusionStrategy(){

			public boolean shouldSkipClass(Class<?> incomingClass) {
				if(null!=clazzs&&clazzs.size()>0){
					return clazzs.contains(incomingClass);
				}
				return false;
			}

			public boolean shouldSkipField(FieldAttributes f) {
				if(null!=filterNames&&filterNames.size()>0){
					return filterNames.contains(f.getName());
				}
				return false;
			}
			
		});
		Gson gson = gsonBuilder.create();
		return gson.fromJson(jsonStr, clazz);
	}
	/**
	 * 对象转json字符，条件过滤
	 * @param obj
	 * @param filterNames
	 * @param clazzs
	 * @return
	 */
	public static String bean2Json(Object obj,final List<String> filterNames,final List<Class<?>> clazzs){
		GsonBuilder gsonBuilder = new GsonBuilder(); 
		gsonBuilder.setExclusionStrategies(new ExclusionStrategy(){

			public boolean shouldSkipClass(Class<?> incomingClass) {
				if(null!=clazzs&&clazzs.size()>0){
					return clazzs.contains(incomingClass);
				}
				return false;
			}

			public boolean shouldSkipField(FieldAttributes f) {
				if(null!=filterNames&&filterNames.size()>0){
					return filterNames.contains(f.getName());
				}
				return false;
			}
			
		});
		Gson gson = gsonBuilder.create();
		return gson.toJson(obj);
	}
	public static void main(String[] args) {
		String userJson ="{\"_name\":\"Norman\",\"email\":\"norman@futurestud.io\",\"isDeveloper\":true,\"age\":26,\"registerDate\":\"Dec 26, 2016 11:10:45 AM\"}";
		UserDate user = new UserDate("Norman","norman@futurestud.io",true,26,new Date());
		//过滤字段
		List<String> filterNames = new ArrayList<String>();
		filterNames.add("_name");
		filterNames.add("registerDate");
		//过滤类型
		List<Class<?>> clazzs = new ArrayList<Class<?>>();
		clazzs.add(int.class);
		System.out.println(JsonUtil.bean2Json(user,filterNames,clazzs));
		UserDate userTest = JsonUtil.json2Bean(userJson, UserDate.class,filterNames,clazzs);
		System.out.println(userTest);
	}
}
