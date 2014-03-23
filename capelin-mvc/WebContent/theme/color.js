var curdate = new Date();
//var hours = curdate.getHours(); // 0-13
var minutes = curdate.getMinutes(); // 0-59
var color=["red","blue","green"];
var index = minutes % 3;
document.write('<link rel="stylesheet" type="text/css" href="' + capelinThemeBase +color[index] +'/color.css" />');
lyteboxTheme = color[index];