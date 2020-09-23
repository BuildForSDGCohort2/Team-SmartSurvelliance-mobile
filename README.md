# Smart Survelliance

## Team-SmartSurvelliance-mobile

--
### Table of Contents

1. [Description](#desccription)
2. [Dependencies](#dependencies)
3. [Instation and Running](#installation)

## Desciption <a name="description"></a>
This project in collaboration with [Facebook](
facebook.com) and [Andela](https://andela.com/), is aimed at building a Face recognition security surveillance system with the support nvidia edge device and AWS services. 

The mobile app for this project is build with native android using kotlin. The following actions are carried out in this app
 - [X] User Signup, Login and Signout
 - [X] User subscription to firebase notification service
 - [ ] Notifications carring an image are sent to user via aws and firebase from an edge device
 - [X] User confirms identify from notification 
 - [ ] Stream Live video feeds from Edge Device and Camera via AWS S3 and Cloudfront

## Dependencies <a name="dependencies"></a>

#### Principal 
 - Android Studio
 - Kotlin 1.3.x >
 - Node.js version 10 or higher
 - AWS Amplify CLI version 4.21.0 or later
 - Libvlc library version 3.3.0 or higher
 - Firebase

#### AWS Services
##### AWS Cognito
User authentication

##### AWS Amplify DataStore 
Leverage Shared and distributed data, persist data localy on devices. Uses Facebooks [GraphQl](https://graphql.org/) schema files as the defination of the application data model

##### AWS AppSync
Uses GraphQl API to a manange APi calls for data syncronization with the cloud

##### AWS S3
Host Images and Video streams

##### AWS Cloudfront
Stream Video feeds from s3 in the mobile device

## Installation<a name="installation"></a>
- Clone repoitory and run android studio
- Make sure to turn on usb debudding if in case on test pysical phone or run via android emulator

<b>Note</b>: To pull or push AWS amplify configurations to the cloud one will need an AWS user with appropriate permissions. 


[![Codacy Badge](https://api.codacy.com/project/badge/Grade/b1a90331120c444eb56e9f98cb91ce2e)](https://app.codacy.com/gh/BuildForSDGCohort2/Team-SmartSurvelliance-mobile?utm_source=github.com&utm_medium=referral&utm_content=BuildForSDGCohort2/Team-SmartSurvelliance-mobile&utm_campaign=Badge_Grade_Settings)