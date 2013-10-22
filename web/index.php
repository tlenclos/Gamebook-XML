<?php
/* TODO
- XML Schemas
- XML Parser
- Basic CRUD
- User register & login
- ACL
*/

require 'vendor/autoload.php';
$app = new \Slim\Slim();

$app->get('/', function () {
    echo 'Home';
});

$app->get('/login', function () {
    echo 'Login';
});

$app->get('/register', function () {
    echo 'Register';
});

$app->get('/stories', function () {
    echo 'Stories';
});

$app->get('/stories/:id', function () {
    echo 'Single story';
});

$app->run();