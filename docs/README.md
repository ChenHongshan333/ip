# Mintty User Guide

Mintty is a desktop app for managing tasks, 
optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI). 
If you can type fast, Mintty can get your task management done faster than traditional GUI apps. 
Whether you are tracking assignments or scheduling personal events, Mintty helps you stay on top of your schedule.

<br>

## Table of Contents

* [Quick Start](#quick-start)
* [Features](#features)
  * [Adding a Todo task: `Todo`](#1-todo)
  * [Adding a Deadline task: `Deadline`](#2-deadline)
  * [Adding an Event task: `Event`](#3-event)
  * [Listing out all tasks: `List`](#4-list)
  * [Marking a task as done: `Mark`](#5-mark)
  * [Marking a task as not done: `Unmark`](#6-unmark)
  * [Searching a task: `Find`](#7-find)
  * [Deleting a task: `Delete`](#8-delete)
  * [Snoozing a task: `Snooze`](#9-snooze)
  * [Exiting the program: `Bye`](#10-bye)
* [Command Summary](#command-summary)
* [Contributing](#contributing)


<br>

## Quick Start

1. Ensure you have Java `17` or above installed in your Computer.
2. Download the latest `mintty.jar` from [here](https://github.com/ChenHongshan333/ip/releases/tag/A-Release).
3. Copy the file to the folder you want to use as the home folder for your Mintty.
4. Use `java -jar mintty.jar` to run the program. The GUI similar to the screenshot below should appear in a few seconds.
5. Type the command in the command box and press Enter to execute it. e.g. typing `list` and pressing Enter will display all your current tasks.

![Ui.png](Ui.png)
> A screenshot of Mintty's UI


<br>

## Features

> **Notes about the command format:**
> * Words in `UPPER_CASE` are the parameters to be supplied by the user.
> * Extraneous parameters for commands that do not take in parameters (such as `list`, `bye`) will be ignored.

### <u>1. Todo</u>

Adds a basic task without any date or time constraints to the list.

**Format:** 
- `todo DESCRIPTION`
- `td DESCRIPTION`

**Example:** 
- `todo Take shower`
- `td Take shower`

**Expected outcome:**
```text
Okie!! I've added this to the task list:
2.[T][ ] Take shower
Now you have 2 tasks in total.
```

### <u>2. Deadline</u>

Adds a task that needs to be done before a specific date/time.

**Format:** 
- `deadline DESCRIPTION /by DATETIME`
- `ddl DESCRIPTION /by DATETIME`

**Example:** 
- `deadline CS2109S midterm /by 2026.3.2 6pm`
- `ddl CS2109S midterm /by 2026.3.2 6pm`

**Expected outcome:**
```text
Okie!! I've added this to the task list:
3.[D][ ] CS2109S midterm (by: 2026-Mar-2 18:30)
Now you have 3 tasks in total.
```

### <u>3. Event</u>

Adds a task that starts at a specific time and ends at a specific time.

**Format:** 
 - `event DESCRIPTION /from START_DATETIME /to END_DATETIME`
 - `e DESCRIPTION /from START_DATETIME /to END_DATETIME`

**Example:** 
- `event prepare for FFC /from 2025.12.3 9pm /to 2026.8.5 11pm`
- `e prepare for FFC /from 2025.12.3 9pm /to 2026.8.5 11pm`

**Expected outcome:**
```text
Okie!! I've added this to the task list:
4.[E][ ] prepare for FFC (from: 2025-Dec-3 21:00, to: 2026-Aug-5 23:00)
Now you have 4 tasks in total.
```

### <u>4. List</u>

Shows a list of all tasks currently stored in Mintty.

**Format:** `list` or `l`

**Expected outcome:**
```text
Here are the tasks in your list:
1.[T][X] take shower
2.[D][ ] CS2109S midterm (by: 2026-Mar-2 18:30)
```

### <u>5. Mark</u>

Marks the specified task from the list as completed.

**Format:** `mark INDEX` or `m INDEX`
> * `INDEX` refers to the index number shown in the displayed task list.
> * The index **must be a positive integer** (e.g., 1, 2, 3...).

**Example:** 
- `mark 2`
- `m 2`

**Expected outcome:**
```text
Niceee! I've marked this task as done:
[D][X] CS2109S midterm (by: 2026-Mar-2 18:30)
```

### <u>6. Unmark</u>

Reverts a completed task back to an uncompleted state.

**Format:** `unmark INDEX` or `u INDEX`

**Example:** `unmark 2` or `u 2`

**Expected outcome:**
```text
Okie, I've marked this task as not done yet:
[D][ ] CS2109S midterm (by: 2026-Mar-2 18:30)
```

### <u>7. Find</u>

Finds tasks whose description matches the given keyword(s).

**Format:** 
- `find KEYWORD`
- `f KEYWORD`

**Example:**
- `find CS2109S`
- `f CS2109S`

**Expected outcome:**
```text
Heyyy! I've matched the following tasks in your list:
2.[D][ ] CS2109S midterm (by: 2026-Mar-2 18:30)
```

### <u>8. Delete</u>

Deletes the specified task from the list.

**Format:** 
 - `delete INDEX`
 - `del INDEX`

**Example:** 
 - `delete 2`
 - `del 2`

**Expected outcome:**
```text
Okie!! I've removed this from the task list:
2.[D][ ] CS2109S midterm (by: 2026-Mar-2 18:30)
Now you have 1 tasks in total.
```

### <u>9. Snooze</u>

Postpones or reschedules a Deadline or Event task to a new date/time.
> **Note:** This feature does not support `Todo` tasks, as they do not have time constraints.

**Format:** 
* For a Deadline: `snooze INDEX by NEW_DATETIME`
* For an Event:
  * `snooze INDEX from NEW_START_DATETIME to NEW_END_DATETIME`
  * `snooze INDEX to NEW_END_DATETIME`
  * `snooze INDEX from NEW_START_DATETIME`

**Example:** `snooze 2 to 2026.3.3 6pm`

**Expected outcome:**
```text
Snoozed: [E][ ] Event (from: XXXX, to: 2026-Mar-3 18:00)
```

### <u>10. Bye</u>

Exits the program and saves your tasks automatically.

**Format:** `bye`

**Expected outcome:**
```text
Nice to talk to you^^
See you!
```

<br>


## Command Summary

| Action | Format                                                                                                               | Example                                                       |
| :--- |:---------------------------------------------------------------------------------------------------------------------|:--------------------------------------------------------------|
| **Todo** | `todo DESCRIPTION` <br> `td DESCRPTION`                                                                              | `todo Take shower`                                            |
| **Deadline** | `deadline DESCRIPTION /by DATETIME`  <br> `ddl DESCRIPTION /by DATETIME`                                             | `deadline CS2109S midterm /by 2026.3.2 6pm`                   |
| **Event** | `event DESCRIPTION /from START_DATETIME /to END_DATETIME` <br> `e DESCRIPTION /from START_DATETIME /to END_DATETIME` | `event prepare for FFC /from 2025.12.3 9pm /to 2026.8.5 11pm` |
| **List** | `list`  <br> `l`                                                                                                     |                                                               |
| **Mark** | `mark INDEX`  <br> `m INDEX`                                                                                         | `mark 2`                                                      |
| **Unmark** | `unmark INDEX`   <br> `u INDEX`                                                                                      | `unmark 2`                                                    |
| **Find** | `find KEYWORD` <br> `f KEYWORD`                                                                                      | `find CS2109S`                                                |
| **Delete** | `delete INDEX` <br> `del INDEX`                                                                                      | `delete 2`                                                    |
| **Snooze** | Deadline: `snooze INDEX by NEW_DATETIME`  <br> Event: `snooze INDEX from NEW_START_DATETIME to NEW_END_DATETIME`     | `snooze 2 to 2026-Mar-3 6pm`                                  |
| **Bye** | `bye` <br> `exit` <br> `quit`                                                                                        |                                                               |

<br>

## Contributing
[Hongshan](https://github.com/ChenHongshan333), 
<br>
for CS2103T AY25/26 sem 2 iP (individual project).