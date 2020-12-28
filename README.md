﻿# Products-Vendors SQL<-->XML
A java application that performs two-way equivalent transformations between an XML document and an SQL Database, which contain information about Products and Vendors with a many-to-many relation
## Build the application
Execute `mvn clean install` in the root directory of the project

## Start the application
Execute `mvn exec:java` in the root directory of the project

### Commands
`convert --tosql <input_file>` - Verifies and validates the input XML file and flushes its content to the database\
`convert --toxml <output_file>` - Fetches the data from the database and writes it into an XML file with a proper inline DTD\
`quit` - Terminate the application

* Note -> The database is located at ${ROOT_PROJECT_FOLDER}/database
