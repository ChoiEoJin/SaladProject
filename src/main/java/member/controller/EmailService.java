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
		String title="������ ���� ���� �Դϴ�";
		String content=userid+"���� ������ȣ�� "+joincode+" �Դϴ�";
		
		try {
			MimeMessage message=javaMailSender.createMimeMessage();
			MimeMessageHelper messageHelper=new MimeMessageHelper(message,true,"UTF-8");
			messageHelper.setFrom(setFrom);
			messageHelper.setTo(tomail);
			messageHelper.setSubject(title);
			messageHelper.setText(content);
			
			javaMailSender.send(message);
			System.out.println("�̸��� ���� �Ϸ�");
		
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public void pwMailSend(String email,String newpw,String userid) {
		
		String setFrom="nooreetest@gmail.com";
		String tomail=email;
		String title="[������ ������Ʈ] ���ο� ��й�ȣ�Դϴ�";
		String content=userid+"���� ���ο� ��й�ȣ�� "+newpw+" �Դϴ�";
		
		try {
			MimeMessage message=javaMailSender.createMimeMessage();
			MimeMessageHelper messageHelper=new MimeMessageHelper(message,true,"UTF-8");
			messageHelper.setFrom(setFrom);
			messageHelper.setTo(tomail);
			messageHelper.setSubject(title);
			messageHelper.setText(content);
			
			javaMailSender.send(message);
			System.out.println("�̸��� ���� �Ϸ�");
		
			
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		
	}
}
