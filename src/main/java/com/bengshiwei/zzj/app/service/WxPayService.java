package com.bengshiwei.zzj.app.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.HashMap;
import java.util.Map;

@Path("pay")
public class WxPayService {
    @Path("/login")
    @GET
    public String login(){
        return "login";
    }

//    public String pay(){
//        //商户号
//        String mchId = "";
////支付密钥
//        String key = "&key=自己的商户密钥";
////交易类型
//        String tradeType = "JSAPI";
////随机字符串
//        String nonceStr = WxPayUtil.getNonceStr();
////微信支付完成后给该链接发送消息，判断订单是否完成
//        String notifyUrl = "外网能够访问的url,支付成功后的回调";
////微信用户唯一id
//        if(param.getOpenid()==null){
//            return result(500 ,"支付失败，openid is null");
//        }
//        String openId = param.getOpenid();
//        //小程序id
//        if(param.getAppid()==null){
//            return result(500 ,"支付失败，appid is null");
//        }
//        String appid  = param.getAppid();
//        //商品订单号(保持唯一性)
//        String outTradeNo = mchId+WxPayUtil.getNonceStr();
//        //支付金额
//        if(param.getTotalFee()==null){
//            return result(500 ,"支付失败，totalfee is null");
//        }
//        String fee = param.getTotalFee();
//        String totalFee = WxPayUtil.getMoney(fee);
//        //发起支付设备ip
//        String spbillCreateIp = param.getSpbillCreateIp();
//        //商品描述
//        if(param.getBody()==null){
//            return result(500 ,"支付失败，body is null");
//        }
//        String body = param.getBody();
//        //附加数据，商户携带的订单的自定义数据 (原样返回到通知中,这类我们需要系统中订单的id 方便对订单进行处理)
//        String attach = param.getAttach();
//
//        //我们后面需要键值对的形式，所以先装入map
//        Map<String, String> sParaTemp = new HashMap<String, String>();
//        sParaTemp.put("appid", appid);
//        sParaTemp.put("attach", attach);
//        sParaTemp.put("body",  body);
//        sParaTemp.put("mch_id", mchId);
//        sParaTemp.put("nonce_str", nonceStr);
//        sParaTemp.put("notify_url",notifyUrl);
//        sParaTemp.put("openid", openId);
//        sParaTemp.put("out_trade_no", outTradeNo);
//        sParaTemp.put("spbill_create_ip", spbillCreateIp);
//        sParaTemp.put("total_fee",totalFee);
//        sParaTemp.put("trade_type", tradeType);
//
//        //去掉空值 跟 签名参数(空值不参与签名，所以需要去掉)
//        Map<String, String> map = WxPayUtil.paraFilter(sParaTemp);
///**
// 按照 参数=参数值&参数2=参数值2 这样的形式拼接（拼接需要按照ASCII码升序排列）
// /
// String mapStr = WxPayUtil.createLinkString(map);
// //MD5运算生成签名
// String sign =
// WxPayUtil.sign(mapStr, key, "utf-8").toUpperCase();
// sParaTemp.put("sign", sign);
// /**
// 组装成xml参数,此处偷懒使用手动组装，严格代码可封装一个方法，XML标排序需要注意，ASCII码升序排列
// */
//        String xml = "<xml>" + "<appid>" + appid + "</appid>"
//                + "<attach>" + attach + "</attach>"
//                + "<body>" + body + "</body>"
//                + "<mch_id>" + mchId + "</mch_id>"
//                + "<nonce_str>" + nonceStr + "</nonce_str>"
//                + "<notify_url>" + notifyUrl + "</notify_url>"
//                + "<openid>" + openId + "</openid>"
//                + "<out_trade_no>" + outTradeNo + "</out_trade_no>"
//                + "<spbill_create_ip>" + spbillCreateIp + "</spbill_create_ip>"
//                + "<total_fee>" + totalFee + "</total_fee>"
//                + "<trade_type>" + tradeType + "</trade_type>"
//                + "<sign>" + sign + "</sign>"
//                + "</xml>";
//        //统一下单url，生成预付id
//        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
//        String result =WxPayUtil.httpRequest(url, "POST", xml);
//
//        Map<String, String> paramMap = new HashMap<String, String>();
//        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
//        //得到预支付id
//        String prepay_id = "";
//        try {
//            prepay_id = WxPayUtil.getPayNo(result);
//        } catch (DocumentException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        String packages = "prepay_id="+prepay_id;
//        String nonceStr1 = WxPayUtil.getNonceStr();
//        //开始第二次签名
//        String mapStr1 = "appId="+appid+"&nonceStr=" + nonceStr1 + "&package=prepay_id=" + prepay_id + "&signType=MD5&timeStamp=" + timeStamp;
//        String paySign = WxPayUtil.sign(mapStr1, key, "utf-8").toUpperCase();
//        //前端所需各项参数拼接
//        String finaPackage = "\"appId\":\"" + appid + "\",\"timeStamp\":\"" + timeStamp
//                + "\",\"nonceStr\":\"" + nonceStr1 + "\",\"package\":\""
//                + packages + "\",\"signType\" : \"MD5" + "\",\"paySign\":\""
//                + paySign + "\"";
//
//        return result(200,finaPackage);
//    }
//
//}
}
