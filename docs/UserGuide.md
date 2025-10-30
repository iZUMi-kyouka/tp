---
layout: default.md
title: "User Guide"
pageNav: 3
---

# TalentNexus User Guide

TalentNexus is a **desktop app for managing recruits**, optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, TalentNexus can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

### Target Audience

TalentNexus is designed for **recruiters and HR professionals** who:
- Recruit internationally from a large and diverse talent pool
- Favour a quick, keyboard-driven interface for efficient management

<div style="
  background: linear-gradient(135deg, #e8f5e9, #c8e6c9);
  border-left: 6px solid #43a047;
  padding: 12px 16px;
  border-radius: 10px;
  font-family: 'Segoe UI', system-ui, sans-serif;
  color: #1b5e20;
  box-shadow: 0 2px 6px rgba(0,0,0,0.1);
">
  <strong>Glossary:</strong>
  <ul style="margin-top: 4px; padding-left: 20px; color: #1b5e20;">
    <li><strong>Command Line Interface (CLI):</strong> Interact with the app by typing commands on a keyboard. Preferred by users who are familiar with keyboard commands and value speed.</li>
    <li><strong>Graphical User Interface (GUI):</strong> Interact with the app using visual elements like buttons, menus, and windows. Easier for users who prefer point-and-click interactions.</li>
  </ul>
</div>

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. **Ensure you have Java `17` or above installed on your computer.**

   ### Mac Users

   **Check if Java 17 is installed**
    1. Open Terminal.
    2. Run:
       ```bash
       java -version
       ```
    3. If the output shows `17` or above, you can skip installation.

   **Install Java 17 (if not installed)**
    1. Run the following commands in Terminal:
       ```bash
       curl -s "https://get.sdkman.io" | bash
       source "$HOME/.sdkman/bin/sdkman-init.sh"
       sdk install java 17.0.14.fx-zulu
       sdk default java 17.0.14.fx-zulu
       ```
    2. Verify installation:
       ```bash
       java -version
       ```

   ### Windows Users

   **Check if Java 17 is installed**
    1. Open Command Prompt.
    2. Run:
       ```cmd
       java -version
       ```
    3. If the output shows `17` or above, you can skip installation.

   **Install Java 17 (if not installed)**
    1. Download Java 17 from [Adoptium Temurin 17](https://adoptium.net/temurin/releases/?version=17) or [Oracle JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).
    2. Run the installer and follow the instructions.
    3. Verify installation:
       ```cmd
       java -version
       ```

   ### Linux Users

   **Check if Java 17 is installed**
    1. Open Terminal.
    2. Run:
       ```bash
       java -version
       ```
    3. If the output shows `17` or above, you can skip installation.

   **Install Java 17 (if not installed)**
    1. Run the following commands in Terminal:
       ```bash
       sudo apt update
       sudo apt install openjdk-17-jdk
       ```
    2. Verify installation:
       ```bash
       java -version
       ```

<div style="background: linear-gradient(135deg, #e0f7fa, #b2ebf2); border-left: 6px solid #00acc1; padding: 12px 16px; border-radius: 10px; font-family: 'Segoe UI', system-ui, sans-serif; color: #004d40; box-shadow: 0 2px 6px rgba(0,0,0,0.1);">
  <strong>Tip:</strong> Java 17 is required for TalentNexus to run correctly. If multiple Java versions are installed, ensure Java 17 is set as the default.
</div>

2. Download the latest `.jar` file from [here](https://github.com/AY2526S1-CS2103T-F09-3/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar TalentNexus.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

    * [`help`](#viewing-help--help) : Shows help information.

    * [`list`](#listing-all-recruits--list) : Lists all recruits.

    * [`add`](#adding-a-recruit-add) `n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a recruit named `John Doe` to the Address Book.

    * [`edit`](#editing-a-recruit--edit) `1 p/91234567` : Edits the phone number of the 1st recruit.

    * [`find`](#locating-recruits-by-name-find) `John` : Finds recruits with "John" in their name.

    * [`delete`](#deleting-a-recruit--delete) `3` : Deletes the 3rd recruit shown in the current list.

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action        | Format, Examples
--------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
[**Add**](#adding-a-recruit-add)                | `add n/NAME... [p/PHONE_NUMBER]... [e/EMAIL]... [a/ADDRESS]... [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
[**Edit**](#editing-a-recruit--edit)            | `edit INDEX/UUID OPERATION [n/NAME]... [p/PHONE_NUMBER]... [e/EMAIL]... [a/ADDRESS]... [t/TAG]…​`<br> e.g.,`edit 2 -ap n/James Lee e/jameslee@example.com`
[**View**](#viewing-a-recruit--view)      | `view INDEX/UUID`<br> e.g., `view 2`
[**Delete**](#deleting-a-recruit--delete)       | `delete INDEX/UUID`<br> e.g., `delete 3`
[**Find**](#locating-recruits-by-name-find)     | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
[**Sort**](#sorting-recruits--sort)             | `sort [n/ ORDER] [p/ ORDER] [e/ ORDER] [a/ ORDER]`<br> e.g., `sort`, `sort desc`, `sort n/ asc p/ desc`
[**List**](#listing-all-recruits--list)         | `list [-archived] [-all]`<br> e.g., `list`, `list -archived`, `list -all`
[**Archive**](#archiving-a-recruit--archive)    | `archive INDEX`<br> e.g., `archive 2`
[**Unarchive**](#unarchiving-a-recruit--unarchive) | `unarchive INDEX`<br> e.g., `unarchive 1`
[**Undo Command**](#unarchiving-a-recruit--unarchive) | `undo`<br> e.g., `undo`
[**Redo Command**](#unarchiving-a-recruit--unarchive) | `redo`<br> e.g., `redo`
[**Export**](#exporting-recruit-data-export)    | `export [FILEPATH]`<br> e.g., `export ./data/recruits.csv`
[**Clear**](#clearing-all-entries--clear)       | `clear`
[**Help**](#viewing-help--help)                 | `help`

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
  </box>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a recruit: `add`

Adds a recruit to the address book.

* You may specify more than one recruit attributes like names and email addresses.
* You may also provide names in various langugaes.

Format: `add n/NAME... [p/PHONE_NUMBER]... [e/EMAIL]... [a/ADDRESS]... [d/DESCRIPTION]... [t/TAG]…​`

<div style=" background: linear-gradient(135deg, #e0f7fa, #b2ebf2); border-left: 6px solid #00acc1; padding: 12px 16px; border-radius: 10px; font-family: 'Segoe UI', system-ui, sans-serif; color: #004d40; box-shadow: 0 2px 6px rgba(0,0,0,0.1); "> <strong>Tip:</strong> A recruit can have any number of tags (or none at all)</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Jenny Mantle p/66612873 e/jenneram@example.com a/Shadow maze, block 3, #01-01 d/Considered a friend inside t/boss`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`
* `add n/田中小泉 n/Max e/koizumi@example.com e/max@example.com a/30 Princess Gina Park t/swe t/test_engineer`

### Listing all recruits : `list`

Shows a list of all unarchived recruits in the address book. (archived recruits are hidden from the list)

You can view [**archived**](#archiving-a-recruit--archive) recruits by following one of the command formats below:

**Formats:**
* `list` — Shows only unarchived (active) recruits (default)
* `list -archived` — Shows only archived recruits
* `list -all` — Shows all recruits (both archived and unarchived)

Format: `list`

### Viewing a recruit : `view`

View the recruit located at the a particular index from the address book.

Format: `view INDEX/UUID`

* Displays the full details of the recruit at the specified `INDEX` or `UUID` in the recruit list.
* The index refers to the index number shown in the displayed recruit list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `view 2` displays the 2nd recruit in the address book.
* `find Betsy` followed by `view 1` displays the 1st recruit in the results of the `find` command.


### Editing a recruit : `edit`

Edits an existing recruit in the address book.

Format: `edit INDEX/UUID OPERATION [n/NAME]... [p/PHONE]... [e/EMAIL]... [a/ADDRESS]... [d/DESCRIPTION]... [t/TAG]…​`

* Edits the recruit at the specified `INDEX` or `UUID`.
* Performs the specified `OPERATION`, which can be append, overwrite, or remove to the specified attributes. If `OPERATION` is missing, the command is implicitly treated as an **overwrite** command.
* The index refers to the index number shown in the displayed recruit list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* You can remove all the recruit’s tags by typing `t/` without specifying any tags after it.

#### Operation Types

The edit command can perform three types of operation: append, remove, and overwrite.
* Append (`-ap`) operation adds the specified attributes to the existing list of attributes.
* Remove (`-rm`) operation removes the specified attributes from the existing list of attributes.
* Overwrite (`-o`) operation overwrites existing values of all the specified attributes.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` edits the phone number and email address of the 1st recruit to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` edits the name of the 2nd recruit to be `Betsy Crower` and clears all existing tags.
*  `edit -ap 3 e/e01234567@u.nus.edu e/dcsat@nus.edu.sg a/1 Computing Drive` adds to the 3rd recruit the email addresses `e01234567@u.nus.edu.sg` and `dcsat@nus.edu.sg`, and the address `1 Computing Drive`.
*  `edit -rm 2 n/花沢かな e/hanazawa@example.com t/seiyuu` removes from the 2nd recruit the name `花沢かな`, the email `hanazawa@example.com`, and the tag `seiyuu`.
*  `edit -o 4 p/80135815 p/94647894 n/Lawrence Wonk n/ローレンスヲン` edits the names of the 4th recruit to include only `Lawrence Wong` and `ローレンスヲン`, and the phone numbers to include only `80135815` and `94647894`.

### Undoing previously done operations: `undo`

Undoes operations that were done recently.

* You may only continuously perform undo operations up to 200 times.

Format: `undo​`

Example usage scenarios:
1. You add an additional Japanese name to recruit John.
2. You delete recruit Li.
3. You accidentally cleared all recruits.
4. You type `undo` to restore all the recruits.
5. You realise that you meant instead to delete recruit Lee instead of Li.
6. You type `undo` again to undo deletion of recruit Li.


### Redoing undone operations: `redo`

Redoes operations that were undone recently.

<div style=" background: linear-gradient(135deg, #fff3e0, #ffe0b2); border-left: 6px solid #fb8c00; padding: 12px 16px; border-radius: 10px; font-family: 'Segoe UI', system-ui, sans-serif; color: #5d4037; box-shadow: 0 2px 6px rgba(0,0,0,0.1); "> <strong>Warning:</strong> If you have undone some operations, and you perform operations that modify any recruit, you will not be able to redo these undone operations anymore.</div>

Format: `redo`

Examples usage scenario:
1. You delete recruit Li.
2. You realise you still need recruit Li's email.
3. You type `undo` to undo deletion of recruit Li and check his email.
5. You type `redo` again to redo the deletion of Recruit Li.

### Locating recruits by name: `find`

Finds and lists all recruits whose details match any of the given keywords. The search is **case-insensitive** and can be filtered by different fields using flags.

If no flag is provided, the command searches by **name** by default.
If both default keyword(s) and name keyword(s) are specified, the **name keyword(s)** will take precedence.

* `FLAG` specifies the field(s) to search under:
    * `-id` — Search by Recruit ID
    * `-n` — Search by Name
    * `-p` — Search by Phone
    * `-e` — Search by Email
    * `-a` — Search by Address
    * `-t` — Search by Tag

* `KEYWORD [MORE_KEYWORDS]...` are one or more search terms.
  Multiple keywords can be separated by the pipe symbol (`|`).

If multiple flags are provided, the results will include recruits who satisfy all search criteria

---

**Shorthand formats:**
* `find alice` — Finds recruits whose name contains “alice” (case-insensitive)
* `find -n alice|bob|charlie` — Finds recruits whose name contains **“alice”**, **“bob”**, or **“charlie”**
* `find -a Clementi|Tampines` — Finds recruits whose address contains **“Clementi”** or **“Tampines”**
* `find -t volunteer|intern` — Finds recruits tagged as **“volunteer”** or **“intern”**
* `find -p 98765432|91234567` — Finds recruits whose phone number contains **“98765432”** or **“91234567”**
* `find -e gmail|hotmail` — Finds recruits whose email contains **“gmail”** or **“hotmail”**

---

**Chaining multiple search flags:**
* `find -n alice|bob -a Clementi|Jurong` — Finds recruits whose **name** contains “alice” or “bob”, **and** whose
* **address** contains “Clementi” or “Jurong”
* `find -n alice -p 98765432|91234567` — Finds recruits whose **name** contains “alice” and whose **phone** matches either number
* `find -t intern|fulltime -a Bukit|Pasir` — Finds recruits tagged as “intern” or “fulltime”, and whose address contains
  “Bukit” or “Pasir”
---

**Examples:**
* `find alice` — Finds all recruits with “alice” in their name
* `find -n alice|bob|charlie` — Finds recruits whose name matches “alice”, “bob”, or “charlie”
* `find -a Clementi|Tampines -p 98765432|91234567` — Finds recruits with “Clementi” or “Tampines” in their address, and whose phone numbers contain “98765432” or “91234567”
* `find -t volunteer|member -e gmail` — Finds recruits tagged as “volunteer” or “member”, and with a Gmail address
* `find -id 123|456|789` — Finds recruits whose ID contains “123”, “456”, or “789”

<div style="white-space: pre-wrap; background: linear-gradient(135deg, #e0f7fa, #b2ebf2); border-left: 6px solid #00acc1; padding: 12px 16px; border-radius: 10px; font-family: 'Segoe UI', system-ui, sans-serif; color: #004d40; box-shadow: 0 2px 6px rgba(0,0,0,0.1); "> <strong>Tip:</strong> Use the pipe symbol  |  to combine multiple search keywords, and use multiple flags to search across different fields. </div>

![result for 'find Benjamin -a Bukit Batok'](images/findBenjaminResult.png)
![result for 'find Johnathon|Ben'](images/findJohnathonBenResult.png)

### Sorting recruits : `sort`

Sorts all recruits in the address book by specified fields in ascending or descending order.

* `ORDER` can be either `asc` (ascending) or `desc` (descending).
* Multiple fields can be specified, and they will be applied in order of priority from left to right.
* If no parameters are provided, recruits will be sorted by name in ascending order by default.
* If only `asc` or `desc` is provided (without field prefixes), recruits will be sorted by name in that order.
* The sort is case-insensitive for text fields (name, email, address).

**Shorthand formats:**
* `sort` - Sorts by name in ascending order
* `sort asc` - Sorts by name in ascending order
* `sort desc` - Sorts by name in descending order

Examples:
* `sort` - Sorts all recruits by name in ascending order (A to Z)
* `sort desc` - Sorts all recruits by name in descending order (Z to A)
* `sort n/ asc` - Sorts all recruits by name in ascending order
* `sort p/ desc` - Sorts all recruits by phone number in descending order
* `sort n/ asc p/ desc` - Sorts by name (ascending) first, then by phone number (descending) for recruits with the same name
* `sort e/ asc a/ desc` - Sorts by email (ascending) first, then by address (descending) for recruits with the same email

<div style=" background: linear-gradient(135deg, #e0f7fa, #b2ebf2); border-left: 6px solid #00acc1; padding: 12px 16px; border-radius: 10px; font-family: 'Segoe UI', system-ui, sans-serif; color: #004d40; box-shadow: 0 2px 6px rgba(0,0,0,0.1); "> <strong>Tip:</strong> Use multiple sort criteria to organize your recruits more precisely. The leftmost field has the highest priority.</div>

### Archiving a recruit : `archive`

Archives a recruit to hide them from the default list view while preserving their information.

**Format:** `archive INDEX`

**Parameters:**
* `INDEX` — The index number shown in the displayed recruit list
* The index **must be a positive integer** 1, 2, 3, …​

**What is archiving?**
* Archived recruits are hidden from the default [**`list`**](#listing-all-recruits--list) view but remain in the system
* Use this feature to organize inactive or past recruits without deleting their data
* View archived recruits using `list -archived` or `list -all`

**Examples:**
* `list` followed by `archive 2` archives the 2nd recruit in the address book
* `find John` followed by `archive 1` archives the 1st recruit in the results of the `find` command

<div style=" background: linear-gradient(135deg, #e0f7fa, #b2ebf2); border-left: 6px solid #00acc1; padding: 12px 16px; border-radius: 10px; font-family: 'Segoe UI', system-ui, sans-serif; color: #004d40; box-shadow: 0 2px 6px rgba(0,0,0,0.1); "> <strong>Tip:</strong> Archive recruits you no longer actively work with to keep your main list clean and focused!</div>

### Unarchiving a recruit : `unarchive`

Unarchives a previously [**archived**](#archiving-a-recruit--archive) recruit to restore them to the active recruit list.

**Format:** `unarchive INDEX`

**Parameters:**
* `INDEX` — The index number shown in the displayed recruit list (must be viewing archived recruits)
* The index **must be a positive integer** 1, 2, 3, …​

**Examples:**
* `list -archived` followed by `unarchive 1` unarchives the 1st recruit in the archived list
* `list -all` followed by `unarchive 3` unarchives the 3rd recruit if they are archived


<div style=" background: linear-gradient(135deg, #e0f7fa, #b2ebf2); border-left: 6px solid #00acc1; padding: 12px 16px; border-radius: 10px; font-family: 'Segoe UI', system-ui, sans-serif; color: #004d40; box-shadow: 0 2px 6px rgba(0,0,0,0.1); "> <strong>Tip:</strong> To unarchive a recruit, first use `list -archived` to view your archived recruits, then use `unarchive INDEX`.</div>

### Deleting a recruit : `delete`

Deletes the specified recruit from the address book.

Format: `delete INDEX/UUID`

* Deletes the recruit at the specified `INDEX` or `UUID`.
* The index refers to the index number shown in the displayed recruit list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd recruit in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st recruit in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

<div style=" background: linear-gradient(135deg, #e0f7fa, #b2ebf2); border-left: 6px solid #00acc1; padding: 12px 16px; border-radius: 10px; font-family: 'Segoe UI', system-ui, sans-serif; color: #004d40; box-shadow: 0 2px 6px rgba(0,0,0,0.1); "> <strong>Tip:</strong> If you accidentally cleared all recruits, don't worry! You can use the <em>undo</em> command immediately to restore your previous changes</div>


### Dismissing command result message: `dismiss`

Clears the command result message box.

Format: `dismiss`

<div style=" background: linear-gradient(135deg, #e0f7fa, #b2ebf2); border-left: 6px solid #00acc1; padding: 12px 16px; border-radius: 10px; font-family: 'Segoe UI', system-ui, sans-serif; color: #004d40; box-shadow: 0 2px 6px rgba(0,0,0,0.1); "> <strong>Tip:</strong> You could dismiss command result message that you have read to regain more space to view the recruit list.</div>

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Exporting recruit data: `export`

Export all recruits in the address book to a .csv file.

Format: `export FILEPATH`

* `FILEPATH` is optional, if none is given the default filepath found in preferences.json will be used.

Examples:
*  `export` Exports all recruits to the default filepath found in preferences.json.
*  `export ./data/recruits.csv` Exports all recruits to the relative filepath `./data/recruits.csv`.

<div style=" background: linear-gradient(135deg, #e0f7fa, #b2ebf2); border-left: 6px solid #00acc1; padding: 12px 16px; border-radius: 10px; font-family: 'Segoe UI', system-ui, sans-serif; color: #004d40; box-shadow: 0 2px 6px rgba(0,0,0,0.1); "> <strong>Tip:</strong> Use CSV exports to share data easily between users, or to view it in a spreadsheet software like Excel.</div>

### Saving the data

TalentNexus data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

TalentNexus data is saved automatically as a JSON file `[JAR file location]/data/TalentNexusData.json`. Advanced users are welcome to update data directly by editing that data file.

<div style="
  background: linear-gradient(135deg, #fff3e0, #ffe0b2);
  border-left: 6px solid #fb8c00;
  padding: 12px 16px;
  border-radius: 10px;
  font-family: 'Segoe UI', system-ui, sans-serif;
  color: #5d4037;
  box-shadow: 0 2px 6px rgba(0,0,0,0.1);
">
  <strong>Warning:</strong>
  <ul style="
    margin: 8px 0 0 20px;
    padding: 0;
    color: #5d4037; /* ensures list text matches div */
    background: transparent; /* makes sure gradient shows through */
  ">
    <li>If your changes to the data file make its format invalid, TalentNexus will discard all data and start with an empty data file at the next run. Hence, it is <strong>strongly recommended</strong> to take a backup of the file before editing it.</li>
    <li>Furthermore, certain edits can cause TalentNexus to behave in unexpected ways (e.g., if a value entered is outside the acceptable range).</li>
    <li>Therefore, edit the data file only if you are confident that you can update it correctly.</li>
  </ul>
</div>
--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

---

**Q:** Can I use TalentNexus without an internet connection?  
**A:** Yes. TalentNexus is a fully offline desktop application — no internet connection is required for any of its features.

---

**Q:** What happens if I accidentally delete a recruit?  
**A:** You can restore deleted recruits immediately using the `undo` command (`undo`), as long as you haven’t closed the application since the deletion.

---

**Q:** How do I back up my data?  
**A:** Copy the `addressbook.json` file located in the `/data` folder (in the same directory as the `.jar` file). Store this backup in a safe place (cloud storage, external drive, etc.).

---

**Q:** Can I import data from another AddressBook or TalentNexus installation?  
**A:** Yes. Copy the `addressbook.json` file from the other installation into your current `/data` folder, replacing the existing file. **Always back up** your current `addressbook.json` first.

---

**Q:** Why are my changes not appearing after restarting the app?  
**A:** This can happen if the app is executed from a different directory or the data file path has changed. Make sure the `addressbook.json` file is in the `/data` folder relative to the `.jar` file you run.

---

**Q:** What if I enter invalid data directly into the JSON file?  
**A:** TalentNexus validates the data file on startup. If the file format is invalid, the app may reset the data file to an empty state. Always back up the file before making manual edits.

---

**Q:** How do I view archived recruits?  
**A:** Use `list -archived` to show only archived recruits, or `list -all` to view both archived and active recruits.

---

**Q:** What should I do if a command doesn’t work?  
**A:** Check your command syntax against the [**Command summary**](#command-summary) and the relevant command section. You can also run `help` to view the quick command guide.

---

**Q:** How do I reset the application to its default state?  
**A:** Delete both `preferences.json` and `addressbook.json` from the data directory. On next launch the app will recreate default files and sample data.

---

**Q:** How do I run TalentNexus with a specific Java version?  
**A:** Ensure the desired JDK is installed and on your `PATH`. Run the app with that Java binary, e.g.:

**Q:** Where can I find the JSON schema or data format?  
**A:** The primary data file is `addressbook.json` and stores recruits as JSON objects with fields such as `name`, `phone`, `email`, `address`, `tags`, `archived`, and `uuid`. If you plan to edit the file manually, back it up first and ensure you preserve valid JSON and expected field types (arrays for multiple values like `email`/`phone`/`name`).

---

**Q:** The Help window opened off-screen or is minimized — what do I do?  
**A:** Close the app and delete `preferences.json` in the data directory to reset window positions. Relaunch the app to restore windows to default locations.

---

**Q:** Is there a limit to how many operations I can undo?  
**A:** You can perform up to 200 consecutive `undo` operations. Note that certain actions (like closing the app) may clear the undo history.

---

**Q:** Who do I contact for bug reports or feature requests?  
**A:** Create an issue on the project repository (if using the upstream AddressBook/TalentNexus repo). Include reproducible steps, the `preferences.json` and `addressbook.json` (sanitized if needed), and the Java version used.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.
3. **If you are running a Linux-based operating system such as Ubuntu and Linux Mint**, you may experience issues when using an Input Method Editor (IME) to type in non-latin characters. For example, the window showing the candidate kanji characters when typing in Japanese may not show up. Unfortunately, this is a limitation of the technology we used to build this application. If you work with data that require IME input, you may type your command in other text editing program and copy-paste it into the command box.

--------------------------------------------------------------------------------------------------------------------
