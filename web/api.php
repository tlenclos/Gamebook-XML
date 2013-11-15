<?php

/* TODO
  - User register & login
  - Middleware
  - Refactor user session (bad code/concept due to schedule)
  - ACL
 */

// Config
error_reporting(E_ALL);
ini_set('display_errors', 1);
session_start();

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

/*
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
*/

$app->get('/user/login/:login/:password/', function ($login, $password) {
    try {
        $user = Users::getUserWithLoginAndPassword(FILE_USER, $login, $password);
        
        if ($user) {
            $user->login();
            $response =  array(
                'success' => true,
                'message' => 'Welcome '.$user->login
            );
        } else {
            $response =  array(
                'success' => false,
                'message' => 'Bad credentials'
            );
        }
    } catch (Exception $e) {
        $response = array(
            'success' => false,
            'message' => $e->getMessage()
        );
    }

    echo json_encode($response);
});

$app->get('/user/logout/', function () {
    User::logout();
    echo json_encode(array(
        'success' => true,
        'message' => 'You are now disconnected'
    ));
});

$app->get('/user/register/:login/:password/:firstname/:lastname/', function ($login, $password, $firsname, $lastname) {
    try {
        $user = Users::add(FILE_USER, $login, $password, $firsname, $lastname);
        $user->login();
        
        $response = array(
            'success' => true,
            'message' => 'Welcome '.$user->firstname.' '.$user->lastname,
            'user' => $user
        );
    } catch (Exception $e) {
        $response = array(
            'success' => false,
            'error' => $e->getMessage()
        );
    }

    echo json_encode($response);
});

$app->get('/delete/:login/', function ($login) {
    User::stopRequestIfUserIsNotConnected();
    
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
    User::stopRequestIfUserIsNotConnected();
    
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
    User::stopRequestIfUserIsNotConnected();
    
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
    User::stopRequestIfUserIsNotConnected();
    
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
    User::stopRequestIfUserIsNotConnected();
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
    User::stopRequestIfUserIsNotConnected();
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
