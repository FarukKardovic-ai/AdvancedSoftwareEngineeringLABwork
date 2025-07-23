

// Test of the Feature: Exam registration
//
//  As a student
//I want to register for an exam via the GUI
//So that I'm signed up in the system
//
//Scenario: Successful registration
//  Given I open the registration window
//  When I enter "Alice" as student name
//  And I select exam "Math 101"
//And I click "Register"
//Then I should see a confirmation message
//And the exam "Math 101" should appear in the registered list for "Alice"
//
//Scenario: Prevent duplicate registration
//Given "Alice" is already registered for "Math 101"
//When "Alice" tries to register again for "Math 101"
//Then the system should show an error message "Already registered"




// the class is written as an example of the test scenario described above
class OOPExamTest {

    var app: ExamApp = _
    var ui: AppFixture = _

    @Given("I open the registration window")
    def openWindow() = { ui = new AppFixture(); app = ui.launch() }

    @When("""I enter "([^"]*)" as student Name""")
    def enterName(name: String) = ui.enterStudent(name)

    @When("""I select exam "([^"]*)"""")
    def selectExam(course: String) = ui.chooseCourse(course)

    @When("I click {string}")
    def click(btn: String) = ui.clickButton(btn)

    @Then("I should see a confirmation message")
    def checkConfirm() = assert(ui.hasConfirmation())

    @Then("the exam appears in my registered list")
    def checkInList() = assert(ui.registeredList.contains("Math 101"))

    @Given(""" "([^"]*)" is already registered for "([^"]*)" """)
    def preRegister(name: String, course: String) = {
      ui = new AppFixture(); app = ui.launch()
      ui.enterStudent(name); ui.chooseCourse(course); ui.clickButton("Register")
      assert(ui.registeredList.contains(course))
    }

    @Then("""the system should show an error "([^"]*)"""")
    def checkError(msg: String) = assert(ui.hasError(msg))

}
