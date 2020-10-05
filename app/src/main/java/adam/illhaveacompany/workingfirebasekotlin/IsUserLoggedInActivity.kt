package adam.illhaveacompany.workingfirebasekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*

class IsUserLoggedInActivity : AppCompatActivity() {

    var mFirebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_is_user_logged_in)

        mFirebaseAuth = FirebaseAuth.getInstance()



    }


}