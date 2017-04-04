"use strict";

require("./helpers/setup");

var wd = require("wd"),
    _ = require('underscore'),
    actions = require("./helpers/actions"),
    serverConfigs = require('./helpers/appium-servers'),
    _p = require('./helpers/promise-utils'),
    Q = require('q');
wd.addPromiseChainMethod('swipe', actions.swipe);

var request = require("request");

describe("android simple", function () {
  this.timeout(300000);
  var driver;
  var allPassed = true;
   var nameOfTheNewApp = "QAAndroid" + new Date();
   var appIDFromResponseBody;
   var portalAPIKey = "X-API-KEY 465618f8-dd75-4165-bfa2-70b10d955ad4";
   var appId = "58d535b8519150d22ab102b4";
   var accountId = "585296f11ea9bb3c3f90e05b";

  before(function () {
    var serverConfig = process.env.npm_package_config_sauce ?
      serverConfigs.sauce : serverConfigs.local;
    driver = wd.promiseChainRemote(serverConfig);
    require("./helpers/logging").configure(driver);

    var desired = process.env.npm_package_config_sauce ?
      _.clone(require("./helpers/caps").android18) :
      _.clone(require("./helpers/caps").android19);
    desired.app = require("./helpers/apps").androidApiDemos;
    if (process.env.npm_package_config_sauce) {
      desired.name = 'android - simple';
      desired.tags = ['sample'];
    }
     request({
                    url: "https://api.nuubit.net:443/v1/apps/" + appId + "?options=publish",
                    method: "PUT",
                    json: true,
                    headers: {
                        "Accept": "application/json",
                        "Authorization": portalAPIKey
                    },
                    body: {

                    "app_name": "Rev Android SDK QA -55",
                    "account_id": accountId,
                    "configs": [
                    {
                        "sdk_release_version": 0,
                        "logging_level": "debug",
                        "configuration_refresh_interval_sec": 67,
                        "configuration_stale_timeout_sec": 36662,
                        "operation_mode": "transfer_and_report",
                        "allowed_transport_protocols": [
                            "rmp",
                            "quic",
                            "standard"
                        ],
                        "initial_transport_protocol": "standard",
                        "stats_reporting_interval_sec": 66,
                        "stats_reporting_level": "debug",
                        "stats_reporting_max_requests_per_report": 501,
                        "domains_provisioned_list": [],
                        "domains_white_list": [],
                        "domains_black_list": [],
                        "a_b_testing_origin_offload_ratio": 0
                    }
                ],
                    "comment":"dsfs"
                }

                     }, function (error, response, body){
                          console.log(body);
                  });
    return driver
      .init(desired)
      .setImplicitWaitTimeout(5000);
  });

  after(function () {
    return driver
      .quit();
    //  .finally(function () {
    //    if (process.env.npm_package_config_sauce) {
    //      return driver.sauceJobStatus(allPassed);
    //    }
    //  });
  });

  afterEach(function () {
    allPassed = allPassed && this.currentTest.state === 'passed';
  });

  it("should find an element", function () {


//CREATE NEW APP ON API*********************************************************************************************************

 /* request({
      url: "https://testsjc20-api01.revsw.net:443/v1/apps",
      method: "POST",
      json: true,
      headers: {
          "Accept": "application/json",
          "Authorization": portalAPIKey
      },
      body: {
                          "account_id": "58d38944eeaccf985f7936f6",
                          "app_name": "\"" + nameOfTheNewApp + "\"" ,
                          "app_platform":"Android",
                          "comment": "comment"
    }
  }, function (error, response, body){
      console.log(response);
      appIDFromResponseBody = JSON.stringify(body).slice(7, 31);
      console.log(appIDFromResponseBody);
  });*/
//CREATE NEW APP ON API***********************************************************************************************************************************************


//DELETE APP ON API USING ID*****************************************************************************************************
 /* request({
        url: "https://testsjc20-api01.revsw.net:443/v1/apps/" + appIDFromResponseBody,
        method: "DELETE",
        headers: {
            "Accept": "application/json",
            "Authorization": portalAPIKey
        },
    }, function (error, response, body){
        console.log(body);
    });*/
//DELETE APP ON API USING ID*******************************************************************************************************************************************

//SEND REQUEST USING OPTIONS WITH AUTHENTIFICATION, ACCEPT HEADERS**********************************************************
 /* var options = {
    url: 'https://testsjc20-api01.revsw.net:443/v1/apps',
    headers: {
      'Accept': 'application/json',
      'Authorization': portalAPIKey
    }
  };

  request(options, function(error, response, body) {
    console.log(body);
  });*/
//SEND REQUEST USING OPTIONS WITH AUTHENTIFICATION, ACCEPT HEADERS*****************************************************************************************************

//SET CONFIG OF THE APP ON API*********************************************************************************************************
    /* request({
          url: "https://api.nuubit.net:443/v1/apps/" + appId + "?options=publish",
          method: "PUT",
          json: true,
          headers: {
              "Accept": "application/json",
              "Authorization": portalAPIKey
          },
          body: {

          "app_name": "Rev Android SDK QA -55",
          "account_id": accountId,
          "configs": [
          {
              "sdk_release_version": 0,
              "logging_level": "debug",
              "configuration_refresh_interval_sec": 65,
              "configuration_stale_timeout_sec": 36662,
              "operation_mode": "transfer_and_report",
              "allowed_transport_protocols": [
                  "rmp",
                  "quic",
                  "standard"
              ],
              "initial_transport_protocol": "standard",
              "stats_reporting_interval_sec": 65,
              "stats_reporting_level": "debug",
              "stats_reporting_max_requests_per_report": 501,
              "domains_provisioned_list": [],
              "domains_white_list": [],
              "domains_black_list": [],
              "a_b_testing_origin_offload_ratio": 0
          }
      ],
          "comment":"dsfs"
      }

           }, function (error, response, body){
                console.log(body);
        });*/
//SET CONFIG OF THE APP ON API***********************************************************************************************************************************************



//GET CONFIG OF THE APP ON API*********************************************************************************************************
    /*  request({
          url: "https://testsjc20-api01.revsw.net:443/v1/apps/" + appId + "?version=0",
          headers: {
              "Accept": "application/json",
              "Authorization": portalAPIKey
          }
      }, function (error, response, body){
          console.log(body);
      });*/
//GET CONFIG OF THE APP ON API***********************************************************************************************************************************************



//CLICK ON ELEMENTS IN APP USING Xpath*********************************************************************************************
     return driver
            .sleep(7000)
            .elementByClassName('android.widget.ImageButton')
           //.elementByXPath('//android.widget.TextView[@text=\'Rev Application Demo\']')
           .click()
             .sleep(2000)
             .elementByXPath('//android.widget.CheckedTextView[@text=\'Configuration view\']')
             .click()
             .sleep(3000)
           //  .elementsByClassName('android.widget.TextView')
          //    .then(function (els) {
          //      console.log(els);
          //    })
             .elementsByXPath('//android.widget.TextView')
               .then(function (els) {
                      for (var i = 0 ; i < els.length; i++)
                        console.log(els[i].text());
                        return els[2].text().should.become('20');

                     })
              .sleep(3000);

//CLICK ON ELEMENTS IN APP USING Xpath**********************************************************************************************************************************





// CREATE NEW APP on API, WAIT CONFIG REFRESH INTERVAL, RESTART(RESET) APP, DELETE APP on API**********************************************
        /*
          return   driver.sleep(5000)
          .then(function () {


            request({
                url: "https://testsjc20-api01.revsw.net:443/v1/apps",
                method: "POST",
                json: true,
                headers: {
                    "Accept": "application/json",
                    "Authorization": portalAPIKey
                },
                body: {
                                    "account_id": "58d38944eeaccf985f7936f6",
                                    "app_name": "\"" + nameOfTheNewApp + "\"" ,
                                    "app_platform":"Android",
                                    "comment": "comment"
              }
            }, function (error, response, body){
                console.log(body);
              //  appIDFromResponseBody = body.toString().slice(0,10);
                appIDFromResponseBody = JSON.stringify(body).slice(7, 31);
                console.log(appIDFromResponseBody);
                    console.log(appIDFromResponseBody);
                    console.log(appIDFromResponseBody);
                    console.log(appIDFromResponseBody);
            });
                  })
          .resetApp()
          .then(function () {
                request({
                        url: "https://testsjc20-api01.revsw.net:443/v1/apps/" + appIDFromResponseBody,
                        method: "DELETE",
                        headers: {
                            "Accept": "application/json",
                            "Authorization": portalAPIKey
                        },
                    }, function (error, response, body){
                        console.log(body);
                    });
                            })
          .sleep(5000);*/
// CREATE NEW APP on API, WAIT CONFIG REFRESH INTERVAL, RESTART(RESET) APP, DELETE APP on API***********************************************************************


  });
});

