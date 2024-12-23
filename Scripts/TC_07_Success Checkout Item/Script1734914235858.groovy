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
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import org.openqa.selenium.WebElement
import java.math.RoundingMode

// Load Data from Test Data File
String DataFiles = 'Data Files/Checkout Form'
def Data = findTestData(DataFiles)
String firstName = Data.getValue('First Name', 1)
String lastName = Data.getValue('Last Name', 1)
String postalCode = Data.getValue('Postal Code', 1)

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

// Fill Checkout Form
WebUI.setText(findTestObject('Object Repository/Checkout/inputFirstName'), firstName)
WebUI.setText(findTestObject('Object Repository/Checkout/inputLastName'), lastName)
WebUI.setText(findTestObject('Object Repository/Checkout/inputPostCode'), postalCode)

// Continue to Checkout Overview
WebUI.click(findTestObject('Object Repository/Checkout/continueButton'))

// Function to Extract Numeric Value from Text 
// If the input is "$12.99", the function returns 12.99 as a BigDecimal.
def extractNumericValue(String text) {
    return new BigDecimal(text.replaceAll("[^\\d.]", ""))
}

// Get Price Texts For Every Item
def productPricesText = [
    WebUI.getText(findTestObject('Object Repository/Checkout/product1Price')),
    WebUI.getText(findTestObject('Object Repository/Checkout/product2Price')),
    WebUI.getText(findTestObject('Object Repository/Checkout/product3Price'))
]
def totalPriceText = WebUI.getText(findTestObject('Object Repository/Checkout/totalPriceOnTax'))
def totalPriceAfterTaxText = WebUI.getText(findTestObject('Object Repository/Checkout/totalPrices'))

// Convert Text to Numeric Values
// totalPrice and totalPriceAfterTax: These lines convert the total prices (before and after tax) into BigDecimal values
BigDecimal[] productPrices = productPricesText.collect { extractNumericValue(it) }
BigDecimal totalPrice = extractNumericValue(totalPriceText)
BigDecimal totalPriceAfterTax = extractNumericValue(totalPriceAfterTaxText)

// Calculate Total Price Including Tax
//This sums up all the product prices in the productPrices array to get the total price before tax
BigDecimal expectedTotalBeforeTax = productPrices.sum() 
// This defines the tax rate 8%
BigDecimal taxRate = new BigDecimal("0.08") 
//This calculates the total price after tax by multiplying the total before tax
BigDecimal expectedTotalAfterTax = expectedTotalBeforeTax.multiply(BigDecimal.ONE.add(taxRate)).setScale(2, RoundingMode.HALF_UP) 

// Log for Verification
println "Product Prices: $productPrices"
println "Total Price Before Tax (Extracted): $totalPrice"
println "Total Price After Tax (Extracted): $totalPriceAfterTax"
println "Expected Total Before Tax: $expectedTotalBeforeTax"

// Verifications
// This function verifies if the extracted values (totalPrice, totalPriceAfterTax) match the expected values 
// (expectedTotalBeforeTax, expectedTotalAfterTax)
WebUI.verifyEqual(totalPrice, expectedTotalBeforeTax, FailureHandling.STOP_ON_FAILURE)
WebUI.verifyEqual(totalPriceAfterTax, expectedTotalAfterTax, FailureHandling.STOP_ON_FAILURE)

// Finish Checkout
WebUI.click(findTestObject('Object Repository/Checkout/finishButton'))

// Verify Checkout Success
WebUI.verifyElementText(findTestObject('Object Repository/Checkout/successCheckout'), 'THANK YOU FOR YOUR ORDER')

// Close Browser
WebUI.closeBrowser()

