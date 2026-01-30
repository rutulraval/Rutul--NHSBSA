# 🧪 NHS Jobs Search Automation Suite – Rutul Raval (NHSBSA)

## 🔍 Project Overview
This project automates the **Search functionality of the NHS Jobs website** using a **user-centric BDD approach** with **Java 21, Selenium WebDriver, Cucumber, and JUnit**.  
It validates job search behaviour across multiple scenarios and supports **cross-browser execution**.

---

## 📘 User Story
**As** a jobseeker on the NHS Jobs website  
**I want** to search for a job using my preferences  
**So that** I can view the most recently posted job results

---

## ✅ Acceptance Criteria
- Given I am a jobseeker on the NHS Jobs website
- When I enter my preferences into the search functionality
- Then I should see a list of jobs matching my preferences
- And I can sort my results by **Date Posted (newest)**

---

## 💻 Tech Stack

| Technology | Purpose |
|----------|--------|
| Java 21 | Programming language |
| Maven | Build & dependency management |
| Selenium WebDriver | Browser automation |
| Cucumber (Gherkin) | BDD test framework |
| JUnit | Test runner |
| WebDriverManager | Driver management for Chrome & Firefox |

---

## 📂 Project Structure
```
NHSBSA_Search/
├── src
│ ├── main
│ └── test
│ ├── java
│ │ ├── pages
│ │ ├── runners
│ │ ├── steps
│ │ └── utils
│ └── resources
│ └── features

```
##

---

## 🎯 Test Coverage

### 🔹 Functional Scenarios
- ✅ Job search using title, location, distance, reference, employer, and pay range
- ✅ Dropdown selection validation and input retention
- ✅ Sorting by **Date Posted (newest)**
- ✅ Clear filters and reset behaviour
- ✅ Search with all fields empty

### 🔹 UI Validations
- ✅ Default dropdown selections (Distance, Pay Range, Sort By)
- ✅ Dynamic enable/disable of Distance based on Location
- ✅ Location suggestion handling
- ✅ Error message validation

### 🔹 Negative & Edge Cases
- ✅ Invalid job reference
- ✅ No results found for given title

### 🔹 Cross-Browser Support
- ✅ Chrome
- ✅ Firefox
- ✅ No machine-bound drivers (WebDriverManager)

---

## ⚙️ How to Run the Tests

### 🔧 Prerequisites
- Java 21+
- Maven 3.8+
- Internet connection

### 📥 Clone the Repository
```bash
git clone https://github.com/rutulraval/Rutul--NHSBSA.git
```

▶️ Run All Tests

Chrome
```mvn clean test -Dbrowser=chrome```

Firefox

```mvn clean test -Dbrowser=firefox```

## 🎯 Run Tests by Tags

| Scenario Type | Command |
|--------------|---------|
| Happy path only | `mvn clean test "-Dcucumber.filter.tags=@happy" "-Dbrowser=chrome"` |
| UI validations | `mvn clean test "-Dcucumber.filter.tags=@ui" "-Dbrowser=chrome"` |
| Negative tests | `mvn clean test "-Dcucumber.filter.tags=@unhappy" "-Dbrowser=chrome"` |
| Happy + UI | `mvn clean test "-Dcucumber.filter.tags=@happy or @ui" "-Dbrowser=chrome"` |
| All tests | `mvn clean test "-Dcucumber.filter.tags=@happy or @ui or @unhappy" "-Dbrowser=chrome"` |

> Replace `chrome` with `firefox` as needed.

---

## 🧾 Test Reports

**HTML Report**

target/cucumber-reports/report.html


---

## 🌱 Future Enhancements

- Parallel execution
- Headless execution for CI
- BrowserStack / Sauce Labs integration
- Accessibility testing (Axe / Lighthouse)
- API-level validation
- Dockerized execution
- External test data (CSV / Excel)
- Retry mechanism for flaky tests
- Allure reporting
- Email notifications
- AWS S3 report storage

---

## 👤 Author

**Rutul Raval**  
🔗 https://github.com/rutulraval/Rutul--NHSBSA.git
