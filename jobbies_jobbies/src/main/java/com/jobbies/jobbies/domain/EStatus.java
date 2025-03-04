package com.jobbies.jobbies.domain;

public enum EStatus {
	APPLIED("Aplicado"),
	PROCESS("En proceso"),
	NOT_APPLIED("Sin aplicar");

	private String name;

	EStatus(String name) {
		this.name = name;
	}
	
	


}
