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

// Call Valid Login Test Case
WebUI.callTestCase(findTestCase('Test Cases/TC_01_Valid Login'), null)

// Add Items to the Cart
WebUI.click(findTestObject('Object Repository/Cart/Item1_Add To Cart'))
WebUI.click(findTestObject('Object Repository/Cart/Item2_Add To Cart'))
WebUI.click(findTestObject('Object Repository/Cart/Item3_Add To Cart'))

// View Cart
WebUI.click(findTestObject('Object Repository/Cart/cartButton'))

// Verify Items in the Cart
boolean isItem1InCart = WebUI.verifyElementPresent(findTestObject('Object Repository/Cart/Item1_inCart'), 10, FailureHandling.OPTIONAL)
boolean isItem2InCart = WebUI.verifyElementPresent(findTestObject('Object Repository/Cart/Item2_inCart'), 10, FailureHandling.OPTIONAL)
boolean isItem3InCart = WebUI.verifyElementPresent(findTestObject('Object Repository/Cart/Item3_inCart'), 10, FailureHandling.OPTIONAL)

if (isItem1InCart && isItem2InCart && isItem3InCart) {
	WebUI.comment('All items are successfully added to the cart.')
} else {
	WebUI.comment('ERROR: One or more items were not added to the cart correctly.')
	throw new AssertionError('Items verification failed in the cart.')
}

// Proceed to Checkout
WebUI.click(findTestObject('Object Repository/Checkout/checkoutButton'))

// Continue to Checkout Overview
WebUI.click(findTestObject('Object Repository/Checkout/continueButton'))

// Check the form 
WebUI.verifyElementPresent(findTestObject('Object Repository/Checkout/formCheck'), 10)

WebUI.closeBrowser()