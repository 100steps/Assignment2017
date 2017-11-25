import java.io.*;
import java.net.*;
//����������ʽ ReGex��
import java.util.regex.*;

public class Crawler{
    public static void main(String[] args) {
        // ���弴�����ʵ�����
        String url = null;
        // ����һ����ҳԴ���������
        BufferedWriter out = null;
        // ����һ����ַ URL������
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        // �������Ӳ���ȡҳ������
        try {
            System.out.println("������һ��url:");
            url = in.readLine();
            String result = sendGet(url);
            out = new BufferedWriter(new FileWriter("E:/Java/Web Crawler/result.txt"));
            // ����ҳ����Ľ������� result.txt�ļ�
            String src = "src��ǩ��\n" + RegexString(result,"src=\"?(.+?)\"");
            String a = "a��ǩ��\n" + RegexString(result,"<a.*?[^>]*>.+?</a>");
            
            out.write(src);
            out.write(a);
           
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                in.close();
                out.close();
                System.out.println("�ɹ�GET��");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		
    }
    // ��ȡ��ҳ���ݣ���װ
    public static String sendGet(String url) throws IOException {
        // ����һ���ַ��������洢��ҳ����
        String result = null;
        // ����һ�������ַ�����������������
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();
            connection.connect();
            // ��ʼ�� BufferedReader����������ȡ URL����Ӧ
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            // ����ҳ���������result����
            while (true) {
                String line = in.readLine();
                if (line == null) {
                    break;
                }
                result += line + "\n";
            }
        } catch (Exception e) {
            System.out.println("GET�����쳣" + e);
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
    // ����ƥ��
    public static String RegexString(String targetStr, String patternStr){
        String result = "";
        // ������ʽץȡ���� patternStr
        Pattern pattern = Pattern.compile(patternStr); 
        // �� Matcher����ƥ��
        Matcher matcher = pattern.matcher(targetStr);
        while(matcher.find()){
			// ��ӡ�����
            result += matcher.group(0) + "\n";
        }
        return result; 
    }
}