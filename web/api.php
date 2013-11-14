<?php

/* TODO
  - User register & login
  - ACL
 */

// Config
error_reporting(E_ALL);
ini_set('display_errors', 1);

define('DIR_DATA', 'data');
define('DIR_STORIES', DIR_DATA . '/stories');
define('FILE_USER', DIR_DATA . '/users/users.xml');

require 'vendor/autoload.php';

use App\Models\Stories;
use App\Models\Story;
use App\Models\Users;
use App\Models\User;

// App
$app = new \Slim\Slim();
$app->response->headers->set('Content-Type', 'application/json');

$app->get('/users/', function () {
    try {
        $response = Users::getAll(FILE_USER);
    } catch (Exception $e) {
        $response = array(
            'success' => false,
            'error' => $e->getMessage()
        );
    }

    echo json_encode($response);
});

$app->get('/login/:login/:password/', function ($login, $password) {
    try {
        $response = Users::getUserWithLoginAndPassword(FILE_USER, $login, $password);
    } catch (Exception $e) {
        $response = array(
            'success' => false,
            'error' => $e->getMessage()
        );
    }

    echo json_encode($response);
});

$app->get('/register/:login/:password/:firstname/:lastname/', function ($login, $password, $firsname, $lastname) {
    try {
        $response = Users::add(FILE_USER, $login, $password, $firsname, $lastname);
    } catch (Exception $e) {
        $response = array(
            'success' => false,
            'error' => $e->getMessage()
        );
    }

    echo json_encode($response);
});

$app->get('/delete/:login/', function ($login) {
    try {
        $response = Users::delete(FILE_USER, $login);
    } catch (Exception $e) {
        $response = array(
            'success' => false,
            'error' => $e->getMessage()
        );
    }

    echo json_encode($response);
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

$app->post('/stories/', function () use($app) {
    $data = $app->request->post();

    try {
        $story = new Story;
        $story->mapToArray($data);
        Stories::save(DIR_STORIES, $story);
        $response = $story;
    } catch (Exception $e) {
        $response = array(
            'success' => false,
            'error' => $e->getMessage()
        );
    }

    echo json_encode($response);
});

$app->put('/stories/:id/', function ($id) use($app) {
    $data = $app->request()->put();

    try {
        $response = Stories::update(DIR_STORIES, $id, $data);
    } catch (Exception $e) {
        $response = array(
            'success' => false,
            'error' => $e->getMessage()
        );
    }

    echo json_encode($response);
});

$app->run();
