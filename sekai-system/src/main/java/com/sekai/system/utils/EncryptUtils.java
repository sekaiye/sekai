package com.sekai.system.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class EncryptUtils {

	public static void main(String[] args){
		String str = new EncryptUtils().Encode("root");
		System.out.println(str);
	}
	/**利用MD5进行加密
	 * @param str  待加密的字符串
	* @return  加密后的字符串
	*/
	public String Encode(String str){
		try {  
            MessageDigest md = MessageDigest.getInstance("MD5");  
            md.update(str.getBytes());  
            byte b[] = md.digest();  
            int i;
            StringBuffer buf = new StringBuffer("");  
            for (int offset = 0; offset < b.length; offset++) {  
                i = b[offset];  
                if (i < 0)  
                    i += 256;  
                if (i < 16)  
                    buf.append("0");  
                buf.append(Integer.toHexString(i));  
            }  
            //32位加密  
            return buf.toString();  
            // 16位的加密  
            //return buf.toString().substring(8, 24);  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
            return null;  
        }  
	}
}
