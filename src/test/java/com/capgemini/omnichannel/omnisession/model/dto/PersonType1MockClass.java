package com.capgemini.omnichannel.omnisession.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersonType1MockClass implements Serializable{
	private static final long serialVersionUID = -3713386222816709872L;

	// common fields
	private String name;
	private String surname;

	private int age;

	// different field
	private List<String> friendNamesList = new ArrayList<String>();

	public PersonType1MockClass() {
		super();
	}

	public PersonType1MockClass(String name, String surname, int age, String... friendNamesList) {
		super();
		this.name = name;
		this.surname = surname;
		this.age = age;

		this.friendNamesList.addAll(Arrays.asList(friendNamesList));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((friendNamesList == null) ? 0 : friendNamesList.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		PersonType1MockClass other = (PersonType1MockClass) obj;
		if (age != other.age)
			return false;
		if (friendNamesList == null) {
			if (other.friendNamesList != null)
				return false;
		} else if (!friendNamesList.equals(other.friendNamesList))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PersonType1MockClass [name=" + name + ", surname=" + surname + ", age=" + age + ", friendNamesList="
				+ friendNamesList + "]";
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

	public List<String> getFriendNamesList() {
		return friendNamesList;
	}

	public void setFriendNamesList(List<String> friendNamesList) {
		this.friendNamesList = friendNamesList;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}