Weather App

Simple app written in java that shows the weather at a given city.

On the first launch, app displays the weather at the current location.

User can search for the city to display the current weather.

Launches the corresponding webpage from openweathermap.org for the displayed city.

App uses the public API from openweathermap.org to fetch the weather details.

All edge cases handled.

Weather App follows MVVM architecture design pattern and uses the following libraries :
* Retrofit 
* Rx Java
* Espresso
* Picasso
* Architecture Components
 	LiveData
	ViewModel
	Navigation

Enhancements:
* Display weather forecast details Hourly, Next day, Next 10 days.
* Handle edge cases for network connectivity

Test Cases:
Basic test cases covered for navigation, Fragment and location in lieu of time
