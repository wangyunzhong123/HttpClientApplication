import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tianxi on 16-4-5.
 */
public class HttpClientTest {

    @Test
    public void jUnitTest() {
//        get();
        post();
    }

    /**
     * HttpClient连接SSL
     */
    public void ssl() {
        CloseableHttpClient httpclient = null;
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            FileInputStream instream = new FileInputStream(new File("d:\\tomcat.keystore"));
            try {
                // 加载keyStore d:\\tomcat.keystore
                trustStore.load(instream, "123456".toCharArray());
            } catch (CertificateException e) {
                e.printStackTrace();
            } finally {
                try {
                    instream.close();
                } catch (Exception ignore) {
                }
            }
            // 相信自己的CA和所有自签名的证书
            SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();
            // 只允许使用TLSv1协议
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            // 创建http请求(get方式)
            HttpGet httpget = new HttpGet("https://localhost:8443/myDemo/Ajax/serivceJ.action");
            System.out.println("executing request" + httpget.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                HttpEntity entity = response.getEntity();
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    System.out.println("Response content length: " + entity.getContentLength());
                    System.out.println(EntityUtils.toString(entity));
                    EntityUtils.consume(entity);
                }
            } finally {
                response.close();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } finally {
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * post方式提交表单（模拟用户登录请求）
     */
    public void postForm() {
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost
        HttpPost httppost = new HttpPost("http://localhost:8080/myDemo/Ajax/serivceJ.action");
        // 创建参数队列
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("username", "admin"));
        formparams.add(new BasicNameValuePair("password", "123456"));
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httppost.setEntity(uefEntity);
            System.out.println("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    System.out.println("--------------------------------------");
                    System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));
                    System.out.println("--------------------------------------");
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
     */
    public void post() {
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost
//        HttpPost httppost = new HttpPost("http://localhost:8080/myDemo/Ajax/serivceJ.action");
        HttpPost httppost = new HttpPost("http://data.10086.cn/app/dispatcherServlet.action");
        //创建请求头
//        Map<String,String> headerList = new HashMap<String,String>();
//        headerList.put("mobile", "E7F18672224E5B22A5E9FAB90F333505");
//        headerList.put("IMEI", "F11A25DF98C47FA972227AA1E8938FF0");
//        headerList.put("user-id", "E6FDC143026974D8970F273CB54814A21CC9D3BED41E38CBC5FA81B68" +
//                "9F49CD7E4A82C7ECC7621FF71D857D345E305628046C0B7B352EB66805632740F61040A");
//        headerList.put("APIVersion", "1.0.0");
//        headerList.put("Resolution", "1794*1080");
//        headerList.put("ClientVer", "android2.8");
//        headerList.put("Action", "update_profile");
//        headerList.put("Cookie", "all_pay_key=xWxx-E_QMhlOlH4cJQBtm_fO3BFp0RsaewyWTGqVyIp1X" +
//                "b8QFUOiMXArfVEFxIcA--MhxRSP8z2lptaoF4kCBuc_QyTRaamDDtQb7jD5jG5lLGTDuo3Unru" +
//                "dO8gExc2J; sso_key=ddgJ39BNQrq1Tk1F1-iwtlU8zm9Bel4Ai9NloABlGMuPZ_PJ0Vx3y" +
//                "TxcL6y_yhho-yr9WI6fAHJN12pe3BZ4VSYEZYZlkLMLSXf4Zuk7uSUNdmXaFjTG1a1i8_mN49" +
//                "S9; dcop_cookie__user_phone=07bGfx_ubUqn5s0EEShGBoxrZd_DbtMRQLrbcLtvUQt5Q4" +
//                "Xih-7zm-qT5rCKVQCHWcuQqt6FAf3lS4fOMpqfWKlZaRb9jt0-L_7igbSFCOo; Path=/app/;" +
//                " LXHs7cQ6tYxcGMGawg8sPhUsV1Le399-TB5g5JEKids=; JSESSIONID=3DAB57A45F3E45961" +
//                "6E49A67778CA9E4");
//        headerList.put("Accept-Language", "UTF-8");
//        headerList.put("Content-Type", "text/html;charset=UTF-8");
//        headerList.put("AppID", "com.aspirecn.dcop");
//        headerList.put("Client-Agent", "Android_Vandroid2.8/1080*1794");
//        headerList.put("ClientVersion", "android2.8");
//        headerList.put("ClientHash", "2aae1cd37bd0414d224c2fbc33d48e80");
//        headerList.put("Channelid", "all139");
//        headerList.put("Content-Length", "160");
//        headerList.put("Host", "data.10086.cn");
//        headerList.put("Connection", "Keep-Alive");
//        headerList.put("User-Agent", "Apache-HttpClient/UNAVAILABLE (java 1.4)");

        //遍历hashMap
//        Iterable it = headerList.entrySet().iterator();//为何出错
        httppost.addHeader("mobile", "E7F18672224E5B22A5E9FAB90F333505");
        httppost.addHeader("IMEI", "F11A25DF98C47FA972227AA1E8938FF0");
        httppost.addHeader("user-id", "E6FDC143026974D8970F273CB54814A21CC9D3BED41E38CBC5FA81B68" +
                "9F49CD7E4A82C7ECC7621FF71D857D345E305628046C0B7B352EB66805632740F61040A");
        httppost.addHeader("APIVersion", "1.0.0");
        httppost.addHeader("Resolution", "1794*1080");
        httppost.addHeader("ClientVer", "android2.8");
        httppost.addHeader("Action", "update_profile");
//        httppost.addHeader("Action", "my_profile");
        httppost.addHeader("Cookie", "all_pay_key=xWxx-E_QMhlOlH4cJQBtm_fO3BFp0RsaewyWTGqVyIp1X" +
                "b8QFUOiMXArfVEFxIcA--MhxRSP8z2lptaoF4kCBuc_QyTRaamDDtQb7jD5jG5lLGTDuo3Unru" +
                "dO8gExc2J; sso_key=ddgJ39BNQrq1Tk1F1-iwtlU8zm9Bel4Ai9NloABlGMuPZ_PJ0Vx3y" +
                "TxcL6y_yhho-yr9WI6fAHJN12pe3BZ4VSYEZYZlkLMLSXf4Zuk7uSUNdmXaFjTG1a1i8_mN49" +
                "S9; dcop_cookie__user_phone=07bGfx_ubUqn5s0EEShGBoxrZd_DbtMRQLrbcLtvUQt5Q4" +
                "Xih-7zm-qT5rCKVQCHWcuQqt6FAf3lS4fOMpqfWKlZaRb9jt0-L_7igbSFCOo; Path=/app/;" +
                " LXHs7cQ6tYxcGMGawg8sPhUsV1Le399-TB5g5JEKids=; JSESSIONID=85DC288344DA3D2520A74E85814A1CA6");
        httppost.addHeader("Accept-Language", "UTF-8");
        httppost.addHeader("Content-Type", "text/html;charset=UTF-8");
        httppost.addHeader("AppID", "com.aspirecn.dcop");
        httppost.addHeader("Client-Agent", "Android_Vandroid2.8/1080*1794");
        httppost.addHeader("ClientVersion", "android2.8");
        httppost.addHeader("ClientHash", "2aae1cd37bd0414d224c2fbc33d48e80");
        httppost.addHeader("Channelid", "all139");
//        httppost.addHeader("Content-Length", "160");
        httppost.addHeader("Host", "data.10086.cn");
        httppost.addHeader("Connection", "Keep-Alive");
        httppost.addHeader("User-Agent", "Apache-HttpClient/UNAVAILABLE (java 1.8)");
        // 创建参数队列
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();

        formparams.add(new BasicNameValuePair("maritalstatus", "2"));
        formparams.add(new BasicNameValuePair("nickname", "王云中"));
        formparams.add(new BasicNameValuePair("hobby", "[]"));
        formparams.add(new BasicNameValuePair("birth", "1992-05-13"));
        formparams.add(new BasicNameValuePair("city", "8"));
        formparams.add(new BasicNameValuePair("district", "0"));
        formparams.add(new BasicNameValuePair("name", "为士大夫"));
        formparams.add(new BasicNameValuePair("gender", "1"));
        formparams.add(new BasicNameValuePair("province", "0"));
        formparams.add(new BasicNameValuePair("constellaction", "6"));

        for(Header he:httppost.getAllHeaders()){
            System.out.println(he.toString());
        }
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams,"UTF-8");
            httppost.setEntity(uefEntity);
            System.out.println("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            //遍历返回头
            Header header[] = response.getAllHeaders();
            for(Header he:header){
                System.out.println(he.toString());
            }

            System.out.println(response.getStatusLine());
            System.out.println(response.getProtocolVersion());
            try{
                HttpEntity entity = response.getEntity();
                if(entity != null){
                    System.out.println("--------------------------------------");
                    System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));
                    System.out.println("--------------------------------------");
                }
            }catch (Exception e){

            }finally {
                response.close();
            }

        }catch (ClientProtocolException e) {
            e.printStackTrace();
        }catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送 get请求
     */
    public void get() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            // 创建httpget.
            HttpGet httpget = new HttpGet("http://www.baidu.com/");
            System.out.println("executing request " + httpget.getURI());
            // 执行get请求.
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                System.out.println("--------------------------------------");
                // 打印响应状态,相应行
                System.out.println(response.getStatusLine());
                //打印响应报头
                System.out.println(response.getAllHeaders());
                if (entity != null) {
                    // 打印响应内容长度
                    System.out.println("Response content length: " + entity.getContentLength());
                    // 打印响应内容
                    System.out.println("Response content: " + EntityUtils.toString(entity));
                }
                System.out.println("------------------------------------");
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 上传文件
     */
    public void upload() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost("http://localhost:8080/myDemo/Ajax/serivceFile.action");

            FileBody bin = new FileBody(new File("F:\\image\\sendpix0.jpg"));
            StringBody comment = new StringBody("A binary file of some kind", ContentType.TEXT_PLAIN);

            HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("bin", bin).addPart("comment", comment).build();

            httppost.setEntity(reqEntity);

            System.out.println("executing request " + httppost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    System.out.println("Response content length: " + resEntity.getContentLength());
                }
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
