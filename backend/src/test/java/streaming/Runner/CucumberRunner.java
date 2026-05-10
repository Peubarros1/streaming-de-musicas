package streaming.runner;
 
import org.junit.platform.suite.api.*;
 
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource(".")           // lê os .feature copiados de ../features via pom.xml
@ConfigurationParameter(key = "cucumber.glue",          value = "streaming.steps")
@ConfigurationParameter(key = "cucumber.plugin",        value = "pretty, html:target/cucumber-report.html")
@ConfigurationParameter(key = "cucumber.publish.quiet", value = "true")
public class CucumberRunner {
    // Classe vazia — configuração feita pelas anotações acima
}
