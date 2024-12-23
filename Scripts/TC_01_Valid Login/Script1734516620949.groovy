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

import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// Define URL & Test Data file
String url = 'https://www.saucedemo.com/v1/index.html'
String DataFiles = 'Data Files/Login'

// Open the browser
WebUI.openBrowser('')
WebUI.navigateToUrl(url)
WebUI.maximizeWindow()

// Get test data from the Data Files
def Data = findTestData(DataFiles)
String username = Data.getValue('Username', 1)
String password = Data.getValue('Password', 1)

// Log Verification
WebUI.comment("Logging in with Username: ${username} and Password: ${password}")

// Login
WebUI.setText(findTestObject('Object Repository/Login/inputUsername'), username)
WebUI.setText(findTestObject('Object Repository/Login/inputPassword'), password)
WebUI.click(findTestObject('Object Repository/Login/buttonLogin'))
WebUI.delay(2)

// Verify success login
boolean isLoginSuccess = WebUI.verifyElementPresent(findTestObject('Object Repository/Login/validLogin'), 10, FailureHandling.CONTINUE_ON_FAILURE)

// Log the result
if (isLoginSuccess) {
	WebUI.comment("Login Success, Valid Credentials || Username: ${username} and Password: ${password}")
} else {
	WebUI.comment('Login Failed')
}
