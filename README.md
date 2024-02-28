# Detention tracking system

## Description
It's a server app that provides API to handle detentions of persons before state authorities, such as the State Tax Service and the Service of Bailiffs. App to gather, store, and manage the cases of detained suspects and the processing of fees.

## Key Features of the Project:
1. Two Types of State Agencies: State Tax Service and the Service of Bailiffs;
2. Email Notifications: The ADMIN can fill out a table with persons's data and the user receives notifications with a document number on their email;
3. User Registration: Users can register in the system and view information about themselves by document number after verification;
4. Protection of personal data: The series and number of the passport are filled in a special format corresponding to the specific service and type of passport. After the passport series is converted to a standard form and stored in the database;

Inner View(in this format of series will be stored in DB)
| Inner code | Name | Format |
|------------|------|--------|
| 1 | passport | NNNNNN SS SS|
| 2 | foreign passport | NNNNNN SS |

State Tax Service(code 39)
| Code | Name | Format |
|------|------|--------|
| 21 | passport | SS SS NNNNNN|
| 22 | foreign passport | SS NNNNNN |

Service of Bailiffs(code 17)
| Code | Name | Format |
|------|------|--------|
| 70 | passport |NNNNNN-SS |
| 80 | foreign passport | NNNNNN.SS |

![Screenshot_10](https://github.com/MaksymMedvedchuk/DetentionTrackingSystem/assets/106758793/cfe0afac-7a70-462b-8bbc-20da31fe9d54)

## Installation and launch instructions:
1. Clone the repository to your computer;
2. Make a build of project;
3. Configure a database connection and an SMTP server to send messages in a file applicaion.properties;
4. Run the application from the executable or from the docker-compose.yml file;
## Usage process:
1. The administrator creates a new offense;
2. The user receives a notification by e-mail;
3. If the user is not registered, he registers and verifies his email;
4. After that, the user can check his information in the database or pay the fine immediately;
5. After successfully paying the fine, its status becomes "canceled";








  






