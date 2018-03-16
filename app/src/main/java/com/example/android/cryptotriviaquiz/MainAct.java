package com.example.android.cryptotriviaquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainAct extends AppCompatActivity {
    private final int QUESTIONCOUNT = 5;
    private ArrayList<Question> totalQuestionList;
    private ArrayList<Question> quizQuestionList;
    private int score = 0;
    private int qCount = 0;
    private Question currentQuestion;
    ImageView img;
    TextView qHeader;
    Button a1Button;
    Button a2Button;
    Button a3Button;
    Button a4Button;
    TextView scoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //load views in variables
        img = findViewById(R.id.qImage);
        qHeader = findViewById(R.id.qHeader);
        a1Button = findViewById(R.id.a1Button);
        a2Button = findViewById(R.id.a2Button);
        a3Button = findViewById(R.id.a3Button);
        a4Button = findViewById(R.id.a4Button);
        scoreView = findViewById(R.id.scoreText);

        //create 10 question, randomly pick 5 (QUESTIONCOUNT) later
        createQuestionList();

        //randomly pick 5 questions
        selectQuestions(QUESTIONCOUNT);

        //set score to 0
        updateScore();

        //Load question in UI
        nextQuestion();

    }

    private void extraQuestions() {
        //#TODO FIX THIS LAYOUT MESS
        //local variable masterLayout;
        LinearLayout masterLayout = (LinearLayout) img.getParent();

        //replace qHeader text
        qHeader.setText(getResources().getString(R.string.extra_questions));

        //remove ImageView, answerView & scoreText
        masterLayout.removeView(img);
        masterLayout.removeView(findViewById(R.id.answerView));
        masterLayout.removeView(findViewById(R.id.scoreText));

        //create int margin with value = 16dp
        int marginValue = 8;
        float d = getResources().getDisplayMetrics().density;
        int margin = (int) (marginValue*d) ;

        //create ScrollView id questionScroll containing the 3 questions
        NestedScrollView scrollView = new NestedScrollView(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0, 8);
        scrollView.setFillViewport(true);
        masterLayout.addView(scrollView,lp);

       //create parent Layout for questions
        LinearLayout parentLayout = new LinearLayout(this);
        LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        parentLayout.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(parentLayout, rlp);

        //create LinearLayout for question 1
        LinearLayout newLayout = new LinearLayout(this);
        rlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
        newLayout.setId(R.id.q1Layout);
        rlp.setMargins(margin,margin,margin,0);
        newLayout.setLayoutParams(rlp);

        //add layout to masterLayout
        parentLayout.addView(newLayout);

        //update newLayout for question 2, reuse lp
        newLayout = new LinearLayout(this);
        newLayout.setId(R.id.q2Layout);
        rlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
        rlp.setMargins(margin, 0,margin,0);
        newLayout.setLayoutParams(rlp);
        //add layout to masterLayout
        parentLayout.addView(newLayout,rlp);

        //update newLayout for question 3
        newLayout = new LinearLayout(this);
        newLayout.setId(R.id.q3Layout);
        rlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
        rlp.setMargins(margin,margin, 0,margin*2);
        newLayout.setLayoutParams(rlp);

        //add layout to masterLayout
        parentLayout.addView(newLayout,rlp);

        //#TODO add extra question data to views

        //create Button to submit answers (added to masterLayout)
        Button submitButton = new Button(this);
        submitButton.setId(R.id.submitButton);
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1);

        submitButton.setText(getResources().getString(R.string.submit_button));
        submitButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        //add onClickListener, calls to check & submit answers
        submitButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                submitAnswers();
            }
        });

        masterLayout.addView(submitButton, lp);
    }

    private void submitAnswers() {
        //#TODO add code to check & submit answers
    };

    private void endgame() {
        //#TODO FIX THIS LAYOUT MESS
        //convert padding pixels to dp
        int paddingValue = 16;
        float d = getResources().getDisplayMetrics().density;
        int padding = (int) (paddingValue*d);

        //Clear answerButton Layout,
        LinearLayout layout = findViewById(R.id.answerView);
        layout.removeAllViewsInLayout();

        //add Scrollview and LinearLayout child
        ScrollView scrollView = new ScrollView(this);
        ScrollView.LayoutParams scrollParams = new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT,ScrollView.LayoutParams.WRAP_CONTENT);
        scrollView.setLayoutParams(scrollParams);
        layout.addView(scrollView);

        RelativeLayout buttonView = new RelativeLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(padding,padding,padding,padding);
        buttonView.setLayoutParams(lp);
        scrollView.addView(buttonView);

        //add 'play again' button
        Button againButton = new Button(this);
        againButton.setText(getResources().getString(R.string.play_again));
        againButton.setId(R.id.againButton);
        lp =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        
        againButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        againButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        buttonView.addView(againButton, lp);

        //add 'quit app' button
        Button endButton = new Button(this);
        endButton.setText(getResources().getString(R.string.exit_app));
        endButton.setId(R.id.quitButton);
        lp =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, padding ,0,0);
        endButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        endButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        buttonView.addView(endButton, lp);

        //add 'extra questions' button
        Button extraButton = new Button(this);
        extraButton.setText(getResources().getString(R.string.extra_button));
        extraButton.setId(R.id.extraButton);
        extraButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        extraButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                extraQuestions();
            }
        });
        //reuse lp from endbutton
        buttonView.addView(extraButton, lp);


        extraButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        extraButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                extraQuestions();
            };
        });

        switch(score) {
            case 5:img.setImageResource(R.drawable.winner);
                qHeader.setText(getResources().getString(R.string.winner));
                break;
            case 0: img.setImageResource(R.drawable.gameover);
                qHeader.setText(getResources().getString(R.string.loser));
                break;
            default:
                img.setImageResource(R.drawable.trophy);
                qHeader.setText(getResources().getString(R.string.endgameText));
                break;
        }


    }

    public void validateAnswer (View v) {
        Button b = (Button) v;
        String ans = String.valueOf(b.getText());
        if (ans.equals(currentQuestion.getRightAnswer())) {
            Toast.makeText(this, R.string.answer_correct,Toast.LENGTH_SHORT).show();
            score++;
            updateScore();
        }
        else {
            Toast.makeText(this,R.string.answer_fault, Toast.LENGTH_SHORT).show();
        }
        qCount++;
        //check if all questions done
        if(qCount==5)
        {
            endgame();
        }
        else {
            nextQuestion();
        }
    }

    private void nextQuestion() {

        //Load question in variables
        currentQuestion = quizQuestionList.get(qCount);
        ArrayList<String> answers = currentQuestion.getAnswerArrayList();

        //Load variables onto UI
        img.setImageResource(currentQuestion.getImgSrc());
        qHeader.setText(currentQuestion.getQuestion());

        //generate random order for right answers
        int random;
        random = (int) (Math.random()*answers.size());
        a1Button.setText(answers.remove(random));
        random = (int) (Math.random()*answers.size());
        a2Button.setText(answers.remove(random));
        random = (int) (Math.random()*answers.size());
        a3Button.setText(answers.remove(random));
        a4Button.setText(answers.remove(0));
    }

    private void updateScore() {
        scoreView.setText("Score: ".concat(String.valueOf(score)));
    }

    private void selectQuestions(int amount) {
        quizQuestionList = new ArrayList<Question>();
        Question q;
        int random;
        for (int i=0; i < amount; i++) {
            random = (int) (Math.random()*totalQuestionList.size());
            q = totalQuestionList.remove(random);
            quizQuestionList.add(q);
        }
    }

    private void createQuestionList() {
        totalQuestionList = new ArrayList<>();
        String question;
        ArrayList<String> answerList;
        String rightAnswer;
        int imgSrc;

        //Question 1 (Binance)
        answerList = new ArrayList<>();
        question = getResources().getString(R.string.exchange_logo_question);
        answerList.add("Kraken");
        answerList.add("Binance");
        answerList.add("Cobinhood");
        answerList.add("Kucoin");
        rightAnswer = "Binance";
        imgSrc = R.drawable.binance;
        totalQuestionList.add(new Question(answerList,rightAnswer,imgSrc,question));

        //Question 2 (BTC)
        answerList = new ArrayList<>();
        question = getResources().getString(R.string.coin_logo_question);
        answerList.add("BTC");
        answerList.add("ETH");
        answerList.add("ETC");
        answerList.add("TRX");
        rightAnswer = "BTC";
        imgSrc = R.drawable.btc;
        totalQuestionList.add(new Question(answerList,rightAnswer,imgSrc,question));

        //Question 3(ENJ)
        answerList = new ArrayList<>();
        question = getResources().getString(R.string.coin_logo_question);
        answerList.add("ENG");
        answerList.add("ETH");
        answerList.add("ENJ");
        answerList.add("WTC");
        rightAnswer = "ENJ";
        imgSrc = R.drawable.enj;
        totalQuestionList.add(new Question(answerList,rightAnswer,imgSrc,question));

        //Question 4 (ETH)
        answerList = new ArrayList<>();
        question = getResources().getString(R.string.coin_logo_question);
        answerList.add("OST");
        answerList.add("ETH");
        answerList.add("IOTA");
        answerList.add("ADX");
        rightAnswer = "ETH";
        imgSrc = R.drawable.eth;
        totalQuestionList.add(new Question(answerList,rightAnswer,imgSrc,question));

        //Question 5 (ICX)
        answerList = new ArrayList<>();
        question = getResources().getString(R.string.coin_logo_question);
        answerList.add("DASH");
        answerList.add("AMB");
        answerList.add("QSP");
        answerList.add("ICX");
        rightAnswer = "ICX";
        imgSrc = R.drawable.icx;
        totalQuestionList.add(new Question(answerList,rightAnswer,imgSrc,question));

        //Question 6(IOTA)
        answerList = new ArrayList<>();
        question = getResources().getString(R.string.coin_logo_question);
        answerList.add("IOTA");
        answerList.add("BRD");
        answerList.add("BCC");
        answerList.add("XVG");
        rightAnswer = "IOTA";
        imgSrc = R.drawable.iota;
        totalQuestionList.add(new Question(answerList,rightAnswer,imgSrc,question));

        //Question 7 (NEO)
        answerList = new ArrayList<>();
        question = getResources().getString(R.string.coin_logo_question);
        answerList.add("LTC");
        answerList.add("CND");
        answerList.add("NCASH");
        answerList.add("NEO");
        rightAnswer = "NEO";
        imgSrc = R.drawable.neo;
        totalQuestionList.add(new Question(answerList,rightAnswer,imgSrc,question));

        //Question 8 (OMG)
        answerList = new ArrayList<>();
        question = getResources().getString(R.string.coin_logo_question);
        answerList.add("XRP");
        answerList.add("WTC");
        answerList.add("OMG");
        answerList.add("ICX");
        rightAnswer = "OMG";
        imgSrc = R.drawable.omg;
        totalQuestionList.add(new Question(answerList,rightAnswer,imgSrc,question));

        //Question 9 (TRX)
        answerList = new ArrayList<>();
        question = getResources().getString(R.string.coin_logo_question);
        answerList.add("TRX");
        answerList.add("BTC");
        answerList.add("GVT");
        answerList.add("BNB");
        rightAnswer = "TRX";
        imgSrc = R.drawable.trx;
        totalQuestionList.add(new Question(answerList,rightAnswer,imgSrc,question));

        //Question 10 (XRP)
        answerList = new ArrayList<>();
        question = getResources().getString(R.string.coin_logo_question);
        answerList.add("ADA");
        answerList.add("XRP");
        answerList.add("VEN");
        answerList.add("XMR");
        rightAnswer = "XRP";
        imgSrc = R.drawable.xrp;
        totalQuestionList.add(new Question(answerList,rightAnswer,imgSrc,question));


    }
}
