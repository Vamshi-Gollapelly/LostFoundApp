# LostFoundApp

A simple Android app I built for SIT708 Task 7.1 that helps people report and find lost items in their area.

## What it does

The app lets users post adverts for items they have lost or found. Each post includes a photo, description, contact details, category, location, and an automatic timestamp. Other users can browse the listings and filter by category to find relevant posts. Once an item is returned to its owner, the advert can be removed from the list.

## Built with

- Java
- Android Studio
- SQLite for local data storage
- RecyclerView for the listings screen
- Android Jetpack (AppCompat, Material Components)

## How to run it

1. Clone or download this repository
2. Open the project in Android Studio
3. Let Gradle sync finish
4. Run on an emulator or connect a physical Android device (Android 5.0 or higher)

## App screens

- **Home** - two buttons to either create a new advert or view all listings
- **Create Advert** - form to fill in item details, select a category, upload a photo, and save
- **All Items** - scrollable list of all posts with a category filter at the top
- **Item Detail** - full view of a selected post with a remove button at the bottom

## Files worth noting

- `DatabaseHelper.java` handles all SQLite operations including insert, query, and delete
- `CreateAdvertActivity.java` manages the image picker, permission handling, and form validation
- `ShowAllItemsActivity.java` loads items from the database and applies category filtering
- `ItemAdapter.java` binds each item to a card view in the RecyclerView

