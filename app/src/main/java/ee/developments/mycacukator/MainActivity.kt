package ee.developments.mycacukator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException
import java.math.RoundingMode
import kotlin.math.round

class MainActivity : AppCompatActivity() {

    private var tvOnCliced: TextView? = null
    private var tvDotOnCliced: TextView? = null

    private var lastnumeric: Boolean = false
    private var lastDot: Boolean = false
    private var lastoperator: Boolean = true

    private var limitDot : Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvOnCliced = findViewById(R.id.masc)
        tvDotOnCliced = findViewById(R.id.masc)
    }

    fun onDigit(view: View) {
        tvOnCliced?.append((view as Button).text)
        lastnumeric = true
        lastDot = false
        lastoperator = true
    }

    fun onClear(view: View) {
        tvOnCliced?.text = ""
    }

    fun onDot(view: View) {
        if (lastnumeric && !lastDot && limitDot == 0) {
            tvDotOnCliced?.append(".")
            lastnumeric = false
            lastDot = true
            limitDot = limitDot!! +1
        }
    }

    fun onOperator(view: View) {
        tvOnCliced?.text?.let {

            if (lastnumeric && !isOparatorAdded(it.toString()))
                tvOnCliced?.append((view as Button).text)
            lastnumeric = true
            lastDot = false
            lastoperator=false

            limitDot = 0
        }
    }

    fun isOparatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") ||
                    value.contains("+") ||
                    value.contains("-") ||
                    value.contains("*")

        }
    }

    fun onEqual(view: View) {
        if (lastnumeric&&lastoperator) {

            limitDot = 0

            var tvValue = tvOnCliced?.text.toString()
            var prefix = ""
            if (lastnumeric) {
                try {
                    if (tvValue.startsWith("-")) {
                        tvValue = tvValue.substring(1)
                    }
                    if (tvValue.contains("-")) {
                        val splitValue = tvValue.split("-")
                        var leftVlue = splitValue[0]
                        var rightVlue = splitValue[1]

                        if (prefix.isNotEmpty()) {
                            rightVlue = prefix + rightVlue
                        }

                        tvOnCliced?.text =
                            removeZeroAfterDot((leftVlue.toDouble() - rightVlue.toDouble()).toString())
                    } else if (tvValue.contains("+")) {
                        val splitValue = tvValue.split("+")
                        var leftVlue = splitValue[0]
                        var rightVlue = splitValue[1]

                        if (prefix.isNotEmpty()) {
                            rightVlue = prefix + rightVlue
                        }

                        tvOnCliced?.text =
                            removeZeroAfterDot((leftVlue.toDouble() + rightVlue.toDouble()).toString())
                    } else if (tvValue.contains("/")) {
                        val splitValue = tvValue.split("/")
                        var leftVlue = splitValue[0]
                        var rightVlue = splitValue[1]

                        if (prefix.isNotEmpty()) {
                            rightVlue = prefix + rightVlue
                        }

                        tvOnCliced?.text =
                            removeZeroAfterDot((leftVlue.toDouble() / rightVlue.toDouble()).toString())
                    } else if (tvValue.contains("*")) {
                        val splitValue = tvValue.split("*")
                        var leftVlue = splitValue[0]
                        var rightVlue = splitValue[1]

                        if (prefix.isNotEmpty()) {
                            rightVlue = prefix + rightVlue
                        }

                        tvOnCliced?.text =
                            removeZeroAfterDot((leftVlue.toDouble() * rightVlue.toDouble()).toString())
                    }
                } catch (e: ArithmeticException) {
                    e.printStackTrace()
                }
            }else{
                tvOnCliced?.text =""
            }
        }
    }

    private fun removeZeroAfterDot(reasult: String) : String {
        var value = reasult

        if (reasult.contains(".0")) {
                 value = reasult.substring(0, reasult.length - 2)

        } else if (reasult.contains(".") && !reasult.contains(".0")) {
//                rounding=reasult.toDouble()
                  value = reasult.toFloat().toString()//to limit the size of the reasult to 8 numbers
                  value  = value.toString()

        }

        return value

    }
}