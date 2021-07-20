
package example.micronaut.service;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import io.micronaut.context.env.Environment;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Singleton
public class FireStoreConfig {


    //    @Property(name =  "firebase.credential.path")
    private String credentialPath = "src/main/data/big-synthesizer-250605-0d15f91754ab.json";

    public FireStoreConfig() {}

    public Firestore getConf() throws IOException {

        InputStream serviceAccount = new FileInputStream(credentialPath);
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(credentials).build();

        FirebaseApp.initializeApp(options);

        return FirestoreClient.getFirestore();

    }


}
