import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

// Define URL & Test Data file
String url = 'https://www.saucedemo.com/v1/index.html'
String testDataFile = 'Data Files/Login'

// Open the browser
WebUI.openBrowser('')
WebUI.navigateToUrl(url)
WebUI.maximizeWindow()

// Get test data
def testData = findTestData(testDataFile)

// Loop all row
for (def row = 1; row <= testData.getRowNumbers(); row++) {
    // Get username and password from the current row
    String username = testData.getValue('Username', row)
    String password = testData.getValue('Password', row)
    
    WebUI.comment("Attempting login with Username: ${username} and Password: ${password}")

    // Perform login steps
    WebUI.setText(findTestObject('Object Repository/Login/inputUsername'), username)
    WebUI.setText(findTestObject('Object Repository/Login/inputPassword'), password)
    WebUI.click(findTestObject('Object Repository/Login/buttonLogin'))

    // Verify login
    if (username == 'standard_user') {
        boolean loginSuccess = WebUI.verifyElementPresent(findTestObject('Object Repository/Login/validLogin'), 10, FailureHandling.CONTINUE_ON_FAILURE)
        
        // Check if login is successful
        if (loginSuccess) {
            WebUI.comment("Login successful for ${username}")
            // Log out after successful login
            WebUI.click(findTestObject('Object Repository/Login/burgerMenu'))
            WebUI.click(findTestObject('Object Repository/Login/logoutButton'))
        } else {
            WebUI.comment("Login failed for ${username}")
        }
    } else {
        boolean loginFailure = WebUI.verifyElementPresent(findTestObject('Object Repository/Login/invalidLogin'), 10, FailureHandling.CONTINUE_ON_FAILURE)
        
        // Check invalid login
        if (loginFailure) {
            WebUI.comment("Invalid login attempt for ${username}")
        } else {
            WebUI.comment("Unexpected behavior for ${username}, no invalid login detected.")
        }
    }
    
    // Refresh page for next loop
    WebUI.navigateToUrl('https://www.saucedemo.com/v1/index.html')
}

// Close the browser
WebUI.closeBrowser()

