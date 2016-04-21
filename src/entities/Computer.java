package entities;

import java.time.LocalDate;

public class Computer {
	private long id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Company company;
	
	private long company_id;
	private String companyName;
	
	
	public long getId(){
		return this.id;
	}
	public void setId(long id){
		this.id = id;
	}
	
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	public LocalDate getIntroduced(){
		return this.introduced;
	}
	public void setIntroduced(LocalDate introduced){
		this.introduced = introduced;
	}
	
	public LocalDate getDiscontinued(){
		return this.discontinued;
	}
	public void setDiscontinued(LocalDate discontinued){
		this.discontinued = discontinued;
	}
	
	public Company getCompany(){
		return this.company;
	}
	public void setCompany(Company company){
		this.company = company;
	}
	
	public long getCompanyId(){
		return this.company_id;
	}
	public void setCompanyId(long company_id){
		this.company_id = company_id;
	}
	
	public void setCompanyName(String companyName){
		this.companyName = companyName;
	}
	public String getCompanyName(){
		return this.companyName;
	}
	
	
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("- ID: ");
		buffer.append(this.id);
		buffer.append(", name: ");
		buffer.append(this.name);
		buffer.append(", introduced on: ");
		buffer.append(this.introduced);
		buffer.append(", discontinued on: ");
		buffer.append(this.discontinued);

		if(this.company != null) {
			buffer.append(", provided by ");
			buffer.append(this.company.getName());
		}
	
		return buffer.toString();
	}
	
	
}
