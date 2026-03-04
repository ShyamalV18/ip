# Porus User Guide

Welcome to **Porus**  
*(Personally Operating Real Understanding Service)*

Porus is a lightweight **Command Line Interface (CLI)** task management chatbot.  
It allows you to manage your tasks efficiently using simple text commands.

---

# Quick Start

1. Ensure you have **Java 17** or above installed.
2. Download the latest `porus.jar` from the Releases page.
3. Copy the jar file into a folder of your choice.
4. Open a terminal and navigate to that folder.
5. Run:


java -jar porus.jar


6. Type commands and press **Enter** to execute them.

---

# Features

---

## Adding Tasks

Porus supports three types of tasks.

---

### 1. Todo

Adds a simple task without date/time.

**Format:**

todo DESCRIPTION


**Example:**

todo read book


---

### 2. Deadline

Adds a task with a deadline.

**Format:**

deadline DESCRIPTION /by DATE


**Example:**

deadline submit assignment /by Friday 5pm


> **Note:** `/by` is required for deadlines.

---

### 3. Event

Adds a task with a start and end time.

**Format:**

event DESCRIPTION /from START /to END


**Example:**

event project meeting /from 2pm /to 4pm


> **Note:** Both `/from` and `/to` are required.

---

## Listing Tasks

Displays all tasks in your list.

**Format:**

list


---

## Marking Tasks as Done

Marks a task as completed.

**Format:**

mark TASK_NUMBER


**Example:**

mark 2


---

## Unmarking Tasks

Marks a completed task as not done.

**Format:**

unmark TASK_NUMBER


**Example:**

unmark 2


---

## Deleting Tasks

Deletes a task from the list.

**Format:**

delete TASK_NUMBER


**Example:**

delete 1


---

## Finding Tasks

Finds tasks containing an exact keyword match.

**Format:**

find KEYWORD


**Example:**

find book


### Matching Rules

- Search is **case-insensitive**
- Only **full words** are matched
- `find book` will match:
   - `return book`
- It will NOT match:
   - `bookmark`

---

## Exiting the Program

Closes Porus.

**Format:**

bye


---

# Data Storage

All tasks are automatically saved after every change.

Data file location:


./data/porus.txt


There is no need to manually save.

---

# Error Handling

Porus will display clear error messages if:

- A command format is invalid
- A required field is missing
- A task number is not valid
- A task number is out of range

---

# Command Summary

| Command | Description |
|----------|-------------|
| `todo` | Add a todo task |
| `deadline` | Add a deadline task |
| `event` | Add an event task |
| `list` | Show all tasks |
| `mark` | Mark task as done |
| `unmark` | Unmark task |
| `delete` | Delete task |
| `find` | Search tasks |
| `bye` | Exit program |

---

# FAQ

### Q: How do I transfer my tasks to another computer?
Copy the `porus.txt` file inside the `data` folder to the new computer after installing Porus.

### Q: What happens if I edit the data file manually?
If the file format becomes invalid, Porus may fail to load tasks correctly.

---

Thank you for using Porus!