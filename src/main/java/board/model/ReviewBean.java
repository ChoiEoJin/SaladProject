package board.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class ReviewBean {

	private int num;
	@NotEmpty(message = "������ �Է����ּ���")
	private String subject;
	private MultipartFile upload;
	private String upload2;
	@NotEmpty(message = "�̹����� �־��ּ���")
	private String image;
	private String writer;
	private Timestamp regdate;
	@NotEmpty(message = "������ �Է����ּ���")
	private String content;
	private int secret;
	
	
	
	//��۸���Ʈ 
	private List<ReviewReplyBean> reviewRelpyBeanList=new ArrayList<ReviewReplyBean>();//�ش� �ѹ��� �ش��ϴ� ��۵��� ��������Ʈ

	//���ƿ丮��Ʈ
	private List<ReviewLikeBean> reviewLikeBean=new ArrayList<ReviewLikeBean>();
	
	//���� ���� �����ϳ�
	private String people="";

	public ReviewBean() {
		
		super();
	}


	public MultipartFile getUpload() {
		return upload;
	}

	public void setUpload(MultipartFile upload) {
		if (upload != null) {

			this.image = upload.getOriginalFilename();

		}

		this.upload = upload;
	}

	public String getUpload2() {
		return upload2;
	}

	public void setUpload2(String upload2) {
		this.upload2 = upload2;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getSecret() {
		return secret;
	}

	public void setSecret(int secret) {
		this.secret = secret;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public List<ReviewReplyBean> getReviewRelpyBeanList() {
		return reviewRelpyBeanList;
	}

	public void setReviewRelpyBeanList(List<ReviewReplyBean> reviewRelpyBeanList) {
		this.reviewRelpyBeanList = reviewRelpyBeanList;
	}

	public List<ReviewLikeBean> getReviewLikeBean() {
		return reviewLikeBean;
	}

	public void setReviewLikeBean(List<ReviewLikeBean> reviewLikeBean) {
		this.reviewLikeBean = reviewLikeBean;
	}


	public String getPeople() {
		return people;
	}


	public void setPeople(String people) {
		this.people = people;
	}
}
