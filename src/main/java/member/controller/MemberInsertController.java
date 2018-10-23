package member.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;

import java.util.Random;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import member.model.MemberBean;
import member.model.MemberDao;



@Controller
public class MemberInsertController {
	
	@Autowired
	private SHApassword shapassword;
	
	@Autowired
	private MemberDao memberdao;
	
	@Autowired
	private EmailService emailService;
	

	@RequestMapping(value="joinform.member",method=RequestMethod.GET)
	public String goJoinForm() {
		return "join";
	}
	
	@RequestMapping(value="insert.member",method=RequestMethod.POST,produces="application/json")
	public ModelAndView JoinPoc(
			MemberBean member,HttpSession session){
		ModelAndView mav=new ModelAndView();
		String userpw=member.getUserpw();

		//패스워드 암호화
		userpw=shapassword.pwSHA(userpw);
		member.setUserpw(userpw);	
			
		//조인코드 생성
		int randomcode=new Random().nextInt(100000)+10000;
		String joincode=String.valueOf(randomcode);
		member.setJoincode(joincode);
		
		memberdao.insertMember(member);
		emailService.mailSend(member.getEmail(),joincode,member.getUserid());
		mav.setViewName("redirect:emailchkForm.member");
			
		return mav;
	}
	
	@RequestMapping(value="emailchkForm.member")
	public String goEmailchkForm() {
		return "emailchkForm";
	}
	
	@RequestMapping(value="idchk.member")
	public void idchk(@RequestParam("userid") String userid,HttpServletResponse response) {
		String idchkResult=memberdao.idchk(userid);
		
		response.setContentType("UTF-8");
		try {
			PrintWriter out=response.getWriter();
			out.println(idchkResult);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	

	
}
