package com.example.geoquizz;

import android.content.Intent;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //declaration des variables
    private Button mBoutonVrai;
    private Button mBoutonFaux;
    private Button mBoutonSuivant;
    private Button mBoutonAide;
    private Button mBoutonRestart;
    private TextView mQestionTextView;
    private TextView mScoreTextView;

    //tableau de question a vec les bonnes réponses associé
    private VraiFaux[] mTabQuestions=initTab();

    //indexe pour parcourir le tableau
    private int mIndexActuel=0;
    private int score;
    private  boolean restart;
    private boolean mEstTRicheur;

    //tag pour les log
    private static final String TAG ="QuizActivity";
    //tag pour saveInsatance
    private static final String KEY_INDEX ="index";
    private static final String KEY_SCORE ="score";
    private static final String KEY_QUESTION ="question";

    private VraiFaux[] initTab(){
        return new VraiFaux[]{
                new VraiFaux(R.string.question_oceans,true),
                new VraiFaux(R.string.question_africa,false),
                new VraiFaux(R.string.question_americas,true),
                new VraiFaux(R.string.question_asia,true),
                new VraiFaux(R.string.question_mideast,false)
        };
    }
    //Fonction de gestion des question
    private void majQuestion(){
        int question= mTabQuestions[mIndexActuel].getQuestion();
        mQestionTextView.setText(question);
        if(mTabQuestions[mIndexActuel].isQuestionRepondue()){
            changeBouton(false);
        }else{
            changeBouton(true);
        }
    }
    private void majScore(){
        String scoreS="Score:"+score+"/"+mTabQuestions.length;
        mScoreTextView.setText(scoreS);
    }
    private void verifieQuestion(boolean userVrai){
        boolean reponsVraie= mTabQuestions[mIndexActuel].isQuestionVraie();

        int mesReponseId=0;
        if(mTabQuestions[mIndexActuel].ismReponseRegarde()){
            mesReponseId=R.string.toast_aide;
            score--;
        }else {
            if (userVrai == reponsVraie) {
                mesReponseId = R.string.toast_correct;
                score++;
            } else {
                mesReponseId = R.string.toast_faux;
            }
        }
        Toast.makeText(MainActivity.this,mesReponseId,Toast.LENGTH_SHORT).show();
        majScore();
        mTabQuestions[mIndexActuel].setQuestionRepondue(true);
        changeBouton(false);
        questionSuivante();
    }
    private void changeBouton(boolean state){
        mBoutonFaux.setEnabled(state);
        mBoutonVrai.setEnabled(state);

    }
    private void questionSuivante(){
        mIndexActuel=(mIndexActuel+1)%mTabQuestions.length;
        majQuestion();
    }
    //Fonction de gestion du cycle de vie
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        restart=false;
        score=0;
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate appelée");
        setContentView(R.layout.activity_main);
        mScoreTextView=(TextView)findViewById(R.id.score_text_view);
        mQestionTextView=(TextView)findViewById(R.id.question_text_view);
        if(savedInstanceState!=null){
            mIndexActuel=savedInstanceState.getInt(KEY_INDEX,0);
            score=savedInstanceState.getInt(KEY_SCORE,0);
            if(mTabQuestions[mIndexActuel].isQuestionRepondue()){
                changeBouton(false);
            }
            Parcelable[] temp =savedInstanceState.getParcelableArray(KEY_QUESTION);

            for (int i=0;i<temp.length;i++){
                mTabQuestions[i]= (VraiFaux)temp[i];
            }
        }
        majScore();
        mBoutonVrai =(Button)findViewById(R.id.bouton_vrai);
        mBoutonVrai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifieQuestion(true);
            }
        });
        mBoutonFaux =(Button)findViewById(R.id.bouton_faux);
        mBoutonFaux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifieQuestion(false);
            }
        });
        mBoutonSuivant=(Button)findViewById(R.id.bouton_suivant);
        mBoutonSuivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionSuivante();
            }
        });
        mBoutonAide=(Button)findViewById(R.id.bouton_aide);
        mBoutonAide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intention=new Intent(MainActivity.this,AideActivity.class);
                boolean reponseVraie=mTabQuestions[mIndexActuel].isQuestionVraie();
                intention.putExtra(AideActivity.EXTRA_REPONSE_VRAIE,reponseVraie);
                startActivityForResult(intention,69);
            }
        });
        mBoutonRestart=(Button)findViewById(R.id.bouton_restart);
        mBoutonRestart.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View view) {
                mIndexActuel=0;
                score=0;
                mEstTRicheur=false;
                majScore();
                mTabQuestions=initTab();
                majQuestion();
            }
        });
        majQuestion();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart appelée");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause appelée");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume appelée");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop appelée");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDesrtoy appelée");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(!restart){
            Log.i(TAG, "onSaveInstance appelée");
            outState.putInt(KEY_INDEX, mIndexActuel);
            outState.putInt(KEY_SCORE, score);
            outState.putParcelableArray(KEY_QUESTION, mTabQuestions);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(data !=null){
            mEstTRicheur=data.getBooleanExtra(AideActivity.EXTRA_REPONSE_AFFICHEE,false);
            mTabQuestions[mIndexActuel].setmReponseRegarde(true);
        }else return;
    }
}
