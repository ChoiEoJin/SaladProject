package member.controller;

import java.security.MessageDigest;

import org.springframework.stereotype.Component;


@Component
public class SHApassword {

	public String pwSHA(String userpw) {
		try {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash=digest.digest(userpw.getBytes("UTF-8"));
		StringBuffer userpwhex=new StringBuffer();
		
		for(int i=0;i<hash.length;i++) {
			String hex=Integer.toHexString(0xff & hash[i]);
			
			if(hex.length()==1) {userpwhex.append(0);}
			userpwhex.append(hex);				
		}
		
		return userpwhex.toString();
		
		}catch(Exception e) {
			e.printStackTrace();
			return "암호화 실패";
		}
		
	}
	
}
