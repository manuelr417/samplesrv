package controllers;

import models.Person;
import models.PersonList;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class PersonController extends Controller {
	
	public static Result getPerson(Long id){
		System.out.println("Get id: "+ id);
		ObjectNode result = Json.newObject();
		PersonList theList = PersonList.getInstance();
		Person P = theList.getPersonById(id);
		if (P == null){
			System.out.println("Not Found");
			return notFound(); // 404
//			result.put("Person", Json.toJson(new Person(1L, "Joe","Diaz", 22)));
//			return ok(result);
//			//return ok(Json.toJson(new Person(1L, "Joe","Diaz", 22)));
		}
		else {
			System.out.println("Get: " + P);
			result.put("Person", Json.toJson(P));
			return ok(result);
	
		}
	}
	
	@BodyParser.Of(BodyParser.Json.class)
	public static Result storePerson(){
		ObjectMapper mapper = new ObjectMapper();
		 try {
			 System.out.println("Read Data");
			 JsonNode json = request().body().asJson();
			 System.err.println("json: " + json);
			 Person newPerson = mapper.readValue(json.toString(), Person.class);
			 PersonList theList = PersonList.getInstance(); 
			 newPerson = theList.addPerson(newPerson);
			 ObjectNode result = Json.newObject();
			 result.put("Person", Json.toJson(newPerson));
			 return created(result);
		 }
		 catch(Exception e){
			 e.printStackTrace();
			 return badRequest("Missing information");
		 }
		
	}
	
	@BodyParser.Of(BodyParser.Json.class)
	public static Result updatePerson(Long id){
		ObjectMapper mapper = new ObjectMapper();
		try {
			 System.out.println("Read Data");
			 JsonNode json = request().body().asJson();
			 System.err.println("json: " + json);
			 Person updPerson = mapper.readValue(json.toString(), Person.class);
			 PersonList theList = PersonList.getInstance(); 
			 updPerson = theList.updatePerson(updPerson);
			 System.err.println("Upd: " + updPerson);
			 ObjectNode result = Json.newObject();
			 result.put("Person", Json.toJson(updPerson));
			 return ok(result);		
		}
		catch(Exception e){
			 e.printStackTrace();
			 return badRequest("Missing information");			
		}
	}
	
	public static Result deletePerson(Long id){
		System.out.println("Get id: "+ id);
		PersonList theList = PersonList.getInstance();
		boolean erased = theList.deletePerson(id);
		if (erased){
			System.out.println("Erased");
			// This is code 204 - OK with no content to return
			return noContent();
		}
		else {
			System.out.println("Not Erased");
			return notFound("Person not found");
		}

	}

}
