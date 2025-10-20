# 🌟 LingoQuest - Interactive Language Learning Platform

![Java](https://img.shields.io/badge/Java-17+-orange.svg)
![JavaFX](https://img.shields.io/badge/JavaFX-21-blue.svg)
![AWS](https://img.shields.io/badge/AWS-Polly-yellow.svg)
![Maven](https://img.shields.io/badge/Maven-3.6+-red.svg)
![License](https://img.shields.io/badge/License-MIT-green.svg)

A comprehensive, enterprise-grade language learning platform built with JavaFX, featuring AWS Polly text-to-speech integration, real-time multiplayer functionality, and an advanced progression system.

## 🎥 Demo Video

[Watch the Full Application Demo](https://youtu.be/NGEjiR2yx3U)

## ✨ Key Features

### 🎯 Interactive Learning System

- **4 Question Types**: True/False, Multiple Choice, Fill-in-the-Blank, and Matching exercises
- **Progressive Difficulty**: Adaptive questioning based on user performance
- **Real-time Audio**: AWS Polly text-to-speech for pronunciation assistance
- **Answer Streak Tracking**: Gamified learning with streak counters and rewards

### 👥 Social Learning Platform

- **Friend System**: Send, accept, and manage friend requests
- **Live Leaderboard**: Real-time competitive rankings with points and coins
- **Progress Sharing**: View friends' achievements and language completion rates
- **Multiplayer Engagement**: Social features to enhance motivation

### 📊 Advanced Progress Tracking

- **Language Completion Percentage**: Visual progress indicators
- **Coins & Points System**: Reward-based learning with virtual currency
- **Section-based Learning**: Organized curriculum with targeted practice
- **Performance Analytics**: Detailed tracking of strengths and areas for improvement

### 🎨 Professional User Experience

- **Intuitive Navigation**: Clean, modern JavaFX interface
- **Dynamic Word Display**: New vocabulary introduced on each homepage visit
- **Responsive Design**: Optimized for various screen sizes
- **Color-coded Feedback**: Green/red visual indicators for correct/incorrect answers

## 🏗️ Technical Architecture

### Frontend

- **JavaFX 21**: Modern desktop GUI framework
- **FXML**: Declarative UI design with MVC architecture
- **CSS Styling**: Custom themes and responsive layouts
- **15+ Specialized Controllers**: Modular, maintainable codebase

### Backend Integration

- **AWS Polly**: Cloud-based text-to-speech synthesis
- **JSON Data Processing**: Structured data management
- **Singleton Pattern**: Efficient resource management
- **Real-time Updates**: Live leaderboard and friend system synchronization

### Build System

- **Maven**: Professional dependency management and build automation
- **Java Module System (JPMS)**: Modern modular architecture
- **Cross-platform Support**: ARM64 and x86_64 compatibility
- **Automated Testing**: JUnit integration for reliability

## 🚀 Installation & Setup

### Prerequisites

- **Java 17 or higher**
- **Maven 3.6+**
- **Git**

### Quick Start

```bash
# Clone the repository
git clone https://github.com/Alokothro/LingoQuest.git
cd LingoQuest2/lingoquest2

# Build and run with Maven
mvn clean javafx:run
```

### Apple Silicon Mac Setup

The project includes optimized configuration for Apple Silicon Macs:

```bash
# Clear any cached JavaFX libraries
rm -rf ~/.openjfx/cache

# Run with Maven (automatically handles ARM64 compatibility)
mvn clean javafx:run
```

### 🔑 Test Accounts

Use these pre-configured accounts to test the application:

| Username | Password |
|----------|----------|
| CadeTester | Password |
| CadeTester3 | Password |
| CadeTester4 | Password |
| SteveAccount | Password |
| SteveAccount2 | Password |
| JoeTester1 | Password |
| JoeTester2 | Password |

**Or create your own account using the "Create Account" button!**

## 📁 Project Structure

```
lingoquest2/
├── src/main/java/
│   ├── lingoquestpackage/
│   │   ├── controllers/          # 15+ UI Controllers
│   │   │   ├── LoginController.java
│   │   │   ├── HomeController.java
│   │   │   ├── LeaderboardController.java
│   │   │   ├── FriendsController.java
│   │   │   └── ...
│   │   ├── models/               # Data Models & Business Logic
│   │   │   ├── LanguageGame.java
│   │   │   ├── DictionaryManager.java
│   │   │   └── DataLoader.java
│   │   ├── narriator/           # AWS Polly Integration
│   │   └── lingoquest/          # Main Application
│   ├── module-info.java          # Java Module Configuration
│   └── resources/
│       └── lingoquestpackage/    # FXML Files & Assets
├── pom.xml                       # Maven Configuration
└── README.md
```

## 🎮 How to Use

### Getting Started

1. **Create Account**: Register with username and password
2. **Choose Language**: Select your target language (Spanish, etc.)
3. **Take Placement Test**: Determine your starting level
4. **Start Learning**: Begin with lessons suited to your level

### Learning Features

- **Answer Questions**: Engage with 4 different question types
- **Hear Pronunciation**: Click play buttons for AWS Polly audio
- **Track Progress**: Monitor completion percentage and streaks
- **Earn Rewards**: Collect coins and points for correct answers

### Social Features

- **Add Friends**: Send requests to other learners
- **Compete**: Check your ranking on the live leaderboard
- **Share Progress**: View friends' achievements and progress

## 🛠️ Development

### Technologies Used

- **Language**: Java 17
- **GUI Framework**: JavaFX 21
- **Cloud Services**: AWS Polly (Text-to-Speech)
- **Build Tool**: Maven 3.9
- **Module System**: Java Platform Module System (JPMS)
- **Testing**: JUnit 4 & 5
- **Data Format**: JSON
- **Audio**: JLayer for audio processing

### Key Dependencies

```xml
<dependencies>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>21</version>
    </dependency>
    <dependency>
        <groupId>software.amazon.awssdk</groupId>
        <artifactId>polly</artifactId>
        <version>2.29.12</version>
    </dependency>
    <!-- Additional dependencies in pom.xml -->
</dependencies>
```

## 🏆 Highlights

### Technical Achievements

✅ **Enterprise Architecture**: Professional MVC design with modular components
✅ **Cloud Integration**: Seamless AWS Polly text-to-speech implementation
✅ **Cross-platform Deployment**: Apple Silicon and Intel Mac compatibility
✅ **Modern Java**: Java Module System with proper encapsulation
✅ **Professional Build System**: Maven-based development workflow

### User Experience

✅ **Gamification**: Points, coins, streaks, and leaderboards
✅ **Social Learning**: Real-time friend system and competition
✅ **Adaptive Learning**: Progressive difficulty and personalized content
✅ **Multimedia Integration**: Audio pronunciation with visual feedback

## 👥 Contributors

**Team Members:**

- **Cade Stocker** - Project Lead & Core Development
- **Alok Patel** - DevOps Architecture & Java Module System Implementation
- **Wade Little** - UI/UX Design & Frontend Development
- **Preston Willis** - Backend Logic & Data Management

## 🎨 Design

**Color Palette:** #FAF9F9, #002642, #FABD00, #169873, #AD1519

## 🔗 Links

- **Repository**: [GitHub - LingoQuest](https://github.com/Alokothro/LingoQuest)
- **Demo Video**: [YouTube Demo](https://youtu.be/NGEjiR2yx3U)
- **JavaFX Documentation**: [OpenJFX](https://openjfx.io/)
- **AWS Polly**: [Amazon Polly](https://aws.amazon.com/polly/)

---

**Built with ❤️ by the LingoQuest Team**

🌟 **Star this repository if you found it helpful!**
