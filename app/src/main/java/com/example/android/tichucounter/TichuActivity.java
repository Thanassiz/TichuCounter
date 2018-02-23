package com.example.android.tichucounter;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TichuActivity extends AppCompatActivity implements View.OnClickListener {

    // Key vars, helps to save variables in Bundles (savedInstanceState, outState)
    private final String STATE_KEY_SCORE_A = "scoreA";
    private final String STATE_KEY_SCORE_B = "scoreB";
    private final String STATE_KEY_FLAG = "flag";
    private final String STATE_KEY_PRESSED= "isPressed";

    // Declare Views
    private EditText mEditTextTeamAName, mEditTextTeamBName, mEditTextTeamAScore, mEditTextTeamBScore;
    private Button mNumber1Button, mNumber2Button, mNumber3Button, mNumber4Button, mNumber5Button,
            mNumber6Button, mNumber7Button, mNumber8Button, mNumber9Button, mNumber0Button,
            mResetButton, mMinusButton, mDeleteButton, mSetTeamsButton, mGoButton;
    private TextView scoreViewA, scoreViewB;
    // main score vars. Used in TextViews.
    private int scoreA = 0;
    private int scoreB = 0;
    // Flag is helper var to change SetTeamsButton
    private int flag = 0;
    private boolean isPressed = false;
    // Helper vars to take values from strings xml and use em to update the score's editTexts.
    private String pressedNumberA = "";
    private String pressedNumberB = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tichu);

        // Initialize Views
        initializeViews();
        // changes font on Teams and Score views
        changeFont();
        // Makes EditTexts of Score null so keyboard Input is disabled.
        mEditTextTeamAScore.setInputType(InputType.TYPE_NULL);
        mEditTextTeamBScore.setInputType(InputType.TYPE_NULL);
        /*
         *  make ScoreEditText focused. Cause its inputType is disabled.
         *  So Keyboard doesn't pop up. Cause by default the first Edittext is focused.
         */
            mEditTextTeamAScore.requestFocus();
    }

    // on CLICK BUTTON , ADD SCORE for A TEAM ~~~~~~~~~~~~~~~~~~~~~~~~~~
    // This functions are called from activity_tichu.xml
    public void plus100A(View v) {
        scoreA = scoreA + 100;
        displayScoreA(scoreA);
    }

    public void minus100A(View v) {
        scoreA = scoreA - 100;
        displayScoreA(scoreA);
    }

    // on CLICK BUTTON , ADD SCORE for B TEAM ~~~~~~~~~~~~~~~~~~~~~~~~~~
    // This functions are called from activity_tichu.xml
    public void plus100B(View v) {
        scoreB = scoreB + 100;
        displayScoreB(scoreB);
    }

    public void minus100B(View v) {
        scoreB = scoreB - 100;
        displayScoreB(scoreB);
    }

    // Display score for TEAM A ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public void displayScoreA(int scoreA) {
        scoreViewA.setText(String.valueOf(scoreA));
    }

    // Display score for TEAM B ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public void displayScoreB(int scoreB) {
        scoreViewB.setText(String.valueOf(scoreB));
    }

    // Reset scores for TEAM A and B~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public void resetScore(View v) {
        scoreA = 0;
        scoreB = 0;
        displayScoreA(scoreA);
        displayScoreB(scoreB);
    }

    // Sets scores for TEAM A and B (when GoButton is pressed)~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public void setScore(View v) {
        /*
         * Sets score only when there is something in the edits texts.
         * To avoid NumberFormatException!
         */
        if ((!TextUtils.isEmpty(mEditTextTeamAScore.getText()) && !TextUtils.isEmpty(mEditTextTeamBScore.getText()))
                && (!TextUtils.isEmpty(mEditTextTeamAScore.getText()) || TextUtils.isEmpty(mEditTextTeamBScore.getText()))
                && (TextUtils.isEmpty(mEditTextTeamAScore.getText()) || !TextUtils.isEmpty(mEditTextTeamBScore.getText()))) {
            scoreA += Integer.parseInt(mEditTextTeamAScore.getText().toString());
            scoreB += Integer.parseInt(mEditTextTeamBScore.getText().toString());
            displayScoreA(scoreA);
            displayScoreB(scoreB);
            // Clears score's edit texts
            pressedNumberA = "";
            pressedNumberB = "";
            mEditTextTeamAScore.setText(pressedNumberA);
            mEditTextTeamBScore.setText(pressedNumberB);
        }
    }

    // Set names for TEAM A and B~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Locks the EditTexts Views and changes SetTeamsButton appearance.
    // Also changes Flag which is used to define which function should be called when pressing  SetTeamsButton.
    public void setTeamNames() {
        mEditTextTeamAName.setClickable(false);
        mEditTextTeamAName.setFocusable(false);
        mEditTextTeamAName.setBackgroundColor(getResources().getColor(R.color.colorTransperant));
        mEditTextTeamBName.setClickable(false);
        mEditTextTeamBName.setFocusable(false);
        mEditTextTeamBName.setBackgroundColor(getResources().getColor(R.color.colorTransperant));
        mSetTeamsButton.setText(getString(R.string.reset_teams_button_text));
        mSetTeamsButton.setTextColor(getResources().getColor(R.color.colorWhite));
        mSetTeamsButton.setShadowLayer(5, 2, 2, getResources().getColor(R.color.colorBlack));
        mSetTeamsButton.setBackgroundResource(R.drawable.gradiant_special_buttons_bg);
        flag = 1;
    }

    // Reset names for TEAM A and B~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Unlocks the EditTexts Views, also reset the Team names and changes SetTeams button appearance to default.
    // Also changes Flag which is used to define which function should be called when pressing  SetTeamsButton.
    public void resetTeamNames() {
        mEditTextTeamAName.setClickable(true);
        mEditTextTeamAName.setFocusableInTouchMode(true);
        mEditTextTeamAName.setFocusable(true);
        mEditTextTeamAName.setText(getString(R.string.team_a_text));
        mEditTextTeamAName.setBackgroundColor(android.R.drawable.editbox_background);
        mEditTextTeamBName.setClickable(true);
        mEditTextTeamBName.setFocusableInTouchMode(true);
        mEditTextTeamBName.setFocusable(true);
        mEditTextTeamBName.setText(getString(R.string.team_b_text));
        mEditTextTeamBName.setBackgroundColor(android.R.drawable.editbox_background);
        mSetTeamsButton.setText(getString(R.string.set_teams_button_text));
        mSetTeamsButton.setTextColor(getResources().getColor(R.color.colorBlack));
        mSetTeamsButton.setBackgroundResource(R.drawable.gradiant_buttons_bg);
        flag = 0;
    }

    // changes font on Teams and Score views
    private void changeFont() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), getString(R.string.font_path));
        mEditTextTeamAName.setTypeface(typeface);
        mEditTextTeamBName.setTypeface(typeface);
        scoreViewA.setTypeface(typeface);
        scoreViewB.setTypeface(typeface);
    }


    @Override
    public void onClick(View view) {
        /*
         * Based on the id of the clicked button, we add the value of the
         * pressed button to the focused / selected editeText_score.
         */
        switch (view.getId()) {
            case R.id.number_0_id:
                if (mEditTextTeamAScore.isFocused()) {
                    pressedNumberA += getString(R.string.number_0_text);
                    mEditTextTeamAScore.setText(pressedNumberA);
                } else if (mEditTextTeamBScore.isFocused()) {
                    pressedNumberB += getString(R.string.number_0_text);
                    mEditTextTeamBScore.setText(pressedNumberB);
                }
                break;
            case R.id.number_1_id:
                if (mEditTextTeamAScore.isFocused()) {
                    pressedNumberA += getString(R.string.number_1_text);
                    mEditTextTeamAScore.setText(pressedNumberA);
                } else if (mEditTextTeamBScore.isFocused()) {
                    pressedNumberB += getString(R.string.number_1_text);
                    mEditTextTeamBScore.setText(pressedNumberB);
                }
                break;
            case R.id.number_2_id:
                if (mEditTextTeamAScore.isFocused()) {
                    pressedNumberA += getString(R.string.number_2_text);
                    mEditTextTeamAScore.setText(pressedNumberA);
                } else if (mEditTextTeamBScore.isFocused()) {
                    pressedNumberB += getString(R.string.number_2_text);
                    mEditTextTeamBScore.setText(pressedNumberB);
                }
                break;
            case R.id.number_3_id:
                if (mEditTextTeamAScore.isFocused()) {
                    pressedNumberA += getString(R.string.number_3_text);
                    mEditTextTeamAScore.setText(pressedNumberA);
                } else if (mEditTextTeamBScore.isFocused()) {
                    pressedNumberB += getString(R.string.number_3_text);
                    mEditTextTeamBScore.setText(pressedNumberB);
                }
                break;
            case R.id.number_4_id:
                if (mEditTextTeamAScore.isFocused()) {
                    pressedNumberA += getString(R.string.number_4_text);
                    mEditTextTeamAScore.setText(pressedNumberA);
                } else if (mEditTextTeamBScore.isFocused()) {
                    pressedNumberB += getString(R.string.number_4_text);
                    mEditTextTeamBScore.setText(pressedNumberB);
                }
                break;
            case R.id.number_5_id:
                if (mEditTextTeamAScore.isFocused()) {
                    pressedNumberA += getString(R.string.number_5_text);
                    mEditTextTeamAScore.setText(pressedNumberA);
                } else if (mEditTextTeamBScore.isFocused()) {
                    pressedNumberB += getString(R.string.number_5_text);
                    mEditTextTeamBScore.setText(pressedNumberB);
                }
                break;
            case R.id.number_6_id:
                if (mEditTextTeamAScore.isFocused()) {
                    pressedNumberA += getString(R.string.number_6_text);
                    mEditTextTeamAScore.setText(pressedNumberA);
                } else if (mEditTextTeamBScore.isFocused()) {
                    pressedNumberB += getString(R.string.number_6_text);
                    mEditTextTeamBScore.setText(pressedNumberB);
                }
                break;
            case R.id.number_7_id:
                if (mEditTextTeamAScore.isFocused()) {
                    pressedNumberA += getString(R.string.number_7_text);
                    mEditTextTeamAScore.setText(pressedNumberA);
                } else if (mEditTextTeamBScore.isFocused()) {
                    pressedNumberB += getString(R.string.number_7_text);
                    mEditTextTeamBScore.setText(pressedNumberB);
                }
                break;
            case R.id.number_8_id:
                if (mEditTextTeamAScore.isFocused()) {
                    pressedNumberA += getString(R.string.number_8_text);
                    mEditTextTeamAScore.setText(pressedNumberA);
                } else if (mEditTextTeamBScore.isFocused()) {
                    pressedNumberB += getString(R.string.number_8_text);
                    mEditTextTeamBScore.setText(pressedNumberB);
                }
                break;
            case R.id.number_9_id:
                if (mEditTextTeamAScore.isFocused()) {
                    pressedNumberA += getString(R.string.number_9_text);
                    mEditTextTeamAScore.setText(pressedNumberA);
                } else if (mEditTextTeamBScore.isFocused()) {
                    pressedNumberB += getString(R.string.number_9_text);
                    mEditTextTeamBScore.setText(pressedNumberB);
                }
                break;
            case R.id.symbol_minus_id:
                /*
                 * Checks if the textView is empty, so it will add only one minus symbol "-"
                 * and only in the beggining of the text (caue its a surplus sign)
                 * it can't be added repeatedly
                 */
                if (mEditTextTeamAScore.isFocused()) {
                    if (TextUtils.isEmpty(mEditTextTeamAScore.getText())) {
                        pressedNumberA += getString(R.string.symbol_minus_text);
                        mEditTextTeamAScore.setText(pressedNumberA);
                    }
                }
                /*
                 * Checks if the textView is empty, so it will add only one minus symbol "-"
                 * and only in the beggining of the text (caue its a surplus sign)
                 * it can't be added repeatedly
                 */
                else if (mEditTextTeamBScore.isFocused()) {
                    if (TextUtils.isEmpty(mEditTextTeamBScore.getText())) {
                        pressedNumberB += getString(R.string.symbol_minus_text);
                        mEditTextTeamBScore.setText(pressedNumberB);
                    }
                }
                break;
            case R.id.del_button_id:
                if (mEditTextTeamAScore.isFocused()) {

                    /*
                     * Checks if there is text in the Edittext , only then it deletes a digit.
                     * To avoid StringIndexOutOfBoundsException!
                     */
                    if (pressedNumberA.length() > 0) {
                        pressedNumberA = pressedNumberA.substring(0, pressedNumberA.length() - 1);
                        mEditTextTeamAScore.setText(pressedNumberA);
                    }
                } else if (mEditTextTeamBScore.isFocused()) {
                    /*
                     * Checks if there is text in the Edittext , only then it deletes a digit.
                     * To avoid StringIndexOutOfBoundsException!
                     */
                    if (!pressedNumberB.contentEquals("")) {
                        pressedNumberB = pressedNumberB.substring(0, pressedNumberB.length() - 1);
                        mEditTextTeamBScore.setText(pressedNumberB);
                    }
                }
                break;
            case R.id.reset_button_id:
                resetScore(view);
                break;
            case R.id.go_button_id:
                setScore(view);
                break;
            case R.id.set_teams_button_id:
                // Button is pressed.
                isPressed = true;
                // Default Flag value is 0. Flag a helper var to switch function when pressing this button.
                if (flag == 0) {
                    setTeamNames();
                } else {
                    resetTeamNames();
                }
                break;
            default:
                break;
        }
    }

    // Initializes all views
    private void initializeViews() {
        mEditTextTeamAName = findViewById(R.id.editText_name_teamA_id);
        mEditTextTeamBName = findViewById(R.id.editText_name_teamB_id);
        mEditTextTeamAScore = findViewById(R.id.editText_score_teamA_id);
        mEditTextTeamBScore = findViewById(R.id.editText_score_teamB_id);
        scoreViewA = findViewById(R.id.textView_score_teamA_id);
        scoreViewB = findViewById(R.id.textView_score_teamB_id);
        mNumber1Button = findViewById(R.id.number_1_id);
        mNumber2Button = findViewById(R.id.number_2_id);
        mNumber3Button = findViewById(R.id.number_3_id);
        mNumber4Button = findViewById(R.id.number_4_id);
        mNumber5Button = findViewById(R.id.number_5_id);
        mNumber6Button = findViewById(R.id.number_6_id);
        mNumber7Button = findViewById(R.id.number_7_id);
        mNumber8Button = findViewById(R.id.number_8_id);
        mNumber9Button = findViewById(R.id.number_9_id);
        mNumber0Button = findViewById(R.id.number_0_id);
        mResetButton = findViewById(R.id.reset_button_id);
        mMinusButton = findViewById(R.id.symbol_minus_id);
        mDeleteButton = findViewById(R.id.del_button_id);
        mSetTeamsButton = findViewById(R.id.set_teams_button_id);
        mGoButton = findViewById(R.id.go_button_id);
        // Calls on click when buttons are clicked.
        mNumber0Button.setOnClickListener(TichuActivity.this);
        mNumber1Button.setOnClickListener(TichuActivity.this);
        mNumber2Button.setOnClickListener(TichuActivity.this);
        mNumber3Button.setOnClickListener(TichuActivity.this);
        mNumber4Button.setOnClickListener(TichuActivity.this);
        mNumber5Button.setOnClickListener(TichuActivity.this);
        mNumber6Button.setOnClickListener(TichuActivity.this);
        mNumber7Button.setOnClickListener(TichuActivity.this);
        mNumber8Button.setOnClickListener(TichuActivity.this);
        mNumber9Button.setOnClickListener(TichuActivity.this);
        mResetButton.setOnClickListener(TichuActivity.this);
        mMinusButton.setOnClickListener(TichuActivity.this);
        mDeleteButton.setOnClickListener(TichuActivity.this);
        mSetTeamsButton.setOnClickListener(TichuActivity.this);
        mGoButton.setOnClickListener(TichuActivity.this);
    }

    // This method is used to save vars (in Bundle) before the Activity is destroyed.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_KEY_SCORE_A, scoreA);
        outState.putInt(STATE_KEY_SCORE_B, scoreB);
        outState.putInt(STATE_KEY_FLAG, flag);
        outState.putBoolean(STATE_KEY_PRESSED, isPressed);
    }

    // Repopulates the values that were saved in onSavedInstaceState's Bundle.
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        scoreA = savedInstanceState.getInt(STATE_KEY_SCORE_A);
        scoreB = savedInstanceState.getInt(STATE_KEY_SCORE_B);
        flag = savedInstanceState.getInt(STATE_KEY_FLAG);
        isPressed = savedInstanceState.getBoolean(STATE_KEY_PRESSED);
    }

    // onResume is called after onRestoreInstanceState method, so this method
    // is used to redisplay the Views (now that vars are already repopulated)
    @Override
    protected void onResume() {
        super.onResume();
        displayScoreA(scoreA);
        displayScoreB(scoreB);
        // Necessary checks, to redisplay the UI as it was before orientation changed.
        // because UI is changing when button (set teams) is pressed.
        if (isPressed && flag == 1 ) {
            setTeamNames();
        } else {
            resetTeamNames();
        }
    }
}
