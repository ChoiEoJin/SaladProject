package board.model;

import java.sql.Timestamp;

import org.hibernate.validator.constraints.NotEmpty;

public class ReviewReplyBean {
	private int num;
	private String writer;//세션아이디
	private Timestamp regdate; 
	
	@NotEmpty(message="글내용을 입력해주세요")
	private String replyContent; //not null

	
	
	public ReviewReplyBean() {
		super();
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

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public Timestamp getRegdate() {
		return regdate;
	}

	public void setRegdate(Timestamp regdate) {
		this.regdate = regdate;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	
	
	
	
}
