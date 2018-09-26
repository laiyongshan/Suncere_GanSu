package suncere.gansu.androidapp.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import suncere.gansu.androidapp.R;

/**
 * @author lys
 * @time 2018/9/11 09:46
 * @desc:
 */

public class TakePhotoDialog extends Dialog {

    @BindView(R.id.take_pic_btn)
    Button take_pic_btn;

    @BindView(R.id.choose_file_btn)
    Button choose_file_btn;

    @BindView(R.id.cancel_btn)
    Button cancel_btn;



    FileManagerFragment fragment;

    public TakePhotoDialog(@NonNull FileManagerFragment fragment, int themeResId) {
        super(fragment.getActivity(),themeResId);
        this.fragment=fragment;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_take_photo);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.take_pic_btn,R.id.choose_file_btn,R.id.cancel_btn})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.take_pic_btn:

                break;

            case R.id.choose_file_btn:
                selectFile();
                dismiss();
                break;

            case R.id.cancel_btn:
                dismiss();
                break;
        }
    }

    /**
     * 通过手机选择文件
     */
    public void selectFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            fragment.startActivityForResult(Intent.createChooser(intent, "选择文件上传"), FileManagerFragment.CHOOSE_FILE_REQUEST_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(fragment.getActivity(),"请安装一个文件管理器",Toast.LENGTH_SHORT).show();
        }
    }


}
