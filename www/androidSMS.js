var exec = require('cordova/exec');

var serviceName = 'cordova.android.sms.plugin.MyService';
var factory = require('com.red_folder.phonegap.plugin.backgroundservice.BackgroundService');
module.exports = factory.create(serviceName);

// exports.coolMethod = function(arg0, success, error) {
//     exec(success, error, "androidSMS", "coolMethod", [arg0]);
// };
