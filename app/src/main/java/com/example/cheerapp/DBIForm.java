package com.example.cheerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class DBIForm extends AppCompatActivity {
    public RadioGroup rbGroup;
    public TextView titutlo;
    public RadioButton rb1;
    public RadioButton rb2;
    public RadioButton rb3;
    public RadioButton rb4;
    public Button Confirm;
    public int contador = 0;
    public int puntaje = 0;


    ArrayList<PreguntasDBI> TPreguntas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbiform);
        Confirm = findViewById(R.id.button_confirm_next);
        PreguntasDB();
        titutlo = findViewById(R.id.TituloDBI);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        rb4 = findViewById(R.id.radio_button4);

        titutlo.setText(TPreguntas.get(contador).getTitulo());
        rb1.setText(TPreguntas.get(contador).getP1());
        rb2.setText(TPreguntas.get(contador).getP2());
        rb3.setText(TPreguntas.get(contador).getP3());
        rb4.setText(TPreguntas.get(contador).getP4());

        Confirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Siguiente();
            }
        });

    }

    private void Siguiente() {
        if(rb1.isChecked() == false && rb2.isChecked() == false && rb3.isChecked() == false && rb4.isChecked() == false){
            Toast.makeText(DBIForm.this,"No ha marcado respuesta",Toast.LENGTH_SHORT).show();
        } else {
            contador = contador+1;
            if(contador >= 21) {
                Intent i = new Intent(DBIForm.this,MainActivity.class);
                startActivity(i);
                //if puntaje es mayor a 19 se le pedira que busque ayuda profesional y se le mandara un mensaje a un amigo
            }
            titutlo.setText(TPreguntas.get(contador).getTitulo());
            rb1.setText(TPreguntas.get(contador).getP1());
            rb2.setText(TPreguntas.get(contador).getP2());
            rb3.setText(TPreguntas.get(contador).getP3());
            rb4.setText(TPreguntas.get(contador).getP4());
            if(rb1.isChecked() == true){
                puntaje += 0;
            }
            if(rb2.isChecked() == true){
                puntaje += 1;
            }
            if(rb3.isChecked() == true){
                puntaje += 2;
            }
            if(rb4.isChecked() == true){
                puntaje += 3;
            }

        }
    }


    private void PreguntasDB() {
        TPreguntas = new ArrayList<>();

        TPreguntas.add(new PreguntasDBI("Tristeza", "No me siento triste.", "Me siento triste gran parte del tiempo.","Estoy triste todo el tiempo.","Estoy tan triste o soy tan infeliz que no puedo\n" +
                "soportarlo."));
        TPreguntas.add(new PreguntasDBI("Pesimismo", "No estoy desalentado respecto de mi futuro.", "Me siento más desalentado respecto de mi\n" +
                "futuro que lo que solía estarlo.","No espero que las cosas funcionen para mí.","Siento que no hay esperanza para mi futuro y\n" +
                "que sólo puede empeorar."));
        TPreguntas.add(new PreguntasDBI("Fracaso", "No me siento un fracasado.", "He fracasado más de lo que hubiera debido.","Cuando miro hacia atrás veo muchos fracasos.","Siento que como persona soy un fracaso total."));

        TPreguntas.add(new PreguntasDBI("Pérdida de Placer", "Obtengo tanto placer como siempre por las\n" +
                "cosas de las que disfruto.", "No disfruto tanto de las cosas.","Obtengo muy poco placer de las cosas de las\n" +
                "que solía disfrutar.","No puedo obtener ningún placer de las cosas\n" +
                "de las que solía disfrutar."));
        TPreguntas.add(new PreguntasDBI("Sentimientos de Culpa", "No me siento particularmente culpable.", "Me siento culpable respecto de varias cosas\n" +
                "que he hecho o que debería haber hecho.","Me siento bastante culpable la mayor parte del\n" +
                "tiempo.","Me siento culpable todo el tiempo."));
        TPreguntas.add(new PreguntasDBI("Sentimientos de Castigo", "No siento que estoy siendo castigado.", "Siento que tal vez pueda ser castigado.","Espero ser castigado.","Siento que estoy siendo castigado."));

        TPreguntas.add(new PreguntasDBI("Disconformidad con Uno Mismo", "Siento acerca de mí lo mismo que siempre.", "He perdido la confianza en mí mismo.","Estoy decepcionado conmigo mismo.","No me gusto a mí mismo."));

        TPreguntas.add(new PreguntasDBI("Autocrítica", "No me critico ni me culpo más de lo habitual.", "Estoy más crítico conmigo mismo que lo que solía\n" +
                "estarlo.","Me critico a mí mismo por todos mis errores.","Me culpo a mí mismo por todo lo malo que sucede."));

        TPreguntas.add(new PreguntasDBI("Pensamiento o Deseos Suicidas", "No tengo ningún pensamiento de matarme.", "He tenido pensamientos de matarme, pero no lo\n" +
                "haría.","Querría matarme.","Me mataría si tuviera la oportunidad de hacerlo."));
        TPreguntas.add(new PreguntasDBI("Llanto", "No lloro más de lo que solía hacerlo.", "Lloro más de lo que solía hacerlo.","Lloro por cualquier pequeñez.","Siento ganas de llorar pero no puedo."));

        TPreguntas.add(new PreguntasDBI("Agitación", "No estoy más inquieto o tenso que lo habitual.", "Me siento más inquieto o tenso que lo habitual.","Estoy tan inquieto o agitado que me es difícil\n" +
                "quedarme quieto.","Estoy tan inquieto o agitado que tengo que estar\n" +
                "siempre en movimiento o haciendo algo."));

        TPreguntas.add(new PreguntasDBI("Pérdida de Interés", "No he perdido el interés en otras actividades o\n" +
                "personas.", "Estoy menos interesado que antes en otras\n" +
                "personas o cosas.","He perdido casi todo el interés en otras personas\n" +
                "o cosas.","Me es difícil interesarme por algo."));

        TPreguntas.add(new PreguntasDBI("Indecisión", "Tomo mis decisiones tan bien como siempre.", "Me resulta más difícil que de costumbre tomar\n" +
                "decisiones.","Encuentro mucha más dificultad que antes para\n" +
                "tomar decisiones.","Tengo problemas para tomar cualquier decisión."));

        TPreguntas.add(new PreguntasDBI("Desvalorización", "No siento que yo no sea valioso.", "No me considero a mí mismo tan valioso y útil\n" +
                "como solía considerarme.","Me siento menos valioso cuando me comparo\n" +
                "con otros.","Siento que no valgo nada."));

        TPreguntas.add(new PreguntasDBI("Pérdida de Energía", "Tengo tanta energía como siempre.", "Tengo menos energía que la que solía tener.","No tengo suficiente energía para hacer\n" +
                "demasiado.","No tengo energía suficiente para hacer nada."));

        TPreguntas.add(new PreguntasDBI("Cambios en los Hábitos de Sueño", "No he experimentado ningún cambio en mis\n" +
                "hábitos de sueño.", "Duermo un poco más/menos que lo habitual.","Duermo mucho más/menos que lo habitual.","Duermo la mayor parte del día/Duermo casi una hora al dia."));

        TPreguntas.add(new PreguntasDBI("Irritabilidad", "No estoy más irritable que lo habitual.", "Estoy más irritable que lo habitual.","Estoy mucho más irritable que lo habitual.","Estoy irritable todo el tiempo."));

        TPreguntas.add(new PreguntasDBI("Cambios en el Apetito", "No he experimentado ningún cambio en mi\n" +
                "apetito.", "Mi apetito es un poco menor/mayor que lo habitual.","Mi apetito es mucho menor/mayor que antes.","No tengo apetito en absoluto/Quiero comer todo el tiempo."));

        TPreguntas.add(new PreguntasDBI("Dificultad de concentración", "Puedo concentrarme tan bien como siempre.", "No puedo concentrarme tan bien como\n" +
                "habitualmente.","Me es difícil mantener la mente en algo por\n" +
                "mucho tiempo.","Encuentro que no puedo concentrarme en nada."));

        TPreguntas.add(new PreguntasDBI("Cansancio o Fatiga", "No estoy más cansado o fatigado que lo habitual.", "Me fatigo o me canso más fácilmente que lo\n" +
                "habitual.","Estoy demasiado fatigado o cansado para hacer\n" +
                "muchas de las cosas que solía hacer.","Estoy demasiado fatigado o cansado para hacer\n" +
                "la mayoría de las cosas que solía hacer."));

        TPreguntas.add(new PreguntasDBI("Pérdida de Interés en el Sexo", "No he notado ningún cambio reciente en mi\n" +
                "interés por el sexo", "Estoy menos interesado por el sexo de lo que\n" +
                "solía estarlo.","Ahora estoy mucho menos interesado en el sexo.","He perdido completamente el interés en el sexo."));
    }


}