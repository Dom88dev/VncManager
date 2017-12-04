package customer;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class FamDataModel {
	private SimpleStringProperty fName;
	private SimpleStringProperty relation;
	private SimpleIntegerProperty id;
	
	public FamDataModel(String fName, String relation, int id) {
		this.fName = new SimpleStringProperty(fName);
		this.relation = new SimpleStringProperty(relation);
		this.id = new SimpleIntegerProperty(id);
	}

	public String getfName() {
		return fName.get();
	}

	public void setfName(String fName) {
		this.fName.set(fName);;
	}

	public String getRelation() {
		return relation.get();
	}

	public void setRelation(String relation) {
		this.relation.set(relation);;
	}
	public int getId() {
		return id.get();
	}
	public void setId(int id) {
		this.id.set(id);
	}
	
}
