package entities;

public class Company {
	
	private long id;
	private String name;
	
	//TODO constructeur ?
	
	public long getId(){
		return this.id;
	}
	public void setId(long id){
		this.id = id;
	}
	
	public void setName(String name){
		this.name= name;
	}
	public String getName(){
		return this.name;
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("- ID: ");
		buffer.append(this.id);
		buffer.append(", name: ");
		buffer.append(this.name);
		
		return buffer.toString();
	}
	

}
