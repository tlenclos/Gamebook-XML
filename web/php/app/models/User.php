<?php

namespace App\Models;

class User {

    public $login = "";
    public $password = "";
    public $firstname = "";
    public $lastname = "";
    public $score = "0";
    public $stories = array();

    public function mapToArray(array $data) {
        foreach ($data as $k => $v) {
            if (property_exists(get_class($this), $k)) {
                $this->$k = $v;
            }
        }

        $stories = isset($data['stories']) ? $data['stories'] : null;
        if (!empty($stories) && is_array($stories)) {
            $this->stories = array();

            foreach ($stories as $storyId) {
                $this->stories[] = $storyId;
            }
        }
    }
    
    // User session
    public function login() {
        $_SESSION['user'] = $this->login;
    }
    
    public static function isLogged() {
        return !empty($_SESSION['user']);
    }
    
    public static function stopRequestIfUserIsNotConnected() {
        if (!self::isLogged()) {
            echo json_encode(array(
                'success' => false,
                'message' => 'You must be logged to access this resource'
            ));
            exit;
        }
    }
    
    public static function logout() {
        unset($_SESSION['user']);
    }

}
