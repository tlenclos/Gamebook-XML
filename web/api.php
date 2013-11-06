<?php
/* TODO
- XML Parser
- Basic CRUD
- User register & login
- ACL
*/

// Config
error_reporting(E_ALL);
ini_set('display_errors', 1);

define('DIR_DATA', 'data');
define('DIR_STORIES', DIR_DATA.'/stories');
define('DIR_USER', DIR_DATA.'/users');

require 'vendor/autoload.php';
require 'models/stories.php'; // TODO autoload

// App
$app = new \Slim\Slim();
$app->response->headers->set('Content-Type', 'application/json');

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
    try {
        $response = array(
            'success' => true,
            'stories' => Stories::getAll(DIR_STORIES)
        );
    } catch (Exception $e) {
        $response = array(
            'success' => false,
            'error' => $e
        );
    }

    echo json_encode($response);
});

$app->get('/stories/:id', function () {
    echo 'Single story';
});

$app->post('/stories', function ($data) {
    // TODO Create story object, map datas with form, validate
    $id = uniqid();
    $response = array(
        'success' => false
    );

    echo json_encode($response);
});

$app->run();