package de.htwsaar.pib2021.inet.model;

import org.hibernate.annotations.Parameter;

public @interface GenericGenerator {

	String name();

	String strategy();

	Parameter[] parameters();

}
