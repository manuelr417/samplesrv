package models;

import java.util.ArrayList;
import java.util.UUID;


public class PersonList {
	
	private ArrayList<Person> personList;
	int counter = 1;
	
	private PersonList(){
		this.personList = new ArrayList<Person>(10);
	}
	
	public Person getPersonById(long id){
		System.err.println("List len: " + this.personList.size());
		for (Person p : this.personList){
			System.err.println("id: " + id +  "p.Id: " + p.getId());
			if (p.getId() == id){
				return p;
			}
		}
		return null;
	}
	
	public Person[] getAllPerson(){
		Person result[] = new Person[this.personList.size()];
		for (int i=0; i < this.personList.size(); ++i){
			result[i] = this.personList.get(i);
		}
		return result;
	}
	
	public Person addPerson(Person obj){
		long id = this.counter++;
		obj.setId(id);
		this.personList.add(obj);
		return obj;
	}
	
	public boolean deletePerson(long id){
		int target = -1;
		
		for (int i=0; i< this.personList.size(); ++i){
			if (this.personList.get(i).getId() == id){
				target = i;
				break;
			}
		}
		if (target == -1){
			return false;
		}
		else {
			this.personList.remove(target);
			return true;
		}
	}
	
	public Person updatePerson(Person obj){
		int target = -1;
		
		for (int i=0; i< this.personList.size(); ++i){
			if (this.personList.get(i).getId() == obj.getId()){
				target = i;
				break;
			}
		}
		if (target == -1){
			return null;
		}
		else {
			Person P = this.personList.get(target);
			P.setAge(obj.getAge());
			P.setFirstName(obj.getFirstName());
			P.setLastName(obj.getLastName());
			return P;
		}
	}
	
	private static PersonList singleton= new PersonList();
	
	public static PersonList getInstance(){
		return singleton;
	}
}
