# Mintty User Guide

Made by [Hongshan](https://github.com/ChenHongshan333),
for CS2103T AY26/27 sem2 iP (individual project).

<br>

## Brief Introduction

> 60 apples a day keep unemployment away. - asgyph777wyxcc999 [(source)](https://www.reddit.com/user/asgyph777wyxcc999/)

The above quote is from the comments to a Reddit topic [Applied to 60 apple jobs today alone lol](https://www.reddit.com/r/csMajors/comments/19btarz/applied_to_60_apple_jobs_today_alone_lol/)
It looks fun, isn't it? But it also shows how bad the job market is today :dizzy_face:

Therefore, it is important to get started early - **Do something concrete RIGHT NOW !!**
—— And that's is where **Mintty** comes from.
<br>
<br>

![Ui.png](Ui.png)
> A screenshot of Mintty's ui (^^)

<br>

Mintty is a cute robot that help you take notes of upcoming events. It's,
- text-based
- easy to learn
- ~~FAST~~ *SUPER FAST* to use

All you need to do is,
1. download it from [here](https://github.com/ChenHongshan333/ip).
2. double-click it.
3. add your tasks.
4. let it manage tasks for you :kissing_closed_eyes:

And it is **FREE**!

Features:
- [x] Managing tasks
- [x] Store past tasks (unless these tasks are deleted manually)
- [x] Snooze tasks (you DEFINITELY need this 23333)

<br>

If you are a Java programmer, you can use it to practice Java too. Here's the `main` method:
```
public static void main(String[] args) {
        Mintty mintty = new Mintty("data/mintty.txt");
        mintty.run();
}
```

<br>
<br>


## Feature List


### 1. Todo

**Purpose**: Add a task of type `Todo`.

**Example**: `Todo Take shower`

**Expected outcome**:

```
Okie!! I've added this to the task list:
2.[T][ ] Take shower
Now you have 2 tasks in total.
```

<br>

### 2. Deadline

**Purpose**: Add a task of type `Deadline`.

**Example**:
`deadline CS2109S midterm /by: 2026-Mar-2 6pm`

**Expected outcome**:

```
Okie!! I've added this to the task list:
3.[D][ ] CS2109S midterm (by: 2026-Mar-2 18:30)
Now you have 3 tasks in total.
```

<br>

### 3. Event

**Purpose**: Add a task of type `Event`.

**Example**:
`event prepare for FFC /from 2025.12.3 9pm /to 2026.8.5 11pm`

**Expected outcome**:

```
Okie!! I've added this to the task list:
4.[E][ ] prepare for FFC (from: 2025-Dec-3 21:00, to: 2026-Aug-5 23:00)
Now you have 4 tasks in total.
```

<br>

### 4. List

**Purpose**: List out the tasks that the user have already added to Mintty.

**Example**: `list`

**Expected outcome**:

```
Here are the tasks in your list:
1.[T][X] take shower
2.[D][ ] CS2109S midterm (by: 2026-Mar-2 18:30)
```
<br>

### 5. Mark

**Purpose**: Mark a specific task to be done.

**Example**: `mark 2`


**Expected outcome**:

```
Niceee! I've marked this task as done:
[D][X] CS2109S midterm (by: 2026-Mar-2 18:30)
```
<br>


### 6. Unmark

**Purpose**: Unmark a specific task.

**Example**: `unmark 2`


**Expected outcome**:

```
Okie, I've marked this task as not done yet:
[D][ ] CS2109S midterm (by: 2026-Mar-2 18:30)
```

<br>

### 7. Find

**Purpose**: Find a task or multiple tasks by searching its/ their name(s).

**Example**: `Find CS2109S`


**Expected outcome**:

```
Heyyy! I've matched the following tasks in your list:
2.[D][ ] CS2109S midterm (by: 2026-Mar-2 18:30)
```

<br>

### 8. Delete

**Purpose**: Delete a task from the list by specifying its number.

**Example**: `Delete 2`

**Expected outcome**:

```
Okie!! I've removed this from the task list:
2.[D][ ] CS2109S midterm (by: 2026-Mar-2 18:30)
Now you have 1 tasks in total.
```

<br>

### 9. Snooze

**Purpose**: Snooze a task (Deadline or Event). Does not support Todo.

**Example**:
- To snooze a Deadline: `snooze 1 by XXXX`
- To snooze an Event: 
  - `snooze 2 from XXXX to XXXX`
  - `snooze 2 to XXXX`
  - `snooze 2 from XXXX`

**Expected outcome**:

```
Snoozed: [E][ ] Event (from: XXXX, to: XXXX)
```

<br>

### 10. Bye

**Purpose**: Exit the programme.

**Example**: `bye`

**Expected outcome**:

```
Nice to talk to you^^
See you!
```

<br>