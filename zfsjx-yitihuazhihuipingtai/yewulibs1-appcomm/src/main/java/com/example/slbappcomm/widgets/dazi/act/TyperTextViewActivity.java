package com.example.slbappcomm.widgets.dazi.act;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.slbappcomm.R;
import com.example.slbappcomm.widgets.dazi.AnimationListener;
import com.example.slbappcomm.widgets.dazi.HTextView;


public class TyperTextViewActivity extends AppCompatActivity {
    String[] sentences = {"What is design?",
            "Design is not just",
            "what it looks like and feels like.",
            "Design is how it works. \n- Steve Jobs",
            "Older people",
            "sit down and ask,",
            "'What is it?'",
            "but the boy asks,",
            "'What can I do with it?'. \n- Steve Jobs",
            "Swift",
            "Objective-C",
            "iPhone",
            "iPad",
            "Mac Mini", "MacBook Pro", "Mac Pro", "爱老婆", "老婆和女儿"};
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typer_text_view);
        final HTextView textView1 = findViewById(R.id.textview);
        textView1.setOnClickListener(new ClickListener());
        textView1.setAnimationListener(new SimpleAnimationListener(this));
        textView1.animateText(getString(R.string.initStr));

        final HTextView textView2 = findViewById(R.id.textview2);
        textView2.setOnClickListener(new ClickListener());
        textView2.setAnimationListener(new SimpleAnimationListener(this));
    }

    class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v instanceof HTextView) {
                if (index + 1 >= sentences.length) {
                    index = 0;
                }
                ((HTextView) v).animateText(sentences[index++]);
            }
        }
    }

    class SimpleAnimationListener implements AnimationListener {

        private Context context;

        public SimpleAnimationListener(Context context) {
            this.context = context;
        }

        @Override
        public void onAnimationEnd(HTextView hTextView) {
            Toast.makeText(context, "Animation finished", Toast.LENGTH_SHORT).show();
        }
    }
}
