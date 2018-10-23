package product.model;

import org.springframework.web.multipart.MultipartFile;

public class ProductBean {

	private int num;		//������
	private String name;	//�̸�
	private String category;	//ī�װ�
	private int price;		//����
	private String country;		//������
	private String company;		//��޻�
	private int weight;		//�߷�
	private int count;		//����
	private String image;		//�̹���
	private String contents;		//����
	

	
	private MultipartFile upload;
	
	private String upload2; 
	
	
	
	public MultipartFile getUpload() {
		return upload;
	}
	
	public void setUpload(MultipartFile upload) {
		System.out.println("setUpload");
		this.upload = upload;
		System.out.println("upload:"+upload); 
		
		if( this.upload != null){ 
			
			this.image = this.upload.getOriginalFilename();  //�� �Ϸ�
			
		}
	}
	
	
	public String getUpload2() {
		return upload2;
	}
	public void setUpload2(String upload2) {
		System.out.println("setUpload2");
		this.upload2 = upload2;
	}

	public ProductBean() {
		super();
	}

	public ProductBean(int num, String name, String category, int price, String country, String company, int weight,
			int count, String image, String contents, MultipartFile upload, String upload2) {
		super();
		this.num = num;
		this.name = name;
		this.category = category;
		this.price = price;
		this.country = country;
		this.company = company;
		this.weight = weight;
		this.count = count;
		this.image = image;
		this.contents = contents;
		this.upload = upload;
		this.upload2 = upload2;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
	
	
	
	
}
