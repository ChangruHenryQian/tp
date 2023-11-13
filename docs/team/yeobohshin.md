---
layout: default.md
title: "Boh Shin's Project Portfolio Page"
---

### Class Manager 2023

Class Manager 2023 aims to provide fast access to CS2103/T TAs who need help in maintaining student information across multiple classes. It also helps TAs visualise students’ grades, attendance and class participation.

Given below are my contributions to the project.

* **Code Contributed**: [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=yeobohshin&breakdown=true)

* **Enhancements Implemented**:
  * **New feature:** `Assignment` and `AssignmentTracker` field (PR: [#72](https://github.com/AY2324S1-CS2103T-T11-1/tp/pull/72))
    * What it does: Users can record their students' marks for an assignment in the `Assignment` field. Marks for an `Assignment` will be upon 100 marks. `AssignmentTracker` holds the `Assignment`.
    * Justification: Initially, Class Manager 2023 only records the tutorial group number of each student. There is no way of keep track of the marks obtained of each student for each assignment. It will be more organised if users can record the marks obtained by each student into the application.
    * Highlights: Implementing this feature requires in-depth understanding of how the `Student` model functions and interacts with the other component of the application. It also requires more modification to the existing model.
      <br><br>
  * **New feature:** `view` command (PR: [#99](https://github.com/AY2324S1-CS2103T-T11-1/tp/pull/99))
    * What it does: Users can view the class information of a specific student in the Class Manager.
    * Justification: Initially, there was no way to view the class details of a `Student` despite being able to store them. This feature will allow users to view the specifics of a students' performance and edit them accordingly.
    * Highlight: Implementing this feature requires in-depth understanding of how components are rendered on the `GUI` and how it is being updated on the `GUI`. It requires more modifications to the existing model and GUI.
      <br><br>
  * **New/Enhancement to existing feature:** `tag` command (PR: [#5](https://github.com/AY2324S1-CS2103T-T11-1/tp/pull/5))
    * What it does: Allows users to add, delete or replace `Tag` from `Student` in Class Manager 2023.
    * Justification: This feature allows users to label their students with certain characteristics which allows them to easily recognise their students with certain traits.
    * Highlight: `Tag` was previously edited using the `edit` command. However, it was inefficient as each time the user wants to edit the `Tag` of `Student` users have to replace all existing tags. Thus, extracting it from the `edit` command and enhancing the feature to support addition and deletion of `Tag` from `Student` requires deep understanding of how `Tag` is stored under `Student`. It requires modification to the existing model.
      <br><br>
  * **Enhancement to existing commands:** `GUI` (PR: [#96](https://github.com/AY2324S1-CS2103T-T11-1/tp/pull/96))
    * Changes: Added a side panel that will display the class informations of a student.
    * Highlights: Modifying the UI requires in-depth understanding of how the UI classes observe and update when the target component is changed. It also requires understanding of JavaFX, FXML and CSS.
      <br><br>
* **Documentation**:
  * **User Guide:**
    * Added complete documentation for the following features: `tag`, `view`.
    * Updated documentation for other features.
    * Standardise the usage of variables.
    * Added guide for user guide.
    * Manage issue tracker on github.
      <br><br>
  * **Developer Guide:**
    * Added explanation, implementation, diagrams and design considerations for the following feature: `tag`, `view`.
    * Added manual testing and use cases for the following features: `view`, `tag`.
      <br><br>
* **Team-based task**
  * Ensure working hyperlinks for UG and DG
  * Set-up and update AboutUs section
  * Help in review and merge of PRs
  * Maintain issue tracker
    <br><br>
* **Review/mentoring**
  * Review PR (E.g.[#148](https://github.com/AY2324S1-CS2103T-T11-1/tp/pull/148), [#189](https://github.com/AY2324S1-CS2103T-T11-1/tp/pull/189))
  * Help solve bugs in code.
