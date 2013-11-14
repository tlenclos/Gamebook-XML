<?php
namespace App\Models;

class User
{
    public $login = "";
    public $password = "";
    public $firstname = "";
    public $lastname = "";
    
    public function mapToArray(array $data)
    {
        foreach($data as $k=>$v)
        {
            if (property_exists(get_class($this), $k))
            {
                $this->$k = $v;
            }
        }
    }
}