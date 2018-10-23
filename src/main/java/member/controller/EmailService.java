package member.controller;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	public void mailSend(String email,String joincode,String userid) {

		String setFrom="nooreetest@gmail.com";
		String tomail=email;
		String title="샐러드 인증 메일 입니다";
		String content=userid+"님의 인증번호는 "+joincode+" 입니다";
		
		try {
			MimeMessage message=javaMailSender.createMimeMessage();
			MimeMessageHelper messageHelper=new MimeMessageHelper(message,true,"UTF-8");
			messageHelper.setFrom(setFrom);
			messageHelper.setTo(tomail);
			messageHelper.setSubject(title);
			messageHelper.setText(content);
			
			javaMailSender.send(message);
			System.out.println("이메일 전송 완료");
		
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public void pwMailSend(String email,String newpw,String userid) {
		
		String setFrom="nooreetest@gmail.com";
		String tomail=email;
		String title="[샐러드 프로젝트] 새로운 비밀번호입니다";
		String content=userid+"님의 새로운 비밀번호는 "+newpw+" 입니다";
		
		try {
			MimeMessage message=javaMailSender.createMimeMessage();
			MimeMessageHelper messageHelper=new MimeMessageHelper(message,true,"UTF-8");
			messageHelper.setFrom(setFrom);
			messageHelper.setTo(tomail);
			messageHelper.setSubject(title);
			messageHelper.setText(content);
			
			javaMailSender.send(message);
			System.out.println("이메일 전송 완료");
		
			
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		
	}
}
