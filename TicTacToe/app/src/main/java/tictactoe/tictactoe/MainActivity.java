package tictactoe.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //0:yellow , 1: red , 2: empty

    int[][] winingPositions = {{0,1,2} , {3,4,5} , {6,7,8} , {0,3,6} , {1,4,7} , {2,5,8} , {0,4,8} , {2,4,6}};
    int activePlayer = 1;
    int[] coin = {2,2,2,2,2,2,2,2,2};
    String winner = "";
    boolean winnerFound = false;

    public void dropin(View view)
    {
        int tag;
        ImageView imageView = (ImageView) view;

        tag = Integer.valueOf(imageView.getTag().toString());

        if(coin[tag] == 2 && !winnerFound)
        {
            imageView.setTranslationY(-1500);
            if (activePlayer == 1) {
                imageView.setImageResource(R.drawable.yellow);
                activePlayer = 0;
            } else if (activePlayer == 0) {
                imageView.setImageResource(R.drawable.red);
                activePlayer = 1;
            }

            coin[tag] = activePlayer;

            imageView.animate().translationYBy(1500).setDuration(300);

            for (int[] i : winingPositions) {
                //Toast.makeText(this, str[i[0]], Toast.LENGTH_SHORT).show();
                if (coin[i[0]] == coin[i[1]] && coin[i[1]] == coin[i[2]] && coin[i[2]] != 2) {
                    if (coin[i[0]] == 0)
                        winner = "Yellow";
                    else
                        winner = "Red";

                    Toast.makeText(this, winner + " has won.", Toast.LENGTH_SHORT).show();
                    winnerFound = true;
                    break;
                }
            }
        }

        if(winnerFound)
        {
            Button btn = (Button) findViewById(R.id.playAgain);
            TextView txt = (TextView) findViewById(R.id.textView);

            txt.setText(winner + " has won");

            //btn.setVisibility(View.VISIBLE);
            txt.setVisibility(View.VISIBLE);
        }
    }

    public void playAgain(View view) {

        //Button playAgainButton = (Button) findViewById(R.id.playAgain);
        TextView winnerTextView = (TextView) findViewById(R.id.textView);
        //playAgainButton.setVisibility(View.INVISIBLE);
        winnerTextView.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for(int i=0; i < gridLayout.getChildCount(); i++) {
            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            counter.setImageDrawable(null);
        }

        for (int i=0; i<coin.length; i++) {
            coin[i] = 2;
        }
        activePlayer = 1;
        winnerFound = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
