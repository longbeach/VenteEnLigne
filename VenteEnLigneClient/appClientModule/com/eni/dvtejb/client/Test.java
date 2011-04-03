package com.eni.dvtejb.client;

public class Test {

	public static void main(String[] args) {
		
		TypeElement typeElement = new TypeElement();
		
		// String equals Element
		if ((Element.toto).equals(typeElement.getElement())){
			System.out.println("Vrai");
		} else {
			System.out.println("Faux");
		}

	}

}
