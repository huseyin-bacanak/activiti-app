/**
 * Created by raven on 01.03.2015.
 */
$(document).ready(function() {
    loadContent();
});

function loadContent() {
    $.ajax({
       url: "http://localhost:8080/web/ajax/item",
       cache: false,
       beforeSend: function () {
           $('#image').show();
       },
       complete: function () {
           $('#image').hide();
       },
       success: function (html) {
           $('#content').html(html);
       }
   });
}
