package com.example.settings.docType;

public class DocType {
    private String name;

    private String key;

    private DocCategory docCategory;

    public DocType(String name, String key, DocCategory docCategory) {
        this.name = name;
        this.key = key;
        this.docCategory = docCategory;
    }

    @Override
    public String toString(){
        return key + " - " + name + " - " + docCategory.toString();
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}
