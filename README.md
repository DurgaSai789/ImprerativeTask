re's a simple and clean README.md text for your project:

Login with Biometric Authentication – Jetpack Compose
This project implements a login flow using Jetpack Compose and Kotlin. Once the user logs in successfully, biometric authentication is used for subsequent logins until the token becomes invalid or expires.

Features
Simple login screen

Biometric authentication for future logins

Token-based session management

Built with Jetpack Compose for a modern and minimal UI

Technologies Used
Kotlin

Jetpack Compose

Biometric Authentication API

How it works
User logs in using credentials.

On successful login, a token is saved securely.

For the next app launches, biometric login is triggered.

If the token is expired or invalid, the user is redirected to login screen again.
