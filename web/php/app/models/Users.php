<?php
namespace App\Models;
use App\Models\User;

class Users
{
    public static function validateUsers($file)
    {
        //
    }

    // Get all users
    public static function getAll($file)
    {
        return Users::xmlToUsers($file);
    }

    public static function getUserWithLogin($file, $login)
    {
        $users = self::getAll($file);
        
        foreach ($users as $user)
        {
            if($user->login == $login)
                return $user;
        }
        
        return false;
    }
	
	public static function getUserWithLoginAndPassword($file, $login, $password)
    {
        $user = Users::getUserWithLogin($file,$login);
        
		if (!$user || $user->password != $password)
			return false;
		
        return $user;
    }
	
	public static function add($file, $login,$password,$firstname,$lastname)
    {
        $users = self::getAll($file);
        
        foreach ($users as $user)
        {
            if($user->login == $login)
            {
                //login already used
				return false;
            }
        }
		
		$newUser = new User();
		
		$newUser->login = $login;
		$newUser->password = $password;
		$newUser->firstname = $firstname;
		$newUser->lastname = $lastname;
		
		$users[] = $newUser;
        
        self::save($file,$users);
		
		return $newUser;
    }

    public static function delete($file, $login)
    {
        $users = self::getAll($file);
        $result = array();
        foreach ($users as $user)
        {
            if($user->login != $login)
            {
                $result[] = $user;
            }
        }
        
        self::save($file,$result);
    }

    public static function update($file, $login, $data)
    {
        $users = self::getAll($file);
        foreach($users as $user)
        {
            if($user->login == $login)
            {
                $user->mapToArray($data);
                self::save($file,$users);
                return $user;
            }
        }
        
        return false;
    }
    
    public static function save($file,array $users)
    {
        return file_put_contents($file, self::usersToXml($users));
    }

    public static function xmlToUsers($file)
    {
        $xml = simplexml_load_file($file);
        $usersXML = $xml->children();
        $users = array();

        foreach($usersXML as $userXML)
        {
            $userClass = new User();            
            $userClass->login = $userXML->login->__toString(); 
            $userClass->password = $userXML->password->__toString(); 
            $userClass->firstname = $userXML->firstname->__toString(); 
            $userClass->lastname = $userXML->lastname->__toString();   
            $userClass->stories = $userXML->stories->children();
            
            $users[] = $userClass;
        }

        return $users;
    }

    public static function usersToXml(array $users)
    {
        $xml = new SimpleXMLElement("<users></users>");
        
        foreach($users as $user)
        {
            $userXML = $xml->addChild('user');
            $userXML->addChild('login',$user->login);
            $userXML->addChild('password',$user->password);
            $userXML->addChild('firstname',$user->firstname);
            $userXML->addChild('lastname',$user->lastname);
            $storiesXML = $userXML->addChild("stories");
            
            foreach($user->stories as $storyId)
            {
                $storiesXML->addChild("story", $storyId);
            }
        }

        return $xml->asXML();
    }
}