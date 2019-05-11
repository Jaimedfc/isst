package es.upm.dit.isst.gdpr.test;

// Generated by Selenium IDE
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import es.upm.dit.isst.gdpr.dao.SolicitudDAO;
import es.upm.dit.isst.gdpr.dao.UsuarioDAOImplementation;
import es.upm.dit.isst.gdpr.model.Solicitud;
import es.upm.dit.isst.gdpr.model.Usuario;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
public class AceptarSolicitudTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
	System.setProperty( "webdriver.chrome.driver", "chromedriver.exe");
	driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
    UsuarioDAO udao = UsuarioDAOImplementation.getInstance();
    SolicitudDAO sdao = SolicitudDAOImplementation.getInstance();
    
    Usuario inves = new Usuario();
    inves.setEmail("prueba@prueba");
    inves.setPassword("9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08");
    inves.setMcde(false);
    udao.create(inves);
    Usuario mcde = new Usuario();
    mcde.setEmail("test@test");
    mcde.setPassword("9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08");
    mcde.setMcde(true);
    udao.create(mcde);
    Solicitud sol = new Solicitud();
    sol.setEstado(1);
    sol.setTitulo("Esto es una Prueba");
    sol.setInvestigador(udao.read("prueba@prueba"));
    sdao.create(sol);
    
  }
  @After
  public void tearDown() {
    driver.quit();
    UsuarioDAO udao = UsuarioDAOImplementation.getInstance();
    SolicitudDAO sdao = SolicitudDAOImplementation.getInstance();
    ArrayList<Solicitud> sols = (ArrayList<Solicitud>) sdao.readAll();
	for(Solicitud sol: sols) {
		sdao.delete(sol);
	}
	ArrayList<Usuario> usuarios = (ArrayList<Usuario>) udao.readAll();
	for(Usuario usu: usuarios) {
		udao.delete(usu);
	}
  }
  @Test
	  public void aceptarSolicitudTest() {
	    driver.get("http://localhost:8080/GDPR/");
	    driver.manage().window().setSize(new Dimension(1568, 993));
	    driver.findElement(By.linkText("Log In")).click();
	    driver.findElement(By.name("correo")).click();
	    driver.findElement(By.name("correo")).sendKeys("test@test");
	    driver.findElement(By.id("passwd")).click();
	    driver.findElement(By.id("passwd")).sendKeys("test");
	    driver.findElement(By.cssSelector(".btn")).click();
	    driver.findElement(By.cssSelector(".icon-button > .material-icons")).click();
	    driver.findElement(By.cssSelector(".btn-primary:nth-child(4)")).click();
	    File file = new File("testData/prueba.pdf");
	    driver.findElement(By.id("file")).sendKeys(file.getAbsolutePath());
	    driver.findElement(By.cssSelector(".btn-primary:nth-child(4)")).click();
	    driver.findElement(By.id("navbarDropdown")).click();
	    driver.findElement(By.linkText("Log Out")).click();
	    driver.findElement(By.linkText("Log In")).click();
	    driver.findElement(By.name("correo")).click();
	    driver.findElement(By.name("correo")).sendKeys("prueba@prueba");
	    driver.findElement(By.id("passwd")).click();
	    driver.findElement(By.id("passwd")).sendKeys("test");
	    driver.findElement(By.cssSelector(".btn")).click();
	    driver.findElement(By.cssSelector(".text-success")).click();
	    assertThat(driver.findElement(By.cssSelector(".text-success")).getText(), is("Aprobado"));
	  }
}
