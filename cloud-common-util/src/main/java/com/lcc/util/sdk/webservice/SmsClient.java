package com.lcc.util.sdk.webservice;

import okhttp3.*;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author lcc
 */
public class SmsClient {

    /**
     * 构造函数
     *
     * @param sn       序列号
     * @param password 原始密码
     * @throws UnsupportedEncodingException
     */
    public SmsClient(String sn, String password) {
        this.sn = sn;
        this.password = password;
        this.pwd = DigestUtils.md5DigestAsHex((sn + password).getBytes()).toUpperCase();
    }

    /**
     * 发送单条短信
     *
     * @param mobile
     * @param content
     * @param ext
     * @param stime
     * @param rrid
     * @param msgfmt
     * @return
     * @throws IOException
     */
    public String sendSingleMessage(String mobile, String content,
                                    String ext, String stime, String rrid, String msgfmt) throws IOException {
        //TODO 条件判断
        return sendMessageByPostRequest(mobile, content, ext, stime, rrid, msgfmt);
    }


    /**
     * 发送批量短信
     *
     * @param mobile
     * @param content
     * @param ext
     * @param stime
     * @param rrid
     * @param msgfmt
     * @return
     * @throws IOException
     */
    public String sendBatchMessage(String[] mobile, String content,
                                   String ext, String stime, String rrid, String msgfmt) throws IOException {
        int mobileLength = 0;
        String mobileStr = "";
        if (mobile != null) {
            mobileLength = mobile.length;
            if (mobileLength < 1) {
                //TODO 特殊处理
            }
            mobileStr = appendStrings(mobile);
        }
        return sendMessageByPostRequest(mobileStr, URLEncoder.encode(content, "utf-8"), ext, stime, rrid, msgfmt);
    }


    /**
     * 发送批量个性化短信
     * @param mobile
     * @param content
     * @param ext
     * @param stime
     * @param rrid
     * @param msgfmt
     * @return
     * @throws IOException
     */
    public String sendBatchPersonalMessage(String[] mobile, String[] content,
                                           String ext, String[] stime, String rrid, String msgfmt) throws IOException {

        int mobileLength = 0;
        String mobileStr = "";
        if (mobile != null) {
            mobileLength = mobile.length;
            if (mobileLength < 1) {
                //TODO 特殊处理
            }
            mobileStr = appendStrings(mobile);
        }
        String stimeStr = "";
        if (stime != null) {
            int length = stime.length;
            if (length == 1) {
                stimeStr = stime[0];
            } else {
                if (mobileLength == length) {
                    //定时时间和手机个数相同
                    stimeStr = appendStrings(stime);
                } else {
                    //TODO 特殊处理
                }
            }
        }

        String concatStr = "";
        if (content != null) {
            int length = content.length;
            if (length == 1) {
                concatStr = content[0];
            } else {
                if (mobileLength == length) {
                    //短信内容
                    concatStr = appendConcatStrings(content);
                } else {
                    //TODO 特殊处理
                }
            }
        }
       return sendPersonalMessageByPostRequest(mobileStr, concatStr, ext, stimeStr, rrid, msgfmt);
    }

    private String appendConcatStrings(String[] strs) throws UnsupportedEncodingException {
        if (strs != null) {
            StringBuilder sb = new StringBuilder();
            int length = strs.length;
            for (int i = 0; i < length; i++) {
                String encode = URLEncoder.encode(strs[i], "utf-8");
                sb.append(encode);
                if (i < length - 1) {
                    sb.append(",");
                }
            }
            return sb.toString();
        }
        return null;
    }

    /**
     * 注册
     * 公司信息内容 方便出现问题时联系
     * @return
     * @throws IOException
     */
    public String register(String province, String city,String trade,
                           String entname, String linkman, String phone, String mobile, String email,
                           String fax, String address, String postcode, String sign) throws IOException {
        RequestBody requestBody = getRegisterRequestBody(province, city, trade, entname, linkman, phone, mobile, email, fax, address, postcode, sign);
        return sendPostRequest(REGISTER_URL, requestBody);
    }

    /**
     * 取消注册
     * @return
     * @throws IOException
     */
    public String unRegister() throws IOException {
        RequestBody requestBody = getUnRegisterRequestBody();
        return sendPostRequest(UN_REGISTER_URL, requestBody);
    }


    /**
     * 发送个性短信
     * @param mobile
     * @param content
     * @param ext
     * @param stime
     * @param rrid
     * @param msgfmt
     * @return
     * @throws IOException
     */
    private String sendPersonalMessageByPostRequest(String mobile, String content,
                                            String ext, String stime, String rrid, String msgfmt) throws IOException {
        RequestBody requestBody = getSmsRequestBody(mobile, content, ext, stime, rrid, msgfmt);
        return sendPostRequest(SEND_SMS_BATCH_URL, requestBody);
    }

    /**
     * 发送短信
     *
     * @param mobile
     * @param content
     * @param ext
     * @param stime
     * @param rrid
     * @param msgfmt
     * @throws IOException
     */
    private String sendMessageByPostRequest(String mobile, String content,
                                            String ext, String stime, String rrid, String msgfmt) throws IOException {
        RequestBody requestBody = getSmsRequestBody(mobile, content, ext, stime, rrid, msgfmt);
        return sendPostRequest(URL_STR, requestBody);
    }

    /**
     * 短信发送请求体
     *
     * @param mobile
     * @param content
     * @param ext
     * @param stime
     * @param rrid
     * @param msgfmt
     * @return
     * @throws UnsupportedEncodingException
     */
    private RequestBody getSmsRequestBody(String mobile, String content,
                                          String ext, String stime, String rrid, String msgfmt)  {
        RequestBody formBody = new FormBody.Builder()
                .add("sn", this.sn)
                .add("pwd", this.pwd)
                .add("mobile", mobile)
                .add("content", content)
                .add("ext", ext)
                .add("stime", stime)
                .add("rrid", rrid)
                .add("msgfmt", msgfmt)
                .build();
        return formBody;
    }


    /**
     * 注册请求体
     * 使用前 需要注册
     * sn软件序列号;pwd序列号密码;province省;city城市;trade行业;
     * entname企业名称;linkman联系人;phone联系电话;mobile移动电话;email邮件地址;
     * fax传真;address地址;postcode邮政编码;sign签名
     * @param province
     * @param city
     * @param trade
     * @param entname
     * @param linkman
     * @param phone
     * @param mobile
     * @param email
     * @param fax
     * @param address
     * @param postcode
     * @param sign
     * @return
     */
    private RequestBody getRegisterRequestBody(String province, String city,String trade,
                                               String entname, String linkman, String phone, String mobile, String email,
                                               String fax, String address, String postcode, String sign){
        return new FormBody.Builder()
                .add("sn", sn)
                .add("pwd", pwd)
                .add("province", province)
                .add("city", city)
                .add("trade", trade)
                .add("entname", entname)
                .add("linkman", linkman)
                .add("phone", phone)
                .add("mobile", mobile)
                .add("email", email)
                .add("fax", fax)
                .add("address", address)
                .add("postcode", postcode)
                .add("sign", sign)
                .build();
    }

    /**
     * 取消注册
     * @return
     */
    private FormBody getUnRegisterRequestBody(){
        return new FormBody.Builder()
                .add("sn", sn)
                .add("pwd", pwd)
                .build();
    }


    /**
     * 字符串拼接  分隔符英文,
     *
     * @param strs
     * @return
     */
    private String appendStrings(String[] strs) {
        return join(strs, ",");
    }

    public static String join(Object[] array, String separator) {
        return array == null ? null : join((Object[])array, separator, 0, array.length);
    }

    public static String join(Object[] array, String separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        } else {
            if (separator == null) {
                separator = "";
            }

            int noOfItems = endIndex - startIndex;
            if (noOfItems <= 0) {
                return "";
            } else {
                StringBuilder buf = newStringBuilder(noOfItems);

                for(int i = startIndex; i < endIndex; ++i) {
                    if (i > startIndex) {
                        buf.append(separator);
                    }

                    if (array[i] != null) {
                        buf.append(array[i]);
                    }
                }

                return buf.toString();
            }
        }
    }

    private static StringBuilder newStringBuilder(int noOfItems) {
        return new StringBuilder(noOfItems * 16);
    }


    /**
     * 发送post 请求
     *
     * @param urlPath
     * @param formBody
     * @return 响应结果
     * @throws IOException
     */
    private String sendPostRequest(String urlPath, RequestBody formBody) throws IOException {
        Request request = new Request.Builder()
                .url(urlPath)
                .post(formBody)
                .build();
        OkHttpClient httpClient = HttpClientUtil.simpleClient();
        Call call = httpClient.newCall(request);
        Response response = call.execute();
        String responseMessage = processResponseMessage(response);
        response.close();
        return responseMessage;
    }


    /**
     * 处理返回结果  目前只取字符串结果
     *
     * @param response
     * @return
     * @throws IOException
     */
    private String processResponseMessage(Response response) throws IOException {
        String bodyString = response.body().string();
        if (!StringUtils.isEmpty(bodyString)) {
            int length = bodyString.length();
            String substring = bodyString.substring(0, length - 1);
            int begin = substring.lastIndexOf(">");
            int end = substring.lastIndexOf("<");
            if (begin >= 0 && end >= 0 && begin < end ) {
                String result = substring.substring(begin + 1, end);
                return result;
            }
        }
        return bodyString;
    }

    /**
     * 序列号
     */
    private String sn;

    /**
     * 密码
     */
    private String password;

    /**
     * MD5加密
     */
    private String pwd;


    /**
     * 发短信
     */
    private static final String SEND_SMS_URL = "http://sdk.entinfo.cn:8061/webservice.asmx/SendSMS";
    /**
     * 群发个性短信
     */
    private static final String SEND_SMS_BATCH_URL = "http://sdk.entinfo.cn:8061/mdgxsend.ashx";
    /**
     * 短信发送地址
     *
     * 单条发、群发
     *
     */
    private static final String URL_STR = "http://sdk.entinfo.cn:8061/webservice.asmx/mdsmssend";

    /**
     * 注册地址
     */
    private static final String REGISTER_URL = "http://sdk.entinfo.cn:8060/webservice.asmx/Register";

    /**
     * 取消注册地址
     */
    private static final String UN_REGISTER_URL = "http://sdk.entinfo.cn:8060/webservice.asmx/UnRegister";

}
