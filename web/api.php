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
require 'app/models/stories.php'; // TODO autoload

// App
$app = new \Slim\Slim();
$app->response->headers->set('Content-Type', 'application/json');

$app->get('/login', function () {
    echo 'Login';
});

$app->get('/register', function () {
    echo 'Register';
});

$app->get('/stories/', function () {
    try {
        $response = Stories::getAll(DIR_STORIES);
    } catch (Exception $e) {
        $response = array(
            'success' => false,
            'error' => $e->getMessage()
        );
    }

    echo json_encode($response);
});

$app->get('/stories/:id/', function ($id) {
    try {
        $response = Stories::get(DIR_STORIES, $id);
    } catch (Exception $e) {
        $response = array(
            'success' => false,
            'error' => $e->getMessage()
        );
    }

    echo json_encode($response);
});

$app->delete('/stories/:id/', function ($id) {
    try {
        $response = Stories::delete(DIR_STORIES, $id);
    } catch (Exception $e) {
        $response = array(
            'success' => false,
            'error' => $e->getMessage()
        );
    }

    echo json_encode($response);
});

// TODO
$app->post('/stories/', function () use($app) {
    // TODO Create story object, map datas with form, validate
    $data = $app->request->post();
    try {
        $story = new Story;
        $story->mapToArray($data);
        var_dump(Stories::save($story)); exit;
    } catch (Exception $e) {
        $response = array(
            'success' => false,
            'error' => $e->getMessage()
        );
    }

    echo json_encode($response);
});

$app->run();