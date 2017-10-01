package android.letus179.com.givemefive;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

/**
 * 检测输入框是否有输入内容，从而决定按钮是否可点击
 * 以及输入框后面的X的可见性，X点击后删除输入框的内容
 */
public class TextChangeListener {

    private static final String TAG = "TextChangeListener";
    private IEditTextChangeListener mChangeListener;
    private Button button;
    private EditText[] editTexts;

    public TextChangeListener(Button button, IEditTextChangeListener changeListener) {
        this.button = button;
        this.mChangeListener = changeListener;
    }

    public TextChangeListener addAllEditText(EditText... editTexts) {
        this.editTexts = editTexts;
        initEditListener();
        return this;
    }

    private void initEditListener() {
        Log.d(TAG, "initEditListener: called ergodic 'editText' method");
        for (EditText editText : editTexts)
            editText.addTextChangedListener(new MyTextWatcher());
    }


    /**
     * 检查所有的edit是否输入数据
     *
     * @return
     */
    private boolean checkAllEdit() {

        for (EditText editText : editTexts) {
            if (!TextUtils.isEmpty(editText.getText() + "")) {
                continue;
            } else {
                return false;
            }
        }
        return true;

    }


    /**
     * edit的输入变化来改变按钮是否可点击
     */
    private class MyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (checkAllEdit()) {
                Log.d(TAG, "onTextChanged: all editTexts have value");
                mChangeListener.textChange(true);
                button.setEnabled(true);
            } else {
                Log.d(TAG, "onTextChanged: more than one editText has no value");
                mChangeListener.textChange(false);
                button.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

}                                                                                                  
                                                                                                   
                                                                                                   