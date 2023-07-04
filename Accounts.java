package JDBC;

public class Accounts {

	private String name;
	private String id;
	private double balance;

	
	public Accounts() {
	}

	public Accounts(String name, String id, double balance) {
		super();
		this.name = name;
		this.id = id;
		this.balance = balance;
	}

	public Accounts(String name, String id) {
		super();
		this.name = name;
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
}
