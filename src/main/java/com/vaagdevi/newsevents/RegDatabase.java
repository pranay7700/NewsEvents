package com.vaagdevi.newsevents;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegDatabase {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    public String Reguseremail, Reguserpassword;

    public RegDatabase() {

    }

    public RegDatabase(String Reguseremail, String Reguserpassword) {

        this.Reguseremail = Reguseremail;
        this.Reguserpassword = Reguserpassword;
    }
}