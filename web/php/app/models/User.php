<?php

namespace App\Models;

class User {

    public $login = "";
    public $password = "";
    public $firstname = "";
    public $lastname = "";
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

}
