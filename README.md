<div align="center">

<img src="https://capsule-render.vercel.app/api?type=waving&color=gradient&customColorList=12,20,24&height=200&section=header&text=NHS%20BSA%20Job%20Search%20Automation&fontSize=36&fontColor=ffffff&animation=fadeIn&fontAlignY=40&desc=BDD%20%7C%20Selenium%20%7C%20Cucumber%20%7C%20Java%2021&descAlignY=60&descSize=16" width="100%"/>

<p>
  <img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"/>
  <img src="https://img.shields.io/badge/Selenium-4.x-43B02A?style=for-the-badge&logo=selenium&logoColor=white"/>
  <img src="https://img.shields.io/badge/Cucumber-7.x-23D96C?style=for-the-badge&logo=cucumber&logoColor=white"/>
  <img src="https://img.shields.io/badge/Maven-3.8%2B-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white"/>
  <img src="https://img.shields.io/badge/JUnit-4.13-25A162?style=for-the-badge&logo=junit5&logoColor=white"/>
  <img src="https://img.shields.io/badge/BDD-Gherkin-2EAD33?style=for-the-badge&logo=cucumber&logoColor=white"/>
</p>

</div>

---

## 📋 Project Overview

This repository contains a **production-grade Behaviour-Driven Development (BDD) test automation suite** targeting the **NHS Business Services Authority (NHSBSA) Jobs search portal**. The framework automates end-to-end user journeys across the job search interface, validating both functional flows and UI-level assertions in a maintainable, tag-driven, cross-browser architecture.

The suite is built on the **Page Object Model (POM)** design pattern and leverages **Cucumber Gherkin** as the specification language, ensuring human-readable test scenarios that bridge the gap between business requirements and automated test coverage. Dependency injection and property-driven configuration allow the framework to scale across environments and browsers without hardcoded values.

---

## 🎯 Business Context & User Story

```gherkin
Feature: NHS Jobs Search Functionality

  As a jobseeker on the NHS Jobs website
  I want to search for a job using my role preferences, location, and salary range
  So that I can view the most relevant and recently posted NHS job results
```

**Acceptance Criteria fulfilled:**
- Jobseeker can search by keyword, location, distance, employer, job reference, and pay range
- Results are returned matching the entered criteria
- Results can be sorted by **Date Posted (newest)**
- Field state and selected values persist correctly after search execution

---

## 🛠️ Technology Stack

| Layer | Technology | Version | Purpose |
|:---|:---|:---|:---|
| Language | Java | 21 (LTS) | Core automation language |
| Build Tool | Apache Maven | 3.8+ | Dependency management & build lifecycle |
| UI Automation | Selenium WebDriver | 4.39.0 | Browser interaction & element control |
| BDD Framework | Cucumber | 7.32.0 | Gherkin specification & step binding |
| Test Runner | JUnit | 4.13.2 | Test execution & assertions |
| Driver Management | WebDriverManager | 6.0.0 | Automatic ChromeDriver / GeckoDriver resolution |
| Assertion Library | JUnit Assert | 4.13.2 | Test outcome validation |
| IDE | IntelliJ IDEA | — | Development environment |

**Language split:**

```
Java (Steps, Pages, Utils)   ████████████████████  ~85%
Gherkin (Feature files)      ████░░░░░░░░░░░░░░░░  ~10%
XML / Properties (Config)    █░░░░░░░░░░░░░░░░░░░   ~5%
```

---

## 📁 Project Structure

```
NHSBSA_Search/
├── pom.xml                          # Maven build configuration & dependency declarations
├── config.properties                # Externalised config: base URL, element IDs, defaults
└── src/
    └── test/
        ├── java/
        │   ├── pages/
        │   │   ├── SearchPage.java   # POM class: search form interactions & user preferences
        │   │   └── ResultsPage.java  # POM class: results heading, sort controls, job counts
        │   ├── steps/
        │   │   └── Steps.java        # Cucumber step definitions — binds Gherkin to Selenium
        │   ├── runners/
        │   │   └── TestRunner.java   # JUnit runner: tag filtering & report configuration
        │   └── utils/
        │       └── DriverFactory.java # WebDriver lifecycle, property loading, browser factory
        └── resources/
            └── features/
                └── search.feature   # 18 scenarios across @happy, @unhappy, @ui tags
```

---

## 📊 Test Coverage Summary

| Category | Scenarios | Tags |
|:---|:---:|:---|
| Happy path — functional search flows | 7 | `@happy` |
| UI validation — dropdowns, defaults, hints | 5 | `@ui` |
| Negative / error handling | 6 | `@unhappy` |
| **Total** | **18** | — |

### Scenario Breakdown

**Functional (@happy)**
- Basic search using job title + location → results validated against user preferences
- Advanced search using job reference + employer → result list and sort confirmed
- Scenario Outline: multi-row parametrised inputs (Nurse/London, Doctor/Manchester) with full field retention check
- Clear filter button resets all fields to blank / default state
- Empty search returns all available job listings (count > 0 confirmed)
- Location autocomplete: type partial string → select from dropdown suggestion list

**UI Validation (@ui)**
- Distance dropdown disabled by default; enabled after location entry
- Distance dropdown contains all 6 expected mileage options (+5 to +100 Miles)
- Pay Range dropdown contains all 12 expected salary band options
- Sort By dropdown contains all 5 expected sort options after results load
- Placeholder/hint text and default dropdown state verified on "More search options" expansion

**Negative (@unhappy)**
- Invalid job reference → "No result found" error message validated
- Non-existent job title → "No result found" error message validated
- 5-row Scenario Outline: location typos, fake references, fake employers, special characters → error messages verified
- Invalid location text → no autocomplete suggestions rendered

---

## ⚙️ Prerequisites & Setup

| Requirement | Version |
|:---|:---|
| Java JDK | 21+ |
| Apache Maven | 3.8+ |
| Google Chrome / Firefox | Latest stable |
| Internet access | Required (live NHS Jobs site) |

```bash
# Clone the repository
git clone https://github.com/rutulraval/Rutul--NHSBSA.git
cd Rutul--NHSBSA
```

---

## ▶️ Running the Tests

### Run all tests

```bash
# Chrome
mvn clean test -Dbrowser=chrome

# Firefox
mvn clean test -Dbrowser=firefox
```

### Run by tag

| Scenario Type | Command |
|:---|:---|
| Happy path only | `mvn clean test "-Dcucumber.filter.tags=@happy" -Dbrowser=chrome` |
| UI validations only | `mvn clean test "-Dcucumber.filter.tags=@ui" -Dbrowser=chrome` |
| Negative tests only | `mvn clean test "-Dcucumber.filter.tags=@unhappy" -Dbrowser=chrome` |
| Happy + UI combined | `mvn clean test "-Dcucumber.filter.tags=@happy or @ui" -Dbrowser=chrome` |
| Full suite | `mvn clean test "-Dcucumber.filter.tags=@happy or @ui or @unhappy" -Dbrowser=chrome` |

> Substitute `chrome` with `firefox` to run cross-browser. No manual driver setup required — WebDriverManager handles binary resolution automatically.

---

## 📈 Reports

After execution, the Cucumber HTML report is generated at:

```
target/cucumber-reports/report.html
```

Open in any browser for a visual breakdown of passing, failing, and skipped scenarios with step-level detail.

---

## 🔭 Roadmap & Future Enhancements

- [ ] Parallel test execution (Maven Failsafe + thread-safe DriverFactory)
- [ ] Headless browser mode for CI/CD pipeline integration
- [ ] Allure Reporting integration for enhanced dashboards
- [ ] BrowserStack / Sauce Labs cloud browser grid
- [ ] Dockerised execution via Selenium Grid
- [ ] Data-driven testing with external CSV / Excel data sources
- [ ] Retry mechanism for flaky network-dependent assertions
- [ ] GitHub Actions CI/CD workflow
- [ ] Accessibility testing using Axe-core
- [ ] API-level validation for NHS Jobs backend

---

<div align="center">

**👤 Author: [Rutul Raval](https://github.com/rutulraval)** · SDET | ISTQB Certified

<img src="https://capsule-render.vercel.app/api?type=waving&color=gradient&customColorList=12,20,24&height=100&section=footer" width="100%"/>

</div>
