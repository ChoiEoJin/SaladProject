package board.model;

import java.sql.Date;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class BoardBean {

	private int num;
	
	private int secret;
	private String writer;
	@NotEmpty(message="제목누락")
	private String subject;
	@NotEmpty(message="패스워드를 입력해주세요")
	private String passwd;
	private Date regdate;
	private int readcount;
	private int ref;
	private String image;
	private int restep;
	private int relevel;
	@NotEmpty(message="본문을 입력해주세요")
	private String content;
	private MultipartFile upload;
	private String upload2;
	
	private int notice;
	
	

	public MultipartFile getUpload() {
		return upload;
	}

	public void setUpload(MultipartFile upload) {
		
		if(upload !=null) {
			
			this.image=upload.getOriginalFilename();
			
		}
		
		this.upload = upload;
	}

	public String getUpload2() {
		return upload2;
	}

	public void setUpload2(String upload2) {
		this.upload2 = upload2;
	}

	public BoardBean() {

	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getWriter() {
		return writer;
	}

	public int getSecret() {
		return secret;
	}

	public void setSecret(int secret) {
		this.secret = secret;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public int getReadcount() {
		return readcount;
	}

	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}

	public int getRef() {
		return ref;
	}

	public void setRef(int ref) {
		this.ref = ref;
	}

	public int getRestep() {
		return restep;
	}

	public void setRestep(int restep) {
		this.restep = restep;
	}

	public int getRelevel() {
		return relevel;
	}

	public void setRelevel(int relevel) {
		this.relevel = relevel;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getNotice() {
		return notice;
	}

	public void setNotice(int notice) {
		this.notice = notice;
	}

	
}
