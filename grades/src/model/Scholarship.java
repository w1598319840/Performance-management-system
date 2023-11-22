package model;

public class Scholarship {
	private String id;
	private String name;
	private String inst;
	private String major;
	private String type;
	private String reason;

	public Scholarship() {
		super();
	}

	public Scholarship(String id,String name, String inst, String major, String type, String reason) {
		this.id = id;
		this.name = name;
		this.inst = inst;
		this.major = major;
		this.type = type;
		this.reason = reason;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInst() {
		return inst;
	}

	public void setInst(String inst) {
		this.inst = inst;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
