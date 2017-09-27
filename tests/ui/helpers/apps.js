
var currentFolder = __dirname.replace(/revsw\-sdk\-android\/.*/g, '');
console.log('NEW PUTH ==>> ', __dirname);
//--  /var/lib/jenkins/workspace/appium-tests/tests/ui/helpers

///var/lib/jenkins/workspace/appium-tests/NuubitTester/build/outputs/apk/NuubitTester-release-unsigned.apk
//exports.androidTester = "/var/lib/jenkins/workspace/appium-tests/NuubitTester/build/outputs/apk/NuubitTester-debug.apk";
exports.androidTester = currentFolder + "/apks/NuubitTester-debug.apk";
//exports.androidTester = currentFolder + "apps-android/apks/NuubitTester-debug.apk";
exports.androidTesterInvalidSDKkey = currentFolder + "/apks/NuubitTesterInvalidSDKKey.apk";
exports.androidTesterSameSDKKey = currentFolder + "/apks/NuubitTesterSameSDKKey.apk";

//exports.androidTester = currentFolder + "apps-android/apks/apk/NuubitTester-debug.apk";


