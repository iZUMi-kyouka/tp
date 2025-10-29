---
  layout: default.md
  title: "John Doe's Project Portfolio Page"
---

### Project: AddressBook Level 3

AddressBook - Level 3 is a desktop address book application used for teaching Software Engineering principles. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

* **New Feature**: Archive/Unarchive Command
  * What it does: The archive feature allows for recruits to be archived away and hidden from the main recruit list
  * Justification: A user would like to retain the information of a recruit while not actively considering them 
  * Highlights: The user will be able to unarchive the user for later reference if needed through the unarchive command

* **Code contributed**: [RepoSense link]()

* **Project management**:
  * Actively participated in the weekly team calls and made suggestions for the further improvement of the project


* **Enhancements to existing features**:
* * Enhanced Find Command
* * * The original find command only allowed for users to search recruits by name and by first name/last name only
* * * The find command has been enhanced to not only allow partial key searching (e.g. "Do" will now still return John Doe as opposed to having to search for "Doe")
* * * Additionally, I have configured flags to allow search by email, phone and address as well


* * Enhanced List Command
* * * The enhanced list command changes the default behaviour to now only show unarchived recruits
* * * To view archived recruits, the user will have to use the flag `list - archived` to view the archived recruits
* * * Alternatively, the user may also use `list -all` to view all unarchived and archived recruits


* **Documentation**:
  * User Guide:
    * Added Documentation for all the aforementioned features and enhancements done by myself
    * Enhanced the FAQ section to be more comprehensive and useful to the end-user
