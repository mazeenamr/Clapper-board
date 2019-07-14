package com.example.android.chatactivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;

public class MainActivity extends AppCompatActivity implements AIListener ,TextToSpeech.OnInitListener {

        private TextToSpeech mTTS = null;
        private final int ACT_CHECK_TTS_DATA = 1000;
        private ListView messagesContainer;
        private ChatAdapter adapter;
        private ArrayList<ChatMessage> chatHistory;
        private AIService aiService;
        private String Response;
        public String genre=null;
        public String rate;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_chat);

            Toast toast = Toast.makeText(getApplicationContext(), "Click to the button and speak",  Toast.LENGTH_LONG);
            toast.show();

            int permission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.RECORD_AUDIO);

            if (permission != PackageManager.PERMISSION_GRANTED) {
                System.out.println("*****************");
                System.out.println ("permission denied");
                makeRequest();
            }
            final AIConfiguration config = new AIConfiguration("e84bf97a4e654816b05d83668df3cb18",
                    AIConfiguration.SupportedLanguages.English,
                    AIConfiguration.RecognitionEngine.System);
            aiService= AIService.getService(this,config);
            aiService.setListener(this);

            Intent ttsIntent = new Intent();
            ttsIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
            startActivityForResult(ttsIntent, ACT_CHECK_TTS_DATA);

            initControls();
        }
    @Override
        public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode)
        {

            case 101:
            {

                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {

                    Log.i("error", "Permission has been denied by user");
                } else {
                    Log.i("error", "Permission has been granted by user");
                }
                return;
            }
        }
    }
        protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO},
                101);
    }
        public void button_clicked(View v)
    {
        aiService.startListening();
    }
        private void initControls() {
            messagesContainer = (ListView) findViewById (R.id.messagesContainer);

            loadDummyHistory();


        }
        public void displayMessage(ChatMessage message) {
            adapter.add(message);
            adapter.notifyDataSetChanged();
            scroll();
        }
        private void scroll() {
            messagesContainer.setSelection(messagesContainer.getCount() - 1);
        }
        private void loadDummyHistory(){

            chatHistory = new ArrayList<ChatMessage>();
            adapter = new ChatAdapter(MainActivity.this, new ArrayList<ChatMessage>());
            messagesContainer.setAdapter(adapter);
            refactor2("Hey there !");
            refactor2("What do you want to watch today ?.");
        }
        public void refactor (String arg) {
            chatHistory = new ArrayList<>();
            ChatMessage msg = new ChatMessage();
            msg.setId(1);
            msg.setMe(false);
            msg.setMessage(arg);
            msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
            chatHistory.add(msg);
            ChatMessage message=chatHistory.get(0);
            displayMessage(message);
         }
        public void refactor2 (String arg) {
            chatHistory = new ArrayList<>();
            ChatMessage msg = new ChatMessage();
            msg.setId(1);
            msg.setMe(true);
            msg.setMessage(arg);
            msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
            chatHistory.add(msg);
            ChatMessage message=chatHistory.get(0);
            displayMessage(message);
        }
        public String containGenre (String query) {
            String word[]={"action","suspense","romance","comedy","adventure", "romantic","crime","drama","horror","thriller","fantasy","animation","mystery","western"};
            for (int i=0 ; i <word.length ; i++)
            {
                if (query.contains(word[i]))
                {
                    return word[i];
                }
            }
            return null;
        }
        private String containRate(String query) {

            CharSequence []numbersInWords={"one","two","three","four","five","six","seven","eight","nine","ten"};
            for (int j=9;j>0;j--)
            {
                if (query.contains(numbersInWords[j]))
                return SwitchWordToNumbers(numbersInWords[j].toString());
            }

            String[]numbers={"0","1","2","3","4","5","6","7","8","9","10"};
            for (int i=10 ;i>=0;i--){
                if (query.contains(numbers[i]))
                {

                    double lowerBound=-0.0;
                    double upperBound = 10.0;
                    double value = Double.parseDouble(numbers[i]);
                    if (lowerBound <= value && value < upperBound)
                    {
                        return String.valueOf(value);
                    }
                    return null;
                }}

            return null;
          }
        private String SwitchWordToNumbers(String string) {
            switch (string){
                case"one":
                    return "1";
                case "two":
                    return "two";
                case"three":
                    return "3";
                case "four":
                    return "4";
                case"five":
                    return "5";
                case "six":
                    return "6";
                case"seven":
                    return "7";
                case "eight":
                    return "8";
                case"nine":
                    return "9";
                default:
                    return null;
        }
        }

    @Override
        public void onResult(final AIResponse result) {


            String Query = result.getResult().getResolvedQuery();
            refactor(Query);
            if (genre ==null)
            {
                genre = containGenre(Query.toLowerCase());
            }
            if (rate==null){
                rate=containRate(Query);
            }
            Response = result.getResult().getFulfillment().getSpeech();
            refactor2(Response);
            saySomething(Response.trim(), 1);

            if (genre != null && rate!=null) {

            Intent newIntent = new Intent(MainActivity.this, ListActivity.class);
            newIntent.putExtra("genre", genre);
            genre=null;
            newIntent.putExtra("rate", rate);
            rate=null;
            startActivity(newIntent);
        }
    }
    @Override
        public void onError(AIError error) {}
    @Override
        public void onAudioLevel(float level) {

    }
    @Override
        public void onListeningStarted() {

    }
    @Override
        public void onListeningCanceled() {

    }
    @Override
        public void onListeningFinished() {
//        TextToSpeech Voice=new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
//            @Override
//            public void onInit(int status) {
//
//
//            }
//        }
//        );
//        Voice.setLanguage(Locale.ENGLISH);
//
//        Voice.speak(Response, TextToSpeech.QUEUE_FLUSH, null);
        //Voice.addEarcon(Response,"com.example.android.chatactivity",);
    }

    @Override
        public void onInit(int i) {
        if (i == TextToSpeech.SUCCESS) {
            if (mTTS != null) {
                int result = mTTS.setLanguage(Locale.US);
                if (result == TextToSpeech.LANG_MISSING_DATA ||
                        result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(this, "TTS language is not supported", Toast.LENGTH_LONG).show();
                } else {
                    saySomething("TTS is ready", 0);
                }
            }
        } else {
            Toast.makeText(this, "TTS initialization failed",
                    Toast.LENGTH_LONG).show();
        }
    }
    @Override
        protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == ACT_CHECK_TTS_DATA) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                // Data exists, so we instantiate the TTS engine
                mTTS = new TextToSpeech(this, this);
            } else {
                // Data is missing, so we start the TTS
                // installation process
                Intent installIntent = new Intent();
                installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
        }}
    @Override
        protected void onDestroy() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }

        private void saySomething(String text, int qmode) {
        if (qmode == 1)
            mTTS.speak(text, TextToSpeech.QUEUE_ADD, null);
        else
            mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}

