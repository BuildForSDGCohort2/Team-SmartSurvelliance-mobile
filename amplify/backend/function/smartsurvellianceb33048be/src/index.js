/* Amplify Params - DO NOT EDIT
	API_SMARTSURVELLIANCE_GRAPHQLAPIIDOUTPUT
	API_SMARTSURVELLIANCE_IMAGERECOGNITIONSTATUSTABLE_ARN
	API_SMARTSURVELLIANCE_IMAGERECOGNITIONSTATUSTABLE_NAME
	ENV
	REGION
Amplify Params - DO NOT EDIT */

var AWS = require('aws-sdk');
var request = require('request');
const util = require('util');
const axios = require('axios');
const { PassThrough } = require('stream')


// AWS.config.loadFromPath('./config.json');
var s3 = new AWS.S3({apiVersion: '2006-03-01'});



exports.handler = event => {
  //eslint-disable-line
  console.log(JSON.stringify(event, null, 2));

  var callers = [];

 
    event.Records.forEach(record => {
      console.log(record.eventID);
      console.log(record.eventName);

      if(record.eventName !== 'REMOVE') {

        console.log(util.inspect(record, {depth: 5}))
        console.log('DynamoDB Record: %j', record.dynamodb);
    
        var item = record.dynamodb.NewImage
    
        var isRecognised = item.isRecognised.BOOL;
        var imageUrl = item.imageUrl.S;
        var userId = item.userId.S;
        var individualsName = item.individualsName.S;
        individualsName = individualsName.replace(/ /g,"_");

    
        console.log(isRecognised)
        console.log(imageUrl)
        console.log(userId)
    
        var subArray = imageUrl.split('/');
    
        var bucket = "detection-garage";
    
        if(isRecognised == true){
          var bucketSubfolder = "knownfaces";
        } else {
          var bucketSubfolder = "intruderfaces";
        }
        // var bucketSubfolder = "knownfaces";
    
        var subFolder = subArray[subArray.length - 2]
        var oldMediaName = subArray[subArray.length - 1]

        var image = oldMediaName.split('.')
        var imageName = image[image.length - 1]

        var mediaName = `${individualsName}.${imageName}`
    
        var key =  `${bucketSubfolder}/${subFolder}/${mediaName}` //'media/aws_logo.png'
    
        // console.log(key)
    
        // https://detection-garage.s3.amazonaws.com/UnknownFaces/useridforuser/_locko.jpg var subArray = s3Key.split('/')
     // https://detection-garage.s3.amazonaws.com/UnknownFaces/2020-09-24+18%3A01%3A49.jpg
        //'https://i.stack.imgur.com/oKcp4.png'
        //'https://detection-garage.s3.amazonaws.com/UnknownFaces/XYZ123/_locko.jpg'
    
        callers.push(
          putFromUrl(imageUrl, bucket, key)
        ); 
      }
      else {
        console.log("Removed Object Object")
        return Promise.resolve('Removed Object');
      }
    });
   return Promise.resolve(Promise.all(callers))
};


const downloadFile = async (downloadUrl) => {
  return axios.get(downloadUrl, {
    responseType: 'stream',
  });
};

async function putFromUrl(url, bucket, key) {

  const responseStream = await downloadFile(url);

  const passThrough = new PassThrough();

  var promise =  s3.putObject({
    Bucket: bucket,
    Key: key,
    ContentType: responseStream.headers['content-type'],
    ContentLength: responseStream.headers['content-length'],
    Body: passThrough,
    ACL: 'public-read'
  })
  .promise();

  console.log(`Function Called: Trasfering to S3 ${key}`)

  responseStream.data.pipe(passThrough);

  return promise
    .then((result) => {
      console.log("Finished Trasfering")
      return result.Location;
    })
    .catch((e) => {
      console.log("Error In function")
      throw e;
    });






  // const passThrough = new PassThrough();
  
  //  await request({
  //       url: url,
  //       encoding: null
  //   }, function(err, res, body) {
  //       if (err)
  //           return callback(err, res);

  //       s3.putObject({
  //           Bucket: bucket,
  //           Key: key,
  //           ContentType: res.headers['content-type'],
  //           ContentLength: res.headers['content-length'],
  //           Body: body // buffer
  //       }).promise()
  //   })
}


// const uploadFromStream = ( fileResponse, fileName,  bucket) => {
//   return s3.upload({
//       Bucket: bucket,
//       Key: fileName,
//       ContentType: fileResponse.headers['content-type'],
//       ContentLength: fileResponse.headers['content-length'],
//       Body: fileResponse.data,
//     })
//     .promise();
// };