<?php

namespace App\Models;

use App\Models\Step;
use App\Models\Story;

class Stories {

    public static function validateStory($file) {
        
    }

    // Get all stories
    public static function getAll($dir) {
        $result = array();

        if ($handle = opendir($dir)) {
            while (false !== ($entry = readdir($handle))) {
                if ($entry != "." && $entry != "..") {
                    $story = Stories::xmlToStory($dir . '/' . $entry);
                    $result[] = $story;
                }
            }
            closedir($handle);
        }

        return $result;
    }

    public static function get($dir, $id) {
        return Stories::xmlToStory($dir . '/' . $id . '.xml');
    }

    public static function delete($dir, $id) {
        return unlink($dir . '/' . $id . '.xml');
    }

    public static function update($dir, $id, $data) {
        $story = self::get($dir, $id);
        $story->mapToArray($data);
        self::save($dir, $story);
        return $story;
    }

    public static function save($dir, Story $story) {
        $path = $dir . '/' . $story->id . '.xml';
        return file_put_contents($path, self::storyToXml($story));
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

        foreach ($stepsXML as $step) {
            $stepClass = new Step();
            $stepClass->id = (int) $step->id;
            $stepClass->description = $step->description->__toString();
            $stepClass->question = $step->question->__toString();

            foreach ($step->actions->children() as $action) {
                $gotostep = (array) $action->attributes();
                if (isset($gotostep['@attributes']['gotostep'])) {
                    $gotostep = $gotostep['@attributes']['gotostep'];
                    $stepClass->choices[$gotostep] = $action->__toString();
                }
            }

            $steps[] = $stepClass;
        }

        $story->steps = $steps;
        return $story;
    }

    public static function storyToXml(Story $story) {
        $xml = new \SimpleXMLElement("<game></game>");
        $xml->addAttribute('title', $story->title);
        $xml->addAttribute('lang', $story->lang);
        $steps = $xml->addChild('steps');

        foreach ($story->steps as $storyStep) {
            $step = $steps->addChild('step');
            $step->addChild('id', $storyStep->id);
            $step->addChild('description', $storyStep->description);
            $step->addChild('question', $storyStep->question);

            $actions = $step->addChild('actions');
            foreach ($storyStep->choices as $stepChoiceId => $stepChoiceText) {
                $choice = $actions->addChild('choice', $stepChoiceText);
                $choice->addAttribute('gotostep', $stepChoiceId);
            }
        }

        return $xml->asXML();
    }

}
