package com.example.warcaby;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.Toast;

import java.lang.Object.*;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.DrawableRes;
import androidx.annotation.RequiresApi;
import androidx.core.content.res.TypedArrayUtils;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public Button buttons[] = new Button[32];
    private static MyField tablica[] = new MyField[32];
    public static final String cleanBoard = "11111111000000000000000022222222";
    int nrGracza = 1;
    boolean select = false;
    boolean hit = false;
    MyField selectField = new MyField();
    MyField targetField = new MyField();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();

        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 4; j++){
                tablica[i*4 + j] = new MyField(j*2+((i%2))+1, i+1, 0);
                //else tablica[i*4 + j] = new MyField(j*2+((i%2)), i+1, 0);
                if (i<2) tablica[i*4 + j].setPawn(1);
                else if (i>5) tablica[i*4 + j].setPawn(2);
            }
        }
        buttons[0] = findViewById(R.id.button41);
        buttons[1] = findViewById(R.id.button42);
        buttons[2] = findViewById(R.id.button43);
        buttons[3] = findViewById(R.id.button44);
        buttons[4] = findViewById(R.id.but1);
        buttons[5] = findViewById(R.id.but2);
        buttons[6] = findViewById(R.id.but3);
        buttons[7] = findViewById(R.id.but4);
        buttons[8] = findViewById(R.id.button37);
        buttons[9] = findViewById(R.id.button39);
        buttons[10] = findViewById(R.id.button40);
        buttons[11] = findViewById(R.id.button38);
        buttons[12] = findViewById(R.id.button33);
        buttons[13] = findViewById(R.id.button34);
        buttons[14] = findViewById(R.id.button35);
        buttons[15] = findViewById(R.id.button36);
        buttons[16] = findViewById(R.id.button29);
        buttons[17] = findViewById(R.id.button30);
        buttons[18] = findViewById(R.id.button31);
        buttons[19] = findViewById(R.id.button32);
        buttons[20] = findViewById(R.id.button25);
        buttons[21] = findViewById(R.id.button26);
        buttons[22] = findViewById(R.id.button27);
        buttons[23] = findViewById(R.id.button28);
        buttons[24] = findViewById(R.id.button21);
        buttons[25] = findViewById(R.id.button24);
        buttons[26] = findViewById(R.id.button23);
        buttons[27] = findViewById(R.id.button22);
        buttons[28] = findViewById(R.id.button17);
        buttons[29] = findViewById(R.id.button18);
        buttons[30] = findViewById(R.id.button19);
        buttons[31] = findViewById(R.id.button20);
        wyswietlPlansze();
    }

    //metoda wyświetlająca planszę
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void wyswietlPlansze(){
        //select = false;
        //hit = false;
        for (int i = 0; i < 32; i++){
            if (nrGracza == 1){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (tablica[i].getPawn() == 1) {
                        buttons[i].setForeground(getDrawable(R.drawable.pawn_white_foreground));
                        buttons[i].setBackgroundColor(Color.parseColor("#03A9F4"));
                    }
                    else if (tablica[i].getPawn() == 2) {
                        buttons[i].setForeground(getDrawable(R.drawable.pawn_black_foreground));
                        buttons[i].setBackgroundColor(Color.parseColor("#03A9F4"));
                    }
                    else if (tablica[i].getPawn() == 0) {
                        buttons[i].setForeground(null);
                        buttons[i].setBackgroundColor(Color.parseColor("#03A9F4"));
                    }
                    else if (tablica[i].getPawn() == 3) {
                        buttons[i].setForeground(getDrawable(R.drawable.queen_white_foreground));
                        buttons[i].setBackgroundColor(Color.parseColor("#03A9F4"));
                    }
                    else{
                        buttons[i].setForeground(getDrawable(R.drawable.queen_black_foreground));
                        buttons[i].setBackgroundColor(Color.parseColor("#03A9F4"));
                    }
                }
            }
            else{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (tablica[i].getPawn() == 1) {
                        buttons[31-i].setForeground(getDrawable(R.drawable.pawn_white_foreground));
                        buttons[31-i].setBackgroundColor(Color.parseColor("#03A9F4"));
                    }
                    else if (tablica[i].getPawn() == 2) {
                        buttons[31-i].setForeground(getDrawable(R.drawable.pawn_black_foreground));
                        buttons[31-i].setBackgroundColor(Color.parseColor("#03A9F4"));
                    }
                    else if (tablica[i].getPawn() == 0) {
                        buttons[31-i].setForeground(null);
                        buttons[31-i].setBackgroundColor(Color.parseColor("#03A9F4"));
                    }
                    else if (tablica[i].getPawn() == 3) {
                        buttons[31-i].setForeground(getDrawable(R.drawable.queen_white_foreground));
                        buttons[31-i].setBackgroundColor(Color.parseColor("#03A9F4"));
                    }
                    else{
                        buttons[31-i].setForeground(getDrawable(R.drawable.queen_black_foreground));
                        buttons[31-i].setBackgroundColor(Color.parseColor("#03A9F4"));
                    }
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    //metoda obsługująca wciśnięcie przycisku
    public void buttonClicked(View view){

        int index = -1;

        if (nrGracza == 1){
            for (int i=0; i<32; i++){
                if (buttons[i] == view){
                    index = i;
                    break;
                }
            }
            System.out.println("x = " + tablica[index].getX() + " a y = " + tablica[index].getY() + " a pionek = " + tablica[index].getPawn());
            if ((tablica[index].getPawn() == nrGracza || tablica[index].getPawn() == nrGracza+2) && !hit){
                if (((pawnCanMove(tablica[index]) || pawnCanHit(tablica[index])) && tablica[index].getPawn()==nrGracza) || (tablica[index].getPawn() == nrGracza+2 && (queenCanHit(tablica[index]) || queenCanMove(tablica[index])))){
                    wyswietlPlansze();
                    select = true;
                    selectField = tablica[index];
                    lightPath(selectField);
                }
            }
            else if (select){
                targetField = tablica[index];
                if (targetField == selectField && hit){
                    select = false;
                    hit = false;
                    System.out.println("Zakończono mimo możliwości kolejnego bicia!");
                    endTurn();
                }
                if (checkFieldPawn(selectField, targetField) && selectField.getPawn() == nrGracza){
                    if (playerCanAttack()){
                        Toast.makeText(this,"Możliwe bicie!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        targetField.setPawn(nrGracza);
                        selectField.setPawn(0);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            select = false;
                            hit = false;
                            if (targetField.getY()==8) targetField.setPawn(nrGracza + 2);
                            System.out.println("Wykonano ruch!");
                            endTurn();
                        }
                    }
                }
                else if (checkHitPawn(selectField,targetField) && selectField.getPawn() == nrGracza){
                    destroyPawn(selectField,targetField);
                    targetField.setPawn(nrGracza);
                    selectField.setPawn(0);
                    hit = true;
                    if (targetField.getY()==8) targetField.setPawn(nrGracza + 2);
                    if (!pawnCanHit(targetField) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        select = false;
                        hit = false;
                        System.out.println("Kolejne bicie niemożliwe, koniec tury!");
                        endTurn();
                    }
                    else{
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            wyswietlPlansze();
                        }
                        System.out.println("Możliwe kolejne bicie!");
                        selectField = targetField;
                        lightPath(selectField);
                    }
                }
                //KRÓLÓWKI!!!
                else if (checkFieldQueen(selectField, targetField) && selectField.getPawn() == nrGracza+2){
                    if (playerCanAttack()){
                        Toast.makeText(this,"Możliwe bicie!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        targetField.setPawn(nrGracza+2);
                        selectField.setPawn(0);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            select = false;
                            hit = false;
                            System.out.println("Wykonano ruch!");
                            endTurn();
                        }
                    }
                }
                else if (checkHitQueen(selectField,targetField) && selectField.getPawn() == nrGracza+2){
                    destroyPawn(selectField,targetField);
                    targetField.setPawn(nrGracza+2);
                    selectField.setPawn(0);
                    hit = true;
                    if (!queenCanHit(targetField) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        select = false;
                        hit = false;
                        System.out.println("Kolejne bicie niemożliwe, koniec tury!");
                        endTurn();
                    }
                    else{
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            wyswietlPlansze();
                        }
                        System.out.println("Możliwe kolejne bicie!");
                        selectField = targetField;
                        lightPath(selectField);
                    }
                }
            }
        }

        else{
            for (int i=0; i<32; i++){
                if (buttons[i] == view){
                    index = 31-i;
                    break;
                }
            }
            System.out.println("x = " + tablica[index].getX() + " a y = " + tablica[index].getY() + " a pionek = " + tablica[index].getPawn());

            if ((tablica[index].getPawn() == nrGracza || tablica[index].getPawn() == nrGracza+2) && !hit){
                if (((pawnCanMove(tablica[index]) || pawnCanHit(tablica[index])) && tablica[index].getPawn()==nrGracza) || (tablica[index].getPawn() == nrGracza+2 && (queenCanHit(tablica[index]) || queenCanMove(tablica[index])))){
                    wyswietlPlansze();
                    select = true;
                    selectField = tablica[index];
                    lightPath(selectField);
                }
            }
            else if (select){
                targetField = tablica[index];
                if (targetField == selectField && hit) {
                    select = false;
                    hit = false;
                    endTurn();
                }
                if (checkFieldPawn(selectField, targetField) && selectField.getPawn() == nrGracza){
                    if (playerCanAttack()){
                        Toast.makeText(this,"Możliwe bicie!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        targetField.setPawn(nrGracza);
                        selectField.setPawn(0);
                        if (targetField.getY()==8) targetField.setPawn(nrGracza + 2);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            endTurn();
                        }
                    }
                }
                else if (checkHitPawn(selectField,targetField) && selectField.getPawn() == nrGracza){
                    destroyPawn(selectField,targetField);
                    targetField.setPawn(nrGracza);
                    selectField.setPawn(0);
                    hit = true;
                    if (targetField.getY()==8) targetField.setPawn(nrGracza + 2);
                    if (!pawnCanHit(targetField) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        select = false;
                        hit = false;
                        endTurn();
                    }
                    else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            wyswietlPlansze();
                        }
                        selectField = targetField;
                        lightPath(selectField);
                    }
                }
                //KRÓLÓWKI!!!
                else if (checkFieldQueen(selectField, targetField) && selectField.getPawn() == nrGracza+2){
                    if (playerCanAttack()){
                        Toast.makeText(this,"Możliwe bicie!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        targetField.setPawn(nrGracza+2);
                        selectField.setPawn(0);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            select = false;
                            hit = false;
                            System.out.println("Wykonano ruch!");
                            endTurn();
                        }
                    }
                }
                else if (checkHitQueen(selectField,targetField) && selectField.getPawn() == nrGracza+2){
                    destroyPawn(selectField,targetField);
                    targetField.setPawn(nrGracza+2);
                    selectField.setPawn(0);
                    hit = true;
                    if (!queenCanHit(targetField) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        select = false;
                        hit = false;
                        System.out.println("Kolejne bicie niemożliwe, koniec tury!");
                        endTurn();
                    }
                    else{
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            wyswietlPlansze();
                        }
                        System.out.println("Możliwe kolejne bicie!");
                        selectField = targetField;
                        lightPath(selectField);
                    }
                }
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void endTurn(){
        if (nrGracza == 1) nrGracza=2;
        else nrGracza = 1;
        if (!canPlayerPlay()){
            Toast.makeText(this, "Gracz nr " + nrGracza + " przegrał!",Toast.LENGTH_SHORT).show();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            wyswietlPlansze();
        }
    }

    public boolean canPlayerPlay(){
        for (int i=0; i<32; i++){
            if (tablica[i].getPawn()==nrGracza){
                if (pawnCanMove(tablica[i]) || pawnCanHit(tablica[i])) return true;
            }
            if (tablica[i].getPawn()==nrGracza+2){
                if (queenCanMove(tablica[i]) || queenCanHit(tablica[i])) return true;
            }
        }
        return false;
    }

    public void lightPath(MyField pole){
        int index = getIndex(pole);
        int x = pole.getX();
        int y = pole.getY();
        buttons[index].setBackgroundColor(Color.parseColor("#00DC29"));
        if (pole.getPawn()==nrGracza){
            if(pawnCanHit(pole)){
                if (x>1){
                    if (getFieldFromAxis(x-1, y+1).getPawn()!=nrGracza && getFieldFromAxis(x-1, y+1).getPawn()!=nrGracza+2 && getFieldFromAxis(x-1,y+1).getPawn()!=0){
                        if (getFieldFromAxis(x-2,y+2).getPawn()==0) buttons[getIndex(getFieldFromAxis(x-2,y+2))].setBackgroundColor(Color.parseColor("#DC0005"));
                    }
                }
                if (x<8){
                    if (getFieldFromAxis(x+1, y+1).getPawn()!=nrGracza && getFieldFromAxis(x+1, y+1).getPawn()!=nrGracza+2 && getFieldFromAxis(x+1,y+1).getPawn()!=0){
                        if (getFieldFromAxis(x+2,y+2).getPawn()==0) buttons[getIndex(getFieldFromAxis(x+2,y+2))].setBackgroundColor(Color.parseColor("#DC0005"));
                    }
                }
            }
            else if (pawnCanMove(pole)){
                if (x>1){
                    if(getFieldFromAxis(x-1, y+1).getPawn()==0){
                        buttons[getIndex(getFieldFromAxis(x-1,y+1))].setBackgroundColor(Color.parseColor("#00DC29"));
                    }
                }
                if (x<8){
                    if(getFieldFromAxis(x+1, y+1).getPawn()==0){
                        buttons[getIndex(getFieldFromAxis(x+1,y+1))].setBackgroundColor(Color.parseColor("#00DC29"));
                    }
                }
            }
        }
        else if (pole.getPawn()==nrGracza+2){
            if (queenCanHit(pole)){
                int iloscPionkowPodRzad = 0;
                MyField poleTestowe = new MyField();

                if (x>2 && y<7){
                    int x1 = x;
                    int y1 = y;
                    while (x1>1 && y1<8){
                        x1--;
                        y1++;
                        if (getFieldFromAxis(x1,y1).getPawn()!=0){
                            if (getFieldFromAxis(x1,y1).getPawn()==nrGracza || getFieldFromAxis(x1,y1).getPawn()==nrGracza+2) break;
                            else{
                                iloscPionkowPodRzad++;
                                if (iloscPionkowPodRzad>1) break;
                            }
                        }
                        else{
                            if (iloscPionkowPodRzad==1){
                                index = getIndex(getFieldFromAxis(x1,y1));
                                buttons[index].setBackgroundColor(Color.parseColor("#DC0005"));
                            }
                        }
                    }
                }

                if (x>2 && y>2){
                    iloscPionkowPodRzad = 0;
                    int x1 = x;
                    int y1 = y;
                    while (x1>1 && y1>1){
                        x1--;
                        y1--;
                        if (getFieldFromAxis(x1,y1).getPawn()!=0){
                            if (getFieldFromAxis(x1,y1).getPawn()==nrGracza || getFieldFromAxis(x1,y1).getPawn()==nrGracza+2) break;
                            else{
                                iloscPionkowPodRzad++;
                                if (iloscPionkowPodRzad>1) break;
                            }
                        }
                        else{
                            if (iloscPionkowPodRzad==1){
                                index = getIndex(getFieldFromAxis(x1,y1));
                                buttons[index].setBackgroundColor(Color.parseColor("#DC0005"));
                            }
                        }
                    }
                }

                if (x<7 && y<7){
                    iloscPionkowPodRzad = 0;
                    int x1 = x;
                    int y1 = y;
                    while (x1<8 && y1<8){
                        x1++;
                        y1++;
                        if (getFieldFromAxis(x1,y1).getPawn()!=0){
                            if (getFieldFromAxis(x1,y1).getPawn()==nrGracza || getFieldFromAxis(x1,y1).getPawn()==nrGracza+2) break;
                            else{
                                iloscPionkowPodRzad++;
                                if (iloscPionkowPodRzad>1) break;
                            }
                        }
                        else{
                            if (iloscPionkowPodRzad==1){
                                index = getIndex(getFieldFromAxis(x1,y1));
                                buttons[index].setBackgroundColor(Color.parseColor("#DC0005"));
                            }
                        }
                    }
                }

                if (x<7 && y>2){
                    iloscPionkowPodRzad = 0;
                    int x1 = x;
                    int y1 = y;
                    while (x1<8 && y1>1){
                        x1++;
                        y1--;
                        if (getFieldFromAxis(x1,y1).getPawn()!=0){
                            if (getFieldFromAxis(x1,y1).getPawn()==nrGracza || getFieldFromAxis(x1,y1).getPawn()==nrGracza+2) break;
                            else{
                                iloscPionkowPodRzad++;
                                if (iloscPionkowPodRzad>1) break;
                            }
                        }
                        else{
                            if (iloscPionkowPodRzad==1){
                                index = getIndex(getFieldFromAxis(x1,y1));
                                buttons[index].setBackgroundColor(Color.parseColor("#DC0005"));
                            }
                        }
                    }
                }
            }
            else if (queenCanMove(pole)){
                int x1 = x;
                int y1 = y;
                while (x1>1 && y1<8){
                    x1--;
                    y1++;
                    if (getFieldFromAxis(x1, y1).getPawn()==0){
                        index = getIndex(getFieldFromAxis(x1,y1));
                        buttons[index].setBackgroundColor(Color.parseColor("#00DC29"));
                    }
                    else break;
                }
                x1 = x;
                y1 = y;
                while (x1<8 && y1<8){
                    x1++;
                    y1++;
                    if (getFieldFromAxis(x1, y1).getPawn()==0){
                        index = getIndex(getFieldFromAxis(x1,y1));
                        buttons[index].setBackgroundColor(Color.parseColor("#00DC29"));
                    }
                    else break;
                }
                x1 = x;
                y1 = y;
                while (y1>1 && x1<8){
                    x1++;
                    y1--;
                    if (getFieldFromAxis(x1, y1).getPawn()==0){
                        index = getIndex(getFieldFromAxis(x1,y1));
                        buttons[index].setBackgroundColor(Color.parseColor("#00DC29"));
                    }
                    else break;
                }
                x1 = x;
                y1 = y;
                while (x1>1 && y1>1){
                    x1--;
                    y1--;
                    if (getFieldFromAxis(x1, y1).getPawn()==0){
                        index = getIndex(getFieldFromAxis(x1,y1));
                        buttons[index].setBackgroundColor(Color.parseColor("#00DC29"));
                    }
                    else break;
                }
            }
        }
    }

    public int getIndex(MyField pole){
        for (int i = 0; i<32; i++){
            if (tablica[i] == pole) return (nrGracza==1 ? i : 31-i);
        }
        return 32;
    }

    public MyField getFieldFromAxis(int x, int y){
        for(MyField pole: tablica){
            if (pole.getX() == x && pole.getY() == y) return pole;
        }
        return null;
    }

    public boolean pawnCanMove(MyField pole){
        int x = pole.getX();
        int y = pole.getY();
        MyField polePoLewej = new MyField();
        MyField polePoPrawej = new MyField();
        if (x>1 && y<8){
            polePoLewej = getFieldFromAxis(x-1, y+1);
            if (polePoLewej.getPawn() == 0) return true;
        }
        if (x<8 && y<8){
            polePoPrawej = getFieldFromAxis(x+1, y+1);
            if (polePoPrawej.getPawn() == 0) return true;
        }
        return false;
    }

    public boolean pawnCanHit(MyField pole){
        int x = pole.getX();
        int y = pole.getY();
        MyField polePoLewej = new MyField();
        MyField polePoPrawej = new MyField();

        if (x>2 && y<7){
            polePoLewej = getFieldFromAxis(x-1,y+1);
            if (polePoLewej.getPawn()!=nrGracza && polePoLewej.getPawn()!=nrGracza+2 && polePoLewej.getPawn()!=0){
                int x1 = polePoLewej.getX();
                int y1 = polePoLewej.getY();
                polePoLewej = getFieldFromAxis(x1-1, y1+1);
                if (polePoLewej.getPawn()==0) return true;
            }
        }

        if (x<7 && y<7){
            polePoPrawej = getFieldFromAxis(x+1,y+1);
            if (polePoPrawej.getPawn()!=nrGracza && polePoPrawej.getPawn()!=nrGracza+2 && polePoPrawej.getPawn()!=0){
                int x1 = polePoPrawej.getX();
                int y1 = polePoPrawej.getY();
                polePoPrawej = getFieldFromAxis(x1+1, y1+1);
                if (polePoPrawej.getPawn()==0) return true;
            }
        }

        return false;
    }

    public boolean queenCanMove(MyField pole){
        int x = pole.getX();
        int y = pole.getY();
        MyField poleTestowe = new MyField();
        if (x>1 && y<8){
            poleTestowe = getFieldFromAxis(x-1, y+1);
            if (poleTestowe.getPawn() == 0) return true;
        }
        if (x<8 && y<8){
            poleTestowe = getFieldFromAxis(x+1, y+1);
            if (poleTestowe.getPawn() == 0) return true;
        }
        if (y>1 && x<8){
            poleTestowe = getFieldFromAxis(x+1, y-1);
            if (poleTestowe.getPawn() == 0) return true;
        }
        if (x>1 && y>1){
            poleTestowe = getFieldFromAxis(x-1, y-1);
            if (poleTestowe.getPawn() == 0) return true;
        }
        return false;
    }

    public boolean queenCanHit(MyField pole){
        int x = pole.getX();
        int y = pole.getY();
        int iloscPionkowPodRzad = 0;
        MyField poleTestowe = new MyField();

        if (x>2 && y<7){
            int x1 = x;
            int y1 = y;
            while (x1>1 && y1<8){
                x1--;
                y1++;
                if (getFieldFromAxis(x1,y1).getPawn()!=0){
                    if (getFieldFromAxis(x1,y1).getPawn()==nrGracza || getFieldFromAxis(x1,y1).getPawn()==nrGracza+2) break;
                    else{
                        iloscPionkowPodRzad++;
                        if (iloscPionkowPodRzad>1) break;
                    }
                }
                else{
                    if (iloscPionkowPodRzad==1) return true;
                }
            }
        }

        if (x>2 && y>2){
            iloscPionkowPodRzad = 0;
            int x1 = x;
            int y1 = y;
            while (x1>1 && y1>1){
                x1--;
                y1--;
                if (getFieldFromAxis(x1,y1).getPawn()!=0){
                    if (getFieldFromAxis(x1,y1).getPawn()==nrGracza || getFieldFromAxis(x1,y1).getPawn()==nrGracza+2) break;
                    else{
                        iloscPionkowPodRzad++;
                        if (iloscPionkowPodRzad>1) break;
                    }
                }
                else{
                    if (iloscPionkowPodRzad==1) return true;
                }
            }
        }

        if (x<7 && y<7){
            iloscPionkowPodRzad = 0;
            int x1 = x;
            int y1 = y;
            while (x1<8 && y1<8){
                x1++;
                y1++;
                if (getFieldFromAxis(x1,y1).getPawn()!=0){
                    if (getFieldFromAxis(x1,y1).getPawn()==nrGracza || getFieldFromAxis(x1,y1).getPawn()==nrGracza+2) break;
                    else{
                        iloscPionkowPodRzad++;
                        if (iloscPionkowPodRzad>1) break;
                    }
                }
                else{
                    if (iloscPionkowPodRzad==1) return true;
                }
            }
        }

        if (x<7 && y>2){
            iloscPionkowPodRzad = 0;
            int x1 = x;
            int y1 = y;
            while (x1<8 && y1>1){
                x1++;
                y1--;
                if (getFieldFromAxis(x1,y1).getPawn()!=0){
                    if (getFieldFromAxis(x1,y1).getPawn()==nrGracza || getFieldFromAxis(x1,y1).getPawn()==nrGracza+2) break;
                    else{
                        iloscPionkowPodRzad++;
                        if (iloscPionkowPodRzad>1) break;
                    }
                }
                else{
                    if (iloscPionkowPodRzad==1) return true;
                }
            }
        }
        return false;
    }

    public boolean checkFieldPawn(MyField start, MyField end){
        if (end.getPawn()!=0) return false;
        int startX = start.getX();
        int startY = start.getY();
        int endX = end.getX();
        int endY = end.getY();
        if ((startX-1 == endX || startX+1 == endX) && startY+1 == endY) return true;
        return false;
    }

    public boolean checkFieldQueen(MyField start, MyField end){
        if (end.getPawn()!=0) return false;
        int startX = start.getX();
        int startY = start.getY();
        int endX = end.getX();
        int endY = end.getY();
        if (startX - endX == startY - endY || startX - endX + (startY - endY) == 0){
            int diffX = endX - startX;
            diffX = diffX >= 0 ? 1 : -1;
            int diffY = endY - startY;
            diffY = diffY >= 0 ? 1 : -1;
            while (startX != endX){
                startX += diffX;
                startY += diffY;
                if (getFieldFromAxis(startX, startY).getPawn()!=0) return false;
            }
            return true;
        }
        else return false;
    }

    public boolean checkHitQueen(MyField start, MyField end){
        if (end.getPawn()!=0) return false;
        int startX = start.getX();
        int startY = start.getY();
        int endX = end.getX();
        int endY = end.getY();
        int iloscPionkowPodRzad = 0;
        if (startX - endX == startY - endY || startX - endX + (startY - endY) == 0){
            int diffX = endX - startX;
            diffX = diffX >= 0 ? 1 : -1;
            int diffY = endY - startY;
            diffY = diffY >= 0 ? 1 : -1;
            while (startX != endX){
                startX += diffX;
                startY += diffY;
                if (getFieldFromAxis(startX, startY).getPawn()!=0){
                    if (getFieldFromAxis(startX, startY).getPawn()!=nrGracza && getFieldFromAxis(startX, startY).getPawn()!=nrGracza+2){
                        iloscPionkowPodRzad++;
                        if (iloscPionkowPodRzad>1) return false;
                    }
                    else return false;
                }
                else if (iloscPionkowPodRzad==1) return true;
            }
        }
        return false;
    }

    public boolean checkHitPawn(MyField start, MyField end){
        if (end.getPawn()!=0) return false;
        int startX = start.getX();
        int startY = start.getY();
        int endX = end.getX();
        int endY = end.getY();
        if (startX-2 == endX && startY+2 == endY){
            MyField temp = new MyField();
            temp = getFieldFromAxis(startX-1, startY+1);
            if (temp.getPawn() != nrGracza && temp.getPawn() != nrGracza+2 && temp.getPawn() != 0) return true;
        }
        if (startX+2 == endX && startY+2 == endY){
            MyField temp = new MyField();
            temp = getFieldFromAxis(startX+1, startY+1);
            if (temp.getPawn() != nrGracza && temp.getPawn() != nrGracza+2 && temp.getPawn() != 0) return true;
        }
        return false;
    }

    public void destroyPawn(MyField start, MyField end){
        int startX = start.getX();
        int startY = start.getY();
        int endX = end.getX();
        int endY = end.getY();
        int diffY = endY - startY;
        diffY = diffY>=0 ? 1 : -1;
        int diffX = endX - startX;
        diffX = diffX>=0 ? 1 : -1;
        while (startX != endX){
            startX += diffX;
            startY += diffY;
            getFieldFromAxis(startX, startY).setPawn(0);
        }
    }

    public boolean playerCanAttack(){
        for (int i=0; i<32; i++){
            if ((tablica[i].getPawn() == nrGracza && pawnCanHit(tablica[i])) || (tablica[i].getPawn() == nrGracza +2 && queenCanHit(tablica[i]))) return true;
        }
        return false;
    }

    class MyField{
        protected int x,y,pawn;

        public MyField(int x, int y, int pawn){
            this.x = x;
            this.y = y;
            this.pawn = pawn;
        }

        public MyField(){
            this.x = 0;
            this.y = 0;
            this.pawn = 5;
        }

        public void setPawn(int pawn){
            this.pawn = pawn;
        }

        public int getPawn(){
            return this.pawn;
        }

        public int getX(){
            if (nrGracza == 1) return this.x;
            else return 9-this.x;
        }

        public int getY(){
            if (nrGracza == 1) return this.y;
            else return 9-this.y;
        }
    }

    public static void stringToBoard (String input){
        for (int i = 0; i < 32; i++){
            tablica[i].setPawn(Character.getNumericValue(input.charAt(i)));
        }
    }

    public static String boardToString(){
        String result = "";
        for (int i = 0; i < 32; i++){
            result += Integer.toString(tablica[i].pawn);
        }
        return result;
    }
}