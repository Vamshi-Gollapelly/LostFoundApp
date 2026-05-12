# LostFoundApp

An Android app I built for SIT708 that helps people report and find lost items 
in their area. Task 7.1 covered the core listing features and Task 9.1 added 
Google Maps integration with live location and radius-based search.

## What it does

Users can post adverts for items they have lost or found. Each post includes a 
photo, description, contact details, category, location picked via autocomplete 
or GPS, and an automatic timestamp. Posts show up as markers on an interactive 
map and can be filtered by distance from your current location. Once an item is 
returned to its owner the advert can be removed from the list.

## Built with

- Java
- Android Studio
- SQLite for local data storage
- Google Maps SDK for Android
- Google Places API for location autocomplete
- Fused Location Provider for GPS
- RecyclerView for the listings screen
- Android Jetpack (AppCompat, Material Components)

## How to run it

1. Clone or download this repository
2. Open the project in Android Studio
3. Add your Google Maps API key in AndroidManifest.xml and MainActivity.java
4. Let Gradle sync finish
5. Run on an emulator or physical Android device (Android 5.0 or higher)

## App screens

- **Home** - buttons to create a new advert, view all listings, or open the map
- **Create Advert** - form to fill in item details, upload a photo, and pick a 
  location using autocomplete or the Get Current Location button
- **All Items** - scrollable list of all posts with a category filter at the top
- **Item Detail** - full view of a selected post with a remove button
- **Map View** - all items shown as colour coded markers on Google Maps with a 
  radius slider to filter by distance from current location

## Files worth noting

- `DatabaseHelper.java` handles all SQLite operations including insert, query, 
  and delete, and stores latitude and longitude for each post
- `CreateAdvertActivity.java` manages image upload, Places autocomplete, GPS 
  location retrieval, and form validation
- `MapActivity.java` loads all items onto Google Maps and applies radius-based 
  filtering using the device location
- `ShowAllItemsActivity.java` loads items from the database with category filter
- `ItemAdapter.java` binds each item to a card in the RecyclerView
