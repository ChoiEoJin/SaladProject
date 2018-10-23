package member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.crypto.Cipher;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import member.model.MemberBean;
import member.model.MemberDao;


@Controller
public class MemberLoginController {
 
	
	@Autowired
	SHApassword SHApassword;
	
	@Autowired
	MemberDao memberDao;
	
	@Autowired
	EmailService emailService;
	
    
    @RequestMapping(value="loginForm.member",method=RequestMethod.GET)
    public ModelAndView loginForm(HttpSession session){
 
	    ModelAndView mav=makeRSAKey(session);	
	    mav.setViewName("loginForm");
 
        return mav;
    }
 
    // 로그인
    @RequestMapping(value="login.member",method=RequestMethod.POST)
    public ModelAndView login(HttpServletRequest request, HttpSession session,HttpServletResponse response) throws Exception {
    	response.setContentType("text/html charset=UTF-8");
    	
    	ModelAndView mav = new ModelAndView();
        String userid = (String) request.getParameter("userid");
        String userpw = (String) request.getParameter("userpw");
        
        System.out.println("복호화 전 id:"+userid);
        System.out.println("복호화 전 pw:"+userpw);
        
        PrivateKey privateKey = (PrivateKey) session.getAttribute("privatekey");
 
        // 암호해석
        userid = decryptRsa(privateKey, userid);
        userpw = decryptRsa(privateKey, userpw);
 
        System.out.println("복호화 후 id:"+userid);
        System.out.println("복호화 후 pw:"+userpw);
        
        // 개인키 삭제
        session.removeAttribute("privatekey");
        
        
        //로그인 단계
        userpw=SHApassword.pwSHA(userpw);
        int cnt=memberDao.login(userid,userpw);
        
        if(cnt==1) {
        	System.out.println("로그인 성공");
        	MemberBean memberinfo=memberDao.getBean(userid,userpw);
        	session.setAttribute("loginfo", memberinfo);
        	mav.setViewName("redirect:main.main");
        }else {
        	System.out.println("로그인 실패");	
        	PrintWriter out=response.getWriter();
        	out.println("<script>");
        	out.println("alert('아이디나 패스워드를 잘못 입력하셨습니다');");
        	out.println("location.href='"+request.getContextPath()+"/loginForm.member';");
        	out.println("</script>");
        	out.flush();
        	mav.setViewName("redirect:loginForm.member");
        }

        return mav;
    }
 

    private String decryptRsa(PrivateKey privateKey, String securedValue) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        byte[] encryptedBytes = hexToByteArray(securedValue);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        String decryptedValue = new String(decryptedBytes, "utf-8"); // 문자 인코딩 주의.
        return decryptedValue;
    }
 

    public static byte[] hexToByteArray(String hex) {
        if (hex == null || hex.length() % 2 != 0) { return new byte[] {}; }
 
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            byte value = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
            bytes[(int) Math.floor(i / 2)] = value;
        }
        return bytes;
    }
 

    public ModelAndView makeRSAKey(HttpSession session) {
        
    	ModelAndView mav=new ModelAndView();
        KeyPairGenerator generator;
        try {
            generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(1024);
 
            KeyPair keyPair = generator.genKeyPair();
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
 
            session.setAttribute("privatekey", privateKey); 
 
            RSAPublicKeySpec publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
            String publicKeyModulus = publicSpec.getModulus().toString(16);
            String publicKeyExponent = publicSpec.getPublicExponent().toString(16);
            System.out.println("키생성 완료");
            mav.addObject("RSAmodulus",publicKeyModulus);
            mav.addObject("RSAExponent",publicKeyExponent);
            
            return mav;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return mav;
    }
    @RequestMapping(value="emailchk.member")
    public void emailCheck(HttpServletRequest request,HttpServletResponse response) {
    	
    	response.setContentType("text/html; charset=UTF-8");
    	
    	String userid=request.getParameter("userid");
    	String userpw=request.getParameter("userpw");
    	String joincode=request.getParameter("joincode");
    	
    	
    	
    	userpw=SHApassword.pwSHA(userpw);
    	System.out.println(userid);
    	System.out.println(userpw);
    	System.out.println(joincode);
    	
    	int cnt=memberDao.emailchk(userid,userpw,joincode);
    	if(cnt==1) {
    		memberDao.emailChkResult(userid,userpw);
    		PrintWriter out;
			try {
				out = response.getWriter();
				out.println("<script>");
				out.println("alert('인증에 성공했습니다')");
				out.println("location.href='"+request.getContextPath()+"/loginForm.member';");
				out.println("</script>");
	    		out.flush();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}else {
    		PrintWriter out;
			try {
				out = response.getWriter();
				out.println("<script>");
				out.println("alert('인증에 실패했습니다');");
				out.println("location.href='"+request.getContextPath()+"/emailchkForm.member';");
	    		out.println("</script>");
	    		out.flush();
	    		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    	}

    }
    
    @RequestMapping(value="searchpw.member",method=RequestMethod.GET)
    public String searchpw() {
    	return "searchpw";
    }
    
    @RequestMapping(value="searchpw.member",method=RequestMethod.POST)
    public void searchpw(MemberBean member,HttpServletResponse response,HttpServletRequest request) {
    	response.setContentType("text/html; charset=UTF-8");
    	int cnt=memberDao.searchpw(member);
    	if(cnt==1) {
    		int newpwnum=new Random().nextInt(100000)+10000;
    		String newpwstr=String.valueOf(newpwnum);
    		String newpwSHA=SHApassword.pwSHA(newpwstr);
    		member.setUserpw(newpwSHA);
    		memberDao.updatePW(member);
    		emailService.pwMailSend(member.getEmail(), newpwstr, member.getUserid());
    		
    		PrintWriter out;
			try {
				out = response.getWriter();
				out.println("<script>");
	    		out.println("alert('새로운 비밀번호를 입력하신 이메일로 전송하였습니다')");
	    		out.println("location.href='"+request.getContextPath()+"/loginForm.member';");
	    		out.println("</script>");
	    		out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		
    		
    	}else {
    		PrintWriter out;
			try {
				out = response.getWriter();
				out.println("<script>");
	    		out.println("alert('입력하신 정보와 일치하는 회원이 없습니다')");
	    		out.println("location.href='"+request.getContextPath()+"/searchpw.member';");
	    		out.println("</script>");
	    		out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
    }
    
    @RequestMapping(value="logout.member")
    public ModelAndView logoutPro(HttpSession session) {
    	session.invalidate();
    	ModelAndView mav=new ModelAndView();
    	mav.setViewName("redirect:main.main");
    	return mav;
    }

    @RequestMapping(value="searchid.member")
    public String goSearchIdForm() {
    	return "searchId";
    }
    
    @RequestMapping(value="searchIdPro.member")
    public void searchIdPro(HttpServletResponse response,HttpServletRequest request,MemberBean bean) {
    	int cnt=memberDao.searchId(bean);
    	response.setContentType("text/html; charset=UTF-8");
    	try {
			PrintWriter out=response.getWriter();
			
			if(cnt==1) {
				String userid=memberDao.searchIdResult(bean);
				out.println("<script>");
				out.println("alert('당신의 아이디는"+userid+" 입니다');");
				out.println("location.href='"+request.getContextPath()+"/loginForm.member';");
				out.println("</script>");
				
			}else {
				out.println("<script>");
				out.println("alert('입력하신 정보와 일치하는 회원이 없습니다');");
				out.println("location.href='"+request.getContextPath()+"/searchid.member';");
				out.println("</script>");
			}
			out.flush();
			
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    @RequestMapping(value="updateMemberInfo.member")
    public ModelAndView updateMemberInfo(HttpSession session) {
    	ModelAndView mav=new ModelAndView();
    	String userid=((MemberBean)session.getAttribute("loginfo")).getUserid();
    	String userpw=((MemberBean)session.getAttribute("loginfo")).getUserpw();
    	MemberBean mBean=memberDao.getBean(userid, userpw);
    	mav.addObject("mBean",mBean);
    	mav.setViewName("memberUpdateForm");
    	return mav;
    }
    
    @RequestMapping(value="updateMember.member")
    public void updateMember(MemberBean bean,HttpServletResponse response,HttpServletRequest request,HttpSession session) {
    	response.setContentType("text/html; charset=UTF-8");
    	String userpw=bean.getUserpw();
    	String SHAuserpw=SHApassword.pwSHA(userpw);
    	bean.setUserpw(SHAuserpw);
    	
    	memberDao.updateMember(bean);
    	try {
    		session.invalidate();
			PrintWriter out=response.getWriter();
			out.println("<script>");
			out.println("alert('정보가 수정되었습니다 새로운 비밀번호로 로그인해주세요')");
			out.println("location.href='"+request.getContextPath()+"/loginForm.member'");
			out.println("</script>");
			out.flush();
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
    
}



	

