# The Shortcutter's Android Challenge

## Completed Tasks
-------------------------------------
- [x] browse through the comics,
- [x] see the comic details, including its description,
- [x] search for comics by the comic number as well as text,
- [ ] get the comic explanation
- [x] favorite the comics, which would be available offline too,
- [x] send comics to others,
- [ ] get notifications when a new comic is published,
- [x] support multiple form factors.

## Challenges and Solutions
- Used MVVM architecture with Repository pattern. The Repository pattern is used as a mediator between different data sources, such as persistent models and web service. 
- Provided browse through all XKCD comics was challenging as the [API](https://xkcd.com/json.html) returns always just one json object as a result. I wanted to display all comics in a scroll view, so I decided to use the [Paging Library](https://developer.android.com/topic/libraries/architecture/paging/) from the Android Architecture Components, so that data is being requested on small portion at a time, providing pagination through all existing comics. The challenge was that the library provides a
[PageKeyedDataSource](https://developer.android.com/reference/android/arch/paging/PageKeyedDataSource) that helps request data from APIs that include a next/previous link or key with each page load. However, this was not the case with the XKCD API, so I had to come up with a workaround and provide a `number of comic` instead (and decrements it each time). The first network request is made to the http://xkcd.com/info.0.json (current comic) endpoint that returns always the latest comic. Then with the help of `loadAfter()` method the app requests data from the second endpoint: http://xkcd.com/{comicNumber}/info.0.json and browse through all existing comics, by requesting a new number each time. 
[Link to the solution](https://github.com/fireflyfif/shortcut-android-challenge/blob/master/app/src/main/java/com/example/android/myxkcdcomics/ui/comicsfragment/paging/ComicDataSource.java)
- The user can save items into a local database. The [Room Persistence Library](https://developer.android.com/topic/libraries/architecture/room) is used to provide an abstraction layer over SQLite, so that the user can access starred comics even offline. Each call to the database is made on a background thread.
- Provided portrait and landscape layout, as well as support for bigger screens (such as tablets). The app uses dimens that corresponds to each case, hence provide a better user experience.
- Provided search options through the WebView that directly access the https://relevantxkcd.appspot.com/ url. The WebView was too large and it wasn't fitting the screen at first, so it needed adjustments. In order for the user to search by providing either a comic number or text the JavaScrip had to be enabled on the WebView. The result from the search is provided in a link that when clicked loads a new url with the comic image. While it's not yet ideal, it's still functional.  
- Comics can be shared by providing the link to the current comic's image. 

## Libraries
- [Support Libraries](https://developer.android.com/topic/libraries/support-library/)
- [Retrofit2](https://github.com/square/retrofit)
- [Picasso](https://github.com/square/picasso)
- [Architecture Components](https://developer.android.com/topic/libraries/architecture/)
- [Room Persistence Library](https://developer.android.com/topic/libraries/architecture/room)
- [Paging Library](https://developer.android.com/topic/libraries/architecture/paging/)
- [Butterknife](https://github.com/JakeWharton/butterknife)
- [OkHttp](https://github.com/square/okhttp)
- [AHBottomNavigation](https://github.com/aurelhubert/ahbottomnavigation)
- [Mockito](https://github.com/mockito/mockito)

## Screenshots

### Phone 
![text](https://github.com/fireflyfif/shortcut-android-challenge/blob/master/art/phone_collection_2.png)

### Tablet
![text](https://github.com/fireflyfif/shortcut-android-challenge/blob/master/art/tablet_collection.png)
