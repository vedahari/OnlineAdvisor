package edu.vt.ece.onlineadvisor;

import android.os.Bundle;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;


/*
* Demo class has to be removed once built successfully
*
*
*
* */
/* demo.java */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import lpsolve.*;

class Demo {

    public Demo() {
    }

    public int execute() throws LpSolveException {
        LpSolve lp;
        int Ncol, j, ret = 0;

          /* We will build the model row by row
             So we start with creating a model with 0 rows and 2 columns */
        Ncol = 2; /* there are two variables in the model */

          /* create space large enough for one row */
        int[] colno = new int[Ncol];
        double[] row = new double[Ncol];

        lp = LpSolve.makeLp(0, Ncol);
        if(lp.getLp() == 0)
            ret = 1; /* couldn't construct a new model... */

        if(ret == 0) {
            /* let us name our variables. Not required, but can be useful for debugging */
            lp.setColName(1, "x");
            lp.setColName(2, "y");

            lp.setAddRowmode(true);  /* makes building the model faster if it is done rows by row */

            /* construct first row (120 x + 210 y <= 15000) */
            j = 0;

            colno[j] = 1; /* first column */
            row[j++] = 120;

            colno[j] = 2; /* second column */
            row[j++] = 210;

            /* add the row to lpsolve */
            lp.addConstraintex(j, row, colno, LpSolve.LE, 15000);
        }

        if(ret == 0) {
            /* construct second row (110 x + 30 y <= 4000) */
            j = 0;

            colno[j] = 1; /* first column */
            row[j++] = 110;

            colno[j] = 2; /* second column */
            row[j++] = 30;

            /* add the row to lpsolve */
            lp.addConstraintex(j, row, colno, LpSolve.LE, 4000);
        }

        if(ret == 0) {
            /* construct third row (x + y <= 75) */
            j = 0;

            colno[j] = 1; /* first column */
            row[j++] = 1;

            colno[j] = 2; /* second column */
            row[j++] = 1;

            /* add the row to lpsolve */
            lp.addConstraintex(j, row, colno, LpSolve.LE, 75);
        }

        if(ret == 0) {
            lp.setAddRowmode(false); /* rowmode should be turned off again when done building the model */

            /* set the objective function (143 x + 60 y) */
            j = 0;

            colno[j] = 1; /* first column */
            row[j++] = 143;

            colno[j] = 2; /* second column */
            row[j++] = 60;

            /* set the objective in lpsolve */
            lp.setObjFnex(j, row, colno);
        }

        if(ret == 0) {
            /* set the object direction to maximize */
            lp.setMaxim();

            /* just out of curioucity, now generate the model in lp format in file model.lp */
           // lp.writeLp("model.lp");

            /* I only want to see important messages on screen while solving */
            lp.setVerbose(LpSolve.IMPORTANT);

            /* Now let lpsolve calculate a solution */
            ret = lp.solve();
            if(ret == LpSolve.OPTIMAL)
                ret = 0;
            else
                ret = 5;
        }

        if(ret == 0) {
            /* a solution is calculated, now lets get some results */

            /* objective value */
            System.out.println("Objective value: " + lp.getObjective());

            /* variable values */
            lp.getVariables(row);
            for(j = 0; j < Ncol; j++)
                System.out.println(lp.getColName(j + 1) + ": " + row[j]);

            /* we are done now */
        }

          /* clean up such that all used memory by lpsolve is freed */
        if(lp.getLp() != 0)
            lp.deleteLp();

        return(ret);
    }

    public static void main(String[] args) {
        try {
            new Demo().execute();
        }
        catch (LpSolveException e) {
            e.printStackTrace();
        }
    }
}

/*
* Experimental code ends
*
*
* */



public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
        System.loadLibrary("native-liblpsolve");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Example of a call to a native method

        TextView tv = (TextView) findViewById(R.id.sample_text);
        if ( true){
            tv.setText("Truer");
            try {
                System.out.println("Printing output from main activity");
                new Demo().execute();
            }
            catch (LpSolveException e) {
                e.printStackTrace();
            }
        }
        else {
            tv.setText("falser");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    public native boolean isUseNames();
    public native void init();
}
