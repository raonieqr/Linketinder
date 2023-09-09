# LinkeTinder

## Description
LinkeTinder is a simple Groovy program that simulates a matchmaking platform to connect job candidates and companies. It allows you to list companies and candidates, add new candidates, and view their information.

## Prerequisites
Make sure you have Groovy installed on your local machine. If not, you can download and install it from the official Groovy website (https://groovy-lang.org/download.html).

## How to Run the Project
1. Clone this repository to your local machine.
2. Navigate to the root directory of the project.

## Stacks
**Backend**
-  Groovy
 
**Frontend**
- HTML5: The standard markup language for structuring and creating web application content.

- CSS3: Used to style and visually enhance the application, ensuring a pleasing user experience.

- Typescript/JavaScript: The programming language used to add interactivity and dynamic functionality to the application.

- LocalStorage: Utilized to store data locally in the browser, allowing candidates to register and store profile and application information.

- DOM Manipulation: Manipulating the Document Object Model (DOM) is used to interact with HTML elements and dynamically update page content.

- Event Listeners: Event listeners are used to detect user interactions, such as button clicks or links, and trigger corresponding actions.

- Async Functions: Asynchronous functions are used to handle asynchronous operations, such as retrieving and storing data in LocalStorage.

### Running the Program
````
groovy src/Main.groovy
````
## Features
- List Companies: The program allows you to list information about registered companies.
- List Candidates: The program enables you to list information about registered candidates.
- Add New Candidate: You can add a new candidate with details such as name, email, skills, age, state, description, CPF, and ZIP code.
- Matching  
  - Create Vacancies: Companys can now create job vacancies for their companies. The application will prompt for the vacancy name, description, and required skills. Skills are entered as a comma-separated list. This feature streamlines the process    of creating vacancies and finding suitable candidates.
  - Like Vacancies: Companies can now "like" vacancies posted by candidates that match their needs. This demonstrates interest in the vacancy and enables potential matches between companies and candidates.
  - View Matches: Candidates and companies can now view their mutual matches. Candidates can see the vacancies that have been "liked" by companies, and companies can see the candidates that have expressed interest.
- View Matches: Candidates and companies can now view their mutual matches. Candidates can see the vacancies that have been "liked" by companies, and companies can see the candidates that have expressed interest.
- Database: Save application data
- **NEW** Gradle:
  - Gradle was added to take care of dependency injections, testing and running the program.
    The commands can be used: gradle test to run the tests, gradle build to build the project and gradle run to run
## Contribution
Contributions are welcome! If you encounter issues, bugs, or want to add new features, feel free to open a pull request.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

