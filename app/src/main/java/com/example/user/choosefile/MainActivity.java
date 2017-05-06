package com.example.user.choosefile;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
/*Reference  :
http://tomkuo139.blogspot.tw/2010/02/android-choose-file.html (main coding)
http://zfejdje.blogspot.tw/2014/04/anadroid-imageview.html (ImageView size)
https://goo.gl/A8Mh9o (Uri to actual path)
https://goo.gl/mXzLMd (startActivityForResult)
*/
    TextView show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b=(Button) findViewById(R.id.button);
        show=(TextView) findViewById(R.id.textView);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                Intent destIntent = Intent.createChooser(intent,"選擇圖檔案");
                startActivityForResult(destIntent,0);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==RESULT_OK){
            Uri uri=data.getData();
            if(uri!=null){
                ImageView iv=(ImageView)findViewById(R.id.imageView);
                iv.setImageURI(uri);
                setTitle(uri.toString());

                //Uri to actual path
                String []proj={MediaStore.Images.Media.DATA};
                //Cursor actualImagecursor=managedQuery(uri,proj,null,null,null); //已被取代
                Cursor actualImagecursor=getContentResolver().query(uri,proj,null,null,null);

                int actual_image_column_index=
                        actualImagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                actualImagecursor.moveToFirst();
                String img_path=actualImagecursor.getString(actual_image_column_index);

                show.setText(img_path.toString());
            }else{
                setTitle("error");
            }
        }else{
            setTitle("Cancel choose file!!");
        }
    }
}
