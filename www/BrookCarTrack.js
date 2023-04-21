// Empty constructor
function BrookCarTrack() {}

// The function that passes work along to native shells
// Message is a string, duration may be 'long' or 'short'
BrookCarTrack.prototype.show = function(message, duration, successCallback, errorCallback) {
  var options = {};
  options.message = message;
  options.duration = duration;
  cordova.exec(successCallback, errorCallback, 'BrookCarTrack', 'show', [options]);
}

// Installation constructor that binds ToastyPlugin to window
BrookCarTrack.install = function() {
  if (!window.plugins) {
    window.plugins = {};
  }
  window.plugins.BrookCarTrack = new BrookCarTrack();
  return window.plugins.BrookCarTrack;
};
cordova.addConstructor(BrookCarTrack.install);