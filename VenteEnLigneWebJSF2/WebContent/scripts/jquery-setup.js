// This file is from the JSF 2.0 tutorials at 
// http://www.coreservlets.com/JSF-Tutorial/jsf2/

function highlightBenefits() {
  $("#benefits li").removeClass("red")
                   .removeClass("yellow")
                   .removeClass("green")
                   .each(setRandomStyle)
                   .fadeTo("slow", 0.333)
                   .fadeTo("slow", 1.0)
                   .fadeTo("slow", 0.333)
                   .fadeTo("slow", 1.0);
}

function setRandomStyle() {
  $(this).addClass(randomStyle());
}

function randomStyle() {
  var styles = ["red", "yellow", "green"];
  return(randomElement(styles));
}

function randomElement(array) {
  var index = Math.floor(Math.random()*array.length);
  return(array[index]);
}

$(function() {
    $("#highlight-button").click(highlightBenefits);
});