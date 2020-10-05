package adam.illhaveacompany.workingfirebasekotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.btnSignIn
import kotlinx.android.synthetic.main.activity_main.etLiEmail
import kotlinx.android.synthetic.main.activity_main.etLiPassword
import kotlinx.android.synthetic.main.activity_main.tvSignUp2

/*
- Email auth only takes an actual email so I have to put in stuff for that
- I might want to not switch activities every time
- It might be a MASSIVE headache if I have someone logged in without the ability to reach firebase, either the database or login info.
    What if the system is trying to load up points from firebase with a bad connection to firebase? that would suuuck. I might want to do an internet
    check in the login activity and if that works then check if they can get the user data and if that works then immediately jump to the next activity
*/


class MainActivity : AppCompatActivity() {

    var mFirebaseAuth: FirebaseAuth? = null
    private var mAuthStateListener: FirebaseAuth.AuthStateListener? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mFirebaseAuth = FirebaseAuth.getInstance()

        //Sign Up Page

        btnSignUp.setOnClickListener {
            val email = etSuEmail.text.toString()
            val pwd = etSuPassword.text.toString()
            val verifyPwd = etSuVerifyPassword.text.toString()
            if (email.isEmpty()) {
                etSuEmail.error = "Please enter email"
                etSuEmail.requestFocus()
            } else if (pwd.isEmpty()) {
                etSuPassword.error = "Please enter password"
                etSuPassword.requestFocus()
                //Possible error - didn't follow the video cause I don't like her programming style
            }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                etSuEmail.error = "Email must be in correct format (ex email@gmail.com)"
                etSuEmail.requestFocus()
            } else if(pwd != verifyPwd){
                etSuVerifyPassword.error = "Passwords must match"
                etSuVerifyPassword.requestFocus()
            }else {
                mFirebaseAuth!!.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(
                    this
                ) { task ->
                    if (!task.isSuccessful) {
                        Toast.makeText(
                            this,
                            "Unsuccessful!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        startActivity(Intent(this, HomeActivity::class.java))
                    }
                }
            }
        }

        //Sign In Page
        mAuthStateListener = object : FirebaseAuth.AuthStateListener {
            var mFirebaseUser = mFirebaseAuth!!.currentUser
            override fun onAuthStateChanged(firebaseAuth: FirebaseAuth) {
                if (mFirebaseUser != null) {
                    Toast.makeText(this@MainActivity, "You are logged in", Toast.LENGTH_SHORT)
                        .show()
                    val i = Intent(this@MainActivity, HomeActivity::class.java)
                    startActivity(i)
                }
            }
        }

        btnSignIn.setOnClickListener{
            val email = etLiEmail.text.toString()
            val pwd = etLiPassword.text.toString()
            if (email.isEmpty()) {
                etLiEmail.error = "Please enter email id"
                etLiEmail.requestFocus()
            } else if (pwd.isEmpty()) {
                etLiPassword.error = "Please enter password"
                etLiPassword.requestFocus()
                //Possible error - didn't follow the video cause I don't like her programming style
            }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                etLiEmail.error = "Email must be in correct format (username@email.com)"
                etLiEmail.requestFocus()
            } else {
                mFirebaseAuth!!.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(this@MainActivity) { task ->
                    if (!task.isSuccessful) {
                        Toast.makeText(this@MainActivity, "Login Unsuccessful. Ensure that the username and password is correct (passwords are case-sensitive)",
                            Toast.LENGTH_SHORT).show()
                    } else {
                        startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                    }
                }
            }
        }



        tvLogin.setOnClickListener {
            llSignUp.visibility = View.GONE
            llSignIn.visibility = View.VISIBLE
        }

        tvSignUp2.setOnClickListener {
            llSignIn.visibility = View.GONE
            llSignUp.visibility = View.VISIBLE
        }

    }


    override fun onStart() {
        super.onStart()
        mFirebaseAuth!!.addAuthStateListener(mAuthStateListener!!)
    }
}