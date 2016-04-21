package entities;

import java.time.LocalDate;

import util.Utils;

public class Computer {
	private long id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Company company;
	

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
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Computer other = (Computer) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (id != other.id)
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}


	public static class Builder {
		//required parameters
		private String name;
		
		//optional parameters
		private LocalDate introduced = null;
		private LocalDate discontinued = null;
		private Company company = null;
		
		
		public Builder(String name){
			this.name = name;
		}
		
		public Builder introduced(LocalDate date){
			this.introduced = date;
			return this;
		}
		public Builder discontinued(LocalDate date){
			this.discontinued = date;
			return this;
		}
		
		public Builder company(Company company){
			this.company = company;
			return this;
		}
		
		public Computer build(){
			return new Computer(this);
		}
	}
	
	private Computer(Builder builder){
		this.name = builder.name;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.company = builder.company;
	}
	
	
}
