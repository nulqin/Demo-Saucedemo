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

// Call Valid Login
WebUI.callTestCase(findTestCase('Test Cases/TC_01_Valid Login'), null)

// Add Items to the Cart
WebUI.click(findTestObject('Object Repository/Cart/Item1_Add To Cart'))
WebUI.click(findTestObject('Object Repository/Cart/Item2_Add To Cart'))
WebUI.click(findTestObject('Object Repository/Cart/Item3_Add To Cart'))

// View Cart
WebUI.click(findTestObject('Object Repository/Cart/cartButton'))

// Verify items in the cart
boolean isItem1InCart = WebUI.verifyElementPresent(findTestObject('Object Repository/Cart/Item1_inCart'), 10, FailureHandling.OPTIONAL)
boolean isItem2InCart = WebUI.verifyElementPresent(findTestObject('Object Repository/Cart/Item2_inCart'), 10, FailureHandling.OPTIONAL)
boolean isItem3InCart = WebUI.verifyElementPresent(findTestObject('Object Repository/Cart/Item3_inCart'), 10, FailureHandling.OPTIONAL)

if (isItem1InCart && isItem2InCart && isItem3InCart) {
    WebUI.comment('All items are successfully added to the cart.')
} else {
    WebUI.comment('One or more items were not added to the cart correctly.')
}

// Remove Items from Cart
if (isItem1InCart) {
    WebUI.click(findTestObject('Object Repository/Cart/buttonRemove'))  // Remove button for Item 1
    WebUI.comment('Item 1 removed from cart.')
} else {
    WebUI.comment('Item 1 is not present in the cart to remove.')
}

// End the test (close browser)
WebUI.closeBrowser()
