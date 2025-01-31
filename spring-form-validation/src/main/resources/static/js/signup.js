$(document).ready(function() {
    $("#signupForm").on("submit", function(event) {
        event.preventDefault();

        const data = {
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        };

        $.ajax({
            url: '/register',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function(response) {
                console.log(response);
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
            }
        });
    });
});