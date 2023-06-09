package com.example.localfarm.utils;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.localfarm.R;
import com.example.localfarm.models.command.Command;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NotifHandler extends FirebaseMessagingService {
    private static int notificationIdCounter = 0;

    private static NotificationChannel channel = null;
    private static final String CHANNEL_ID = "my_channel_id";
    private static final String CHANNEL_NAME = "My Channel";
    private static final String CHANNEL_DESCRIPTION = "My Channel Description";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void createNotification(Context context, Command command) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        // Vérifiez si la version Android est égale ou supérieure à Android 8.0 (Oreo)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Créez un canal de notification
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESCRIPTION);

            // Ajoutez le canal de notification au gestionnaire de notifications
            notificationManager.createNotificationChannel(channel);
        }
        String title ="";
        String body = "";
        // Créez la notification
        switch (command.getStatus()) {
            case Waiting:
                title = "Nouvelle commande!";
                body = "Vous avez recu une nouvelle commande!";
                break;
            case Accepted:
                title = "Votre commande a été accepter!";
                body = "Le producteur a accepté votre commande!";
                break;
            case Completed:
                title = "Nouvelle commande!";
                body = "Votre commande a été récuperer!";
                break;
            case Canceled:
                title = "Commande annulée!";
                body = "Une de vos commande a été annulé. :(";
                break;
            default:
                return;
        }

        Notification.Builder builder = new Notification.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.icon_localfarm)
                .setContentTitle(title)
                .setContentText(body);

        // Affichez la notification
        notificationManager.notify(notificationIdCounter++, builder.build());
    }

    // Envoyer une notification à un appareil cible
    public void sendNotificationToDevice(String idReceveur, Command command) {
        //On parcours la liste de TOUT les utilisateurs
        FirebaseDatabase.getInstance().getReference("account").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                System.out.println("DataBase - success");
                DataSnapshot dataSnapshot = task.getResult();

                //On parcours tout les utilisateur et on regarde la valeur qu'ils ont dans leur attribut id
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String idFound = userSnapshot.child("id").getValue(String.class);
                    System.out.println("finding " + idFound + "...");

                    // Pour chaque utilisateur on regarde si il a l'id donnée en parametre de fonction
                    if (idFound != null && idFound.equals(idReceveur)) {
                        System.out.println("Receiver found ! ");

                        //Liste des tokens auquels envoye une notif
                        List<String> Tokens = new ArrayList<>();
                        //On remplis la liste
                        System.out.println("finding Tokens...");
                        for (DataSnapshot valuesTab : userSnapshot.child("ActiveToken").getChildren()) {
                            String value = valuesTab.getValue(String.class);
                            Tokens.add(value);
                            System.out.println("...found: " + value);
                        }

                        RemoteMessage notification;

                        // On envoie le message approprie a chaque token
                        switch (command.getStatus()) {
                            case Waiting:
                                sendNotifications(Tokens, "Nouvelle commande!", "Vous avez recu une nouvelle commande!");
                                break;
                            case Accepted:
                                sendNotifications(Tokens, "Votre commande a été accepter!", "Le producteur a accepté votre commande!");
                                break;
                            case Completed:
                                sendNotifications(Tokens, "Nouvelle commande!", "Votre commande a été récuperer!");
                                break;
                            case Canceled:
                                sendNotifications(Tokens, "Commande annulée!", "Une de vos commande a été annulé. :(");
                                break;
                            default:
                                return;
                        }

                        return;

                    }
                }
            } else {
                // Une erreur s'est produite lors de la récupération des données
            }
        });

    }

    public void sendNotificationToDevices(Command command) {
        sendNotificationToDevice(command.getBuyer().getId(), command);
        sendNotificationToDevice(command.getSeller().getId(), command);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
            // send notification
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody());
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        //TODO the token has changed
    }


    public void sendNotifications(List<String> toTokens, String title, String body) {
        /*for (String deviceToken : toTokens) {
            RemoteMessage notification = new RemoteMessage.Builder(deviceToken)
                    .addData("title", title)
                    .addData("message", body)
                    .build();
            System.out.println("Sending notifications! ");
            FirebaseMessaging.getInstance().send(notification);
        }
        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), ApplicationClass.channelid)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true);
        ApplicationClass.getNotificationManager().notify(++notificationIdCounter,notification.build());*/
        // Création du canal de notification (pour les appareils Android Oreo et supérieurs)



    }

}
