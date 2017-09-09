package br.com.giltech.agenda.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;
import android.widget.Toast;

import br.com.giltech.agenda.R;
import br.com.giltech.agenda.dao.AlunoDAO;

/**
 * Created by Gilciney Marques on 09/09/2017.
 */

public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context, "Mensagem recebida",Toast.LENGTH_SHORT).show();
        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");
        byte[] pdu = (byte[]) pdus[0];
        String formato = (String) intent.getSerializableExtra("format");
        SmsMessage message = SmsMessage.createFromPdu(pdu, formato);

        String telefone = message.getDisplayOriginatingAddress();

        AlunoDAO dao = new AlunoDAO(context);
        if(dao.isAluno(telefone)){
            Toast.makeText(context, "Mensagem recebida = "+telefone,Toast.LENGTH_SHORT).show();
            MediaPlayer m = MediaPlayer.create(context, R.raw.msg);
            m.start();
        }
        dao.close();
    }
}
