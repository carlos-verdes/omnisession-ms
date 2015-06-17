package com.capgemini.omnichannel.omnisession.model.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class PersonType2MockClass implements Serializable {
	private static final long serialVersionUID = 1135599662799457858L;

	// common fields
	private String name;
	private String surname;

	private Integer age;

	// complex children
	private PersonType2MockClass parent;

	// map
	private Map<String, String> someKeyValues = new HashMap<String, String>();

	public PersonType2MockClass() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PersonType2MockClass(String name, String surname, int age, String parentName, String someKey,
			String someValue) {
		super();
		this.name = name;
		this.surname = surname;
		this.age = age;

		if (parentName != null) {
			this.parent = new PersonType2MockClass(parentName, surname, -1, null, null, null);
		}
		if (someKey != null && someValue != null) {
			this.someKeyValues.put(someKey, someValue);
		}

	}

	@Override
	public String toString() {
		return "PersonType2MockClass [name=" + name + ", surname=" + surname + ", age=" + age + ", someKeyValues="
				+ someKeyValues + "]" + ", parent=" + parent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((age == null) ? 0 : age.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result + ((someKeyValues == null) ? 0 : someKeyValues.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
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
		PersonType2MockClass other = (PersonType2MockClass) obj;
		if (age == null) {
			if (other.age != null)
				return false;
		} else if (!age.equals(other.age))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (someKeyValues == null) {
			if (other.someKeyValues != null)
				return false;
		} else if (!someKeyValues.equals(other.someKeyValues))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public PersonType2MockClass getParent() {
		return parent;
	}

	public void setParent(PersonType2MockClass parent) {
		this.parent = parent;
	}

	public Map<String, String> getSomeKeyValues() {
		return someKeyValues;
	}

	public void setSomeKeyValues(Map<String, String> someKeyValues) {
		this.someKeyValues = someKeyValues;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
