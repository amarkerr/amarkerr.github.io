# Enhancement Three: Databases

The final enhancement replaced the app’s original SQLite setup with Firebase Firestore for better cloud-based and scalable data management. After setting up the Firebase project and integrating the required SDKs, I swapped out local database interactions for Firestore queries. A new UserRepository was created to handle authentication and user data, leading to updates in the login and registration processes. The Event class and all related data-handling components were restructured to work with Firestore. The old DatabaseHelper class was completely removed, and preference settings were updated to store values using Firebase-supported methods. Each module underwent incremental testing to confirm proper integration with the new backend.

<details>
<summary>Original accompanying narrative</summary>
<br>
This artifact is the same one I have been working on throughout the term. It was developed during my CS-360 course. Initially, the artifact functioned as an Android app for listing events and sending notifications. The app featured straightforward functionality, enabling users to register, log in, and create, edit, or delete events displayed on the landing screen. Moreover, it would send users an SMS alert on each event's day.
<br/>
<br/>

This project was mainly included for enhancing the database because of its initially limited database and reliance on internal storage. I initially planned to upgrade the data and storage from SQL with SQLite to NoSQL using an alternative tool. However, after additional consideration, the shift from internal storage to secure cloud storage became a significant advancement for this app. This was perfect for demonstrating my capability to work with various languages and tools that support different database types, as well as to decide which languages and tools would yield the best outcomes.
<br/>
<br/>

Completing this enhancement involved multiple steps. Initially, I had to set up a project in Firebase, including configuring authentication and establishing a database collection. Next, I incorporated the Firestore dependencies and necessary import statements into almost every Java file, along with placing the Google services JSON file in the appropriate directory. With these steps complete, I proceeded to recode. I started by developing a new User Repository class and updating the relevant login and registration files. Following that, I concentrated on the Event class and all files interacting with Event objects. I was also able to delete the original Database Helper file since it was no longer necessary. Lastly, I implemented storage for the setting options, testing each part incrementally with Firebase open in another window.
<br/>
<br/>

Originally, I planned to meet course outcomes 3, 4, and 5 with this enhancement. Below follows how each outcome is or is not being met: 

- Outcome 3: MET
  - I used algorithmic thinking to manage the loading, filtering, and display of events on specific dates. During the shift from SQLite to Firestore, I considered factors like speed, complexity, and scalability. Although Firestore brought additional complexity, it provided real-time updates and simplified cross-device syncing, which enhanced long-term value.  

- Outcome 4: MET
  - This update enabled the app to synchronize data instantly across devices and function more dependably. By utilizing contemporary tools and implementing careful design strategies, I improved both the app’s performance and user experience.

- Outcome 5: MET
  - Switching to Firebase authentication enhanced security significantly. I utilized Firebase’s integrated authentication features and security rules to manage access, ensuring that only authorized users can read or write data. Additionally, I evaluated potential risks such as insecure data transmission and incorrect permissions, implementing best practices to safeguard user information.

<br/>
This upgrade was considerably more extensive than earlier ones. Before starting, I needed to thoroughly understand the Firebase SDK and API. I had previously delayed this learning process because I was uncertain about using Firebase, which set me back a bit. Fortunately, when creating a new project in Firebase, the platform guides you through adding the necessary dependencies and JSON file. After that, I engaged in further research and watched videos to learn how to correctly implement new methods and functions for Firebase's Firestore and Fireauth features. Utilizing any available time that I could not dedicate to my artifact for reading and watching implementation guides was essential, particularly given my limited work hours. Additionally, I navigated through various Firebase pages to explore different options, usages, and extensions. While using a simpler tool might have been more straightforward, exploring Firebase was a valuable learning experience that will benefit any future Android development I may pursue.

</details>

###### amarkerr.github.io
