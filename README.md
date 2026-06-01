# LostFoundApp

An Android app for reporting and finding lost items in your local area. Users post adverts with photos, descriptions, and GPS location — items appear as markers on an interactive Google Map and can be filtered by distance from your current location.

---

## Screenshots

> Add screenshots after running the app on an emulator

| Home | Create Advert | All Items | Map View |
|---|---|---|---|
| *(screenshot)* | *(screenshot)* | *(screenshot)* | *(screenshot)* |

---

## Features

- **Post lost & found adverts** — photo, description, category, contact details, location, auto timestamp
- **Google Places autocomplete** — search and select a location by name when creating a post
- **GPS location** — one-tap "Get Current Location" button fills in your exact coordinates
- **Interactive map view** — all items shown as colour-coded markers on Google Maps
- **Radius-based filtering** — slider to filter map markers by distance from your current location
- **Category filter** — filter the listings screen by item category
- **Remove adverts** — mark an item as returned and remove it from the list
- **Persistent storage** — all posts stored locally with SQLite including lat/lng coordinates

---

## Tech stack

![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=openjdk&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=flat&logo=android&logoColor=white)
![Google Maps](https://img.shields.io/badge/Google%20Maps%20SDK-4285F4?style=flat&logo=googlemaps&logoColor=white)

| Layer | Technology |
|---|---|
| Language | Java |
| Maps | Google Maps SDK for Android |
| Location autocomplete | Google Places API |
| GPS | Fused Location Provider |
| Database | SQLite (via DatabaseHelper) |
| UI | RecyclerView, Material Components, AppCompat |
| Build | Gradle |

---

## Architecture

```
app/
├── DatabaseHelper.java         # All SQLite ops — insert, query, delete; stores lat/lng per post
├── MainActivity.java           # Home screen — navigate to create, list, or map
├── CreateAdvertActivity.java   # Form — photo upload, Places autocomplete, GPS, validation
├── ShowAllItemsActivity.java   # RecyclerView list with category filter
├── ItemDetailActivity.java     # Full post view with remove button
├── MapActivity.java            # Google Maps — markers + radius slider filter
└── ItemAdapter.java            # RecyclerView adapter — binds items to cards
```

---

## How the map works

Each advert stores **latitude and longitude** in SQLite at creation time (either from Places autocomplete or the Fused Location Provider). `MapActivity` loads all items, places a colour-coded marker for each one, and applies a **radius filter** using the Haversine formula to calculate distance from the device's current location.

```java
// Radius filter — only show markers within selected distance
for (LostItem item : allItems) {
    float[] result = new float[1];
    Location.distanceBetween(
        currentLat, currentLng,
        item.getLatitude(), item.getLongitude(),
        result
    );
    if (result[0] / 1000 <= selectedRadiusKm) {
        // Add marker to map
    }
}
```

---

## Getting started

### Prerequisites
- Android Studio Hedgehog or later
- Android SDK 21+
- Java 11+
- A Google Maps API key ([get one here](https://console.cloud.google.com/))

### Run locally

```bash
git clone https://github.com/Vamshi-Gollapelly/LostFoundApp.git
```

1. Open in Android Studio
2. Add your Google Maps API key in `AndroidManifest.xml`:
   ```xml
   <meta-data
       android:name="com.google.android.geo.API_KEY"
       android:value="YOUR_API_KEY_HERE" />
   ```
3. Let Gradle sync
4. Run on emulator or physical device (Android 5.0+)

> ⚠️ Never commit your API key directly. Use `local.properties` or environment variables in production.

---

## Planned improvements

- [ ] Cloud sync so items are visible to other users in real time
- [ ] Push notifications when a new item is posted near your location
- [ ] Image storage on Firebase instead of local device
- [ ] In-app messaging between poster and finder

---

## What I learned

- Integrating Google Maps SDK and Google Places API in an Android app
- Using Fused Location Provider for accurate real-time GPS coordinates
- Storing and querying geolocation data (lat/lng) in SQLite
- Implementing distance-based filtering using the Haversine formula
- Building a full CRUD app with photo upload and form validation

---

## Author

**Vamshi Gollapelly**
[LinkedIn](https://linkedin.com/in/vamshigollapelly) · [GitHub](https://github.com/Vamshi-Gollapelly) · [Email](mailto:vamshigollapelly225@gmail.com)
