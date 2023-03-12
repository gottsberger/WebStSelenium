# Selenium  project

This project uses Selenium to automate testing of the WebstaurauntStore site. Scenarios tested include:
- Searching for an item and validating all items returned include an expected word in the description.
- User is able to add and remove item from the cart.

It uses the WebDriverManager library to handle Selenium browser dependencies, and Chrome is the only browser tested against.

## Reporting

I added some basic reporting to the project, to use this, simply enter the following command in the terminal:
>mvn surefire-report:report

It will run the tests and output a html report in the target->site directory when ran.