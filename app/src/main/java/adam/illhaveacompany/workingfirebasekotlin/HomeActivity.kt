package adam.illhaveacompany.workingfirebasekotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    var mFirebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mFirebaseAuth = FirebaseAuth.getInstance()

        tvHomeUserName.text = mFirebaseAuth!!.currentUser?.email

        btnLogOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this@HomeActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}