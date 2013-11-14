<?php
namespace App\Models;
use App\Models\Step;

class Story {
    public $id = "";
    public $title = "";
    public $lang = "en";
    public $steps = array();

    public function __construct() {
        $this->id = uniqid();
    }
    
    public function mapToArray(array $data) {
        foreach($data as $k=>$v) {
            if (property_exists(get_class($this), $k)) {
                $this->$k = $v;
            }
        }

        $steps = isset($data['steps']) ? $data['steps'] : null;
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