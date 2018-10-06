var exec = require("cordova/exec");

exports.coolMethod = function(arg0, success, error) {
    exec(success, error, "navigationbar", "coolMethod", [arg0]);
};
exports.create = function() {
    exec(function() {}, function() {}, "navigationbar", "create", [{}]);
};
exports.setBg = function(color) {
    exec(function() {}, function() {}, "navigationbar", "setBg", [color]);
};
exports.setGradientBg = function(color1, color2) {
    exec(function() {}, function() {}, "navigationbar", "setGradientBg", [
        color1,
        color2
    ]);
};
exports.setStyle = function(style) {
    exec(function() {}, function() {}, "navigationbar", "setStyle", [style]);
};
exports.setTitle = function(title) {
    exec(function() {}, function() {}, "navigationbar", "setTitle", [title]);
};
exports.showBack = function(title) {
    exec(function() {}, function() {}, "navigationbar", "showBack", [""]);
};
exports.hideBack = function(title) {
    exec(function() {}, function() {}, "navigationbar", "hideBack", [""]);
};
