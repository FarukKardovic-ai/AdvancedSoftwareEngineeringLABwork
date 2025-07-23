package steps

import io.cucumber.java.en.{Given, When, Then}
import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.{By, Keys, WebDriver}
import org.openqa.selenium.chrome.ChromeDriver
import org.scalatest.matchers.should.Matchers

class GoogleSteps extends Matchers {

  var driver: WebDriver = _

  @Given("I am on the Google homepage")
  def goToGoogle(): Unit = {
    WebDriverManager.chromedriver().setup()
    driver = new ChromeDriver()
    driver.get("https://www.google.com")
  }

  @When("I search for {string}")
  def searchFor(query: String): Unit = {
    val searchBox = driver.findElement(By.name("q"))
    searchBox.sendKeys(query)
    searchBox.sendKeys(Keys.RETURN)
  }

  @Then("the page title should contain {string}")
  def pageTitleShouldContain(expected: String): Unit = {
    Thread.sleep(2000) // Consider using WebDriverWait
    driver.getTitle should include(expected)
    driver.quit()
  }
}
