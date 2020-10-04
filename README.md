# Simple-RSS-Reading
News feed application, using standards Material Design components.\
Android 6 or higher required.

**Usages:**
> API from https://www.vesti.ru/ \
> Java\
> MVP\
> Refrofit for working with API and parsing XML\
> Glide for load news images and its thumbnails\
> Realm for save news in local DB\
> AndroidX components: RecyclerView, CardView and SwipeRefreshLayout
# Main Screen
List of news, each news includes title and publication date.\
Under ActionBar is the progress of scrolling the news feed (filled with red).\
It is also possible to display news from a specific category. The list of categories is parsed every time the news is loaded.\
In case of error loading news, a snackBar appears with an error notification and a "Retry" button.\
![hippo](https://media.giphy.com/media/9lLfsM3B7pCO5XFSr5/giphy.gif)
![hippo](https://media.giphy.com/media/lXK9mVHGBMeq6k1UHA/giphy.gif)
![hippo](https://media.giphy.com/media/TpI1qOPD0Sucg41ycr/giphy.gif)
# Screen with one news
A specific news item is displayed in full: photo (if any), title, text and date.\
ActionBar has two buttons: a "Back" button to exit back to the news list and an "Open in browser" button to open news in browser.\
![hippo](https://media.giphy.com/media/WJhh0ifnSOYtLWgJ4V/giphy.gif)
