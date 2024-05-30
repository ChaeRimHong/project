// package com.hrm.project.view;

// import java.io.BufferedReader;
// import java.io.BufferedWriter;
// import java.io.IOException;
// import java.io.InputStreamReader;
// import java.io.OutputStreamWriter;
// import java.net.HttpURLConnection;
// import java.net.MalformedURLException;
// import java.net.URL;
// import org.h2.util.json.JSONObject;
// import org.springframework.boot.configurationprocessor.json.JSONException;

// import com.google.gson.Gson;
// import com.google.gson.GsonBuilder;
// import com.google.gson.JsonArray;
// import com.google.gson.JsonObject;

// public class HttpUtil {
 
//     public static void callApi(JsonObject params, String type){
        
//         HttpURLConnection conn = null;
//         JSONObject responseJson = null;
        
//         try {
//             //URL 설정
//             URL url = new URL("http://localhost:8080/test/api/action");
 
//             conn = (HttpURLConnection) url.openConnection();
            
//             String api = "POST";

//             // type의 경우 POST, GET, PUT, DELETE 가능
//             conn.setRequestMethod(api);
//             conn.setRequestProperty("Content-Type", "application/json");
//             conn.setRequestProperty("Transfer-Encoding", "chunked");
//             conn.setRequestProperty("Connection", "keep-alive");
//             conn.setDoOutput(true);
            
            
//             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
//             // JSON 형식의 데이터 셋팅
//             JsonObject commands = new JsonObject();
//             JsonArray jsonArray = new JsonArray();
            
//             params.addProperty("key", 1);
//             params.addProperty("age", 20);
//             params.addProperty("userNm", "김준오");
 
//             commands.add("userInfo", params);
//              // JSON 형식의 데이터 셋팅 끝
            
//             // 데이터를 STRING으로 변경
//             Gson gson = new GsonBuilder().setPrettyPrinting().create();
//             String jsonOutput = gson.toJson(commands);
                 
//             bw.write(commands.toString());
//             bw.flush();
//             bw.close();
            
//             // 보내고 결과값 받기
//             int responseCode = conn.getResponseCode();
//             if (responseCode == 200) {
//                  BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                 StringBuilder sb = new StringBuilder();
//                 String line = "";
//                 while ((line = br.readLine()) != null) {
//                     sb.append(line);
//                 }
//                 //responseJson = new JSONObject(sb.toString());
                
//                 // 응답 데이터
//                 System.out.println("responseJson :: " + responseJson);
//             } 
//         } catch (MalformedURLException e) {
//             e.printStackTrace();
//         } catch (IOException e) {
//             e.printStackTrace();
//         // } catch (JSONException e) {
//         //     System.out.println("not JSON Format response");
//         //     e.printStackTrace();
//         // }
//         }
//     }
// }

