package IFTTT;

import java.io.IOException;  
import java.util.ArrayList;  
import java.util.List;  
  
import org.apache.commons.httpclient.HttpException;   
//import java.util.Properties;
import org.apache.commons.httpclient.Header;  
import org.apache.commons.httpclient.HttpClient;  
import org.apache.commons.httpclient.methods.PostMethod;  
import org.apache.commons.httpclient.params.HttpMethodParams;  
//import org.apache.http.HttpException;  
import weibo4j.Oauth;  
import weibo4j.Timeline;  
import weibo4j.http.AccessToken;  
import weibo4j.model.WeiboException;  
import weibo4j.util.WeiboConfig;  

public class WeiboTask {

	public static AccessToken getToken(String id,String password) throws HttpException, IOException   //使用http协议模拟授权，并返回授权token
    {  
		    //从文档中获取应用的id和回调页地址（使用客户端的默认地址回调）
            String clientId = WeiboConfig.getValue("client_ID") ;  
            String redirectURI = WeiboConfig.getValue("redirect_URI") ;  
            String url = WeiboConfig.getValue("authorizeURL");    
            PostMethod postMethod = new PostMethod(url);  
            //将获取的应用id、回调页以及用户id，密码等信息填入，并用协议http模拟进入网页，用户予以授权，最后获取code的过程  
            postMethod.addParameter("client_id",clientId);  
            postMethod.addParameter("redirect_uri",redirectURI);  
            postMethod.addParameter("userId", id);  
            postMethod.addParameter("passwd", password);  
            postMethod.addParameter("isLoginSina", "0");  
            postMethod.addParameter("action", "submit");  
            postMethod.addParameter("response_type","code");  
            HttpMethodParams param = postMethod.getParams();  
            param.setContentCharset("UTF-8");  
            //添加头信息  
            List<Header> headers = new ArrayList<Header>();  
            headers.add(new Header("Referer", "https://api.weibo.com/oauth2/authorize?client_id="+clientId+"&redirect_uri="+redirectURI+"&from=sina&response_type=code"));  
            headers.add(new Header("Host", "api.weibo.com"));  
            headers.add(new Header("User-Agent","Mozilla/5.0 (Windows NT 6.1; rv:11.0) Gecko/20100101 Firefox/11.0"));  
            HttpClient client = new HttpClient();  
            client.getHostConfiguration().getParams().setParameter("http.default-headers", headers);  
            client.executeMethod(postMethod);  
            //验证是否获取code
            int status = postMethod.getStatusCode();  
            System.out.println(status);  
            if (status != 302)  
            {  
                System.out.println("授权失败" + id + "\n" + password);  
                return null;  
            }  
            //获取code之后，开始解析Token  
            Header location = postMethod.getResponseHeader("Location");  
            if (location != null)   
            {  
                String retUrl = location.getValue();  
                int begin = retUrl.indexOf("code=");  
                if (begin != -1) {  
                    int end = retUrl.indexOf("&", begin);  
                    if (end == -1)  
                        end = retUrl.length();  
                    String code = retUrl.substring(begin + 5, end);  
                    if (code != null) {  
                        Oauth oauth = new Oauth();  
                        try{  
                            AccessToken token = oauth.getAccessTokenByCode(code);  
                            return token;  
                        }catch(Exception e){  
                            e.printStackTrace();  
                        }  
                    }  
                }  
            }  
        return null;  
    }  
   //使用已授权的token更新微博状态content
    public static void updateWeibo(String token,String content) throws Exception {   
        Timeline timeline = new Timeline();  
        timeline.client.setToken(token);  
        try   
        {  
            timeline.UpdateStatus(content);  
        }   
        catch (WeiboException e){System.out.println(e.getErrorCode());}    
    }  
      
    public static void sendWeibo(String id,String password,String content) throws Exception  
    {  
        AccessToken at = getToken(id,password);  
        updateWeibo(at.getAccessToken(),content);  
    }  
}
