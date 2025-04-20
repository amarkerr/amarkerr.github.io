# Computer Science Captsone ePortfolio
<br/>

## 🔹 Intro 🔹
Hi, I’m Amanda Kerr! As a Computer Science major specializing in software engineering, I am increasingly interested in creating tools that promote accessibility and inclusive design. For my final project at SNHU, I improved a calendar application I had developed earlier in the program to align it more closely with industry standards and contemporary architecture.

This project involved shifting the backend from SQLite to Firebase for scalable, cloud-based storage, restructuring it into a modular MVVM pattern, and adding a calendar view to enhance user interaction. Although the app currently falls short of achieving the complete level of accessibility I strive for in my projects, this process demonstrates my dedication to crafting clean, maintainable code and developing intuitive user experiences that align with that goal.
<br/>
<br/>

## 🔹 Self Assessment 🔹
During the Computer Science program, I acquired both technical skills and a professional mindset essential for success in software development. Creating this ePortfolio allowed me to reflect on my journey, evolving from writing simple components to developing scalable, cloud-based applications that adhere to modern architectural patterns. This process also enabled me to critically evaluate my long-term aspirations: to create intuitive, inclusive, and well-designed software.

I acquired many of my skills through hands-on coursework, applying fundamental concepts to real-world scenarios. I gained significant experience in collaborative settings, especially during team projects that utilized version control, shared responsibilities, and peer code reviews. In these situations, I became proficient in using Git, effectively communicating changes, and contributing to a shared codebase. A notable instance was during the elective course, [GAM-305](https://github.com/amarkerr/GAM305-BlueTeam), where I guided a small team of developers in creating a short platformer video game. Besides leading the team, I dedicated much of my time to teaching them how to use Git and GitHub effectively and helping resolve several merge conflicts.

Effective communication has also been a consistent focus in my coursework. From documenting code and creating walkthrough videos to participating in class discussions, I have sharpened my ability to convey technical decisions clearly to diverse audiences. For instance, the code review video featured in this portfolio pushed me to present my work in a concise and accessible way, deepening my understanding and ability to explain the rationale behind my design choices. Additionally, collaborating with classmates on Discord has allowed me to forge connections and offer tutoring support to my peers.

From a technical standpoint, I've enhanced my understanding of data structures and algorithms through projects that emphasized performance considerations. For instance, my work in [CS-300](https://github.com/amarkerr/CS300-BinaryTree) necessitated a clear grasp of various data structures to identify the most suitable one for a small project, which I then built. Additionally, my knowledge in software engineering and database management has grown considerably. This growth is evident when comparing one of my initial projects in [IT-140](https://github.com/amarkerr/IT140-Game), where I utilized basic console output and a simple Python dictionary, to my project in [CS-340](https://github.com/amarkerr/CS340-ClientServerDev), where I engaged with a NoSQL database and developed a web application that allowed users to search the database effortlessly.

Security has increasingly influenced my approach to software development. In this project, I substituted insecure login methods with Firebase Fireauth. Reviewing my repository history reveals that my development practices did not always prioritize security. This shifted significantly after taking CS-305. Even in light-hearted personal projects like my [Discord bot](https://github.com/amarkerr/ReplyBot), which posts a GIF when users employ a specific word, my code utilizes a hidden token for secure operation rather than being hardcoded into the script.

All these skills come together in the project presented here. Rather than submitting multiple artifacts, I concentrated on enhancing a single Android application in three key areas: software design and architecture, algorithms and data handling, and database integration. Each enhancement addresses a fundamental aspect of computer science while improving the app's core features, resulting in a refined final product. This approach allowed me to demonstrate my technical versatility and ability to iteratively improve a project, emphasizing long-term maintainability and user experience.

Looking forward, I hope to pursue roles that enable me to create tools that prioritize accessibility and usability. This capstone experience has boosted my confidence in my abilities, highlighted the importance of thoughtful design, and enhanced my understanding of how to contribute meaningfully to a development team. I am eager to continue my growth as a developer and apply these skills in a professional environment.
<br/>
<br/>

## 🔹 Code Review 🔹
Before starting this project's enhancement phase, I reviewed the original codebase for the calendar application. I walked through the existing features, noted areas where the structure and functionality could be improved, and outlined specific updates in three key areas: software design, algorithm performance, and database integration. I created a video walkthrough of the code review to document this process and share my thought process. In the video, I explain how the original code worked, what changes were needed, and how the planned enhancements would improve the application overall. This review served as the foundation for the technical improvements that followed.
- Link
<br/>

## 🔹 Enhancements 🔹
In this project, I enhanced three key components of the original application to showcase development in vital computer science areas: software design and engineering, algorithms and data structures, and databases. Each enhancement reflects a dedicated effort to align the project with industry best practices and illustrates my ability to transform an existing codebase into a more maintainable and user-friendly solution.

The [initial version](https://github.com/amarkerr/CS499Capstone/tree/original "Original Branch") of this project was developed in my CS-360 course and functioned as a simple Android app for event management. Users could register, log in, and create, edit, or delete events displayed as a list on the main screen. The app also included basic SMS reminders to alert users on the day of each scheduled event. While it was functional, the original version offered numerous opportunities to enhance its structure, scalability, and user experience, making it an ideal candidate for upgrades.

For [Enhancement One](https://github.com/amarkerr/CS499Capstone/tree/Enhancement1 "Enhancement One"), I focused on refining the app's structure and maintainability by shifting from an activity-based architecture to the Model-View-ViewModel (MVVM) pattern. This change involved organizing the UI, business logic, and data management into separate layers, leading to more modular and testable code. Additionally, I upgraded the basic event list to a comprehensive calendar view, improving scheduled events' usability and clarity. Furthermore, I replaced the original SMS notification system with a modern push notification solution. Currently, notifications are sent once daily, but plans are in place for enhancements that will enable users to choose their preferred alert times.

The [second enhancement](https://github.com/amarkerr/CS499Capstone/tree/Enhancement2 "Enhancement Two") aimed to better organize, filter, and display event data within the application. Previously, events appeared in a flat list, lacking relevance to the current day or context. During the refactor, I introduced logic to dynamically query and present events based on the selected date in the calendar view. This involved reworking how event data flowed through the ViewModel layer and was displayed in the UI. By optimizing the data flow and applying more focused filtering, the app became capable of loading only essential events. These adjustments improved both performance and user experience, showcasing a more systematic approach to data handling in a mobile setting.

The [final enhancement](https://github.com/amarkerr/CS499Capstone/tree/Enhancement2 "Enhancement Three") replaced the app’s original SQLite setup with Firebase Firestore for better cloud-based and scalable data management. After setting up the Firebase project and integrating the required SDKs, I swapped out local database interactions for Firestore queries. A new UserRepository was created to handle authentication and user data, leading to updates in the login and registration processes. The Event class and all related data-handling components were restructured to work with Firestore. The old DatabaseHelper class was completely removed, and preference settings were updated to store values using Firebase-supported methods. Each module underwent incremental testing to confirm proper integration with the new backend.

<br/>
<details>
<summary>Final Enhancement Images</summary>
<br>
images go here
</details>
<br/>

Repository branches with only Java files and narrative documents can be found here:
- [Original](https://github.com/amarkerr/amarkerr.github.io/tree/Original "Original")
- [Enhancement One](https://github.com/amarkerr/amarkerr.github.io/tree/Enhancement-1 "Enhancement One")
- [Enhancement Two](https://github.com/amarkerr/amarkerr.github.io/tree/Enhancement-2 "Enhancement Two")
- [Enhancement Three](https://github.com/amarkerr/amarkerr.github.io/tree/Enhancement-3 "Enhancement Three")
<br/>
<br/>



___
###### amarkerr.github.io
