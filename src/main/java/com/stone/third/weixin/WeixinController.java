package com.stone.third.weixin;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.stone.study.util.HttpRequestUtil;
import com.stone.study.util.OperateConfigUtil;

@Controller
@RequestMapping("/weixin")
public class WeixinController {

	/**
	 * 获取js配置
	 * 
	 * @param url
	 * @return
	 * @author 俞世贵
	 * @date 2015-1-14
	 * @-----------------------------
	 * @修改 修改日期 修改描述
	 * @俞世贵 2015-1-14 创建
	 * @-----------------------------
	 * @Version Ver1.0
	 */
	@RequestMapping(value = "/ajaxjsconfig", produces = { "text/plain;charset=utf-8" })
	@ResponseBody
	public String getWeixinJSConfigs(@RequestParam("url") String url) {
		JSONObject json = new JSONObject();
		try {
			String isFirst = OperateConfigUtil
					.getParamByKey("wxisfirst", "yes").trim();
			System.out.println("是否第一次" + isFirst);
			String appId = OperateConfigUtil.getParamByKey("wxappid", "APPID");
			String secret = OperateConfigUtil.getParamByKey("wxsecret",
					"SECRET");
			String ticket = "";
			if ("yes".equals(isFirst)) { // 第一次请求微信
				String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
						+ appId + "&secret=" + secret;
				HttpRequestUtil hru = new HttpRequestUtil();
				String accessTokenStr = hru.getRequestUrl(tokenUrl);
				System.out.println("=================accessTokenStr"
						+ accessTokenStr);
				JSONObject tokenJson = JSON.parseObject(accessTokenStr);
				String access_token = tokenJson.getString("access_token");
				int token_expires = tokenJson.getIntValue("expires_in");
				String ticketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
						+ access_token + "&type=jsapi";
				String ticketStr = hru.getRequestUrl(ticketUrl);
				JSONObject ticketJson = JSON.parseObject(ticketStr);
				ticket = ticketJson.getString("ticket");
				/* 保存变量 */
				OperateConfigUtil.setParam("wxaccess_token", access_token);
				OperateConfigUtil.setParam("wxtoken_expires_in", ""
						+ token_expires);
				OperateConfigUtil.setParam("wxticket", ticket);
			} else {
				long wxTimestamp = Long.parseLong(OperateConfigUtil
						.getParamByKey("wxtimestamp", "0"));
				int expiresTime = Integer.parseInt(OperateConfigUtil
						.getParamByKey("wxtoken_expires_in", "0"));
				long curTime = (long) (System.currentTimeMillis() / 1000);
				if (curTime - wxTimestamp > expiresTime) { // 失效
					System.out.println("===失效，重新===");
					String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
							+ appId + "&secret=" + secret;
					HttpRequestUtil hru = new HttpRequestUtil();
					String accessTokenStr = hru.getRequestUrl(tokenUrl);
					JSONObject tokenJson = JSON.parseObject(accessTokenStr);
					String access_token = tokenJson.getString("access_token");
					int token_expires = tokenJson.getIntValue("expires_in");
					String ticketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
							+ access_token + "&type=jsapi";
					String ticketStr = hru.getRequestUrl(ticketUrl);
					JSONObject ticketJson = JSON.parseObject(ticketStr);
					ticket = ticketJson.getString("ticket");
					/* 保存变量 */
					OperateConfigUtil.setParam("wxaccess_token", access_token);
					OperateConfigUtil.setParam("wxtoken_expires_in", ""
							+ token_expires);
					OperateConfigUtil.setParam("wxticket", ticket);
				} else {
					ticket = OperateConfigUtil.getParamByKey("wxticket", "0");
				}
			}

			String timestamp = "" + (long) (System.currentTimeMillis() / 1000);
			String nonceStr = UUID.randomUUID().toString().replace("-", "");
			String signature = WxSignUtil.getSignature(timestamp, nonceStr,
					ticket, url);
			OperateConfigUtil.setParam("wxisfirst", "no");
			OperateConfigUtil.setParam("wxtimestamp", timestamp);
			/* 返回前台 */
			json.put("result", 0);
			json.put("appid", appId);
			json.put("timestamp", timestamp);
			json.put("nonceStr", nonceStr);
			json.put("signature", signature);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("result", -1);
		}
		return json.toJSONString();
	}

	/**
	 * 获取微信TOKEN Stone+
	 * 
	 * @return
	 */
	@RequestMapping(value = "/gettokenstr", produces = { "text/plain;charset=utf-8" })
	@ResponseBody
	public String getTokenStr() {
		JSONObject json = new JSONObject();
		String isFirst = OperateConfigUtil.getParamByKey("wxisfirst", "yes")
				.trim();
		String appId = OperateConfigUtil.getParamByKey("wxappid", "APPID");
		String secret = OperateConfigUtil.getParamByKey("wxsecret", "SECRET");
		String token = null;
		if ("yes".equals(isFirst)) { // 第一次请求微信
			String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
					+ appId + "&secret=" + secret;
			HttpRequestUtil hru = new HttpRequestUtil();
			String accessTokenStr = hru.getRequestUrl(tokenUrl);
			JSONObject tokenJson = JSON.parseObject(accessTokenStr);
			token = tokenJson.getString("access_token");
		} else {
			long wxTimestamp = Long.parseLong(OperateConfigUtil.getParamByKey(
					"wxtimestamp", "0"));
			int expiresTime = Integer.parseInt(OperateConfigUtil.getParamByKey(
					"wxtoken_expires_in", "0"));
			long curTime = (long) (System.currentTimeMillis() / 1000);
			if (curTime - wxTimestamp > expiresTime) { // 失效
				System.out.println("===失效，重新===");
				String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
						+ appId + "&secret=" + secret;
				HttpRequestUtil hru = new HttpRequestUtil();
				String accessTokenStr = hru.getRequestUrl(tokenUrl);
				JSONObject tokenJson = JSON.parseObject(accessTokenStr);
				token = tokenJson.getString("access_token");
			} else {
				token = OperateConfigUtil.getParamByKey("wxaccess_token", "no");
			}
		}
		String timestamp = "" + (long) (System.currentTimeMillis() / 1000);
		OperateConfigUtil.setParam("wxtimestamp", timestamp);
		OperateConfigUtil.setParam("wxaccess_token", token);
		OperateConfigUtil.setParam("wxisfirst", "yes");
		json.put("token", token);
		return json.toJSONString();
	}

	/**
	 * 新增临时素材
	 * 
	 * @param accessToken
	 * @param type
	 *            媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）
	 * @param filename
	 * @param mediaFileUrl
	 */
	public static void uploadPermanentMedia(String accessToken, String type,
			String filename, String mediaFileUrl) {
		try {
			// 拼装请求地址
			String uploadMediaUrl = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token="
					+ accessToken + "&type=" + type;
			System.out.println(uploadMediaUrl);

			URL url = new URL(uploadMediaUrl);

			String result = null;

			File file = new File(mediaFileUrl);

			if (!file.exists() || !file.isFile()) {

				System.out.println("上传的文件不存在");

			}

			// 打开和URL之间的连接 与服务器建立TCP连接
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式

			con.setDoInput(true);

			con.setDoOutput(true);

			con.setUseCaches(false); // post方式不能使用缓存

			// 设置请求头信息

			con.setRequestProperty("Connection", "Keep-Alive");

			con.setRequestProperty("Charset", "UTF-8");

			// 设置边界

			String BOUNDARY = "----------" + System.currentTimeMillis();

			con.setRequestProperty("Content-Type",
					"multipart/form-data; boundary="

					+ BOUNDARY);

			// 请求正文信息

			// 第一部分：

			StringBuilder sb = new StringBuilder();

			sb.append("--"); // 必须多两道线

			sb.append(BOUNDARY);

			sb.append("\r\n");

			sb.append("Content-Disposition: form-data;name=\"media\";filename=\""

					+ filename + "\" \r\n");

			sb.append("Content-Type:application/octet-stream\r\n\r\n");

			byte[] head = sb.toString().getBytes("utf-8");

			// 获得输出流

			OutputStream out = new DataOutputStream(con.getOutputStream());

			// 输出表头

			out.write(head);

			// 文件正文部分

			// 把文件已流文件的方式 推入到url中

			DataInputStream in = new DataInputStream(new FileInputStream(file));

			int bytes = 0;

			byte[] bufferOut = new byte[1024];

			while ((bytes = in.read(bufferOut)) != -1) {

				out.write(bufferOut, 0, bytes);

			}

			in.close();

			// 结尾部分

			byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线

			out.write(foot);

			out.flush();

			out.close();

			StringBuffer buffer = new StringBuffer();

			BufferedReader reader = null;

			// 定义BufferedReader输入流来读取URL的响应

			reader = new BufferedReader(new InputStreamReader(con

			.getInputStream()));

			String line = null;

			while ((line = reader.readLine()) != null) {

				buffer.append(line);

			}

			if (result == null) {

				result = buffer.toString();

			}

			// 使用JSON-lib解析返回结果
			JSONObject jsonObject = JSONObject.parseObject(result);
			// weixinMedia.setType(jsonObject.getString("type"));
			// type等于thumb时的返回结果和其它类型不一样
			// if ("thumb".equals(type))
			// weixinMedia.setMediaId(jsonObject.getString("thumb_media_id"));
			// else
			// if (jsonObject.has("")) {
			//
			// System.out.println(jsonObject.getString("media_id"));
			// weixinMedia.setMediaId(jsonObject.getString("media_id"));
			// } else {
			// System.out.println(jsonObject.toString());

			// }
			System.out.println(jsonObject.toJSONString());
			// weixinMedia.setCreatedAt(jsonObject.getInt("created_at"));

		} catch (IOException e) {

			System.out.println("发送POST请求出现异常！" + e);

			e.printStackTrace();

		} finally {

			// if (reader != null) {
			//
			// reader.close();
			//
			// }

		}

	}

	public static void main(String[] args) {
		WeixinController wx = new WeixinController();
		// String tokenStr =
		// JSON.parseObject(wx.getTokenStr()).getString("token");
		String tokenStr = "ZgzbN4fqro8-qEQcOexgvlcOuZtYQRVCUuxr5TKatoYc9-Lz2u9Dw85SbnhnPElY59LJychFAh0hb0MzOF2K1AC9bEaFEJ7g9EFJdOuvA3g";
		System.out.println(tokenStr);
		uploadPermanentMedia(tokenStr, "image", "mmqrcode1430965138055.png",
				"C:\\Users\\Administrator\\Desktop\\测试资源\\mmqrcode1430965138055.png");
	}
}
