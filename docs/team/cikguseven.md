---
layout: default.md
title: "Kieron's Project Portfolio Page"
---

### Class Manager 2023

Class Manager 2023 aims to provide fast access to NUS TAs in maintaining and managing student information across multiple classes. It also helps TAs visualise students’ grades, attendance and class participation. Class Manager 2023 is written in Java, and has about 20 kLoC.

Given below are my contributions to the project:

* **Code contributed**: [Individual Dashboard](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=cikguseven&breakdown=true)

* **Enhancements implemented**:
  * New feature: `load` command (PR: [#57](https://github.com/AY2324S1-CS2103T-T11-1/tp/pull/57))
    * What it does: Allows users to load their saved data file and set it as the new default save file.
    * Justification: This feature allows users to save and continue editing their data from where they left off. This is especially useful for TAs who teach multiple modules as each file is configured to store tutorial and assignment information for a specific module.
    * Highlights: Implementing this feature requires sufficient understanding of the `Model` and `Storage` components of the application. This enhancement required me to refactor the `Storage` class to allow for the loading of data from a specified file path. I also had to refactor the `Command` class to allow for the `load` command to be executed.
  * New feature: `config` command (PR: [#82](https://github.com/AY2324S1-CS2103T-T11-1/tp/pull/82))
    * What it does: Allows users to configure Class Manager with the tutorial and assignment count of the module.
    * Justification: This feature allows users to configure the application based on their module as the tutorial and assignment count varies between modules in NUS. The correct number of tutorials and assignments can then be displayed for accurate recording of student's attendance, class participation and assignment grades.
    * Highlights: Implementing this feature also requires sufficient understanding of the `Model` and `Storage` components of the application. In order for `config` to be used multiple times, I had to reset the class details of existing students whenever the user configures Class Manager. The previous states of Class Manager also had to be reset to prevent users from using `undo` commands to reach a state before Class Manager was configured.
  * Enhancement to existing AB-4 features: `undo` and `redo` command (PR: [#119](https://github.com/AY2324S1-CS2103T-T11-1/tp/pull/119))
    * What it does: Allows users to undo and redo commands that modify the data of Class Manager.
    * Justification: This feature allows users to undo and redo commands that modify the data of Class Manager. This is especially useful for TAs who may have accidentally deleted a student or recorded the wrong attendance for a student.
    * Highlights: Reusing this AB-4 feature requires a comprehensive understanding of all components of Class Manager, as these components had to be updated to support undo and redo. There was also deliberate effort to ensure that only relevant commands can be undone and redone. For example, the `view` command cannot be undone as it is not a command that modifies the data of Class Manager.
* **Documentation**:
    * User Guide:
      * Added documentation for the following features: `load`, `config`, `undo`, `redo`, `history`.
      * Formatted the command summary into relevant sections.
      * Added features section that provides helpful tips to users.
      * Handled formatting of the User Guide.
    * Developer Guide:
      * Added implementation details of the following features: `load`, `config`, `undo`, `redo`, `history`.
      * Added user stories and use cases.
* **Team-based tasks**:
  * Set up the GitHub team organisation and repository.
  * Managed releases and milestones for v1.2 and v1.3.
  * Created the features demo for v1.2 and v1.3.
  * Updated the project logs.
* **Review/mentoring**:
  * Reviewed PRs
    * [#56](https://github.com/AY2324S1-CS2103T-T11-1/tp/pull/56#discussion_r1359157372)
    * [#78](https://github.com/AY2324S1-CS2103T-T11-1/tp/pull/78#discussion_r1363703267)
    * [#99](https://github.com/AY2324S1-CS2103T-T11-1/tp/pull/99#discussion_r1371868108)
* **Beyond the project team**:
  * Forum discussions
    * [#1](https://github.com/nus-cs2103-AY2324S1/forum/issues/54)
