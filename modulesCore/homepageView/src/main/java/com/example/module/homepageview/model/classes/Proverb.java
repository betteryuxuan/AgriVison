package com.example.module.homepageview.model.classes;

public class Proverb {
    private String proverb;
    private String meaning;

    public Proverb(String proverb, String meaning) {
        this.proverb = proverb;
        this.meaning = meaning;
    }

    public Proverb() {
    }

    public String getProverb() {
        return proverb;
    }

    public void setProverb(String proverb) {
        this.proverb = proverb;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
}
