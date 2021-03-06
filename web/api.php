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

define('DIR_DATA', __DIR__. '/data');
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

$app->view(new \JsonApiView());
$app->add(new \JsonApiMiddleware());

/* Users */

$app->get('/users/', function () use ($app) 
{
    $response =  array(
            'success' => true,
            'path' => FILE_USER,
            'filename' => basename(FILE_USER)
    );
    
    $app->render(200, $response);
});

// User

$app->get('/user/login/:login/:password/', function ($login, $password) use ($app) {
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

    $app->render(200, $response);
});

$app->get('/user/logout/', function () use ($app) {
    User::logout();

    $app->render(200, array(
        'success' => true,
        'message' => 'You are now disconnected'
    ));
});

$app->get('/user/register/:login/:password/:firstname/:lastname/', function ($login, $password, $firsname, $lastname) use ($app) {
    $user = Users::add(FILE_USER, $login, $password, $firsname, $lastname);
    $user->login();

    $response = array(
        'success' => true,
        'message' => 'Welcome '.$user->firstname.' '.$user->lastname,
        'user' => $user
    );
    $app->render(200, $response);
});

$app->get('/delete/:login/', function ($login) use ($app) {
    User::stopRequestIfUserIsNotConnected();

    $response = Users::delete(FILE_USER, $login);
    $app->render(200, (array) $response);
});

// score

$app->get('/user/setScore/:score/', function ($score) use ($app) {
    
    $userLogin = $_SESSION['user'];
    $success = false;
    if( isset( $userLogin ))
    {
        $user = Users::getUserWithLogin(FILE_USER,$userLogin);
        if($user != false)
        {
            Users::update(FILE_USER,$userLogin,array("score" => $score));
            $success = true;
        }
    }
     
    $response = array(
        'success' => $success,
    );
    $app->render(200, $response);
});

$app->get('/user/addScore/:score/', function ($score) use ($app) {
    
    $userLogin = $_SESSION['user'];
    $success = false;
    if( isset( $userLogin ))
    {
        $user = Users::getUserWithLogin(FILE_USER,$userLogin);
        if($user != false)
        {
            $value = intval($user->score) + intval($score);
            Users::update(FILE_USER,$userLogin,array("score" => $value));
            $success = true;
        }
    }
     
    $response = array(
        'success' => $success,
    );
    $app->render(200, $response);
});

/* Stories */
$app->get('/stories/xml/', function () use ($app) {
    User::stopRequestIfUserIsNotConnected();

    $response = array();
    $response['path'] = 'data/stories/';
    $response['stories'] = Stories::getAll(DIR_STORIES, true);
    $app->render(200, (array) $response);
});

$app->get('/stories/', function () use ($app) {
    User::stopRequestIfUserIsNotConnected();

    $response = Stories::getAll(DIR_STORIES);
    $app->render(200, (array) $response);
});

$app->get('/stories/:id/', function ($id) use ($app) {
    User::stopRequestIfUserIsNotConnected();

    $response = Stories::get(DIR_STORIES, $id);
    $app->render(200, (array) $response);
});

$app->delete('/stories/:id/', function ($id) use ($app) {
    User::stopRequestIfUserIsNotConnected();

    $response = Stories::delete(DIR_STORIES, $id);
    $app->render(200, (array) $response);
});

$app->post('/stories/', function () use($app) {
    User::stopRequestIfUserIsNotConnected();
    $data = $app->request->post();

    $story = new Story;
    $story->mapToArray($data);
    Stories::save(DIR_STORIES, $story);
    $app->render(200, (array) $story);
});

$app->put('/stories/:id/', function ($id) use($app) {
    User::stopRequestIfUserIsNotConnected();
    $data = $app->request()->put();
    
    $response = Stories::update(DIR_STORIES, $id, $data);
    $app->render(200, (array) $response);
});

$app->run();    
