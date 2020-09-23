/* Amplify Params - DO NOT EDIT
	AUTH_SMARTSURVELLIANCE18E83A4C_USERPOOLID
	ENV
	REGION
	STORAGE_EDGEVISITORS_ARN
	STORAGE_EDGEVISITORS_NAME
Amplify Params - DO NOT EDIT */

const admin = require("firebase-admin");
var https = require('https');

var serviceAccount = require("./firebase-adminsdk.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://smart-surveillance-1eaf6.firebaseio.com"
});

exports.handler = async (event)  => {

  //==eslint-disable-line
  console.log(JSON.stringify(event, null, 2));
 //event.Records.forEach( async (record) => {
   // console.log(record.eventID);
  ////  console.log(record.eventName);
   // console.log('DynamoDB Record: %j', record.dynamodb);

    //  if(record.eventName == "INSERT" || record.eventName == "MODIFY") {
    //    var phoneId = record.dynamodb.NewImage.phoneId.S
     //   var imageUrl = record.dynamodb.NewImage.imageUrl.S
        const authHeader = 'key=AAAARjZ7jhU:APA91bFkHbhG5rChfxO0gt9gWfRk57PT0LJ659Yp9rBMC2u4kGrBEN_rnvau_PMu3kh-wvHO0OxQoX_Pa4JxqWNkiG3trsQ_7IIFjV3nhFIwULd3jHUQkQsLO0jTOZmn0zHx6rii6DqP';
        var imageUrl = "imaheurl"
        var phoneId = "fqO36xZwS4qzNzYc0m9zRp:APA91bHp7VdNBRgTc2EEWfg6WKR2rY4zekjzm2iYnScYmjoFjLVME7_Iczz8dYOSdF-muZ58-B-SXn5-UNTABneA1CJLJ1pk8r1_dAdqn92MItx6HWPI1RoI1n5mt-oqRtxf2VdvFdwn"
      //  console.log(phoneId)
       // console.log(imageUrl)

        // Define a condition which will send to devices which are subscribed
        // to either the Google stock or the tech industry topics.

        // var options = {
        //   priority: 'normal',
        //   timeToLive: 60 * 60,
        // };

        // See documentation on defining a message payload.
        var message = {
          notification: {
            title: 'Photo Identification',
            body: 'Confirm If you know this individual or not',
            image: imageUrl
          },
          data: {
            image_url: `${imageUrl}`,
            time: `${new Date().toString()}`,
          },
        };

        console.log(message)

        return new Promise((resolve, reject) => {
          const options = {
              host: 'fcm.googleapis.com',
              path: '/fcm/send',
              method: 'POST',
              headers: {
                  'Authorization': authHeader,
                  'Content-Type': 'application/json',
              },
          };
      
          console.log(options);

          const req = https.request(options, (res) => {
              console.log('success');
              console.log(res.statusCode);
              console.log(res)

              admin.messaging().sendToDevice(phoneId, message , options)
              .then(function(response) {
                console.log("Successfully sent message:", response);
                resolve('success');
              })
              .catch(function(error) {
                resolve('success');
                console.log("Error sending message:", error);
              });
          });
      0
          req.on('error', (e) => {
              console.log('failuree' + e.message);
              reject(e.message);
          });
      
          // const reqBody = '{"to":"' + deviceToken + '", "priority" : "high"}';
          const reqBody = '{"to":"' + phoneId + '", "priority": "high", "notification": {"title": "Test", "body": "Test"}}';
          console.log(reqBody);
      
          req.write(reqBody);
          req.end();
        });
    
 // });
  // return Promise.resolve('Successfully processed DynamoDB record');
};
