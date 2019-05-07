var elem = document.getElementById('user');
if(elem){
    elem.value = '%s';
    document.getElementById('password').value = '%s';
    document.querySelector('#login_form .btn-login').click()
}