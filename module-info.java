open module edu.sb.cookbook.model {
	requires transitive java.sql;
	requires transitive java.logging;
	requires transitive jakarta.annotation;
	requires transitive jakarta.validation;
	requires transitive jakarta.json.bind;
	requires transitive jakarta.xml.bind;
	requires transitive jakarta.ws.rs;
	requires transitive jakarta.persistence;
	requires transitive eclipselink;

	exports edu.sb.tool;
	exports edu.sb.cookbook.persistence;
	exports edu.sb.cookbook.service;
}