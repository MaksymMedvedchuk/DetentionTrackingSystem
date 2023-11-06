Arrest Management System for Clients
-
Overview
-
This project is an application for managing arrests related to clients. It's a server application that provides the necessary API to handle arrests. When the application starts, it checks for the existence of tables and creates them if necessary.

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

Supported Government Bodies:
--
State Tax Service
- Code
- Name
- Format

 Service of Bailiffs
 - Code
 - Name
 - Format

Procedures
-
Arrest Management Procedure
-
Input Parameters
- RequestId (GUID): Unique message identifier (mandatory)
- LastName (String): Client's last name
- FirstName (String): Client's first name
- IdentDoc
  - Type (Integer): ID document type
  - NumberSeries (String): ID document number and series
  - IssueDate (Date): Document issue date
- OrganCode (Integer): Government body code (17 - Federal Bailiffs Service, 39 - Federal Tax Service)
- Arrest
  - DocDate (Date): Arrest document date
  - DocNum (String): Arrest document number
  - Purpose (String): Basis (text)
  - Amount (Long): Arrest amount in kopecks
  - RefDocNum (String): Reference document number for related documents, if Operation is 2 or 3
  - Operation (Integer): Operation type (1 - initial, 2 - modification, 3 - cancellation)

Return Values
-
- ArrestId: Internal arrest identifier within the program (primary key from the database)
- ResultCode: Processing result (0 - success, 3 - business data error, 5 - technical error)
- ResultText: Error explanation

General Algorithm
--
1. The service manages a list of arrests for each client
2. Arrests are associated with clients. Client uniqueness is determined by the attributes: last name, first name, ID type, ID number-series
3. Before searching for a client, it's necessary to validate the ID number-series format
4. If a client is not found in the system, they should be created
5. Before creating an arrest object, data correctness should be verified: document number format, ID format, and other aspects
6. Specifics of arrest operations
   - Initial arrest - an arrest object is created, linked to a client, and its status is set to "Active"
   - Modification of an arrest - the system searches for the arrest, and its fields (amount, basis) are updated. If the arrest amount is greater than 0, the status is set to "Active"; otherwise, it's set to "Canceled"
   - Cancellation of an arrest - the system searches for the arrest, and the internal arrest status is set to "Canceled"





  






