ASSETLOGIC-AI

1. Run backend using mvn spring-boot:run
2. Run agent using JavaFX
3. Run AI using uvicorn main:app


Asset Logic AI: Smart IT Asset Monitoring System
Asset Logic AI ek advanced Monitoring aur Predictive Maintenance system hai jo computers ki health ko real-time mein track karta hai aur AI ka use karke system failures ko pehle hi predict kar leta hai.
 Key Features
Real-time Monitoring: CPU usage, RAM usage, Temperature, aur Disk space ko live track karta hai (Powered by OSHI).

AI Health Scoring: Har system ko 0-100 ke beech ek Health Score deta hai.

Visual Alerts (Red/Blue Logic): * Blue: System stable aur healthy hai.

Red: System mein problem hai (High RAM/Temp) aur turant dhyan dene ki zaroorat hai.

AI Predictions & Suggestions: AI analyze karta hai ki system mein kya problem aa sakti hai aur use fix karne ke suggestions deta hai.

Secure Admin Dashboard: JWT Token-based login ke saath ek centralized command center jahan se saare devices ko monitor kiya ja sakta hai.

Hardware Fingerprinting: Har device ki details (OS Version, IP Address, GPU, Disk Type) automatically fetch karta hai.

 Tech Stack
Frontend: JavaFX (GUI), CSS for styling.

Backend: Spring Boot (Java), REST APIs.

Database: MySQL (For storing historical metrics).

Libraries: * OSHI: Hardware data fetch karne ke liye.

Jackson/JSON: Data handling ke liye.

HttpClient: Secure communication ke liye.

 Project Structure
agent-desktop: Ye wo software hai jo har client PC par chalega aur data collect karega.

backend-server: Main server jo saare agents se data lekar database mein save karta hai.

admin-command-center: Admin ka dashboard jahan saare devices ki live health dikhti hai.

ai-engine: Health scoring aur prediction logic handle karta hai.

 How to Run
Database Setup:

MySQL mein system_metrics table banayein (Columns: device_name, cpu_usage, ram_usage, temperature, ip_address, health_score, etc.)

Run Backend:

cd backend-server

mvn spring-boot:run

Run Admin Dashboard:

cd admin-command-center

mvn javafx:run

Run Agent:

cd agent-desktop

mvn javafx:run


Developer Details
Name: Saurabh Kumar

Role: Java Developer / MCA Student

Email: saurabhs9878@gmail.com

LinkedIn: https://www.linkedin.com/in/saurabh-kumar-24785823b 

