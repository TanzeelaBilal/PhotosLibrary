
# Flickr Pics

This is an Android project fetching pictures from the custom Flickr API. 

It supports light and dark mode both and can be run in both the configurations i.e. Landscape and Portrait. 

The data is received in bulk and the pagination is not supported by API for now. Therefore pagination on the list on scroll is implemented. The Quick Return pattern is also added using header of the listview.

The project uses following dependencies


## Architecture and Libraries

Architecture implemented : MVVM

Network : Retrofit

Image Library : Coil

Dependency Injection : Hilt

Unit Testing : Mockito

## Why chose MVVM?

Although it was a smaller project and MVVM is not advised for smaller projects but keeping scalability in mind I chose MVVM. MVVM has following advantages

Easier to develop: Separating the View from the logic makes it possible to have different teams work on different components simultaneously. A part of team can focus on the UI while others can work on implementing the logic (ViewModel and Model).

Easier to maintain: The separation between the different components of the application makes the code simpler and cleaner. As a result, the application code is much easier to understand, and therefore, to maintain. It’s easier to understand where we should implement new features and how they connect to the existing pattern. With an MVVM, it’s also easier to implement further architectural patterns (dependency inversion, services and more). 

Configurational changes are supported
