package com.example.android.cryptotriviaquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainAct extends AppCompatActivity {
    private final int QUESTIONCOUNT = 5;
    ImageView img;
    TextView qHeader;
    Button a1Button;
    Button a2Button;
    Button a3Button;
    Button a4Button;
    TextView scoreView;
    private ArrayList<Question> totalQuestionList;
    private ArrayList<Question> quizQuestionList;
    private int score;
    private int qCount;
    private Question currentQuestion;

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

        //create int margin with value = 8;
        int marginValue = 8;
        float d = getResources().getDisplayMetrics().density;
        int margin = (int) (marginValue * d);

        //local variable masterLayout;
        LinearLayout masterLayout = (LinearLayout) img.getParent();
        masterLayout.setPadding(margin, margin, margin, margin);

        //replace qHeader text
        qHeader.setText(getResources().getString(R.string.extra_questions));

        //remove ImageView, answerView & scoreText
        masterLayout.removeView(img);
        masterLayout.removeView(findViewById(R.id.answerView));
        masterLayout.removeView(findViewById(R.id.scoreText));

        //create ScrollView id questionScroll containing the 3 questions
        NestedScrollView scrollView = new NestedScrollView(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 8);
        scrollView.setFillViewport(true);
        masterLayout.addView(scrollView, lp);

        //create parent Layout for questions
        LinearLayout parentLayout = new LinearLayout(this);
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        parentLayout.setOrientation(LinearLayout.VERTICAL);
        parentLayout.setPadding(margin,0,margin,0);
        scrollView.addView(parentLayout, lp);

        //create LinearLayout for question 1
        LinearLayout newLayout = new LinearLayout(this);
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        newLayout.setOrientation(LinearLayout.VERTICAL);
        newLayout.setId(R.id.q1Layout);
        newLayout.setLayoutParams(lp);

        //add TextView with question
        TextView q1 = new TextView(this);
        q1.setId(R.id.extraQ1);
        q1.setTextSize(getResources().getDimension(R.dimen.questionSize));
        q1.setText(R.string.extraQ1);
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        newLayout.addView(q1, lp);

        //add EditText with answer
        EditText a1 = new EditText(this);
        a1.setId(R.id.extraA1);
        a1.setTextSize(getResources().getDimension(R.dimen.answerSize));
        a1.setHint(R.string.answerHint);
        newLayout.addView(a1, lp);

        //add layout to masterLayout
        parentLayout.addView(newLayout);

        //update newLayout for question 2, reuse lp
        newLayout = new LinearLayout(this);
        newLayout.setId(R.id.q2Layout);
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        newLayout.setPadding(0,margin,0,0);
        newLayout.setOrientation(LinearLayout.VERTICAL);
        newLayout.setLayoutParams(lp);
        parentLayout.addView(newLayout);

        //add TextView with question
        TextView q2 = new TextView(this);
        q2.setId(R.id.extraQ2);
        q2.setTextSize(getResources().getDimension(R.dimen.questionSize));
        q2.setText(R.string.extraQ2);
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        newLayout.addView(q2, lp);

        //add RadioGroup
        RadioGroup radioGroup = new RadioGroup(this);
        RadioGroup.LayoutParams radioParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        radioGroup.setPadding(0,margin/2,0,0);
        newLayout.addView(radioGroup, radioParams);

        //add button 1
        RadioButton newButton = new RadioButton(this);
        newButton.setId(R.id.extraA2B1);
        newButton.setText(getResources().getString(R.string.extra_notThis));
        newButton.setTextSize(getResources().getDimension(R.dimen.answerSize));
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        radioGroup.addView(newButton, lp);

        //add button 2
        newButton = new RadioButton(this);
        newButton.setId(R.id.extraA2B2);
        newButton.setText(getResources().getString(R.string.extra_notThis));
        newButton.setTextSize(getResources().getDimension(R.dimen.answerSize));
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        radioGroup.addView(newButton, lp);

        //add button 3
        newButton = new RadioButton(this);
        newButton.setId(R.id.extraA2B3);
        newButton.setText(getResources().getString(R.string.extra_thisOne));
        newButton.setTextSize(getResources().getDimension(R.dimen.answerSize));
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        radioGroup.addView(newButton, lp);

        //add button 4
        newButton = new RadioButton(this);
        newButton.setId(R.id.extraA2B4);
        newButton.setText(getResources().getString(R.string.extra_notThis));
        newButton.setTextSize(getResources().getDimension(R.dimen.answerSize));
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
          radioGroup.addView(newButton, lp);

        //update newLayout for question 3
        newLayout = new LinearLayout(this);
        newLayout.setId(R.id.q3Layout);
        newLayout.setPadding(0,margin,0,0);
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //lp.setMargins(0,margin,0,0);
        newLayout.setOrientation(LinearLayout.VERTICAL);
        newLayout.setLayoutParams(lp);

        //create TextView with 3rd question
        TextView q3 = new TextView(this);
        q3.setId(R.id.extraQ3);
        q3.setText(getResources().getString(R.string.extraQ3));
        q3.setTextSize(getResources().getDimension(R.dimen.questionSize));
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        newLayout.addView(q3);

        //add checkbox 1
        CheckBox answer = new CheckBox(this);
        answer.setPadding(0,margin/2,0,0);
        answer.setId(R.id.extraA3B1);
        answer.setText(getResources().getString(R.string.extra_thisOne));
        answer.setTextSize(getResources().getDimension(R.dimen.answerSize));
        newLayout.addView(answer);

        //add checkbox 2
        answer = new CheckBox(this);
        answer.setId(R.id.extraA3B2);
        answer.setText(getResources().getString(R.string.extra_thisOne));
        answer.setTextSize(getResources().getDimension(R.dimen.answerSize));
        newLayout.addView(answer);

        //add checkbox 3
        answer = new CheckBox(this);
        answer.setId(R.id.extraA3B3);
        answer.setText(getResources().getString(R.string.extra_thisOne));
        answer.setTextSize(getResources().getDimension(R.dimen.answerSize));
        newLayout.addView(answer);

        answer = new CheckBox(this);
        answer.setId(R.id.extraA3B4);
        answer.setText(getResources().getString(R.string.extra_andThisOne));
        answer.setTextSize(getResources().getDimension(R.dimen.answerSize));
        newLayout.addView(answer);

        //add layout to parentLayout
        parentLayout.addView(newLayout, lp);

        //add button to restart app
        Button restartButton = new Button(this);
        restartButton.setText(getResources().getString(R.string.play_again));
        restartButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                restartApp();
            }
        });
        parentLayout.addView(restartButton, lp);

        //add button to quit app
        Button quitButton = new Button(this);
        quitButton.setText(getResources().getString(R.string.exit_app));
        quitButton.setOnClickListener( new Button.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        parentLayout.addView(quitButton, lp);

        //create Button to submit answers (added to masterLayout)
        Button submitButton = new Button(this);
        submitButton.setId(R.id.submitButton);
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);

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
        //Check Q1, right answer = Johnny
        String rightAnswer1 = "johnny";
        int extraScore = 0;
        ArrayList<Integer> correctScores = new ArrayList<>();

        EditText answer1 = findViewById(R.id.extraA1);
        String answer = answer1.getText().toString().trim().toLowerCase();
        TextView question;
        question = findViewById(R.id.extraQ1);
        if(answer.equals(rightAnswer1)) {
            extraScore++;
            correctScores.add(R.id.extraQ1);
            question.setTextColor(getResources().getColor(R.color.correctAnswer));
        } else
        {
            question.setTextColor(getResources().getColor(R.color.incorrectAnswer));
        }

        //Check Q2, right answer = radiobox 3
        question = findViewById(R.id.extraQ2);
        RadioButton answer2 = findViewById(R.id.extraA2B3);
        if (answer2.isChecked()) {
             extraScore++;
             correctScores.add(R.id.extraQ2);
             question.setTextColor(getResources().getColor(R.color.correctAnswer));
        } else {
            question.setTextColor(getResources().getColor(R.color.incorrectAnswer));
        }

        //check Q3, right answer = everything checked
        question = findViewById(R.id.extraQ3);
        CheckBox answer3b1;
        CheckBox answer3b2;
        CheckBox answer3b3;
        CheckBox answer3b4;

        answer3b1 = findViewById(R.id.extraA3B1);
        answer3b2 = findViewById(R.id.extraA3B2);
        answer3b3 = findViewById(R.id.extraA3B3);
        answer3b4 = findViewById(R.id.extraA3B4);

        if (answer3b1.isChecked() && answer3b2.isChecked() && answer3b3.isChecked() && answer3b4.isChecked()) {
            extraScore++;
            correctScores.add(R.id.extraQ3);
            question.setTextColor(getResources().getColor(R.color.correctAnswer));
        } else {
            question.setTextColor(getResources().getColor(R.color.incorrectAnswer));
        }
        String toastText = getResources().getString(R.string.extraEndScore, extraScore);
        Toast.makeText(this,toastText,Toast.LENGTH_LONG).show();



        //#TODO add code to check & submit answers

    }

    private void endgame() {
        //convert padding pixels to dp
        int paddingValue = 16;
        float d = getResources().getDisplayMetrics().density;
        int padding = (int) (paddingValue * d);

        //Clear answerButton Layout,
        LinearLayout layout = findViewById(R.id.answerView);
        layout.removeAllViewsInLayout();
        layout.setPadding(padding, padding, padding, padding);


        //add Scrollview and LinearLayout child
        NestedScrollView scrollView = new NestedScrollView(this);
        NestedScrollView.LayoutParams scrollParams = new NestedScrollView.LayoutParams(NestedScrollView.LayoutParams.MATCH_PARENT, NestedScrollView.LayoutParams.MATCH_PARENT);
        scrollView.setFillViewport(true);
        scrollView.setLayoutParams(scrollParams);
        layout.addView(scrollView);

        LinearLayout buttonView = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonView.setOrientation(LinearLayout.VERTICAL);
        buttonView.setLayoutParams(lp);
        scrollView.addView(buttonView);

        //add 'play again' button
        Button againButton = new Button(this);
        againButton.setText(getResources().getString(R.string.play_again));
        lp = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        againButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        againButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                restartApp();
            }
        });
        buttonView.addView(againButton, 0, lp);

        //add 'quit app' button
        Button endButton = new Button(this);
        endButton.setText(getResources().getString(R.string.exit_app));
        lp.setMargins(0, padding, 0, 0);
        endButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        endButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        buttonView.addView(endButton, 1, lp);

        //add 'extra questions' button
        Button extraButton = new Button(this);
        extraButton.setText(getResources().getString(R.string.extra_button));
        extraButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        extraButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                extraQuestions();
            }
        });
        extraButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        extraButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                extraQuestions();
            }
        });

        switch (score) {
            case 5:
                img.setImageResource(R.drawable.winner);
                qHeader.setText(getResources().getString(R.string.winner));
                break;
            case 0:
                img.setImageResource(R.drawable.gameover);
                qHeader.setText(getResources().getString(R.string.loser));
                break;
            default:
                img.setImageResource(R.drawable.trophy);
                qHeader.setText(getResources().getString(R.string.endgameText));
                break;
        }
        buttonView.addView(extraButton, 2, lp);
    }

    public void validateAnswer(View v) {
        Button b = (Button) v;
        String ans = String.valueOf(b.getText());
        if (ans.equals(currentQuestion.getRightAnswer())) {
            Toast.makeText(this, R.string.answer_correct, Toast.LENGTH_SHORT).show();
            score++;
            updateScore();
        } else {
            Toast.makeText(this, R.string.answer_fault, Toast.LENGTH_SHORT).show();
        }
        qCount++;
        //check if all questions done
        if (qCount == 5) {
            endgame();
        } else {
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
        random = (int) (Math.random() * answers.size());
        a1Button.setText(answers.remove(random));
        random = (int) (Math.random() * answers.size());
        a2Button.setText(answers.remove(random));
        random = (int) (Math.random() * answers.size());
        a3Button.setText(answers.remove(random));
        a4Button.setText(answers.remove(0));
    }

    private void updateScore() {
        scoreView.setText("Score: ".concat(String.valueOf(score)));
    }

    private void selectQuestions(int amount) {
        quizQuestionList = new ArrayList<>();
        Question q;
        int random;
        for (int i = 0; i < amount; i++) {
            random = (int) (Math.random() * totalQuestionList.size());
            q = totalQuestionList.remove(random);
            quizQuestionList.add(q);
        }
    }

    private void restartApp() {
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
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
        totalQuestionList.add(new Question(answerList, rightAnswer, imgSrc, question));

        //Question 2 (BTC)
        answerList = new ArrayList<>();
        question = getResources().getString(R.string.coin_logo_question);
        answerList.add("BTC");
        answerList.add("ETH");
        answerList.add("ETC");
        answerList.add("TRX");
        rightAnswer = "BTC";
        imgSrc = R.drawable.btc;
        totalQuestionList.add(new Question(answerList, rightAnswer, imgSrc, question));

        //Question 3(ENJ)
        answerList = new ArrayList<>();
        question = getResources().getString(R.string.coin_logo_question);
        answerList.add("ENG");
        answerList.add("ETH");
        answerList.add("ENJ");
        answerList.add("WTC");
        rightAnswer = "ENJ";
        imgSrc = R.drawable.enj;
        totalQuestionList.add(new Question(answerList, rightAnswer, imgSrc, question));

        //Question 4 (ETH)
        answerList = new ArrayList<>();
        question = getResources().getString(R.string.coin_logo_question);
        answerList.add("OST");
        answerList.add("ETH");
        answerList.add("IOTA");
        answerList.add("ADX");
        rightAnswer = "ETH";
        imgSrc = R.drawable.eth;
        totalQuestionList.add(new Question(answerList, rightAnswer, imgSrc, question));

        //Question 5 (ICX)
        answerList = new ArrayList<>();
        question = getResources().getString(R.string.coin_logo_question);
        answerList.add("DASH");
        answerList.add("AMB");
        answerList.add("QSP");
        answerList.add("ICX");
        rightAnswer = "ICX";
        imgSrc = R.drawable.icx;
        totalQuestionList.add(new Question(answerList, rightAnswer, imgSrc, question));

        //Question 6(IOTA)
        answerList = new ArrayList<>();
        question = getResources().getString(R.string.coin_logo_question);
        answerList.add("IOTA");
        answerList.add("BRD");
        answerList.add("BCC");
        answerList.add("XVG");
        rightAnswer = "IOTA";
        imgSrc = R.drawable.iota;
        totalQuestionList.add(new Question(answerList, rightAnswer, imgSrc, question));

        //Question 7 (NEO)
        answerList = new ArrayList<>();
        question = getResources().getString(R.string.coin_logo_question);
        answerList.add("LTC");
        answerList.add("CND");
        answerList.add("NCASH");
        answerList.add("NEO");
        rightAnswer = "NEO";
        imgSrc = R.drawable.neo;
        totalQuestionList.add(new Question(answerList, rightAnswer, imgSrc, question));

        //Question 8 (OMG)
        answerList = new ArrayList<>();
        question = getResources().getString(R.string.coin_logo_question);
        answerList.add("XRP");
        answerList.add("WTC");
        answerList.add("OMG");
        answerList.add("ICX");
        rightAnswer = "OMG";
        imgSrc = R.drawable.omg;
        totalQuestionList.add(new Question(answerList, rightAnswer, imgSrc, question));

        //Question 9 (TRX)
        answerList = new ArrayList<>();
        question = getResources().getString(R.string.coin_logo_question);
        answerList.add("TRX");
        answerList.add("BTC");
        answerList.add("GVT");
        answerList.add("BNB");
        rightAnswer = "TRX";
        imgSrc = R.drawable.trx;
        totalQuestionList.add(new Question(answerList, rightAnswer, imgSrc, question));

        //Question 10 (XRP)
        answerList = new ArrayList<>();
        question = getResources().getString(R.string.coin_logo_question);
        answerList.add("ADA");
        answerList.add("XRP");
        answerList.add("VEN");
        answerList.add("XMR");
        rightAnswer = "XRP";
        imgSrc = R.drawable.xrp;
        totalQuestionList.add(new Question(answerList, rightAnswer, imgSrc, question));


    }
}
