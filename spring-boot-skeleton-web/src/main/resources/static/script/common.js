$(document).ready(function () {
  console.info("어서와");

  $("#locales").change(function () {
    var selectedOption = $('#locales').val();
    if (selectedOption != "") {
      document.cookie="lang="+selectedOption;
      document.location.reload();
    }
  });
});