package com.bengshiwei.zzj.app.push;

import com.bengshiwei.zzj.app.bean.api.ChatMessageModel;
import com.bengshiwei.zzj.app.bean.db.ChatMessage;
import com.bengshiwei.zzj.app.provider.GsonProvider;
import com.bengshiwei.zzj.app.push.android.*;
import org.json.JSONArray;
import org.json.JSONObject;


public class Demo {
	private String appkey = null;
	private String appMasterSecret = null;
	private String timestamp = null;
	private PushClient client = new PushClient();
	
	public Demo(String key, String secret) {
		try {
			appkey = key;
			appMasterSecret = secret;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void sendAndroidBroadcast() throws Exception {
		AndroidBroadcast broadcast = new AndroidBroadcast(appkey,appMasterSecret);
		broadcast.setTicker( "Android broadcast ticker");
		broadcast.setTitle(  "中文的title");
		broadcast.setText(   "Android broadcast text");
		broadcast.goAppAfterOpen();
		broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		broadcast.setProductionMode();
		// Set customized fields
		broadcast.setExtraField("test", "helloworld");
		client.send(broadcast);
	}
	
	public void sendAndroidUnicast() throws Exception {
		AndroidUnicast unicast = new AndroidUnicast(appkey,appMasterSecret);
		// TODO Set your device token
		unicast.setDeviceToken( "your device token");
		unicast.setTicker( "Android unicast ticker");
		unicast.setTitle(  "中文的title");
		unicast.setText(   "Android unicast text");
		unicast.goAppAfterOpen();
		unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		unicast.setProductionMode();
		// Set customized fields
		unicast.setExtraField("test", "helloworld");
		client.send(unicast);
	}
	
	public void sendAndroidGroupcast() throws Exception {
		AndroidGroupcast groupcast = new AndroidGroupcast(appkey,appMasterSecret);
		/*  TODO
		 *  Construct the filter condition:
		 *  "where": 
		 *	{
    	 *		"and": 
    	 *		[
      	 *			{"tag":"test"},
      	 *			{"tag":"Test"}
    	 *		]
		 *	}
		 */
		JSONObject filterJson = new JSONObject();
		JSONObject whereJson = new JSONObject();
		JSONArray tagArray = new JSONArray();
		JSONObject testTag = new JSONObject();
		JSONObject TestTag = new JSONObject();
		testTag.put("tag", "test");
		TestTag.put("tag", "Test");
		tagArray.put(testTag);
		tagArray.put(TestTag);
		whereJson.put("and", tagArray);
		filterJson.put("where", whereJson);
		System.out.println(filterJson.toString());
		
		groupcast.setFilter(filterJson);
		groupcast.setTicker( "Android groupcast ticker");
		groupcast.setTitle(  "中文的title");
		groupcast.setText(   "Android groupcast text");
		groupcast.goAppAfterOpen();
		groupcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		groupcast.setProductionMode();
		client.send(groupcast);
	}


	public void sendAndroidCustomizedcast(ChatMessage chatMessageModel) throws Exception {
		AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(appkey,appMasterSecret);
		// TODO Set your alias here, and use comma to split them if there are multiple alias.
		// And if you have many alias, you can also upload a file containing these alias, then 
		// use file_id to send customized notification.
		customizedcast.setAlias(chatMessageModel.getReceiverId(),"userID");
		customizedcast.setTicker( "ticker");
		customizedcast.setTitle(chatMessageModel.getSender().getName());
		customizedcast.setText(chatMessageModel.getContent());
		customizedcast.setCustomField(GsonProvider.getGson().toJson(chatMessageModel));

		customizedcast.goAppAfterOpen();
		customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		customizedcast.setProductionMode();
		client.send(customizedcast);
	}
	
	public void sendAndroidCustomizedcastFile() throws Exception {

		AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(appkey,appMasterSecret);
		// TODO Set your alias here, and use comma to split them if there are multiple alias.
		// And if you have many alias, you can also upload a file containing these alias, then 
		// use file_id to send customized notification.
		String fileId = client.uploadContents(appkey,appMasterSecret,"aa"+"\n"+"bb"+"\n"+"alias");
		customizedcast.setFileId(fileId, "alias_type");
		customizedcast.setTicker( "Android customizedcast ticker");
		customizedcast.setTitle(  "中文的title");
		customizedcast.setText(   "Android customizedcast text");
		customizedcast.goAppAfterOpen();
		customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		customizedcast.setProductionMode();
		client.send(customizedcast);
	}
	
	public void sendAndroidFilecast() throws Exception {
		AndroidFilecast filecast = new AndroidFilecast(appkey,appMasterSecret);
		// TODO upload your device tokens, and use '\n' to split them if there are multiple tokens 
		String fileId = client.uploadContents(appkey,appMasterSecret,"aa"+"\n"+"bb");
		filecast.setFileId( fileId);
		filecast.setTicker( "Android filecast ticker");
		filecast.setTitle(  "中文的title");
		filecast.setText(   "Android filecast text");
		filecast.goAppAfterOpen();
		filecast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		client.send(filecast);
	}

	public static void main(String[] args) {
		// TODO set your appkey and master secret here
		Demo demo = new Demo("5ad5b108b27b0a4ba500000d", "w3rqcioprnhzqyabdrgh2sx3mheyhdid");
		try {
			demo.sendAndroidUnicast();
			/* TODO these methods are all available, just fill in some fields and do the test
			 * demo.sendAndroidCustomizedcastFile();
			 * demo.sendAndroidBroadcast();
			 * demo.sendAndroidGroupcast();
			 * demo.sendAndroidCustomizedcast();
			 * demo.sendAndroidFilecast();
			 * 
			 * demo.sendIOSBroadcast();
			 * demo.sendIOSUnicast();
			 * demo.sendIOSGroupcast();
			 * demo.sendIOSCustomizedcast();
			 * demo.sendIOSFilecast();
			 */
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	

}
