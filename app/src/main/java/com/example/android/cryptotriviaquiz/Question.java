package com.example.android.cryptotriviaquiz;

import java.util.ArrayList;

/**
 * Created by Jeroen on 3-3-2018.
 */

class Question {

    private ArrayList<String> answerArrayList;
    private int imgSrc;
    private String rightAnswer;
    private String question;

    public Question(ArrayList<String> answerlist, String rightAnswer, int imgSrc, String question) {
        setAnswerArrayList(answerlist);
        setRightAnswer(rightAnswer);
        setImgSrc(imgSrc);
        setQuestion(question);
    }

    private void setAnswerArrayList(ArrayList<String> answerList)
    {
        this.answerArrayList = answerList;
    }

    private void setRightAnswer(String answer) {
        this.rightAnswer = answer;
    }

    private void setImgSrc(int imgSrc) {
        this.imgSrc = imgSrc;
    }

    private void setQuestion(String q){
        this.question = q;
    }

    public String getQuestion() {
        return question;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public int getImgSrc() {
        return imgSrc;
    }

    public ArrayList<String> getAnswerArrayList() {
        return answerArrayList;
    }
}
