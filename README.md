# Multi-Zone Taxi Cooperative Management System

Console-based Java application that simulates the operation of a multi-zone taxi cooperative using manually implemented data structures and graph-based route management.

---

## Overview

This project was developed as the final assignment for the **Data Structures** course.

The system manages:

* Taxi requests
* Driver assignment
* Dynamic road connectivity
* Route calculation
* Estimated fares
* Operational history

The implementation avoids using Java native collections as primary structures, focusing on manual implementation and integration of core data structures.

---

## Technologies

* Java JDK 25
* Object-Oriented Programming (OOP)
* Console-Based Interface

---

## Data Structures Implemented

* Linked Lists
* Queues (FIFO)
* Stacks (LIFO)
* Graphs with route calculation

---

## Main Features

* Taxi request management
* Driver availability validation
* Multi-zone route system
* Dynamic road closures
* Estimated pickup time
* Estimated fare calculation
* Request history management
* Manual data loading through terminal

---

## Project Structure

```text id="5d83x7"
src/
 └── cooperativa/
      ├── App.java
      ├── modelos/
      ├── estructuras/
      ├── servicios/
      └── utilidades/
```

---

## Compilation

```bash id="j1jlwm"
javac -d out src/cooperativa/*.java
```

---

## Execution

```bash id="t1v7ya"
java -cp out cooperativa.App
```

---

## Academic Purpose

The objective of this project is to demonstrate the practical application of data structures in a real-world scenario involving:

* Dynamic data management
* Graph-based route modeling
* Queue and stack operations
* Technical justification of algorithms and structures
