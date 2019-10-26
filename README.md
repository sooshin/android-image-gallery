# ImageGallery
![ic_launcher](https://user-images.githubusercontent.com/33213229/67619024-7ca84800-f831-11e9-9249-8c8e6035f599.png)

## Project Overview
Image Gallery displays a gallery of the image in a staggered grid layout, and when you tap the image, it gives you the detail of each image.
You can also download images by tapping download button.

This app uses images from [pixabay.com](https://pixabay.com/)

## Screenshots
![img_gallery_01](https://user-images.githubusercontent.com/33213229/67619163-36ec7f00-f833-11e9-972e-051eef1a9fba.png)
![img_gallery_02](https://user-images.githubusercontent.com/33213229/67619166-38b64280-f833-11e9-8a52-dbd589702126.png)

## Image Resources
[Icon](https://www.flaticon.com/free-icon/comment-white-oval-bubble_25663)
made by [Dave Gandy](https://www.flaticon.com/authors/dave-gandy) from [www.flaticon.com](www.flaticon.com)

[Icon](https://www.flaticon.com/free-icon/download_1665583)
made by [Freepik](https://www.flaticon.com/authors/freepik) from [www.flaticon.com](www.flaticon.com)

## Libraries
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/) 
    * [Paging](https://developer.android.com/topic/libraries/architecture/paging) - Load and display small chunks of data at a time.
    * [Lifecycles](https://developer.android.com/topic/libraries/architecture/lifecycle) - Create a UI that automatically responds to lifecycle events.
    * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Store UI-related data that isn't destroyed on app rotations. Easily schedule
     asynchronous tasks for optimal execution.
    * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Build data objects that notify views when the underlying database changes.
    * [Navigation](https://developer.android.com/guide/navigation/navigation-getting-started) - Handle everything needed for in-app navigation.
    * [Data Binding](https://developer.android.com/topic/libraries/data-binding/) - Declaratively bind observable data to UI elements.
    
- [Retrofit](http://square.github.io/retrofit/) for REST api communication
- [Moshi](https://github.com/square/moshi) for parsing JSON into Kotlin objects
- [Glide](https://github.com/bumptech/glide) for image loading
- [Kotlin Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) for managing background threads with simplified code and reducing needs for callbacks.

## License
Apache, see the [LICENSE](LICENSE) file.

Copyright 2019 Soojeong Shin

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
