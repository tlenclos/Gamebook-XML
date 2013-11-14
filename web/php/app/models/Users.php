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
    public static function getAll($dir)
    {
        $result = array();

        if ($handle = opendir($dir))
        {
            while (false !== ($entry = readdir($handle)))
            {
                
                if (strpos($entry,'users') !== false)
                {
                    $result = Users::xmlToUser($dir.'/'.$entry);
                    return $result;
                }
            }
            closedir($handle);
        }

        return $result;
    }

    public static function getUserWithLogin($dir, $login)
    {
        $users = self::getAll($dir);
        
        foreach ($users as $user)
        {
            if($user->login == $login)
                return $user;
        }
        
        return false;
    }

    public static function delete($dir, $login)
    {
        $users = self::getAll($dir);
        $result = array();
        foreach ($users as $user)
        {
            if($user->login != $login)
            {
                $result[] = $user;
            }
        }
        
        self::save($dir,$result);
    }

    public static function update($dir, $login, $data)
    {
        $users = self::getAll($dir);
        foreach($users as $user)
        {
            if($user->login == $login)
            {
                $user->mapToArray($data);
                self::save($dir,$users);
                return $user;
            }
        }
        
        return false;
    }
    
    public static function save($dir,array $users)
    {
        $path = $dir.'/users.xml';
        return file_put_contents($path, self::usersToXml($users));
    }

    public static function xmlToUsers($file)
    {
        $xml = simplexml_load_file($file);
        $usersXML = $xml->users->children();
        $users = array();

        foreach($usersXML as $userXML)
        {
            $userClass = new User();
            $userClass->id = (int) $userXML->id;
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
        $xml = new \SimpleXMLElement("<users></users>");
        
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