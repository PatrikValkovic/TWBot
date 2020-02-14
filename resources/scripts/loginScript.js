(function(){
    var username = '%s';
    var password = '%s';

    var usernameField = document.getElementById('user');
    var passwordField = document.getElementById('password');
    usernameField.value = username;
    passwordField.value = password;
    document.getElementsByClassName('btn-login')[0].click();
})();