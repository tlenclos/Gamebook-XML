<?php
class Story {
    public $title = "";
    public $lang = "en";
    public $steps = array();
}

class Step {
    public $id = 0;
    public $description = "";
    public $question = "";
    public $choices = array();
}

class Stories {

    // Get all stories
    public static function getAll($dir) {
        $result = array();

        if ($handle = opendir($dir)) {
            while (false !== ($entry = readdir($handle))) {
                if ($entry != "." && $entry != "..") {
                    $story = Stories::xmlToStory($dir.'/'.$entry);
                    $result[] = $story;
                }
            }
            closedir($handle);
        }

        return $result;
    }

    public static function xmlToStory($file) {
        $xml = simplexml_load_file($file);
        $metas = (array) $xml->attributes();
        $metas = $metas['@attributes'];
        $stepsXML = $xml->steps->children();
        $steps = array();

        foreach($stepsXML as $step) {
            $stepClass = new Step();
            $stepClass->id = (int) $step->id;
            $stepClass->description =  $step->description->__toString();
            $stepClass->question =  $step->question->__toString();

            foreach($step->actions->children() as $action) {
                $gotostep = (array) $action->attributes();
                $gotostep = $gotostep['@attributes']['gotostep'];
                $stepClass->choices[$gotostep] = $action->__toString();
            }

            $steps[] = $stepClass;
        }

        return $steps;
    }

    public static function storyToXml(Story $data) {
        // TODO Create XML
    }
}