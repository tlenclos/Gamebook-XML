<?php

header('Content-type: text/html; charset=utf-8');

class XMLHelper {

    public static function validate_xml_file($dtdFilePath, $xmlFilePath, $rootTag) {
        $xml = file_get_contents($xmlFilePath);

        $old = new DOMDocument;
        $old->loadXML($xml);

        $creator = new DOMImplementation;
        $doctype = $creator->createDocumentType($rootTag, null, 'user.example.dtd');
        $new = $creator->createDocument(null, null, $doctype);
        $new->encoding = "utf-8";

        $oldNode = $old->getElementsByTagName($rootTag)->item(0);
        $newNode = $new->importNode($oldNode, true);
        $new->appendChild($newNode);

        return $new->validate();
    }

}

?>