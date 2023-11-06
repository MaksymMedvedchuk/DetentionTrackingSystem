Arrest Management System for Clients
-
Overview
-
This project is an application for managing arrests related to clients. It's a server application that provides the necessary API to handle arrests. When the application starts, it checks for the existence of tables and creates them if necessary.
---
System Objects
----
"Person" Object


- Unique client identifier
- Last name (up to 100 characters)
- First name (up to 100 characters)
- Type of Identification Document (ID)
- ID document number and series (format: NNNNNN SS SS or NNNNNN SS)
- Place of birth (up to 250 characters)
- Date of birth (date)
- List of arrests


"Arrest" Object
- Governing body
- Date of the arrest document
- Primary document number (basis for the arrest, up to 30 characters, allowing Latin and Cyrillic characters, numbers, "#" or "№")
- Basis (textual description, up to 1000 characters)
- Arrest amount (modifiable, in kopecks)
- Status: Active, Executed, Canceled






