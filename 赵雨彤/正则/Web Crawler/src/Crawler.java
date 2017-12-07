import java.io.*;
import java.net.*;
//导入正则表达式 ReGex包
import java.util.regex.*;

public class Crawler{
    public static void main(String[] args) {
        // 定义即将访问的链接
        String url = null;
        // 定义一个网页源代码输出流
        BufferedWriter out = null;
        // 定义一个网址 URL输入流
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        // 访问链接并获取页面内容
        try {
            System.out.println("请输入一个url:");
            url = in.readLine();
            String result = sendGet(url);
            out = new BufferedWriter(new FileWriter("E:/Java/Web Crawler/result.txt"));
            // 将网页爬虫的结果输出至 result.txt文件
            String src = "src标签：\n" + RegexString(result,"src=\"?(.+?)\"");
            String a = "a标签：\n" + RegexString(result,"<a.*?[^>]*>.+?</a>");
            
            out.write(src);
            out.write(a);
           
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                in.close();
                out.close();
                System.out.println("成功GET！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		
    }
    // 获取网页内容，封装
    public static String sendGet(String url) throws IOException {
        // 定义一个字符串用来存储网页内容
        String result = null;
        // 定义一个缓冲字符输入流（处理流）
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();
            connection.connect();
            // 初始化 BufferedReader输入流来读取 URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            // 将网页内容输出至result变量
            while (true) {
                String line = in.readLine();
                if (line == null) {
                    break;
                }
                result += line + "\n";
            }
        } catch (Exception e) {
            System.out.println("GET请求异常" + e);
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    // 正则匹配
    public static String RegexString(String targetStr, String patternStr){
        String result = "";
        // 正则表达式抓取内容 patternStr
        Pattern pattern = Pattern.compile(patternStr); 
        // 用 Matcher进行匹配
        Matcher matcher = pattern.matcher(targetStr);
        while(matcher.find()){
			// 打印出结果
            result += matcher.group(0) + "\n";
        }
        return result; 
    }
}