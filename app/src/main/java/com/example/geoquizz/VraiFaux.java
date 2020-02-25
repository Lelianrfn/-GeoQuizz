package com.example.geoquizz;

import android.os.Parcel;
import android.os.Parcelable;

public class VraiFaux implements Parcelable {
    private int mQuestion;
    private boolean mQuestionVraie;
    private boolean mQuestionRepondue;
    private boolean mReponseRegarde;

    //constructeur
    public VraiFaux(int maQuestion, boolean maQuestionVraie) {
        this.mQuestion = maQuestion;
        this.mQuestionVraie = maQuestionVraie;
        this.mQuestionRepondue=false;
        this.mReponseRegarde=false;
    }


    protected VraiFaux(Parcel in) {
        mQuestion = in.readInt();
        mQuestionVraie = in.readByte() != 0;
        mQuestionRepondue = in.readByte() != 0;
        mReponseRegarde = in.readByte() != 0;
    }

    public static final Creator<VraiFaux> CREATOR = new Creator<VraiFaux>() {
        @Override
        public VraiFaux createFromParcel(Parcel in) {
            return new VraiFaux(in);
        }

        @Override
        public VraiFaux[] newArray(int size) {
            return new VraiFaux[size];
        }
    };

    public int getQuestion() {
        return mQuestion;
    }

    public void setQuestion(int question) {
        mQuestion = question;
    }

    public boolean isQuestionVraie() {
        return mQuestionVraie;
    }

    public void setQuestionVraie(boolean questionVraie) {
        mQuestionVraie = questionVraie;
    }

    public boolean isQuestionRepondue() {
        return mQuestionRepondue;
    }

    public void setQuestionRepondue(boolean questionRepondue) {
        mQuestionRepondue = questionRepondue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mQuestion);
        parcel.writeByte((byte) (mQuestionRepondue ? 1 : 0));
        parcel.writeByte((byte) (mQuestionVraie ? 1 : 0));
        parcel.writeByte((byte) (mReponseRegarde ? 1 : 0));
    }

    public boolean ismReponseRegarde() {
        return mReponseRegarde;
    }

    public void setmReponseRegarde(boolean mReponseRegarde) {
        this.mReponseRegarde = mReponseRegarde;
    }
}
