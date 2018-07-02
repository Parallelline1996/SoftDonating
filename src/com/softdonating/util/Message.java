package com.softdonating.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class Message {

	public static boolean sendMs(String phoneNumber, String name) {
		try {
			//SendSmsResponse response = 
			sendSms(phoneNumber, name);
			//if (response.getCode().equals("ok"))
			return true;
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return false;
	}


	public static SendSmsResponse sendSms(String phone,String name) throws ClientException {

	    String product = "Dysmsapi";
	    String domain = "dysmsapi.aliyuncs.com";

	    String accessKeyId = "LTAIrnW0Z9brVIoy";
	    String accessKeySecret = "fuWs6cTFkiPlXpy4nIBxiDt51mOGnc";
		
	    //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "5000");
        System.setProperty("sun.net.client.defaultReadTimeout", "5000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("软软借书");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_138063640");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"name\":\""+name+"\"}");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        return sendSmsResponse;
	}

}
