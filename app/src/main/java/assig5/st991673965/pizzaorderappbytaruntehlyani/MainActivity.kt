//Name = Tarun Tehlyani
//Student Id = 991673965
package assig5.st991673965.pizzaorderappbytaruntehlyani

import android.os.Bundle
//import android.widget.CompoundButton.OnCheckedChangeListener
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
//import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import assig5.st991673965.pizzaorderappbytaruntehlyani.ui.theme.PizzaOrderAppByTarunTehlyaniTheme


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PizzaOrderAppByTarunTehlyaniTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {Text("My Pizza Order : Tarun Tehlyani/991673965")},
                            modifier = Modifier.fillMaxWidth(),
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                    ) { innerPadding ->
                    PizzaOrderPage(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun PizzaOrderPage(modifier: Modifier = Modifier)
{
    var pizzaSize by rememberSaveable { mutableStateOf("Small - $9") }
    var pizzaDelivery by rememberSaveable { mutableStateOf(false) }
    var context = LocalContext.current
    var meatCheckbox by rememberSaveable { mutableStateOf(false) }
    var cheeseCheckbox by rememberSaveable { mutableStateOf(false) }
    var veggiesCheckbox by rememberSaveable { mutableStateOf(false) }
    var result by rememberSaveable {  mutableStateOf("") }

    val total: Double = run {
        var price = when (pizzaSize)
        {
            "Small - $9" -> 9.0
            "Medium - $10" -> 10.0
            "Large - $11" -> 11.0
            else -> 0.0
        }
        if (meatCheckbox) price += 3.0
        if (cheeseCheckbox) price += 2.0
        if (veggiesCheckbox) price += 1.0
        if (pizzaDelivery) price += 5.0
        price
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        TitleAndImages()

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){
            PizzaRadioButtons( selectedOption = pizzaSize,
                               onOptionSelected = { pizzaSize = it})
            Column {
                Text("Select the Toppings: ", style = MaterialTheme.typography.bodyLarge)
                PizzaCheckbox(label = "Meat - $3", checked = meatCheckbox) { meatCheckbox = it }
                PizzaCheckbox(label = "Cheese - $2", checked = cheeseCheckbox) { cheeseCheckbox = it }
                PizzaCheckbox(label = "Veggies - $1", checked = veggiesCheckbox) { veggiesCheckbox = it }
            }
        }

        SwitchDelivery(isChecked = pizzaDelivery, onCheckedChange = {pizzaDelivery = it})
        Text("Total Price: $$total", style = MaterialTheme.typography.bodyLarge)

        Button(onClick = {
            result = buildString {
                append("Selected options:\n")
                if (meatCheckbox) append("Meat\n")
                if (cheeseCheckbox) append("Cheese\n")
                if (veggiesCheckbox) append("Veggies\n")
            }
            Toast.makeText(context, "Order Placed! Total: $$total", Toast.LENGTH_LONG).show()
        }) {
            Text("ORDER")
        }

        Button(onClick = {}) {
            Text("HELP")
        }
    }


}

@Composable
fun TitleAndImages()
{
    Spacer(modifier = Modifier.height(16.dp))
    Row{
        Image(
            painter = painterResource(id = R.drawable.pizzalogo),
            contentDescription = "Image of Pizza",
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(32.dp))
        Text("Pizza Order", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.width(32.dp))
        Image(
            painter = painterResource(id = R.drawable.pizzalogo),
            contentDescription = "Image of Pizza",
            modifier = Modifier.size(50.dp)
        )
    }

}

@Composable
fun PizzaCheckbox(label: String, checked:  Boolean, onCheckedChange: (Boolean) -> Unit)
{
    Row (verticalAlignment = Alignment.CenterVertically){
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Text(label, modifier = Modifier.padding(start = 8.dp))
    }
}

@Composable
fun PizzaRadioButtons(selectedOption: String, onOptionSelected: (String) -> Unit)
{
    val pizzaOptions = listOf("Small - $9", "Medium - $10", "Large - $11")
    Column(horizontalAlignment = Alignment.Start)
    {
        Text("Select the size:", style = MaterialTheme.typography.bodyLarge)
        pizzaOptions.forEach { text ->
            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.selectable(
                    selected = (text == selectedOption),
                    onClick = { onOptionSelected(text)}
                )
            ){
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = {onOptionSelected(text)}
                )
                Text(text, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}

@Composable
fun SwitchDelivery(isChecked: Boolean, onCheckedChange: (Boolean) -> Unit)
{
    Row(verticalAlignment = Alignment.CenterVertically)
    {
        Text("Delivery Required?")
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
    Text(text = if (isChecked) "Delivery is required (+$5)" else "Delivery not required")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PizzaOrderAppByTarunTehlyaniTheme {
        PizzaOrderPage()
    }
}