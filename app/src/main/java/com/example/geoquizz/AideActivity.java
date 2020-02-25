package com.example.geoquizz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AideActivity extends Activity {

    public static final String EXTRA_REPONSE_VRAIE="com.example.geoquizz.reponse_vraie";
    public static final String EXTRA_REPONSE_AFFICHEE ="com.example.geoquizz.reponse_affichee";
    public static final String TRICHE_APRES_ROTATION ="TRICHE";
    private Button mBoutonAide;
    private TextView mReponseTextView;
    private  boolean mRotationTriche;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aide);

        final boolean mReponseVraie=getIntent().getBooleanExtra(EXTRA_REPONSE_VRAIE,false);
        if(savedInstanceState != null){
            mRotationTriche=savedInstanceState.getBoolean(TRICHE_APRES_ROTATION);
            setReponseAffichee(mRotationTriche);
        }else{
            mRotationTriche=false;
        }
        mReponseTextView=(TextView)findViewById(R.id.reponseTextView);
        mBoutonAide=(Button)findViewById(R.id.boutonAfficheAide);
        mBoutonAide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mReponseVraie){mReponseTextView.setText(R.string.bouton_vrai);}
                else{mReponseTextView.setText(R.string.bouton_faux);}
                setReponseAffichee(true);
            }
        });
    }

    private void setReponseAffichee(boolean isReponseAffichee){
        mRotationTriche=true;
        Intent donnees= new Intent();
        donnees.putExtra(EXTRA_REPONSE_AFFICHEE,isReponseAffichee);
        setResult(RESULT_OK,donnees);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(TRICHE_APRES_ROTATION,mRotationTriche);
    }
}
