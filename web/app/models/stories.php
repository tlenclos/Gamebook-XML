<?php
class Step {
    public $id = 0;
    public $description = "";
    public $question = "";
    public $choices = array();
}

class Story {
    public $id = "";
    public $title = "";
    public $lang = "en";
    public $steps = array();

    public function mapToArray(array $data) {
        foreach($data as $k=>$v) {
            if (property_exists(get_class($this), $k)) {
                $this->$k = $v;
            }
        }

        $steps = $data['steps'];
        if (!empty($steps) && is_array($steps)) {
            $this->steps = array();

            foreach($steps as $stepData) {
                $step = new Step;
                !empty($stepData['id']) && $step->id = $stepData['id'];
                !empty($stepData['description']) && $step->description = $stepData['description'];
                !empty($stepData['question']) && $step->question = $stepData['question'];
                !empty($stepData['choices']) && $step->choices = $stepData['choices'];
                $this->steps[] = $step;
            }
        }
    }
}

class Stories {

    public static function validateStory($file) {}

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

    public static function get($dir, $id) {
        return Stories::xmlToStory($dir.'/'.$id.'.xml');
    }

    public static function delete($dir, $id) {
        return unlink($dir.'/'.$id.'.xml');
    }

    public static function save($dir, Story $story) {
        $xmlString = self::storyToXml($story);
        return file_put_contents($dir.'/test.xml', $xmlString);
    }

    public static function xmlToStory($file) {
        $fileInfo = pathinfo($file);
        $xml = simplexml_load_file($file);
        $metas = (array) $xml->attributes();
        $metas = $metas['@attributes'];
        $story = new Story;
        $story->id = $fileInfo['filename'];
        $story->title = $metas['title'];
        $story->lang = $metas['lang'];
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

        $story->steps = $steps;
        return $story;
    }

    public static function storyToXml(Story $story) {
        $xml = new SimpleXMLElement("<game></game>");
        $xml->addAttribute('title', $story->title);
        $xml->addAttribute('lang', $story->lang);
        $steps = $xml->addChild('steps');

        foreach($story->steps as $storyStep) {
            $step = $steps->addChild('step');
            $step->addChild('id', $storyStep->id);
            $step->addChild('description', $storyStep->description);
            $step->addChild('question', $storyStep->question);

            $actions = $step->addChild('actions');
            foreach($storyStep->choices as $stepChoiceId => $stepChoiceText) {
                $choice = $actions->addChild('choice', $stepChoiceText);
                $choice->addAttribute('gotostop', $stepChoiceId);
            }
        }

        return $xml->asXML();
    }
}