package mx.edu.ittepic.marcogaray.tpdm_u5_practica1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    ConexionBase base2;
    @Override
    public void onReceive(Context context, Intent intent) {
        base2 = new ConexionBase(context, "Base4", null, 1);
        insertar_datos();
        Bundle extras = intent.getExtras();
        Object[] pdus = (Object[]) extras.get("pdus");
        SmsMessage mensaje=SmsMessage.createFromPdu((byte[])pdus[0]);
        if(mensaje.getMessageBody().startsWith("MX") || mensaje.getMessageBody().startsWith("EU")){
            if(mensaje.getMessageBody().split("-").length == 2){
                String m =mensaje.getMessageBody().split("-")[1];
                //Toast.makeText(context, "Equipo: "+m+": "+buscaNombre(m),Toast.LENGTH_LONG).show();
                enviaSMS(mensaje.getOriginatingAddress(),"Equipo: "+m+": "+buscaNombre(m),context);
            }
        }
        else{
            //Toast.makeText(context, "El mensaje enviado no coincide con las caracteristicas solicitadas",Toast.LENGTH_LONG).show();
            enviaSMS(mensaje.getOriginatingAddress(),"El mensaje enviado no coincide con las caracteristicas solicitadas",context);
        }
    }

    public String buscaNombre(String no){
        try{
            SQLiteDatabase base = this.base2.getReadableDatabase();
            String[] nombres = {no};
            Cursor c = base.rawQuery("SELECT * FROM Equipos WHERE nombre = ?", nombres);
            System.out.println(c.getCount());
            if(c.moveToFirst()){
                return(c.getString(1));
            } else {
                return("No se encontraron coincidencias");
            }
        } catch (SQLiteException e){
            return (e.getMessage());
        }
    }

    private void enviaSMS(String t, String m, Context c) {
        try{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(t,null,m,null,null);
        }catch (Exception e){
            Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    void insertar_datos(){
        SQLiteDatabase db = this.base2.getWritableDatabase();
        db.execSQL("INSERT INTO Equipos VALUES('probando','Posición actual: x lugar general')");
        db.execSQL("INSERT INTO Equipos VALUES('TIGRES','¡Campeones! Posición actual: 2do lugar general')");
        db.execSQL("INSERT INTO Equipos VALUES('AMERICA','Posición actual: 5to lugar general')");
        db.execSQL("INSERT INTO Equipos VALUES('CHIVAS','Posición actual: 14vo lugar general')");
        db.execSQL("INSERT INTO Equipos VALUES('CRUZ AZUL','Posición actual: 4to lugar general')");
        db.execSQL("INSERT INTO Equipos VALUES('PUMAS','Posición actual: 15vo lugar general')");
        db.execSQL("INSERT INTO Equipos VALUES('ATLAS','Posición actual: 13vo lugar general')");
        db.execSQL("INSERT INTO Equipos VALUES('MONTERREY','Posición actual: 3ro lugar general')");
        db.execSQL("INSERT INTO Equipos VALUES('LEON','¡Fue el superlíder! Posición actual: 1er lugar general')");
        db.execSQL("INSERT INTO Equipos VALUES('SANTOS','Posición actual: 11vo lugar general')");
        db.execSQL("INSERT INTO Equipos VALUES('TOLUCA','Posición actual: 9no lugar general')");
        db.execSQL("INSERT INTO Equipos VALUES('NECAXA','Posición actual: 6to lugar general')");
        db.execSQL("INSERT INTO Equipos VALUES('MORELIA','Posición actual: 16vo lugar general')");
        db.execSQL("INSERT INTO Equipos VALUES('PACHUCA','Posición actual: 7mo lugar general')");
        db.execSQL("INSERT INTO Equipos VALUES('XOLOS','Posición actual: 8vo lugar general')");
        db.execSQL("INSERT INTO Equipos VALUES('VERACRUZ','Posición actual: 18vo lugar general')");
        db.execSQL("INSERT INTO Equipos VALUES('PUEBLA','Posición actual: 10mo lugar general')");
        db.execSQL("INSERT INTO Equipos VALUES('QUERETARO','Posición actual: 17mo lugar general')");
        db.execSQL("INSERT INTO Equipos VALUES('LOBOS','Posición actual: 12vo lugar general')");
        db.execSQL("INSERT INTO Equipos VALUES('SLP','¡Acaba de asender!')");
        db.execSQL("INSERT INTO Equipos VALUES('BARCELONA','El campeón de la liga española!')");
        db.execSQL("INSERT INTO Equipos VALUES('REAL MADRID','No ha ganado nada!')");
        db.execSQL("INSERT INTO Equipos VALUES('JUVENTUS','Campeón de la liga italiana, siuuuu!')");
        db.execSQL("INSERT INTO Equipos VALUES('LIVERPOOL','Campeón de la UEFA Champions League!')");

    }
}
