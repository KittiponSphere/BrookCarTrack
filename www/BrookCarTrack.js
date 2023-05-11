
// Empty constructor
function BrookCarTrack() {}

// The function that passes work along to native shells
// Message is a string, duration may be 'long' or 'short'
BrookCarTrack.prototype.show = function(message, duration, successCallback, errorCallback) {
  var options = {};
  options.message = message;
  options.duration = duration;
  cordova.exec(successCallback, errorCallback, 'BrookCarTrack', 'show', [options]);
};

BrookCarTrack.prototype.CreateBle = function(terminalID, successCallback, errorCallback) {
  var options = {};
  options.terminalID = terminalID;
  cordova.exec(successCallback, errorCallback, 'BrookCarTrack', 'CreateBle', [options]);
};

BrookCarTrack.prototype.saveKey = function(key, successCallback, errorCallback) {
  var options = {};
  options.key = key;
  cordova.exec(successCallback, errorCallback, 'BrookCarTrack', 'saveKey', [options]);
};

BrookCarTrack.prototype.connect = function(terminalID, successCallback, errorCallback) {
  var options = {};
  options.terminalID = terminalID;
  cordova.exec(successCallback, errorCallback, 'BrookCarTrack', 'connect', [options]);
};

BrookCarTrack.prototype.lock = function(successCallback, errorCallback) {
  var options = {};
  cordova.exec(successCallback, errorCallback, 'BrookCarTrack', 'lock', [options]);
};

BrookCarTrack.prototype.unlock = function(successCallback, errorCallback) {
  var options = {};
  cordova.exec(successCallback, errorCallback, 'BrookCarTrack', 'unlock', [options]);
};

BrookCarTrack.prototype.horn = function(successCallback, errorCallback) {
  var options = {};
  cordova.exec(successCallback, errorCallback, 'BrookCarTrack', 'horn', [options]);
};

BrookCarTrack.prototype.oneClickConnect = function(terminalID,key, successCallback, errorCallback) {
  var options = {};
  options.terminalID = terminalID;
  options.key = key;
  cordova.exec(successCallback, errorCallback, 'BrookCarTrack', 'oneClickConnect', [options]);
};
BrookCarTrack.prototype.disconnect = function(successCallback, errorCallback) {
    var options = {};
    cordova.exec(successCallback, errorCallback, 'BrookCarTrack', 'disconnect', [options]);
  };


// Installation constructor that binds ToastyPlugin to window
BrookCarTrack.install = function() {
  if (!window.plugins) {
    window.plugins = {};
  }
  window.plugins.BrookCarTrack = new BrookCarTrack();
  return window.plugins.BrookCarTrack;
};
cordova.addConstructor(BrookCarTrack.install);
