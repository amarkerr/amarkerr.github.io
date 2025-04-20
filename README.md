# Enhancement Two: Algorithms and Data Structures

The second enhancement aimed to better organize, filter, and display event data within the application. Previously, events appeared in a flat list, lacking relevance to the current day or context. During the refactor, I introduced logic to dynamically query and present events based on the selected date in the calendar view. This involved reworking how event data flowed through the ViewModel layer and was displayed in the UI. By optimizing the data flow and applying more focused filtering, the app became capable of loading only essential events. These adjustments improved both performance and user experience, showcasing a more systematic approach to data handling in a mobile setting.

<details>
<summary>Original accompanying narrative</summary>
<br>

I have focused on the same artifact throughout the term. This artifact is the final project from CS-360, in which I developed a simple Android app for event listing. The app enabled users to register, log in, and create, edit, and delete events initially presented in a list format. Before enhancements, the app handled event sorting through manual iteration and nested if statements, requiring a full traversal of all saved events to determine how they should be displayed. This approach became inefficient as the dataset grew.
<br/>
<br/>

I selected this artifact because it aligned closely with the work required for Enhancement Three. As I transitioned the app’s storage system from SQLite to Firebase’s NoSQL Firestore, I was required to rethink how event data was organized, queried, and filtered. This shift allowed me to replace the manual sorting logic with more efficient query operations and indexing strategies, streamlining how events were grouped and retrieved by date. In this enhancement, I updated the filtering and display of event lists, reducing unnecessary iterations and boosting responsiveness. I refined the sorting logic to focus solely on relevant events, enhancing performance and making the display more efficient.
<br/>
<br/>

Originally, I planned to meet course outcomes 2, 3, and 4 with this enhancement. Below follows how each outcome is or is not being met: 
- Outcome 2: PARTIALLY MET
  - This outcome was only partially achieved with this enhancement by writing clean code. I started adding comments to highlight important sections of the code, although it was still somewhat limited. In addition to the enhancement, I am also meeting this outcome with the written documents I have begun compiling for the entire project.

- Outcome 3: MET
  - I enhanced event handling efficiency by substituting a manual, nested-loop method with early filtering via Firestore queries and optimized list sorting. This minimized unnecessary operations and boosted responsiveness.

- Outcome 4: MET
  - I switched from a linear scan-and-check method to Firestore queries that pull only pertinent events, minimizing redundancy and enhancing performance. This improvement demonstrates the efficient, scalable techniques employed in contemporary mobile applications.
<br/>

This experience deepened my understanding of managing data structures under NoSQL constraints while achieving a balance between simplicity and scalability. One challenge was adjusting the filtering logic for Firestore’s snapshot listeners to guarantee precise real-time UI updates. Effectively resolving this issue resulted in enhancements in both algorithmic efficiency and user experience.

  
</details>

___
###### amarkerr.github.io
